package com.primitive2d;

import java.util.Collections;
import java.util.Set;
import java.util.Vector;

import processing.core.PApplet;
import processing.core.PConstants;

import com.datatype.DCFace;
import com.datatype.DCHalfEdge;
import com.datatype.Point;
import com.math.Geom;

//base class for patterns and primitives
public class LineCollection extends DCFace implements Drawable, Turtle{
	
	private Vector<Line> lines;
	private Vector<Point> points; 
	private Vector<Polygon> polygons;
	private Vector<Ellipse> ellipses;
	
	public LineCollection(){
		this.lines = new Vector<Line>();
		this.points = new Vector<Point>(); 
		this.polygons = new Vector<Polygon>();
		this.ellipses = new Vector<Ellipse>();
		
		this.origin = new Point(0,0);

	}
	
	
	public LineCollection(Point origin, Vector<Point> points, Vector<Line> lines, Vector<Polygon> polygons,  Vector<Ellipse> ellipses){
		this.lines = lines;
		this.points = points; 
		this.polygons = polygons;
		this.ellipses = ellipses;
		
		this.origin = origin;

	}
	
	//sets a new origin for translations and rotations
	public void setOrigin(double x, double y){
	    	this.setOrigin(new Point(x,y));
	    }
	
	
	
	//=============================PRIMITIVE ADD METHODS==================================//
	
	//adds in a new point and automatically creates a new line if 1 or more points exist already
	public void addPoint(double x,double y){
		Point point = new Point(x,y);	
		this.addPoint(point);
	}
	
	public void addPoint(Point point){	
		points.add(point);
		//ensures there is never duplicate points in the list
		
	}
	
	//adds a line in cart mode by specifying 4 coordinates
	public void addLine(double startX, double startY, double endX, double endY) {
		Line line = new Line(startX,startY,endX,endY);
		lines.add(line);
			
	}
	
	//adds a line in polar mode by specifying an origin, radius and angle
	public void addLine(Point origin, float radius, float theta) {
		Line line = new Line(origin,radius, theta);
		this.lines.add(line);
	}
	
	//adds a line by passing in a line
	public void addLine(Line line) {
		this.lines.add(line);
		this.addPoint(line.start);
		this.addPoint(line.end);
	}
	
	//adds a line by passing in a line but does not add points to point list
		public void addLineWithoutPoints(Line line) {
			this.lines.add(line);
		}
	
	public void addPolygon(Polygon poly){
		this.polygons.add(poly);
		lines.addAll(poly.getAllLines());
		points.addAll(poly.getAllPoints());

	}
	
	public void addEllipse(Ellipse ellipse){
		this.ellipses.add(ellipse);
	}
	
	//=============================PRIMITIVE REMOVE METHODS==================================//
	
	public void removeDuplicatePoints(){
		points = Geom.removeDuplicateVerts(points);
	}
	
	public void removePoint(double x,double y){
		Point point = new Point(x,y);	
		this.removePoint(point);
	}
	
	public void removePoint(int index){	
		points.remove(index);
	}
	
	public void removePoint(Point point){	
		points.remove(point);
	}
	
	public void removeLine(Line line) {
		lines.remove(line);	
		this.removePoint(line.start);
		this.removePoint(line.end);
	}
	
	public void removeLine(int index) {
		Line line = this.lines.get(index);
		this.removeLine(line);
	}
	
	public void removePolygon(Polygon poly){
		this.polygons.remove(poly);
		lines.removeAll(poly.getAllLines());
		points.removeAll(poly.getAllPoints());
		
	}
	
	public void removePolygon(int index){
		Polygon poly = polygons.get(index);
		this.removePolygon(poly);
		
	}
	
	public void removeEllipse(Ellipse ellipse){
		this.ellipses.remove(ellipse);
	}
	
	public void removeEllipse(int index){
		Ellipse ellipse = ellipses.get(index);
		this.removeEllipse(ellipse);
		
	}
	
	
	
	//=============================PRIMITIVE SET METHODS==================================//
	
