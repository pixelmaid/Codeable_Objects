/*
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

import com.algorithm.VoronoiGenerator;
import com.datastruct.DCHalfEdge;
import com.datastruct.DoublyConnectedEdgeList;
import com.math.CompPoint;
import com.math.Geom;
import processing.core.PApplet;

import java.util.Random;
import java.util.Vector;

public class Pattern {
    private DoublyConnectedEdgeList border = new DoublyConnectedEdgeList();
    private double width;
    private double height;
    private PApplet myParent;
    private VoronoiGenerator vorGenerator;  //voronoi generator
    private DoublyConnectedEdgeList shadeBorder;
    private Vector <DCHalfEdge> voronoiEdges; //pattern edges for drawing
    private Vector <CompPoint> currentPoints; //points for constructing diagram passed in by controller
    private int outerColor = 255;
    private int innerColor = 0x9966FF;
    private int pointColor = 255;
    public int thickWeight = 3;
    private int thinWeight = 1;


    public Pattern(PApplet myParent){
        this.myParent = myParent;
        this.width = myParent.width;
        this.height = myParent.height;
        vorGenerator = new VoronoiGenerator(myParent);
    }




    public void defineVorDiagram(DoublyConnectedEdgeList shadeBorder, Vector<CompPoint> currentPoints){      //seperate out clipping and draw methods
        //add noise to points location to prevent issue with same x or y coordinates
        double focusX = myParent.width/2;
        double focusY = myParent.height/2;
        this.currentPoints=currentPoints;
        this.shadeBorder=shadeBorder;
        Vector<CompPoint> rotatedPoints = new Vector<CompPoint>();
        Random randomGenerator = new Random();

        //randomly rotate points to deal with error
        for(int i=0;i<this.currentPoints.size();i++){
            CompPoint point = currentPoints.get(i);
            double x = point.getX();
            double y = point.getY();

            double dx=x-focusX; //direction x
            double dy=y-focusY; //direction y
            double pointTheta = Geom.cartToPolar(dx, dy)[1];//degree of edge
            double pointR = Geom.cartToPolar(dx, dy)[0];//degree of edge


            double newTheta = pointTheta + getRandomRanged(-0.001, 0.001, randomGenerator);
            double xNew = Math.cos(newTheta * Math.PI/ 180.0)*pointR;
            double yNew = Math.sin(newTheta * Math.PI/ 180.0)*pointR;
            CompPoint newPoint = new CompPoint(xNew+focusX,yNew+focusY);
            rotatedPoints.addElement(newPoint);

        }

        //generate voronoi diagram
       voronoiEdges = vorGenerator.getEdges(rotatedPoints,shadeBorder,myParent.width,myParent.height);

    }



    public void draw(boolean drawPoints,float color){
        myParent.background(100,100,100);
        if(drawPoints){  //draws points that define the diagram
            myParent.stroke(255,255,0);
            myParent.strokeWeight(4);
            for (int i=0;i<currentPoints.size();i++){
                CompPoint point = currentPoints.get(i);

                myParent.point((float)point.getX(),(float)point.getY());

            }
        }

        //draw border of diagram
        try{

            for(int i=0;i<shadeBorder.edges.size(); i++){
                DCHalfEdge edge = shadeBorder.edges.get(i);


                float edgeStartX=(float)(edge.start.getX());
                float edgeStartY=(float)(edge.start.getY());

                float edgeEndX=(float)(edge.end.getX());
                float edgeEndY=(float)(edge.end.getY());
                myParent.stroke(color);
                myParent.strokeWeight(thickWeight);

                myParent.line(edgeStartX,edgeStartY,edgeEndX,edgeEndY);

            }
        }

        catch (java.lang.NullPointerException e){
            System.out.println("you tried to draw an edge in the border that doesn't exist");
        }


        //myParent.println("drew border");
       //draw diagram
        try{
            for (int k=0;k<voronoiEdges.size();k++) {

                DCHalfEdge edge = voronoiEdges.get(k);

                if(edge.end!=null && edge.start!=null){
                    float edgeStartX=(float)Math.round((edge.start.getX()));
                    float edgeStartY=(float)Math.round((edge.start.getY()));

                    float edgeEndX=(float)Math.round((edge.end.getX()));
                    float edgeEndY=(float)Math.round((edge.end.getY()));


                    myParent.stroke(outerColor);

                    myParent.strokeWeight(thickWeight);
                    if(edge.inner){
                        myParent.stroke(255,0,0);
                    }

                    myParent.line(edgeStartX,edgeStartY,edgeEndX,edgeEndY);
                }



            }
        }

        catch (java.lang.NullPointerException e){
            System.out.println("you tried to draw an edge in the voronoi that doesn't exist");
        }
    }


     //generates random gaussian double
    private double getRandomRanged(double aStart, double aEnd, Random aRandom){
        if ( aStart > aEnd ) {
            throw new IllegalArgumentException("Start cannot exceed End.");
        }
        //get the range, casting to long to avoid overflow problems
        double range = aEnd - aStart;
        // compute a fraction of the range, 0 <= frac < range
        double fraction = (range * aRandom.nextGaussian());
        double randomNumber =  (fraction + aStart);
        return randomNumber;
    }


}