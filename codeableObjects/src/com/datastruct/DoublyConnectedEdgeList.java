/*
 * Codeable Objects by Jennifer Jacobs is licensed under a Creative Commons Attribution-ShareAlike 3.0 Unported License.
 * Based on a work at hero-worship.com/portfolio/codeable-objects.
 *
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
import java.util.Random;
import java.util.Vector;

import processing.core.PApplet;

import com.math.Geom;

public class DoublyConnectedEdgeList {

    public Vector<DCFace> faces = new Vector<DCFace>();
    public Vector<DCHalfEdge> edges = new Vector<DCHalfEdge>();
    public Vector<CompPoint> verticies = new Vector<CompPoint>();
    public CompPoint centroid;

    public DoublyConnectedEdgeList() {

    }


    public void addVertex(CompPoint p) {

        verticies.addElement(p);

    }
    
    public DCHalfEdge addHalfEdge(DCHalfEdge newEdge) {

        edges.addElement(newEdge);
       
        return newEdge;
    }

    public DCHalfEdge addEdgeWithPartner(DCHalfEdge newEdge) {
    
       
        if(edges.size()>0){
        	setPartners(newEdge,edges.get(edges.size()-1));
        }
        edges.addElement(newEdge);
        return newEdge;
    }


    public void addEdgeAt(DCHalfEdge newEdge, int addPosition) {

        edges.add(addPosition,newEdge);
        
    }


    public void addFace(DCFace face) {
    	 
    	faces.add(face);
    }
    
    public DCFace getFaceByFocus(CompPoint focus) {
   	 
    	for (int i = 0; i < faces.size(); i++) {
    		DCFace checkFace = faces.get(i);
    		if(checkFace.getFocus().compareTo(focus)==0){
    			return checkFace;
    		}
    		
    	}
    	return null;
    }
    
    public void drawFaces(PApplet parent){
    	for (int i = 0; i < faces.size(); i++) {
    		DCFace checkFace = faces.get(i);
    		checkFace.draw(parent);
    	}
    }

    public boolean deleteEdge(DCHalfEdge edge) {
        return edges.removeElement(edge);

    }

    public double[] getBorderPoints(CompPoint start) {
        double[] thetas = new double[edges.size()];


        for (int i = 0; i < edges.size(); i++) {
            CompPoint point = edges.get(i).start;
            double theta = start.angle(point);
            thetas[i] = theta;

        }
        return thetas;
    }
    
    //translates all edges to a new point;
    public void translate(double x, double y, CompPoint focus) {
        for (int i = 0; i < edges.size(); i++) {
            DCHalfEdge currentEdge = edges.get(i);
            currentEdge.translate(x, y, focus);
        }
        
    }

    //rotates all edges around the focus by an increment of theta;
    public void rotate(double theta, CompPoint _focus) {
        for (int i = 0; i < edges.size(); i++) {
             DCHalfEdge currentEdge = edges.get(i);
            currentEdge.rotate(theta, _focus);


            //System.out.println("startX="+start.getX()+" start y="+start.getY()+" r="+startR+" theta="+startTheta+" new theta="+newStartTheta+"  newX="+(newStart.getX()+_focus.getX())+" newY="+(newStart.getY()+_focus.getY()));

        }
    }
    
    
    private void setPartners(DCHalfEdge currentEdge, DCHalfEdge prevEdge){ //sets two edges as partners
    	int color =  new Random().nextInt()*0xFFFFFF;
		 
		 currentEdge.setPartnerEdge("start", prevEdge,prevEdge.end, color);
		 

		 prevEdge.setPartnerEdge("end", currentEdge, currentEdge.start, color);
    
    }
    public void completePartners(){ //partners the first and last edges of the edge list
    	DCHalfEdge prevEdge = edges.get(edges.size()-1);
    	DCHalfEdge currentEdge = edges.get(0);
    	setPartners(currentEdge,prevEdge);
   	
    }
    private void findPartners(DCHalfEdge edge){
    	 for (int i = 0; i < edges.size(); i++) {
    		 
    		 if(edges.get(i)!=edge){
    			 
    			 CompPoint start = edges.get(i).start;
    			 CompPoint end = edges.get(i).end;
            
    			 if(start.compareTo(edge.start)==0){
    				 int color =  new Random().nextInt()*0xFFFFFF;
    				 System.out.println(color);
    				 edge.setPartnerEdge("start", edges.get(i),start, color);
    				 edges.get(i).setPartnerEdge("start", edge,edge.start, color);
    			 }
             
    			 if(end.compareTo(edge.end)==0){
    				 int color =  new Random().nextInt()*0xFFFFFF;
             
    				 edge.setPartnerEdge("end", edges.get(i),end, color);
    				 edges.get(i).setPartnerEdge("end", edge,edge.end, color);
    			 }
    			 
    			 if(end.compareTo(edge.start)==0){
    				 int color =  new Random().nextInt()*0xFFFFFF;
             
    				 edge.setPartnerEdge("start", edges.get(i),end, color);
    				 edges.get(i).setPartnerEdge("end", edge,edge.start, color);

    			 }
             
    			 if(start.compareTo(edge.end)==0){
    				 int color =  new Random().nextInt()*0xFFFFFF;
             
    				 edge.setPartnerEdge("end", edges.get(i),start, color);
    				 edges.get(i).setPartnerEdge("start", edge,edge.end, color);

    			 }
    	 	}
    	 }
    }
    

}
	
	

