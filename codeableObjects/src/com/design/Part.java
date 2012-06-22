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

package com.design;

import com.datastruct.DCHalfEdge;
import com.datastruct.DoublyConnectedEdgeList;
import com.math.CompPoint;
import com.math.Geom;
import processing.core.PApplet;

import java.util.Vector;

public class Part extends DoublyConnectedEdgeList {
    public double width;
    public double height;
    public CompPoint focus;
    public Vector<Part> subParts;
    
	
    public Part(double width, double height) {
        this.width = width;
        this.height = height;
        //focus starts in the center by default
        this.focus = new CompPoint(width / 2, height / 2);

    }

    public void translate(double x, double y) {
        for (int i = 0; i < edges.size(); i++) {
            DCHalfEdge currentEdge = edges.get(i);
            currentEdge.translate(x, y, focus);
        }
        focus = new CompPoint(x, y);
    }

    public DCHalfEdge findMerge(DCHalfEdge edge, Part border) { //finds the appropriate intersection for a merge;

        System.out.println("border size=" + border.edges.size());

        boolean startInPolygon = Geom.rayPointInPolygon(edge.start, border);
        boolean endInPolygon = Geom.rayPointInPolygon(edge.end, border);

        System.out.println("start in polygon =" + startInPolygon);
        System.out.println("end in polygon =" + endInPolygon);


        if (!startInPolygon && !endInPolygon) {


            Vector<DCHalfEdge> intersectedEdges = Geom.edgeIntersectsPolygon(edge, border);

            if (intersectedEdges.size() > 0) {

                if (intersectedEdges.size() == 1) {
                    System.out.println("segment is tangent");

                    CompPoint intersection1 = Geom.findIntersectionPoint(edge, intersectedEdges.get(0));
                    System.out.println("intersected edge num=" + border.edges.indexOf(edge.intersectedEdge));
                    return edge;
                } else if (intersectedEdges.size() == 2) {
                    System.out.println("segment intersects twice");
                    CompPoint intersection1 = Geom.findIntersectionPoint(edge, intersectedEdges.get(0));
                    CompPoint intersection2 = Geom.findIntersectionPoint(edge, intersectedEdges.get(1));
                    return new DCHalfEdge(intersection1, intersection2);
                } else if (intersectedEdges.size() == 4) {
                    System.out.println("segment intersects 4");
                    CompPoint intersection1 = Geom.findIntersectionPoint(edge, intersectedEdges.get(0));
                    CompPoint intersection2 = Geom.findIntersectionPoint(edge, intersectedEdges.get(2));
                    return new DCHalfEdge(intersection1, intersection2);
                } else {
                    System.out.println("segment intersects " + intersectedEdges.size());
                    return edge;
                }


            } else {
                System.out.println("segment does not intersect");
                return null;
            }
        } else if (startInPolygon && endInPolygon) {
            System.out.println("segment is inside of polygon");
            return edge;
        } else {
            if (startInPolygon && !endInPolygon) {
           	 DCHalfEdge borderEdge = Geom.getIntersectedEdge(edge.start,edge.end, edge, border);

                System.out.println("start is in polygon");
               
                edge.end = Geom.getIntersectedEdgePoint(edge.start, edge.end, edge, borderEdge);
            } else if (!startInPolygon && endInPolygon) {
            	DCHalfEdge borderEdge = Geom.getIntersectedEdge(edge.end,edge.start, edge, border);
                System.out.println("end is in polygon");
                edge.start = Geom.getIntersectedEdgePoint(edge.end, edge.start, edge, borderEdge);

            }
            System.out.println("intersectedEdge=" + edge.intersectedEdge);
            return edge;

        }
    }


    public Part returnCopy() {
        Part copy = new Part(this.width, this.height);
        return copy;
    }
    
    
    public void convertVertexesToEdges(){
    	this.edges = new Vector<DCHalfEdge>();
    	this.verticies = Geom.removeDuplicateVerts(this.verticies);
    	for(int i = 0;i<this.verticies.size();i++){
			int next = i+1;
			if(next==this.verticies.size()){
				next = 0;
			}
			CompPoint start = this.verticies.get(i);
			CompPoint end = this.verticies.get(next);
			this.addHalfEdge(new DCHalfEdge(start,end));
		}
    }
    
