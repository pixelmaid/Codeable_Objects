package com.primitive2d;

import processing.core.PApplet;

import com.datatype.Point;
import com.datatype.DCHalfEdge;

public class Line extends DCHalfEdge implements Drawable {


	public Line(Point start, Point end) {
		super(start,end);
	}
	
	public Line(double startX, double startY, double endX, double endY) {
		super(new Point(startX,startY),new Point(endX,endY));
	}
	
	public Line(Point origin, double radius, double theta) {
		super(origin,radius, theta);
	}
	
	public void draw(PApplet parent, float strokeWeight){
		parent.strokeWeight(strokeWeight);
		parent.line((float)start.getX(),(float)start.getY(), (float)end.getX(), (float)end.getY());
	}
	
	public void print(PApplet parent){
		//TODO:implement print method
	}
	
	public void moveTo(double x, double y) {
       moveTo(x,y,this.start);
        
    }

}
