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

import java.util.Vector;


public class Tab extends Part {

    public DCHalfEdge top;
    public DCHalfEdge bottom;
    public DCHalfEdge left;
    public DCHalfEdge right;


    public Tab(double width, double height, double tabWidth, double tabHeight,double x,double y) {
        super(width+tabWidth, tabHeight);

        //edges and points move in a counter clockwise direction
        this.top = new DCHalfEdge(new CompPoint(width+x, y),new CompPoint(x, y));

        this.left = new DCHalfEdge(new CompPoint(x, y), new CompPoint(x, height+y));
        this.bottom = new DCHalfEdge(new CompPoint(x, height+y), new CompPoint(width+x, height+y));
        this.right = new DCHalfEdge(new CompPoint(width+x, height+y), new CompPoint(width+x, y));
        top.inner = true;
        bottom.inner = true;
        right.inner = true;
        left.inner = true;
        
        
        CompPoint pointA = new CompPoint(x,y);
        CompPoint pointB = new CompPoint(x,y-(tabHeight-height)/2);
        CompPoint pointC = new CompPoint(x-tabWidth,height/2+y);
        CompPoint pointD = new CompPoint(x,height+(tabHeight-height)/2+y);
        CompPoint pointE = new CompPoint(x,height+y);
        
        CompPoint pointF = new CompPoint(width+x,height+y);
        CompPoint pointG = new CompPoint(width+x,height+(tabHeight-height)/2+y);
        CompPoint pointH = new CompPoint(x+width+tabWidth,height/2+y);
        CompPoint pointI = new CompPoint(x+width,y-(tabHeight-height)/2);
        CompPoint pointJ = new CompPoint(x+width,y);
        
        DCHalfEdge arrow1= new DCHalfEdge(pointA,pointB);
        DCHalfEdge arrow2= new DCHalfEdge(pointB,pointC);
        DCHalfEdge arrow3= new DCHalfEdge(pointC,pointD);
        DCHalfEdge arrow4= new DCHalfEdge(pointD,pointE);
        
        DCHalfEdge arrow5= new DCHalfEdge(pointF,pointG);
        DCHalfEdge arrow6= new DCHalfEdge(pointG,pointH);
        DCHalfEdge arrow7= new DCHalfEdge(pointH,pointI);
        DCHalfEdge arrow8= new DCHalfEdge(pointI,pointJ);
       
        this.addHalfEdge(top);
        this.addHalfEdge(arrow1);
        this.addHalfEdge(arrow2);
        this.addHalfEdge(arrow3);
        this.addHalfEdge(arrow4);
        this.addHalfEdge(bottom);
        this.addHalfEdge(arrow5);
        this.addHalfEdge(arrow6);
        this.addHalfEdge(arrow7);
        this.addHalfEdge(arrow8);
    }

    //clips the part and copies its edges into the new part
    public void merge(Part border) {
    	while(!Geom.rayPointInPolygon(bottom.end,border)){
    		bottom.end.setX(bottom.end.getX()+10);
    	}
    	while(!Geom.rayPointInPolygon(top.start,border)){
    		top.start.setX(top.start.getX()+10);
    	}
        DCHalfEdge topIntersection = Geom.edgeIntersectsPolygon(top,border).get(0);
        DCHalfEdge bottomIntersection = Geom.edgeIntersectsPolygon(bottom,border).get(0);
       
        
        CompPoint topIntersectionPoint = Geom.findIntersectionPoint(top, topIntersection);
        CompPoint bottomIntersectionPoint = Geom.findIntersectionPoint(bottom, bottomIntersection);
       
        top.start.setX(topIntersectionPoint.getX());
        top.start.setY(topIntersectionPoint.getY());
        topIntersection.end.setX(topIntersectionPoint.getX());
        topIntersection.end.setY(topIntersectionPoint.getY());
        
        int topIndex = border.edges.indexOf(topIntersection);
        int bottomIndex = border.edges.indexOf(bottomIntersection);
        bottom.end.setX(bottomIntersectionPoint.getX());
        bottom.end.setY(bottomIntersectionPoint.getY());
        
       
        
        bottomIntersection.start.setX(bottomIntersectionPoint.getX());
        bottomIntersection.start.setY(bottomIntersectionPoint.getY());
        int indexCounter = topIndex+1;
        while(indexCounter<bottomIndex){
            
        	border.edges.remove(topIndex+1);
        	indexCounter++;
        }
       
       
        
       
        System.out.println(bottomIndex);
       
        
       border.edges.addAll(topIndex+1,this.edges);
    }
      

}
