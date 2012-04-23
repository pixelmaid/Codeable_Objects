package com.design;

import com.datastruct.DCHalfEdge3d;
import com.datastruct.DoublyConnectedEdgeList;
import com.math.CompPoint3d;
import processing.core.PApplet;

import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: jenniferjacobs
 * Date: 4/20/12
 * Time: 9:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class Model {

        double width;
        double height;
        PApplet myParent;
        Vector <DCHalfEdge3d>edges;
        Vector <DCHalfEdge3d>bottomEdges;
        Vector <DCHalfEdge3d>topEdges;

    public Model(PApplet myParent){
        this.myParent = myParent;
        this.width = myParent.width;
        this.height = myParent.height;
        edges = new Vector<DCHalfEdge3d>();
        topEdges = new Vector<DCHalfEdge3d>();
        bottomEdges = new Vector<DCHalfEdge3d>();
    }

    public void addEdge(DCHalfEdge3d edge){

        this.edges.addElement(edge);

    }

    public void addTopEdge(DCHalfEdge3d edge){

        this.bottomEdges.addElement(edge);

    }

    public void addBottomEdge(DCHalfEdge3d edge){

        this.topEdges.addElement(edge);

    }

    public void clearEdges(){

        this.edges=new Vector<DCHalfEdge3d>();
        this.topEdges = new Vector<DCHalfEdge3d>();
        this.bottomEdges = new Vector<DCHalfEdge3d>();

    }


    public void draw(double maxWidth,double maxHeight){
        myParent.background(100,100,100);
        myParent.pushMatrix();
        myParent.translate(myParent.width/2, (float)(myParent.width/2-maxHeight/2-100),-200);
        myParent.noFill();
        myParent.stroke(255);
        myParent.strokeWeight(2);
        myParent.rotateY((float)(myParent.frameCount*Math.PI/400));
        myParent.beginShape(myParent.QUAD_STRIP);

        for (int j =0; j < edges.size(); j++) {
            CompPoint3d top = edges.get(j).start;
            CompPoint3d bottom = edges.get(j).end;
            myParent.vertex((float)top.getX(),(float)top.getY(),(float)top.getZ());
            myParent.vertex((float)bottom.getX(),(float)bottom.getY(),(float)bottom.getZ());

        }

        myParent.endShape();
        myParent.stroke(255,0,0);
        myParent.beginShape(myParent.QUAD_STRIP);

        for (int j =0; j < topEdges.size(); j++) {
            CompPoint3d top = topEdges.get(j).start;
            CompPoint3d bottom = topEdges.get(j).end;
            myParent.vertex((float)top.getX(),(float)top.getY(),(float)top.getZ());
            myParent.vertex((float)bottom.getX(),(float)bottom.getY(),(float)bottom.getZ());

        }

        myParent.endShape();

        myParent.beginShape(myParent.QUAD_STRIP);

        for (int j =0; j < bottomEdges.size(); j++) {
            CompPoint3d top = bottomEdges.get(j).start;
            CompPoint3d bottom = bottomEdges.get(j).end;
            myParent.vertex((float)top.getX(),(float)top.getY(),(float)top.getZ());
            myParent.vertex((float)bottom.getX(),(float)bottom.getY(),(float)bottom.getZ());

        }

        myParent.endShape();
        myParent.popMatrix();

    }


}
