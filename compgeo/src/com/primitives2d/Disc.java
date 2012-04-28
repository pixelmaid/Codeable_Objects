package com.primitives2d;

import com.datastruct.DCHalfEdge;
import com.datastruct.DoublyConnectedEdgeList;
import com.math.CompPoint;
import com.math.Geom;

/**
 * Created with IntelliJ IDEA.
 * User: jenniferjacobs
 * Date: 4/26/12
 * Time: 6:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class Disc {

public CompPoint origin;
public double radius;
public DoublyConnectedEdgeList border;


    public Disc(double x, double y, double radius){
        this.radius=radius;
        this.origin = new CompPoint(x,y);



    }

    public boolean inGrid(GridSquare grid){

        if(Geom.pointInComPolygon(origin, grid)){
            return true;
        }

        for(int i=0;i>grid.edges.size();i++){
            DCHalfEdge edge = grid.edges.get(i);
          if(Geom.discEdgeIntersect(this, edge)){
              return true;
          }
        }
        return false;

    }




}