	public void setAllPoints(Vector<Point> newPoints){
		this.points = newPoints ;
	}
	
	public void setPointAt(int index, Point newPoint){
		this.points.set(index, newPoint);
	}


	public void setAllLines(Vector<Line> newLines){
		
		
		for(int i=0;i<lines.size();i++){
			points.remove(lines.get(i).start);
			points.remove(lines.get(i).end);

		}
		this.lines = newLines ;
		
		for(int i=0;i<lines.size();i++){
			this.addPoint(lines.get(i).start);
			this.addPoint(lines.get(i).end);
		}
	}
	
	public void setLineAt(int index, Line newLine){
		points.remove(lines.get(index).start);
		points.remove(lines.get(index).end);
		
		this.lines.set(index, newLine);
		
		this.addPoint(lines.get(index).start);
		this.addPoint(lines.get(index).end);
	}
	
	public void setAllPolygons(Vector<Polygon> newPolygons){
		for(int i=0;i<polygons.size();i++){
			lines.removeAll(polygons.get(i).getAllLines());
			points.removeAll(polygons.get(i).getAllPoints());
		}
		this.polygons = newPolygons ;
		
		for(int i=0;i<polygons.size();i++){
			lines.addAll(polygons.get(i).getAllLines());
			points.addAll(polygons.get(i).getAllPoints());
		}
		

	}
	
	public void setPolygonAt(int index, Polygon newPolygon){
		
		lines.removeAll(polygons.get(index).getAllLines());
		points.removeAll(polygons.get(index).getAllPoints());
		
		this.polygons.set(index, newPolygon);
		
		lines.addAll(polygons.get(index).getAllLines());
		points.addAll(polygons.get(index).getAllPoints());
		
		
	}
	
	

	
	//=============================PRIMITIVE GET METHODS==================================//
	
	public Vector<Point> getAllPoints(){
		return this.points;
	}

	public Point getPointAt(int index){
		return this.points.get(index);
	}
	
	public Vector<Line> getAllLines(){
		return this.lines;
	}

	public Line getLineAt(int index){
		return this.lines.get(index);
	}
	
	public Vector<Polygon> getAllPolygons(){
		return this.polygons;
	}

	public Polygon getPolygonAt(int index){
		return this.polygons.get(index);
	}
	
	public Vector<Ellipse> getAllEllipses(){
		return this.ellipses;
	}

	public Ellipse getEllipseAt(int index){
		return this.ellipses.get(index);
	}
	
	//=============================TRANFORM METHODS==================================//
	
	 //translates all points;
    public void moveTo(double x, double y) {
    	//this.removeDuplicatePoints();
        for (int i = 0; i < points.size(); i++) {
           Point currentPoint = points.get(i);
           currentPoint.moveTo(x, y,this.origin);
        }
        this.origin = new Point(x,y);
        
    }
    
    //translates all lines to a new point;
    @Override
    public void moveTo(double x, double y, Point focus) {
    	//this.removeDuplicatePoints();
    	for (int i = 0; i < points.size(); i++) {
             Point currentPoint = points.get(i);
             currentPoint.moveTo(x, y,focus);
          }
    }
    
	@Override
    public void moveBy(double x, double y) {
		//this.removeDuplicatePoints();
		 for (int i = 0; i < points.size(); i++) {
			  Point currentPoint = points.get(i);
	            currentPoint.moveBy(x, y);
	        }
        
    }
	
	@Override
	  //rotates all lines around the origin by an increment of theta;
    public void rotate(double theta) {
        this.rotate(theta,origin);
    }
	
	
	//rotates all lines around the focus by an increment of theta;
    public void rotate(double theta, Point _focus) {
    	//this.removeDuplicatePoints();
        for (int i = 0; i < points.size(); i++) {
			Point currentPoint = points.get(i);

            currentPoint.rotate(theta, _focus);
        }
    }
    
    public void scale(double scaleVal){
    	//this.removeDuplicatePoints();
        for (int i = 0; i < points.size(); i++) {
        	Point currentPoint = points.get(i);
        	currentPoint.scale(scaleVal);
        }
       
    }
	
    
   