    public void expandPart(double dist){
    	subParts = new Vector<Part>(); 
    	Vector<DCHalfEdge> newEdges = new Vector<DCHalfEdge>(); 
    	for (int i = 0; i <edges.size(); i++) {
              DCHalfEdge currentEdge = edges.get(i);
              DCHalfEdge edgeRight = expandEdge(currentEdge,dist);
              DCHalfEdge edgeLeft = expandEdge(currentEdge,0-dist);
              edgeLeft.mirrorPartner(currentEdge);
              edgeRight.mirrorPartner(currentEdge);
              DCHalfEdge edgeTop = new DCHalfEdge(edgeRight.start,edgeLeft.start);
              DCHalfEdge edgeBottom = new DCHalfEdge(edgeLeft.end,edgeRight.end);
              Part part = new Part(0,0);
              part.addHalfEdge(edgeTop);
              part.addHalfEdge(edgeLeft);

              part.addHalfEdge(edgeBottom);

              part.addHalfEdge(edgeRight);
              
              subParts.add(part);
             newEdges.add(edgeTop);
              newEdges.add(edgeLeft);
             newEdges.add(edgeBottom);
              newEdges.add(edgeRight);
           
    	  }
    	
    	this.edges = newEdges;
    	/*for (int i = 0; i <edges.size(); i++) {
    		 DCHalfEdge currentEdge = edges.get(i);
    	  Vector<DCHalfEdge> intersectedEdges = Geom.lineIntersectsPolygon(edge, border);
          if (intersectedEdges.size() != 0) {

              CompPoint intersection1 = Geom.findIntersectionPoint(edge, intersectedEdges.get(0));
              CompPoint intersection2 = Geom.findIntersectionPoint(edge, intersectedEdges.get(1));
              return new DCHalfEdge(intersection1, intersection2);


          }
    	}*/
    	
    }
    
    
    public DCHalfEdge expandEdge(DCHalfEdge edge, double dist){
    	  double a = (edge.end.getY()-edge.start.getY());
    	  double b = (edge.end.getX()-edge.start.getX());
    	  double theta = Geom.cartToPolar(b,a)[1];
    	  
    	  edge.rotate((90-theta), edge.start);
    	  
    	 CompPoint expandedStart = new CompPoint(edge.start.getX()+dist,edge.start.getY());
    	 CompPoint expandedEnd = new CompPoint(edge.end.getX()+dist,edge.end.getY());

    	  DCHalfEdge expandedEdge = new DCHalfEdge(expandedStart,expandedEnd);
    	  
    	  double inverse = (90-theta)*-1;
    	  expandedEdge.rotate(inverse,edge.start);
    	  edge.rotate(inverse, edge.start);
    	  
    	  return expandedEdge;
    	}


    
    public void addTab(Tab tab){
    	tab.merge(this);
    }

    public void draw(PApplet parent,int weight, int color){
        parent.stroke(color);
        parent.strokeWeight(weight);
        for(int i=0;i<edges.size();i++){
           DCHalfEdge currentEdge = edges.get(i);
           parent.line((float)currentEdge.start.getX(),(float)currentEdge.start.getY(),(float)currentEdge.end.getX(),(float)currentEdge.end.getY());
       }
    }
    
    public void drawOffset(PApplet parent,int weight, int color,double offset){
        parent.stroke(color);
        parent.strokeWeight(weight);
        for(int i=0;i<edges.size();i++){
           DCHalfEdge currentEdge = edges.get(i);
           parent.line((float)currentEdge.start.getX()+(float)offset,(float)currentEdge.start.getY(),(float)currentEdge.end.getX()+(float)offset,(float)currentEdge.end.getY());
       }
    }

}
