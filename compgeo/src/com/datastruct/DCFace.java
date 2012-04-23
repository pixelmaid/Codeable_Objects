package com.datastruct;

import java.util.Vector;

public class DCFace implements Comparable <DCFace> {
	
	public Vector<DCHalfEdge> outerComponents;
	public Vector<DCHalfEdge> innerComponents;
	
	public DCFace(){
		outerComponents = new Vector<DCHalfEdge>(0);
		innerComponents = new Vector<DCHalfEdge>(0);
		
	}
	
	public void addOuterComponent(DCHalfEdge edge){
		outerComponents.addElement(edge);
		
	}
	
	public void addInnerComponent(DCHalfEdge edge){
		innerComponents.addElement(edge);
		
	}

	
	public int compareTo(DCFace f) {
		return this.outerComponents.get(0).compareTo(f.outerComponents.get(0));
	}

}