  //=============================DRAW AND PRINT METHODS==================================//
    
    public void draw(PApplet parent, float strokeWeight){
    	for(int i=0;i<points.size();i++){
    		points.get(i).draw(parent, strokeWeight);
    		
    		
    	}
    	
    	for(int i=0;i<lines.size();i++){
    		lines.get(i).draw(parent, strokeWeight);
    		
    		
    	}
		
		
		for(int i=0;i<polygons.size();i++){
    		polygons.get(i).draw(parent, strokeWeight);
    		
    		
    	}
		
		for(int i=0;i<ellipses.size();i++){
    		ellipses.get(i).draw(parent, strokeWeight);
    		
    		
    	}
	}
	
	public void print(PApplet parent, float strokeWeight, String filename){
		parent.beginRaw(PConstants.PDF, filename);
		this.draw(parent, strokeWeight);
		parent.endRaw();
		//TODO:implement print method

	}
	
	//returns a duplicate but separate copy of the line collection
	public LineCollection copy(){
		Vector<Line>lines = new Vector<Line>();
		Vector<Point>points = new Vector<Point>(); 
		Vector<Polygon>polygons = new Vector<Polygon>();
		Vector<Ellipse>ellipses = new Vector<Ellipse>();
		Point newOrigin = this.origin.copy();
		
		for(int i=0;i<this.polygons.size();i++){
			Vector<Line> oldPolygonLines = getPolygonAt(i).getAllLines();
			Polygon polygon = new Polygon();
			
			for(int j=0;j<oldPolygonLines.size();j++){
				polygon.addLine(oldPolygonLines.get(j).copy());
			}
			polygons.add(polygon);
		}
		
		for(int i=0;i<this.lines.size();i++){
			Line line = this.lines.get(i).copy();
			lines.add(line);
			//points.add(line.start);
			//points.add(line.end);
		}
		
		for(int i=0;i<this.points.size();i++){
			Point point = points.get(i).copy();
			points.add(point);
		}
		
		for(int i=0;i<this.ellipses.size();i++){
			Ellipse ellipse = this.ellipses.get(i).copy();
			ellipses.add(ellipse);
		}
		
		LineCollection newLineCollection =  new LineCollection(newOrigin, points, lines, polygons, ellipses);
		
		//newLineCollection.reLinkLines();
		
		return newLineCollection;
	}

	
	public void reLinkLines(){
		for(int i=0;i<lines.size(); i++){
			for(int j=0;j<lines.size(); j++){
				if(lines.get(j).start==lines.get(i).end){
					lines.get(j).start = lines.get(i).end;
				}
			}
		}
	}
	
	
 //=============================TURTLE METHODS==================================//

	@Override
	public void left(double angle) {
		TurtleStruct.angle-=angle;
		/*if(TurtleStruct.angle<0){
			TurtleStruct.angle = 360;
		}*/
		
	}

	@Override
	public void right(double angle) {
		// TODO Auto-generated method stub
		TurtleStruct.angle+=angle;
		/*if(TurtleStruct.angle>360){
			TurtleStruct.angle = 0;
		}*/
	}

	@Override
	public void forward(double dist) {
		Line newLine = new Line(TurtleStruct.location.copy(), dist, TurtleStruct.angle);
		if(TurtleStruct.pen){
			
			this.addLine(newLine);
		}
		TurtleStruct.location = newLine.end;
		
	}
	
	

	@Override
	public void back(double dist) {
		Line newLine = new Line(TurtleStruct.location.copy(), -dist, TurtleStruct.angle);
		if(TurtleStruct.pen){
			
			this.addLine(newLine);
		}
		TurtleStruct.location = newLine.end;
		
	}

	@Override
	public void penUp() {
		TurtleStruct.pen=false;
		// TODO Auto-generated method stub
		
	}

	@Override
	public void penDown() {
		TurtleStruct.pen=true;
		// TODO Auto-generated method stub
		
	}
	
	

	
}

