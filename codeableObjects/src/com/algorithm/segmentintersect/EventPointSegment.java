package com.algorithm.segmentintersect;
import java.util.ArrayList;
import java.util.List;

import com.math.CompPoint;


public class EventPointSegment {
	/**
	 * 
	 */
	private ArrayList<Segment> segments;
	private CompPoint point;
	private boolean isIntersection;
	
	public EventPointSegment() {
		segments = new ArrayList<Segment>();
		
		isIntersection = false;
	}

	public EventPointSegment(CompPoint pt) {
		this();
		this.point = pt;
		segments.clear();
	}
	
	public EventPointSegment(CompPoint pt, Segment seg){
		this();
		this.point = pt;
		segments.add(seg);
	}
	
	public CompPoint getPoint(){
		return point;
	}
	
	public Double getX(){
		return point.getX();
	}
	
	public Double getY(){
		return point.getY();
	}
	
	public void addSegment(Segment seg)
	{
		segments.add(seg);
	}
	
	public ArrayList<Segment> getSegments(){
		return segments;
	}
	
	public void setIsIntersection()
	{
		isIntersection = true;
	}
	
	// set boolean to True if this point is reported as an intersection
	public boolean isIntersection()
	{
		return isIntersection;
	}
	
	public void printEventPoint()
	{
		System.out.println("EventPoint : "+point);
		for(int i=0; i<segments.size(); i++)
		{
			segments.get(i).printSegment();
		}
	}

	public void addIntersectionSegment(Segment segment) {
		// TODO Auto-generated method stub
		
	}

}
