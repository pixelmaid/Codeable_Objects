package com.primitives2d;

import com.datastruct.DCHalfEdge;
import com.math.CompPoint;
import com.datastruct.DoublyConnectedEdgeList;

/**
 * User: jenniferjacobs
 * Date: 4/26/12
 * Time: 10:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class GridSquare extends DoublyConnectedEdgeList {
    public CompPoint origin;
    public double size;



    public GridSquare(double x, double y, double size) {

        this.size =size;
        this.origin=new CompPoint(x,y);

        DCHalfEdge top = new DCHalfEdge(origin,new CompPoint(x+size,y));
        DCHalfEdge right = new DCHalfEdge(top.end,new CompPoint(x+size,y+size));

    }
}
