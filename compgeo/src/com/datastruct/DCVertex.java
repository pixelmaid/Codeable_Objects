package com.datastruct;

import com.math.CompPoint;

public class DCVertex implements Comparable<DCVertex> {
	public CompPoint coordinates;
	public DCHalfEdge incidentEdge = null;
	
	
	public DCVertex(CompPoint point){
		coordinates = point;
		
	}
	
	public void setEdge(DCHalfEdge edge){
		incidentEdge = edge;
	}


	public int compareTo(DCVertex o) {
		// TODO Auto-generated method stub
		return coordinates.compareTo(o.coordinates);
	}
	
}
