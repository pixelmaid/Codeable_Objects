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

package com.algorithm;

import com.datastruct.DCFace;
import com.datastruct.DCHalfEdge;
import com.datastruct.DoublyConnectedEdgeList;
import com.datastruct.voronoi.Arc;
import com.datastruct.voronoi.VorEvent;
import com.datastruct.voronoi.VorEventSorter;
import com.math.CompPoint;
import com.math.Geom;
import com.math.PolyBoolean;

import processing.core.PApplet;

import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Vector;

public class VoronoiGenerator {

    private DoublyConnectedEdgeList dcEdgeList; //dc edge list to store diagram (Contains points and edges that are created during the algorithim)

    /*public VorTree statusStruct; //bst to store parabolas*/

    private Arc root; //root of the bst that stores the parabolas that represent the beach line

    private PriorityQueue<VorEvent> eventQueue; //event queue to store status of sweep line and events to process

    private Vector<CompPoint> initPoints;  //the input points

    private double ly; // y position of sweep line

    private DoublyConnectedEdgeList border; // the border to fit the diagram in;

    
    private DoublyConnectedEdgeList faces; // collection of all of the faces
    private int width;

    private int height; //the width and height of the stage

    private PApplet myParent; //processing parent sketch


    public VoronoiGenerator(PApplet theParent) {//constructor
        myParent = theParent;//sets processing sketch as the parent
    }

    public void getEdges(Vector<CompPoint> _points, DoublyConnectedEdgeList border, int width, int height, DoublyConnectedEdgeList _faces, DoublyConnectedEdgeList diagram) {//returns a vector of edges corresponding to the voronoi diagram

        //set input points as initial points;
        this.initPoints = Geom.removeDuplicateVerts(_points);

        this.border = border;//set the border
        this.width = width;
        this.height = height;

        root = null;//initialize root to null clearing any prior tree

        //initialize doubly connected edge list
        this.dcEdgeList = diagram;
        
        this.faces = _faces;
        
        //create new event queue
        eventQueue = new PriorityQueue<VorEvent>(initPoints.size(), new VorEventSorter());
        //PriorityQueue<VorEvent> eventQueueCopy = new PriorityQueue<VorEvent> (initPoints.size(),new VorEventSorter());


        //add points to event queue as events, set them as site events and sort them by y value;
        for (int i = 0; i < initPoints.size(); i++) {
            CompPoint point = initPoints.get(i);
            VorEvent e1 = new VorEvent(point, "site");
            eventQueue.add(e1);

            

        }
     

        //handle events
        while (eventQueue.size() > 0) {
            VorEvent currentEvent = eventQueue.poll();


            ly = currentEvent.point.getY(); //set lineY to y position of current event;

            //searches the deleted event list for the current event and removes it if it is not at the end of the list;
            //if(deleted.indexOf(currentEvent) != deleted.size()-1) { deleted.remove(currentEvent);}

            if (currentEvent.type == "site") {
                handleSiteEvent(currentEvent.point);
            }


            if (currentEvent.type == "circle") {
                handleCircleEvent(currentEvent);
            }
        }
        //create bounding box that contains all edges
        finishEdge(root);

        //myParent.print("total number of edges=");
        //myParent.println(dcEdgeList.edges.size());

        //remove neighbors and duplicates
        cleanEdges();

       
       


    }


    private void handleSiteEvent(CompPoint p) { // processes a site event
        //myParent.println("handle site event");
    	DCFace newFace = new DCFace(p);
    	faces.addFace(newFace);
        //if there is no root, add first point as a site arc and return
        if (root == null) {
            root = new Arc(p);
            return;
        }

        if((root.isLeaf) && (root.site.getY() - p.getY() ==0)) // if root is a leaf and the current point following it is behind it (degenerate case)
          {
              myParent.println("shared Y");
              p = new CompPoint(p.getX(),p.getY()-0.0001);
             /*CompPoint fp = root.site;
              root.setType();


              root.setLeft(new Arc(fp));
              root.setRight(new Arc(p));
              CompPoint s = new CompPoint((p.getX() + fp.getX())/2, height);
              dcEdgeList.addVertex(s);

              DCHalfEdge edge;
              if(p.getX() > fp.getX()){
                  edge = new DCHalfEdge(s,fp, p);

              }
              else{
                  edge = new DCHalfEdge(s, p, fp);

              }


              root.edge = edge;
              dcEdgeList.addHalfEdge(edge);


              return;*/
          }

        //find arc vertically below current site
        Arc par = getParabolaByX(p.getX());

        /*myParent.print("The below arc for:");
              myParent.print(p.name);
              myParent.print(" is:");
              myParent.println(aboveArc.value.name);*/ //debugging code


        //if arc above current site is pointing to circle event, delete this event from the event queue;
        if (par.circlePointer != null) {
            removeCircleEventFromQueue(par);
        }

        //find intercept of new parabola
        double vertexY = GetY(par.site, p.getX());
        CompPoint start = new CompPoint(p.getX(), vertexY);

        dcEdgeList.addVertex(start);//add intercept as vertex in dc edge list

        //create neighboring edges for intersection
        DCHalfEdge el = new DCHalfEdge(start, par.site, p);
        DCHalfEdge er = new DCHalfEdge(start, p, par.site);
        el.neighbor = er;

        dcEdgeList.addHalfEdge(el); //add left edge as half edge in dc edge list
        newFace.addHalfEdge(el);
        
        DCFace aboveFace = faces.getFaceByFocus(par.site);
        if(aboveFace!=null){
        	aboveFace.addHalfEdge(el);
        }
        
        par.edge = er;
        par.setType();

        //replace the leaf that represents above arc with subtree;

        //create 3 new leaves to store the two new arcs created by the new point and the original arc
        Arc p0 = new Arc(par.site);//left most arc
        Arc p1 = new Arc(p); // middle arc
        Arc p2 = new Arc(par.site);//rightmost arc

        //reconfigure par
        par.setRight(p2); //right child is p2
        par.setLeft(new Arc()); //left child is new internal node (arc intersection);

        par.left().edge = el;//left edge is edge for new internal node;

        //set children of left intersection
        par.left().setLeft(p0);
        par.left().setRight(p1);

        checkCircle(p0);
        checkCircle(p2);

    }

