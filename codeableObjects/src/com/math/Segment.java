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

package com.math;


import processing.core.PApplet;

import java.util.Vector;

public class Segment implements Comparable<Segment> {
    public CompPoint p1;
    public CompPoint p2;
    public int color = 255;
    public boolean startPointIntersect = false;
    public boolean endPointIntersect = false;
    public Vector<CompPoint> containedPoints;
    private PApplet myParent;


    public Segment(PApplet theParent, CompPoint _p1, CompPoint _p2) {
        myParent = theParent;
        containedPoints = new Vector<CompPoint>(2);
        //determine start and end points
        if (_p1.getY() < _p2.getY()) {
            p1 = _p1;
            p2 = _p2;
        }
        //if line is horizontal, leftmost point is start point
        else if (_p1.getY() == _p2.getY()) {
            if (_p1.getX() < _p2.getX()) {
                p1 = _p1;
                p2 = _p2;
            } else {
                p2 = _p1;
                p1 = _p2;
            }
        } else {
            p2 = _p1;
            p1 = _p2;
        }
        containedPoints.add(p1);
        containedPoints.add(p2);
    }

    public int addContainedPoint(CompPoint p) {
        int p1Cont = p1.compareTo(p);
        int p2Cont = p2.compareTo(p);
        if (p1Cont == 0) {
            startPointIntersect = true;
        } else if (p2Cont == 0) {
            endPointIntersect = true;
        } else {
            containedPoints.addElement(p);
        }
        return containedPoints.capacity();


    }

    public int containsPoint(CompPoint p) {
        int p1Cont = p1.compareTo(p);
        int p2Cont = p2.compareTo(p);
        if (p1Cont == 0) {
            //return -1 if p is startPoint
            return -1;
        } else if (p2Cont == 0) {
            //return 1 if p is endPoint
            return 1;
        } else {
            for (int i = 1; i < containedPoints.capacity() - 1; i++) {
                int comp = containedPoints.get(i).compareTo(p);
                if (comp == 0) {
                    return 2;
                }
            }
            return 0;
        }

    }

    /*public CompPoint[] containsPointAsInterior(CompPoint p){
		  
           for (int i=0; i<containedPoints.capacity(); i++){
               int comp = containedPoints[i].compareTo(p);
               if(comp==0)
           }
		  
           return containedPoints

       }*/

    public int compareTo(Segment o) {
        if ((this.p1.compareTo(o.p1) == 0) && (this.p2.compareTo(o.p2) == 0)) {
            //myParent.println("compare returned 0");
            // returns zero if two segments are equal to one another;
            return 0;

        } else if (this.p1.getX() < o.p1.getX()) {
            //returns -1 if this.p1.x is less than the o.p1.x
            return -1;
        }
        //if line is horizontal, leftmost point is start point
        else if (this.p1.getX() == o.p1.getX()) {
            if (this.p1.getY() < o.p1.getY()) {
                //returns -1 if this.p1.x is equal to o.p1.x and this.p1.y < o.p1.y
                return -1;
            } else if (this.p1.getY() > o.p1.getY()) {
                //returns 1 if this.p1.x is equal to o.p1.x and this.p1.y > o.p1.y
                return 1;
            } else {
                //returns -3 if compare does not function (this should throw an error);
                return -3;
            }
        } else if (this.p1.getX() > o.p1.getX()) {
            //returns 1 if this.p1.x is greater than o.p1.x
            return 1;
        } else {
            //returns -2 if compare does not function (this should throw an error);
            return -2;
        }

    }

    public void setColor(int _color) {
        this.color = _color;
    }

    public int getColor() {
        return this.color;
    }

    public void draw(int alpha) {
        myParent.stroke(color, alpha);
        myParent.line((float) p1.getX(), (float) p1.getY(), (float) p2.getX(), (float) p2.getY());
        myParent.noStroke();
        myParent.fill(0, 255, 0, alpha);
        myParent.ellipse((float) p1.getX(), (float) p1.getY(), 2, 2);
        myParent.fill(0, 0, 255, alpha);
        myParent.ellipse((float) p2.getX(), (float) p2.getY(), 2, 2);

    }


}

