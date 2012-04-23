package com.datastruct;

import com.math.CompPoint;

import java.util.Vector;

public class DoublyConnectedEdgeList {

	public Vector<DCFace> faces = new Vector<DCFace>();
	public Vector<DCHalfEdge> edges = new Vector<DCHalfEdge>();
	public Vector<CompPoint> verticies = new Vector<CompPoint>();
	
	public DoublyConnectedEdgeList(){
		
	}
	
	
	public void addVertex(CompPoint p){
		
		verticies.addElement(p);
	
	}
	
	public DCHalfEdge addHalfEdge(DCHalfEdge newEdge){
	
		edges.addElement(newEdge);
		return newEdge;
	}
	
	
	public void addHalfEdgeAt(DCHalfEdge newEdge, int addPosition){
		
		//edges.addElementAt(newEdge);
		//return newEdge;
	}
	
	
	public void addFace(){
		
	}
	
	public boolean deleteEdge(DCHalfEdge edge){
		return edges.removeElement(edge);
		
	}
	
	public double[] getBorderPoints(CompPoint start){
		double[] thetas = new double[edges.size()];
		 
		
		for(int i=0;i<edges.size();i++){
			CompPoint point = edges.get(i).start;
			double theta = start.angle(point);
			thetas[i]=theta;
			
		}
		return thetas;
	}
	
	
	
}
	
	