    private void handleCircleEvent(VorEvent e) { //processes a circle event
        //myParent.println("handle circle event");

        Arc p1 = e.arc;
        Arc xl = Arc.getLeftParent(p1);//get the closest left and right parents of p1
        Arc xr = Arc.getRightParent(p1);

        Arc p0 = Arc.getLeftChildArc(xl); //get the arcs on either side of p1
        Arc p2 = Arc.getRightChildArc(xr);

        if (p0 == p2) myParent.println("arc equality error"); //degenerate case


        //check for circle events stored with p0 and p2 and remove those as false cases;
        if (p0.circlePointer != null) {
            removeCircleEventFromQueue(p0);
        }

        if (p2.circlePointer != null) {
            removeCircleEventFromQueue(p2);
        }

        //find end of left and right edges from circle event and add vertex to edge list;
        CompPoint p = new CompPoint(e.point.getX(), GetY(p1.site, e.point.getX()));
        dcEdgeList.addVertex(p);

        //set left and right edge ends to vertex
        xl.edge.end = p;

        //setNewEnd(xl.edge,2);//checks if the edge needs a new end to fit inside the bounding box

        xr.edge.end = p;

        //setNewEnd(xr.edge,2);//checks if the edge needs a new end to fit inside the bounding box

        Arc higher = null;
        Arc par = p1;

        //re-balancing operations
        while (par != root) {
            par = par.parent;
            if (par == xl) {
                higher = xl;
            }
            if (par == xr) {
                higher = xr;
            }
        }


        higher.edge = new DCHalfEdge(p, p0.site, p2.site);
        
        DCFace face0 = faces.getFaceByFocus(p0.site);
        DCFace face2 = faces.getFaceByFocus(p2.site);

        if(face0!=null){
        	face0.addHalfEdge(higher.edge);
        }
        

        if(face2!=null){
        	face2.addHalfEdge(higher.edge);
        }
        
        
        dcEdgeList.addHalfEdge(higher.edge);

        Arc gparent = p1.parent.parent;
        if (p1.parent.left() == p1) {
            if (gparent.left() == p1.parent) gparent.setLeft(p1.parent.right());
            if (gparent.right() == p1.parent) gparent.setRight(p1.parent.right());
        } else {
            if (gparent.left() == p1.parent) gparent.setLeft(p1.parent.left());
            if (gparent.right() == p1.parent) gparent.setRight(p1.parent.left());
        }


        //remove p1 arc
        p1.parent = null;
        p1 = null;

        checkCircle(p0);
        checkCircle(p2);


    }


   

    public void cleanEdges() { //finishes edges according to their neighbors and removes the neighbors
        Vector<DCHalfEdge> edges = dcEdgeList.edges;
        for (int i = 0; i < edges.size(); i++) {
            DCHalfEdge edge = edges.get(i);
            edge.inner = true;
            if (edge.neighbor != null) {
                edge.start = edge.neighbor.end;
                edge.infiniteEdge = edge.neighbor.infiniteEdge;
                edge.neighbor = null;
            }


        }
       /* DoublyConnectedEdgeList tempEdges = new DoublyConnectedEdgeList();
        boolean errorTriggered = false;
        for (int i = 0; i < edges.size(); i++) {
            DCHalfEdge newEdge = null;
            try {
                newEdge = PolyBoolean.clipInBorder(edges.get(i),border);
            } catch (ArrayIndexOutOfBoundsException e) {
                newEdge = null;
                if (!errorTriggered) {
                    System.out.println("Whoa there turbo. You just made a concave polygon. Do you know how hard it is to detect edge intersections for one of those?");
                }

                errorTriggered = true;

            }
            if (newEdge != null) {
                tempEdges.addHalfEdge(newEdge);
            }
        }

        dcEdgeList = tempEdges;*/


    }

