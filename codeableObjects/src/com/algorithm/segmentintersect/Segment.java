package com.algorithm.segmentintersect;



import java.util.ArrayList;

import processing.core.PApplet;

import com.datastruct.DCHalfEdge;
import com.math.CompPoint;



/**
 * <p>
 * <b>Segment</b>
 * </p>
 * <p>
 * 
 * </p>
 * @author Depoyant Guillaume & Ludmann Micha�l
 *
 */
public class Segment implements Key{
	
	private java.awt.Polygon segment;
	private double[] xpoints;
	private double[] ypoints;
	private CompPoint upperEndpoint;
	private CompPoint lowerEndpoint;
	private CompPoint leftEndpoint;
	private CompPoint rightEndpoint;
	private double slope;
	private double originOrdinate;
    public static final double MAX_SLOPE = 99999999999999.906;
    public static final double MIN_SLOPE = 1;
    public static final double BIG = 1e+15;
	public static final double SMALL = 1e-15;
    private double value;
    private RedBlackNode node;
	private int id;
	private int set;
	private int edge;
	private boolean isVertical = false;
	private boolean isHorizontal = false;
	protected double ERROR_ALLOWED = 1.0001E-002F;
	private ArrayList<CompPoint> split;
	private ArrayList<com.algorithm.segmentintersect.Intersection> intersections;
	

	public Segment() {
		segment = new java.awt.Polygon();
		xpoints = null;
		ypoints = null;
		
		value = -1;
		id = -1;
		set = -1;
		
		setSplit(new ArrayList<CompPoint>());
		intersections = new ArrayList<com.algorithm.segmentintersect.Intersection>();
	}
	
	public Segment(double[] xpoints, double[] ypoints, int npoints) {
		this();
		setXpoints(xpoints);
		setYpoints(ypoints);

		// Here we need to register the vertices as integer for the drawing part
		int[] xpointsInt = new int[npoints], ypointsInt = new int[npoints];
		for(int i=0; i < npoints; i++)
		{
			xpointsInt[i] = Float.floatToIntBits((float) xpoints[i]);
			ypointsInt[i] = Float.floatToIntBits((float) ypoints[i]);

		}
		segment = new java.awt.Polygon(xpointsInt, ypointsInt, npoints);
		computeEndpoints();
	}
	
	public Segment(ArrayList<double[]> points) {
		this();
		setSegment(points);
	}
	
	public Segment(DCHalfEdge edge){
		this(edge.start,edge.end);
	}
	
	public Segment(double x1, double y1,double x2, double y2) {
		this();
		ArrayList<double[]> points = new ArrayList<double[]>();
		double[] point1 = new double[2];
		point1[0]=x1;
		point1[1]=y1;
		double[] point2 = new double[2];
		point2[0]=x2;
		point2[1]=y2;
		points.add(point1);
		points.add(point2);
		setSegment(points);
	}
	
	public Segment(CompPoint a, CompPoint b) {
		this();
		ArrayList<double[]> points = new ArrayList<double[]>();
		double[] point1 = new double[2];
		point1[0]=a.getX();
		point1[1]=a.getY();
		double[] point2 = new double[2];
		point2[0]=b.getX();
		point2[1]=b.getY();
		points.add(point1);
		points.add(point2);
		setSegment(points);
	}
	
	public Segment(ArrayList<double[]> points, int id) {
		this();
		this.id = id;
		setSegment(points);
	}
	
	public void setSegment(double[] xpoints, double[] ypoints, int npoints) {
		segment.reset();
		setXpoints(null);
		setYpoints(null);
		for (int i = 0; i < npoints; i++)
		{
			segment.addPoint((int) xpoints[i], (int) ypoints[i]);
		}
		setXpoints(xpoints);
		setYpoints(ypoints);
		computeEndpoints();
	}
	
	public void setSegment(ArrayList<double[]> points) {
		int npoints = points.size();
		double[] xpoints = new double[npoints];
		double[] ypoints = new double[npoints];
		for (int i = 0; i < npoints; i++)
		{
			double[] point = points.get(i);
			if (point.length == 2)
			{
				xpoints[i] = point[0];
				ypoints[i] = point[1];
			}
		}
		setSegment(xpoints, ypoints, npoints);
	}
	
	
	public double[] getXpoints() {
		return this.xpoints;
	}
	
