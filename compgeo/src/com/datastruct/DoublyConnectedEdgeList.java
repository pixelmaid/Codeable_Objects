/*
 * This file is part of the Codeable Objects Framework.
 *
 *     Codeable Objects is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Codeable Objects is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Codeable Objects.  If not, see <http://www.gnu.org/licenses/>.
 */

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
	
	

