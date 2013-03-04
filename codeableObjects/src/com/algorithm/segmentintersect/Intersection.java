package com.algorithm.segmentintersect;


import java.awt.geom.Point2D;
import java.util.ArrayList;

import com.math.CompPoint;


@SuppressWarnings("serial")
public class Intersection extends CompPoint {
	
	private float radius;
	protected float RADIUS = 10F;
	
	private ArrayList<Segment> segments;

	
	
	public Intersection(double x, double y) {
		super(x,y);
		
		
		segments = new ArrayList<Segment>();
	}
	

	public float getRadius() {
		return radius;
	}

	
	public String toString()
	{
		return "Intersection : {"+this.getX()+", "+this.getY()+"}";
	}
	
	public void printIntersection()
	{
		System.out.println("Intersection : "+this.getX()+" ; "+this.getY());
	}	
	
	public void setSegments(ArrayList<Segment> list)
	{
		this.segments = list;
	}
	
	public ArrayList<Segment> getSegments()
	{
		return this.segments;
	}
	
	public CompPoint getPoint(){
		CompPoint res = null;
		res = new CompPoint((double) this.getX(), (double) this.getY());
		
		
		return res;
	}
}
