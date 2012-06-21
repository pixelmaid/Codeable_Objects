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

package com.primitives2d;

import com.datastruct.DCHalfEdge;
import com.datastruct.DoublyConnectedEdgeList;
import com.math.CompPoint;
import com.math.Geom;
import processing.core.PApplet;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;


public class Disc implements Comparable<Disc> {

    public CompPoint origin;
    public double radius;
    public DoublyConnectedEdgeList border;
    public int setColor;
    public boolean insideGrid = false;
    public boolean kept = false;
    public int numTouching = 0;
    public Vector<Disc> discsTouching;

    public Disc(double x, double y, double radius) {
        this.radius = radius;
        this.origin = new CompPoint(x, y);
        this.discsTouching = new Vector<Disc>();


    }

    public int partInGrid(GridSquare grid) {

        if (Geom.pointInPolygon(origin, grid)) {
            return 1;
        }

        for (int i = 0; i < grid.edges.size(); i++) {
            DCHalfEdge edge = grid.edges.get(i);
            if (Geom.discEdgeIntersect(this, edge)) {
                return 2;
            }
        }

        return 0;


    }

    public boolean wholeInGrid(GridSquare grid) {

        boolean originIn = Geom.pointInPolygon(origin, grid);

        boolean edgesTouch = false;
        for (int i = 0; i < grid.edges.size(); i++) {
            DCHalfEdge edge = grid.edges.get(i);
            if (Geom.discEdgeIntersect(this, edge)) {
                edgesTouch = true;
            }
        }

        if ((originIn) && (!edgesTouch)) {
            return true;
        } else {
            return false;
        }


    }

    public boolean discOverlap(Disc disc) {
        double dist = Geom.distance(this.origin, disc.origin);
        if (dist < this.radius + disc.radius) {
            disc.numTouching++;
            this.numTouching++;
            disc.addTouching(this);
            this.addTouching(disc);
            return true;
        } else {
            return false;
        }

    }

    public void draw(PApplet myParent) {
        myParent.stroke(0, 255, 0);
        myParent.strokeWeight(1);
        if (insideGrid && kept) {
            myParent.fill(0, 0, 255);
        }

        if (insideGrid && !kept) {
            myParent.fill(255, 0, 255, 70);
        }

        if (!insideGrid && !kept) {
            myParent.fill(255, 255, 255, 50);

        }
        myParent.ellipse((float) origin.getX(), (float) origin.getY(), (float) radius * 2, (float) radius * 2);
    }


    public void resetTouching() {
        this.discsTouching = new Vector<Disc>();
        numTouching = 0;

    }

    public void addTouching(Disc d) {
        this.discsTouching.addElement(d);
        Collections.sort(discsTouching, new DiscTouchInternal());

    }

    public boolean removeTouching(Disc d) {
        if (this.discsTouching.indexOf(d) != -1) {
            this.discsTouching.removeElement(d);
            numTouching--;
            return true;
        } else {
            return false;
        }


    }

    @Override
    public int compareTo(Disc disc) {
        if (this.discOverlap(disc)) {

            return 1;


        } else {
            return -1;
        }
    }

}


class DiscTouchInternal implements Comparator<Disc> {
    public int compare(Disc a, Disc b) {
        return (a.numTouching < b.numTouching) ? -1 : (a.numTouching > b.numTouching) ? 1 : 0;
    }
}