    private void finishEdge(Arc n) //recursively finishes all infinite edges in the tree
    {
        //myParent.println("finishEdge");

        if (n.isLeaf) {
            n = null;
            return;
        }

        double mx = 0;
        if (n.edge.direction.getX() > 0.0) {
            mx = Math.max(width, n.edge.start.getX() + 10);
        } else {
            mx = Math.min(0.0, n.edge.start.getX() - 10);
        }

        n.edge.end = new CompPoint(mx, mx * n.edge.m + n.edge.b);


        finishEdge(n.left());
        finishEdge(n.right());

        n = null;
    }

    public double getXOfEdge(Arc par, double y) //finds current x intersection of left and right arcs according to parabola equation
    {


        Arc left = Arc.getLeftChildArc(par);
        Arc right = Arc.getRightChildArc(par);


        CompPoint p = left.site;
        CompPoint r = right.site;

        double dp = 2.0 * (p.getY() - y);
        double a1 = 1.0 / dp;
        double b1 = -2.0 * p.getX() / dp;
        double c1 = y + dp / 4 + p.getX() * p.getX() / dp;

        dp = 2.0 * (r.getY() - y);
        double a2 = 1.0 / dp;
        double b2 = -2.0 * r.getX() / dp;
        double c2 = y + dp / 4 + r.getX() * r.getX() / dp;

        double a = a1 - a2;
        double b = b1 - b2;
        double c = c1 - c2;

        double disc = b * b - 4 * a * c; //quadratic equation
        double x1 = (-b + Math.sqrt(disc)) / (2 * a);
        double x2 = (-b - Math.sqrt(disc)) / (2 * a);

        double ry;
        if (p.getY() < r.getY()) ry = Math.max(x1, x2);
        else ry = Math.min(x1, x2);

        return ry;
    }


    public Arc getParabolaByX(double xx) //returns the parabola that is above this x position in the current beachline
    {
        Arc par = root;
        double x = 0.0;

        while (!par.isLeaf) {
            x = getXOfEdge(par, ly);
            if (x > xx) par = par.left();
            else par = par.right();

        }

        return par;
    }

    private double GetY(CompPoint p, double x) //find y intercept of parabola defined by p with x of new point;
    {

        double dp = 2 * (p.getY() - ly);
        double a1 = 1 / dp;
        double b1 = -2 * p.getX() / dp;
        double c1 = ly + dp / 4 + p.getX() * p.getX() / dp;

        return (a1 * x * x + b1 * x + c1);
    }

    private void checkCircle(Arc b) {//checks to see if breakpoints of 3 arcs converge to determine a potential circle event (the disappearing of the parabola)
        //myParent.println("circle event started");


        Arc lp = Arc.getLeftParent(b);
        Arc rp = Arc.getRightParent(b);

        Arc a = Arc.getLeftChildArc(lp);
        Arc c = Arc.getRightChildArc(rp);

        if (a == null || c == null || a.value == c.value) return; //if a and c are the same arc or are null, end

        CompPoint s = null;
        s = getEdgeIntersection(lp.edge, rp.edge);

        if (s == null) {
            //myParent.println("s = null, ending circle event");
            return;
        }

        double dx = a.site.getX() - s.getX();
        double dy = a.site.getY() - s.getY();

        double d = Math.sqrt((dx * dx) + (dy * dy));

        if (s.getY() - d >= ly) {
            return;
        }

        VorEvent e = new VorEvent(new CompPoint(s.getX(), s.getY() - d), "circle");
        dcEdgeList.addVertex(e.point);
        b.circlePointer = e;
        e.arc = b;

        eventQueue.add(e);

        //myParent.println("circle event added");
    }


    private CompPoint getEdgeIntersection(DCHalfEdge a, DCHalfEdge b)//calculate intersection point between two edges
    {
        double x = (b.b - a.b) / (a.m - b.m); //calculate the x intersection between the two edges
        double y = a.m * x + a.b; //calculate the y intersection between the two edges

        if ((x - a.start.getX()) / a.direction.getX() < 0) return null;
        if ((y - a.start.getY()) / a.direction.getY() < 0) return null;

        if ((x - b.start.getX()) / b.direction.getX() < 0) return null;
        if ((y - b.start.getY()) / b.direction.getY() < 0) return null;

        CompPoint p = new CompPoint(x, y);
        dcEdgeList.addVertex(p);
        return p;
    }


    private void removeCircleEventFromQueue(Arc p) { // removes a false circle event from the event queue

        Iterator<VorEvent> itr = eventQueue.iterator();
        boolean remove = false;
        while (itr.hasNext()) {

            VorEvent e = itr.next();
            if (e.arc == p) {
                remove = eventQueue.remove(e);
                //myParent.println("tried to remove circle pointer:"+remove); //debugging code
                p.circlePointer = null;
                return;
            }

        }
        //myParent.println("tried to remove circle pointer:"+remove); //debugging code


    }

}


