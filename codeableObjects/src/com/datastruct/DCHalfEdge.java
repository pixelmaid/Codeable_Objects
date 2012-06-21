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

import java.util.Collections;
import java.util.Vector;

import processing.core.PApplet;

import com.math.CompPoint;
import com.math.Geom;

public class DCHalfEdge implements Comparable<DCHalfEdge> {

    public CompPoint start; //pointer to start point
    public CompPoint end = null;  //pointer to end point
    public CompPoint direction; //directional vector, from "start", points to "end", normal of |left, right|
    public CompPoint left = null;  //pointer to Voronoi site on the left side of edge
    public CompPoint right = null; //pointer to Voronoi site on the left side of edge
    public DCHalfEdge intersectedEdge = null; //edge that this edge will be added beneath if a merge takes place
    public double length; //euclidean length of edge;
    public int infiniteEdge = 0;

    public EdgePartner startPartner;
    public EdgePartner endPartner;
    
    public boolean inner = false; //determines if edge needs to be cut first because it is an inner edge
    public double m; //directional coefficients satisfying equation y = m*x + b (edge lies on this line)
    public double b;

    public DCHalfEdge neighbor = null; //twin to edge


    public DCHalfEdge(CompPoint start, CompPoint end) {//constructor for end that is predetermined
        this.start = start;
        this.end = end;

        m = (start.getY() - end.getY()) / (start.getX() - end.getX()); //calculate the slope of the line by the inverse of the slope of the line through left and right
        b = start.getY() - (m * start.getX()); //calculate the y intercept with y=mx+b


        this.length = Geom.distance(start, end);
    }

    public DCHalfEdge(CompPoint start, CompPoint left, CompPoint right) {//constructor
        this.start = start;
        this.left = left;
        this.right = right;

        m = (right.getX() - left.getX()) / (left.getY() - right.getY()); //calculate the slope of the line by the inverse of the slope of the line through left and right
        b = start.getY() - m * start.getX(); //calculate the y intercept with y=mx+b

        direction = new CompPoint((right.getY() - left.getY()), -(right.getX() - left.getX()));


    }

    public void setleft(CompPoint left) {
        this.left = left;
    }

    public void setright(CompPoint right) {
        this.right = right;
    }


    public int compareTo(DCHalfEdge e) {//finds edge with smallest y coordinate
    	//this is where you start tomorrow!!!!!!!
    	Vector<CompPoint> verticies = new Vector<CompPoint>();
    	verticies.add(e.start);
    	verticies.add(e.end);
    	verticies.add(this.start);
    	verticies.add(this.end);
    	Collections.sort(verticies);
    	/*for(int i=0;i<verticies.size();i++){
    		System.out.println("vertex at"+i+"="+verticies.get(i).getX()+","+verticies.get(i).getY());
    	}*/
    	if(this.start==verticies.get(0)|| this.end==verticies.get(0)){
    		return -1;
    	}
    	else{
    		return 1;
    	}
    }


    public void translate(double x, double y, CompPoint focus) {
        double dx = x - focus.getX();
        double dy = y - focus.getY();

        start.setX(start.getX() + dx);
        start.setY(start.getY() + dy);
        end.setX(end.getX() + dx);
        end.setY(end.getY() + dy);

        m = (start.getY() - end.getY()) / (start.getX() - end.getX()); //calculate the slope of the line by the inverse of the slope of the line through left and right
        b = start.getY() - (m * start.getX()); //calculate the y intercept with y=mx+b

    }

    public void rotate(double theta, CompPoint _focus) {

        double[] startRT = Geom.cartToPolar(start.getX() - _focus.getX(), start.getY() - _focus.getY());
        double[] endRT = Geom.cartToPolar(end.getX() - _focus.getX(), end.getY() - _focus.getY());
        double startTheta = startRT[1];
        double startR = startRT[0];

        double endTheta = endRT[1];
        double endR = endRT[0];

        double newStartTheta = startTheta + theta;
        double newEndTheta = endTheta + theta;


        CompPoint newStart = Geom.polarToCart(startR, newStartTheta);
        CompPoint newEnd = Geom.polarToCart(endR, newEndTheta);
        start.setX(newStart.getX() + _focus.getX());
        start.setY(newStart.getY() + _focus.getY());
        end.setX(newEnd.getX() + _focus.getX());
        end.setY(newEnd.getY() + _focus.getY());

        m = (start.getY() - end.getY()) / (start.getX() - end.getX()); //calculate the slope of the line by the inverse of the slope of the line through left and right
        b = start.getY() - (m * start.getX()); //calculate the y intercept with y=mx+b

    }
    
    public void setPartnerEdge(String type, DCHalfEdge edge, CompPoint point, int color){
    	
    	if(type=="start"){
    		startPartner = new EdgePartner(edge,point,type,color);
    	}
    	
    	if(type=="end"){
    		
    		endPartner = new EdgePartner(edge,point,type,color);
    		
    	}
    }
    
    public void mirrorPartner( DCHalfEdge edge){
    	
    
    		startPartner = new EdgePartner(edge.getPartnerEdge("start"),edge.getPartnerPoint("start"),"start",edge.getPartnerColor("start"));
    	
    	
    		
    		endPartner = new EdgePartner(edge.getPartnerEdge("end"),edge.getPartnerPoint("end"),"end",edge.getPartnerColor("end"));
    		
    	
    }
    
    public void drawPartners(PApplet parent){
    	if (this.startPartner!=null){
    		parent.stroke(startPartner.color);
    		parent.strokeWeight(4);
    		parent.point((float)this.start.getX(),(float)this.start.getY());
    		
    	}
    	
    	if (this.endPartner!=null){
    		parent.stroke(endPartner.color);
    		parent.strokeWeight(4);
    		parent.point((float)this.end.getX(),(float)this.end.getY());
    	}
    	
    	
    }
    
    public DCHalfEdge getPartnerEdge(String type){
    	
    	
    	
    	if(type=="start"){
    		if (this.startPartner!=null){
    			return this.startPartner.edge;
    		}
    	}
    	
    	if(type=="end"){
    		if (this.endPartner!=null){
    			return this.endPartner.edge;
    		}
    	}
    	return null;
    	
    }
    
    public CompPoint getPartnerPoint(String type){
    	if(type=="start"){
    		if (this.startPartner!=null){
    			return this.startPartner.point;
    		}
    	}
    	
    	if(type=="end"){
    		if (this.endPartner!=null){
    			return this.endPartner.point;
    		}
    	}
    	return null;
    	
    }
    
    public int getPartnerColor(String type){
    	if(type=="start"){
    		if (this.startPartner!=null){
    			return this.startPartner.color;
    		}
    	}
    	
    	if(type=="end"){
    		if (this.endPartner!=null){
    			return this.endPartner.color;
    		}
    	}
    	System.out.println("no color found");
    	return 0;
    	
    }
    
    

}


class EdgePartner{ //object that defines shared edge 
	public DCHalfEdge edge;
	public CompPoint point;
	public String type;
	public int color;
	
	
	
	EdgePartner(DCHalfEdge edge, CompPoint point,String type, int color){
		this.edge = edge;
		this.point = point;
		this.type = type;
		this.color = color;
		
	}

}
