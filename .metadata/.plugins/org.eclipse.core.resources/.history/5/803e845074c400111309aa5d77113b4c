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

package com.datatype;

import java.util.Random;
import java.util.Vector;

import processing.core.PApplet;

import com.math.Geom;

public class DoublyConnectedEdgeList {

    public Vector<DCFace> faces = new Vector<DCFace>();
    public Vector<DCHalfEdge> edges = new Vector<DCHalfEdge>();
    public Vector<Point> verticies = new Vector<Point>();
    public Point centroid;

    public DoublyConnectedEdgeList() {

    }


    public void addVertex(Point p) {

        verticies.addElement(p);

    }
    
    public DCHalfEdge addHalfEdge(DCHalfEdge newEdge) {

        edges.addElement(newEdge);
       
        return newEdge;
    }

    public void addEdgeAt(DCHalfEdge newEdge, int addPosition) {

        edges.add(addPosition,newEdge);
        
    }


    public void addFace(DCFace face) {
    	 
    	faces.add(face);
    }
    
    public DCFace getFaceByFocus(Point focus) {
   	 
    	for (int i = 0; i < faces.size(); i++) {
    		DCFace checkFace = faces.get(i);
    		if(checkFace.getOrigin().compareTo(focus)==0){
    			return checkFace;
    		}
    		
    	}
    	return null;
    }
    
   
    public boolean deleteEdge(DCHalfEdge edge) {
        return edges.removeElement(edge);

    }

    public double[] getBorderPoints(Point start) {
        double[] thetas = new double[edges.size()];


        for (int i = 0; i < edges.size(); i++) {
            Point point = edges.get(i).start;
            double theta = start.angle(point);
            thetas[i] = theta;

        }
        return thetas;
    }
    
    //translates all edges to a new point;
    public void moveTo(double x, double y, Point focus) {
        for (int i = 0; i < edges.size(); i++) {
            DCHalfEdge currentEdge = edges.get(i);
            currentEdge.moveTo(x, y, focus);
        }
        
    }
    
    public void moveBy(double x, double y) {
        for (int i = 0; i < edges.size(); i++) {
            DCHalfEdge currentEdge = edges.get(i);
            currentEdge.moveBy(x, y);
        }
        
    }

  
    //rotates all edges around the focus by an increment of theta;
    public void rotate(double theta, Point _focus) {
        for (int i = 0; i < edges.size(); i++) {
             DCHalfEdge currentEdge = edges.get(i);
            currentEdge.rotate(theta, _focus);


            //System.out.println("startX="+start.getX()+" start y="+start.getY()+" r="+startR+" theta="+startTheta+" new theta="+newStartTheta+"  newX="+(newStart.getX()+_focus.getX())+" newY="+(newStart.getY()+_focus.getY()));

        }
    }
}
    
    
  
	