	public double[] getYpoints() {
		return this.ypoints;
	}

		
	public java.awt.Polygon getShape() {
		return segment;
	}

	public void setXpoints(double[] xpoints) {
		this.xpoints = xpoints;
	}

	public void setYpoints(double[] ypoints) {
		this.ypoints = ypoints;
	}
	
	public void computeEndpoints() {
		if(xpoints.length == 2 && ypoints.length == 2)
		{
			//System.out.println(xpoints[0]+","+xpoints[1]+","+ypoints[0]+","+ypoints[1]);
			if((ypoints[0] > ypoints[1]) || (ypoints[0] == ypoints[1] && xpoints[0] <= xpoints[1])){
				upperEndpoint = new CompPoint(xpoints[0], ypoints[0]);
				lowerEndpoint= new CompPoint(xpoints[1], ypoints[1]);
			} else {
				upperEndpoint= new CompPoint(xpoints[1], ypoints[1]);
				lowerEndpoint= new CompPoint(xpoints[0], ypoints[0]);
			}
			if((xpoints[0] > xpoints[1])){
				rightEndpoint = new CompPoint(xpoints[0], ypoints[0]);
				leftEndpoint= new CompPoint(xpoints[1], ypoints[1]);
			} else {
				rightEndpoint= new CompPoint(xpoints[1], ypoints[1]);
				leftEndpoint= new CompPoint(xpoints[0], ypoints[0]);
			}
			value = leftEndpoint.getX();

			setSlope();
			setOriginOrdinate();
		}	
	}
	
	public void addIntersection(com.algorithm.segmentintersect.Intersection intersection){
		this.intersections.add(intersection);
	}
	
	public ArrayList<com.algorithm.segmentintersect.Intersection> getIntersections(){
		return intersections;
	}

	public CompPoint getLowerEndpoint(){
		return this.lowerEndpoint;
	}
	
	public CompPoint getUpperEndpoint(){
		return this.upperEndpoint;
	}
	
	public CompPoint getLeftEndpoint(){
		return this.leftEndpoint;
	}
	
	public CompPoint getRightEndpoint(){
		return this.rightEndpoint;
	}
	public void printSegment()
	{
		System.out.println("Segment "+id+" : upperEndPoint = "+upperEndpoint+" lowerEndPoint = "+lowerEndpoint);
	}
	
