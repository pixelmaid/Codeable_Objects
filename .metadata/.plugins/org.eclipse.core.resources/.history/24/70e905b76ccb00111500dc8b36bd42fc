package com.ornament;

import java.util.Vector;

import com.datatype.Point;
import com.primitive2d.Ellipse;
import com.primitive2d.Line;
import com.primitive2d.Polygon;

public class Tree extends Pattern{
	private double limit = 2;//minimum length of a branch before the fractal terminates
	private double growthRate= 0.66;//fraction by which the branch is decreased each recursion
	private float startAngle = 270;//starting angle of the tree
	private float angleChange = 2;//starting angle of the tree
	private float startingHeight = 200;//starting height of the tree

	private Point origin = new Point(0,0);//starting angle of the tree
	
	public Tree(){
		super();
		
	}
	
	public Tree(Point origin, Vector<Point> points, Vector<Line> lines, Vector<Polygon> polygons,  Vector<Ellipse> ellipses){
		super(origin, points, lines, polygons, ellipses);

	}
	
	public void setLimit(double limit){ //sets the limit
		this.limit = limit;
	}
	
	public void setGrowthRate(double growthRate){//sets the growth rate
		
		this.growthRate = growthRate;
		if(this.growthRate>0.75){
			this.growthRate=0.75;
		}
	}
	
	public void setStartAngle(float startAngle){//sets the starting angle
		this.startAngle = startAngle;
	}
	
	public void setAngleChange(float angleChange){//sets the angle change
		this.angleChange = angleChange;
	}
	

	public void setStartingHeight(float height){//sets the starting height
		this.startingHeight = height;
	}
	
	public void generate(){
		Line line = new Line(origin.copy(),startingHeight,startAngle);//create the trunk of the tree
		this.addLine(line);// store the line
		this.branch(startingHeight,line.end,startAngle); //begin the branch function
		
	}
	
	private void branch(float height, Point origin, float angle){ //recursive branch function
		height*= growthRate; //modify the height according to the growth rate
		
		//exit function
		if(height >limit){ //as long as the branches are greater than the limit
			float rightTheta = angle + angleChange; //increment the angle by the angle change
			Line rightLine = new Line(origin.copy(),height,rightTheta); //create a "right branching" line
			this.addLine(rightLine);//store the line
			this.branch(height,rightLine.end,rightTheta); //recurse
			
			float leftTheta = angle - angleChange;//decrement the angle by the angle change (to branch left)
			Line leftLine = new Line(origin.copy(),height,leftTheta); //create a "left branching line"
			this.addLine(leftLine);//store the line
			this.branch(height,leftLine.end,leftTheta); //recurse
			
		}
	}
	
	
	public Tree copy(){
		Vector<Line>lines = new Vector<Line>();
		Vector<Point>points = new Vector<Point>(); 
		Vector<Polygon>polygons = new Vector<Polygon>();
		Vector<Ellipse>ellipses = new Vector<Ellipse>();
		Point newOrigin = this.origin.copy();
		
		
		for(int i=0;i<this.getAllLines().size();i++){
			Line line = this.getAllLines().get(i).copy();
			lines.add(line);
			points.add(line.start);
			points.add(line.end);
		}
		
		for(int i=0;i<this.getAllPoints().size();i++){
			Point point = points.get(i).copy();
			points.add(point);
		}
		
		
		
		Tree newTree =  new Tree(newOrigin, points, lines,polygons,ellipses);
		
		
		return newTree;
		
	}
}
