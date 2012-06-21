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
import com.math.CompPoint;
import com.math.Geom;

import java.util.Vector;


public class Base extends Part {
    public String type;

    public Base(double width, double height, String type) {
        super(width, height);
        this.type = type;
        
    }

    public void addNotches(double notchWidth, double notchHeight, double ribNotchOffset, int ribNum, boolean reverse) {

        for (int i = ribNum - 1; i >= 0; i--) {
            //double alpha = 0-((Math.PI*2/ribNum)*180/Math.PI)+360/(ribNum*2); // determines the degree position of your current point

            double startTheta = Geom.cartToPolar(edges.get(i).start.getX(), edges.get(i).start.getY())[1];
            double alpha = 360 / ribNum;
            double theta = startTheta + (i) * alpha; //current position on circle for your intended point
            //System.out.println("startTheta="+startTheta);

            //System.out.println("theta="+theta);
            Notch notch = new Notch(notchWidth, notchHeight);


            notch.rotate(startTheta + 180, notch.focus);

            int after = 0;
            if (i == 0) {
                after = ribNum - 1;
            } else {
                after = i - 1;
            }
            this.setNotch(notch, i, after,reverse);
        }


    }
    
    public void largeTabs(double dist){
    	Vector<DCHalfEdge> newEdges = new Vector<DCHalfEdge>();
    	
    	for(int i =0;i<this.edges.size();i++){
    		DCHalfEdge newEdge =this.expandEdge(this.edges.get(i),dist);
    		DCHalfEdge rightEdge = new DCHalfEdge(newEdge.end,new CompPoint(this.edges.get(i).end.getX(),this.edges.get(i).end.getY()));
    		DCHalfEdge leftEdge = new DCHalfEdge(newEdge.start,new CompPoint(this.edges.get(i).start.getX(),this.edges.get(i).start.getY()));
    		newEdges.addElement(rightEdge);
    		newEdges.addElement(newEdge);
    		newEdges.addElement(leftEdge);
    	}
    	this.edges= newEdges;
    	
    	
    }

    public double findRad(){
    	double rad = Geom.cartToPolar(edges.get(0).start.getX(), edges.get(0).start.getY())[0];
    	return rad;
    }
    

    private void setNotch(Notch notch, int edgeNum, int edgeNumAfter, boolean reverse) {


        notch.translate(this.edges.get(edgeNum).start.getX(), this.edges.get(edgeNum).start.getY());
       if(reverse){
    	   notch.mergeReverse(this, edgeNumAfter, edgeNum);
       }
       else{
        notch.merge(this, edgeNumAfter, edgeNum);
       }
    }


    public void generateHole(double radius) {
        int res = 50; // we will define the circles by a set of evenly spaced points. This variable controls the number of points in your circles
        Vector<CompPoint> points = new Vector<CompPoint>();
        for (int i = 0; i < res; i++) { //loop over the number of points in the circle

            double alpha = Math.PI * 2 / res; // determines the degree position of your current point

            double theta = i * alpha; //current position on circle for your intended point

            double xPos = Math.sin(theta) * radius + focus.getX();
            double yPos = Math.cos(theta) * radius + focus.getY();

            points.addElement(new CompPoint(xPos, yPos));

        }

        for (int i = 0; i < res; i++) {
            int after = i + 1;
            if (i == res - 1) {
                after = 0;
            }

            DCHalfEdge circleEdge = new DCHalfEdge(points.get(after), points.get(i));
            circleEdge.inner = true;

            this.addHalfEdge(circleEdge);
        }
    }


}
