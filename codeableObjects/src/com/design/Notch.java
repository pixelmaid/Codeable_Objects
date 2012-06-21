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


public class Notch extends Part {

    public DCHalfEdge top;
    public DCHalfEdge bottom;
    public DCHalfEdge left;
    public DCHalfEdge right;


    public Notch(double width, double height) {
        super(width, height);

        //edges and points move in a counter clockwise direction
        this.top = new DCHalfEdge(new CompPoint(width, 0), new CompPoint(0, 0));

        this.left = new DCHalfEdge(new CompPoint(0, 0), new CompPoint(0, height));
        this.bottom = new DCHalfEdge(new CompPoint(0, height), new CompPoint(width, height));
        this.right = new DCHalfEdge(new CompPoint(width, height), new CompPoint(width, 0));
        top.inner = true;
        bottom.inner = true;
        right.inner = true;
        left.inner = true;
        this.addHalfEdge(top);
        this.addHalfEdge(left);
        this.addHalfEdge(bottom);
        this.addHalfEdge(right);
    }


    public void merge(Part border, int topEdgeNum, int bottomEdgeNum) {
        this.top.end = Geom.findIntersectionPoint(this.top, border.edges.get(topEdgeNum));
        this.bottom.start = Geom.findIntersectionPoint(this.bottom, border.edges.get(bottomEdgeNum));
        border.addHalfEdge(top);
        border.addHalfEdge(bottom);
        border.addHalfEdge(right);

    }
    
    public void mergeReverse(Part border, int topEdgeNum, int bottomEdgeNum) {
        CompPoint topIntersect = Geom.findIntersectionPoint(this.top, border.edges.get(topEdgeNum));
        CompPoint bottomIntersect = Geom.findIntersectionPoint(this.bottom, border.edges.get(bottomEdgeNum));
    	this.top.start=topIntersect;
        //border.edges.get(topEdgeNum).end = topIntersect;
        this.bottom.end = bottomIntersect;
        //border.edges.get(bottomEdgeNum).start = bottomIntersect;
        border.addHalfEdge(top);
        border.addHalfEdge(bottom);
        border.addHalfEdge(left);

    }


    //clips the part and copies its edges into the new part
    public void merge(Part border) {
        Vector<DCHalfEdge> modEdges = new Vector<DCHalfEdge>();
        Vector<DCHalfEdge> borderIntersects = new Vector<DCHalfEdge>();
        for (int i = 0; i < edges.size(); i++) {
            DCHalfEdge borderEdge = Geom.getIntersectedEdge(edges.get(i).start, edges.get(i).end, edges.get(i), border);
            DCHalfEdge newEdge = findMerge(edges.get(i), border);

            if (newEdge != null) {
                modEdges.addElement(newEdge);
                borderIntersects.addElement(borderEdge);
                newEdge.inner = true;
            }
        }
        for (int i = 0; i < modEdges.size(); i++) {

            border.addHalfEdge(modEdges.get(i));

        }
        //need to remove null elements
    }

}
