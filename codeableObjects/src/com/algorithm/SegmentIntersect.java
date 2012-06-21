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

import com.datastruct.SegmentTree;
import com.math.CompPoint;
import com.math.Intersection;
import com.math.Segment;
import processing.core.PApplet;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;


public class SegmentIntersect {
    private Segment[] initSegments;
    private SegmentTree statusStruct;
    private Vector<SegEvent> eventQueue;
    private PApplet myParent;

    public SegmentIntersect(PApplet theParent) {
        myParent = theParent;

    }

    public void defineSegments(Segment[] _segments) {
        statusStruct = new SegmentTree();
        initSegments = _segments;
        eventQueue = new Vector<SegEvent>(_segments.length * 2);
        //Arrays.sort(initSegments, new SegmentCmp());
        for (int i = 0; i < initSegments.length; i++) {
            Segment seg = initSegments[i];
            SegEvent e1 = new SegEvent(seg.p1);
            e1.addSegment(seg);
            SegEvent e2 = new SegEvent(seg.p2);
            eventQueue.add(e1);
            eventQueue.add(e2);
            //myParent.println(initSegments[i].p1.getY());

        }

        Collections.sort(eventQueue, new SegEventSorter());

        for (int j = 0; j < eventQueue.capacity(); j++) {
            SegEvent e = (SegEvent) eventQueue.get(j);
            //PApplet.println(e.point.getY());
        }

    }

    private void handleEventPoint(SegEvent event) {
        CompPoint eP = event.point;
        Vector<Segment>[] matchingSegments = statusStruct.findMatchingSegs(eP);

        Vector<Segment> lowerP = matchingSegments[0];
        Vector<Segment> upperP = matchingSegments[1];
        Vector<Segment> containedPoints = matchingSegments[2];


        //detectUnions(lowerP,upperP,eP,false,false); I don't think I need this?
        detectUnions(lowerP, containedPoints, eP, true, false);
        int upperIntersections = detectUnions(upperP, containedPoints, eP, false, true);

        if (upperIntersections == 0) {
            //Segment segLeft = statusStruct.findLeftNeighbor(event.segment);
            //Segment segRight =statusStruct.findRightNeighbor(event.segment);

            //findNewEvent(segLeft,segRight,event);
        } else {


        }

    }


    private int detectUnions(Vector<Segment> a, Vector<Segment> b, CompPoint p, boolean delete, boolean insert) {
        int i = 0;
        int j = 0;
        int m = a.capacity();
        int n = b.capacity();
        int numUnions = 0;
        while (i < m && j < n) {
            int comp = a.get(i).compareTo(b.get(j));
            if (comp == -1)
                i++;
            else if (comp == 1)
                j++;
            else if (comp == 0)/* if a == b */ {
                //union detected, return union and create intersection?
                numUnions++;
                Intersection intersection = new Intersection(a.get(i), b.get(i), p);
                if (delete) {
                    statusStruct.remove(a.get(i));
                    statusStruct.remove(b.get(i));
                }
                if (insert) {

                    statusStruct.add(a.get(i));
                    statusStruct.add(b.get(i));

                }
                i++;
                j++;

            }
        }
        return numUnions;

    }

    private void findNewEvent(Segment segLeft, Segment segRight, SegEvent eventPoint) {


    }

}


class SegEvent implements Comparable<SegEvent> {
    public CompPoint point;
    public Segment segment;
    public Boolean hasSegment = false;

    public SegEvent(CompPoint _point) {
        point = _point;
    }

    public void addSegment(Segment _segment) {
        segment = _segment;
        hasSegment = true;
    }

    public int compareTo(SegEvent o) {
        return (int) (this.point.getX() - o.point.getX());
    }

}

class SegEventSorter implements Comparator<SegEvent> {
    public int compare(SegEvent a, SegEvent b) {
        if (a.point.getY() < b.point.getY()) {
            return -1;
        }
        //if line is horizontal, leftmost point is start point
        else if (a.point.getY() == b.point.getY()) {
            if (a.point.getX() < b.point.getX()) {
                return -1;
            } else {
                return 1;
            }
        } else if (a.point.getY() > b.point.getY()) {
            return 1;
        } else {
            return 0;
        }

    }

}