package com.primitive2d;

import com.datatype.Point;

import com.math.Geom;
import com.ornament.Pattern;

public class Spiral extends Pattern {

	public Spiral(double incrementTheta, double finalRadius, double startRadius, int resolution, boolean addToScreen){
		super();
		
		double theta = 0;
		double r=startRadius;
		double increment = (finalRadius-startRadius)/resolution;
		for (int i=0;i<resolution;i++){
			theta+=incrementTheta;
			
			r=increment*i;
			Point point = Geom.polarToCart(r, theta);
			this.addPoint(point.getX(),point.getY());
			
		}
		
	}
	
	@Override
	public void addPoint(double x,double y){
		Point point = new Point(x,y);
		
		int numLines = this.getAllLines().size();
		int numPoints = this.getAllPoints().size();
		if(numPoints == 0){
			this.addPoint(point);
		}
		else if(numLines==0){
			Line line = new Line(this.getPointAt(0),point);
			this.addLineWithoutPoints(line);
			this.addPoint(point);
		}
		if(numLines>0){
			Point start = this.getLineAt(numLines-1).end;
			Line line = new Line(start.copy(),point.copy());
			this.addLine(line);
		}
		
		
		
	}
}