	public String toString()
	{
		return "Segment "+id+" : [{"+upperEndpoint.getX()+", "+upperEndpoint.getY()+"} ; {"+ lowerEndpoint.getX()+", "+lowerEndpoint.getY()+"}]";
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public void setSet(int set)
	{
		this.set = set;
		
	}
	
	public int getSet()
	{
		return this.set;
	}
	
	public void setEdge(int edge)
	{
		this.edge = edge;
		
	}
	
	public int getEdge()
	{
		return this.edge;
	}
	
	public void setSlope()
	{
		if (xpoints[1]-xpoints[0] == 0)
		{
			slope = MAX_SLOPE;
			isVertical  = true;
			
		} else {
			slope = (ypoints[1]-ypoints[0])/(xpoints[1]-xpoints[0]);
			if (slope == 0)
			{
				isHorizontal = true;
				setValue(Float.MIN_VALUE);
			}
		}
	}
	
	public boolean isVertical()
	{
		return isVertical;
	}
	
	public boolean isHorizontal()
	{
		return isHorizontal;
	}
	
	public void setOriginOrdinate()
	{
		originOrdinate = ypoints[0] - ((ypoints[1]-ypoints[0])/(xpoints[1]-xpoints[0]))* xpoints[0];
	}
	
	public double getSlope()
	{
		return slope;
	}
	
	public boolean isInBoundingBox(CompPoint p)
	{
		boolean c1 = ((p.getX() >= xpoints[0] || p.getX() >= xpoints[1]) && (p.getY() >= ypoints[0] || p.getY() >= ypoints[1]));
		boolean c2 = ((p.getX() <= xpoints[0] || p.getX() <= xpoints[1]) && (p.getY() <= ypoints[0] || p.getY() <= ypoints[1]));
		return c1 && c2;
	}
	
	public boolean containsPoint(CompPoint p)
	{
		if (p.equals(lowerEndpoint) || p.equals(upperEndpoint) || (isVertical() && Math.abs(p.getX() - lowerEndpoint.getX()) <= ERROR_ALLOWED))
		{
			return true;
		}
		return (Math.abs(((p.getY()-ypoints[0])/(p.getX()-xpoints[0]) - getSlope())) <= ERROR_ALLOWED) && isInBoundingBox(p);	
	}
	
	// return 1 if p is on the left side of the segment (0 = on ; -1 = right)
	public int isLeft(CompPoint p)
	{
		double res = (lowerEndpoint.getX() - p.getX())*(upperEndpoint.getY() - p.getY()) - (upperEndpoint.getX() - p.getX())*(lowerEndpoint.getY() - p.getY());
		if (res > 0)
		{
			return 1;
		} else {
			if (res == 0)
			{
				return 0;
			} else {
				return -1;
			}
		}
	}

	
	public double getCurrentAbscisse(float Y)
	{
		return (Y-originOrdinate)/slope;
	}
	
	public void updateValue(double newY)
	{
		if(!isHorizontal())
		{
			value = (newY-originOrdinate)/slope;
		} else {
			value = newY;
		}
	}
	
	public boolean equals(Object obj)
    {
        if(obj instanceof Segment)
        {
            return equals((Key)obj);
        } else
        {
            System.out.println("Bad Object equals");
            return false;
        }
    }
	
	@Override
	public boolean equals(Key key) {
        return this == (Segment)key;
	}

	@Override
	public String getLabel() {
        return "S" + value;
	}

	@Override
	public RedBlackNode getNode() {
		return node;
	}

	@Override
	public double getValue() {
		return value;
	}

	@Override
	public boolean largerThan(Object obj) {
     if(obj instanceof Segment)
        {
            return largerThan((Key)obj);
        } else
        {
            System.out.println("Bad Object larger than");
            return false;
        }
	}

	@Override
	public boolean largerThan(Key key) {
        double d = key.getValue();
        double d1 = getValue();
        return d1 < d;
	}

	@Override
	public boolean lessThan(Object obj) {
		if(obj instanceof Segment)
        {
            return lessThan((Key)obj);
        } else
        {
            System.out.println("Bad Object less than");
            return false;
        }
	}

	@Override
	public boolean lessThan(Key key) {
		double d = key.getValue();
        double d1 = getValue();
        return d1 > d;
	}

	@Override
	public void setNode(RedBlackNode redblacknode) {
		node = redblacknode;
	}

	@Override
	public void setValue(double d) {
			value = d;
	}

	@Override
	public void swapValue(Key key) {
		double d = value;
        setValue(key.getValue());
        key.setValue(d);		
	}

	public void setSplit(ArrayList<CompPoint> split) {
		this.split = split;
	}

	public ArrayList<CompPoint> getSplit() {
		return split;
	}

	public void initSplitSegment(){
		this.split.clear();
		
		if(this.isHorizontal){
			this.split.add(this.rightEndpoint);
			this.split.add(this.leftEndpoint);
		}
		else{
			this.split.add(this.lowerEndpoint);
			this.split.add(this.upperEndpoint);
		}
	}
	
	public void addSplit(CompPoint p){

		int nbPoints = this.split.size();

		for(int i=0; i< (nbPoints-1); i++){
			if ( split.get(i).distance(p) < split.get(i).distance(split.get(i+1))
					&& split.get(i+1).distance(p) < split.get(i).distance(split.get(i+1)) ){
				this.split.add(i+1, p);
			}
		}
	}
	
	public void draw(PApplet parent){
		
		
		parent.line((float)xpoints[0],(float)ypoints[0], (float)xpoints[1], (float)ypoints[1]);
		
	
	}

}
