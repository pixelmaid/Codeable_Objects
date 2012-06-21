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

package com.datastruct;

import com.math.CompPoint;
import com.math.Segment;

import java.util.List;
import java.util.Vector;


public class SegmentTree extends BinarySearchTree<Segment> {

    //not sure this is the most efficient method
    public Vector<Segment>[] findMatchingSegs(CompPoint p) {

        Vector<Segment> lowerPoints = new Vector<Segment>();
        Vector<Segment> upperPoints = new Vector<Segment>();
        Vector<Segment> containedPoints = new Vector<Segment>();

        //detects length of adjacent pair segment;
        boolean startFlag = false;
        boolean endFlag = false;

        List<Segment> treeList = this.toList();

        for (int i = 0; i < treeList.size(); i++) {
            switch (treeList.get(i).containsPoint(p)) {
                case -1:
                    upperPoints.addElement(treeList.get(i));
                    if (!startFlag) ;
                    startFlag = true;
                    break;
                case 1:
                    lowerPoints.addElement(treeList.get(i));
                    if (!startFlag) ;
                    startFlag = true;
                    break;
                case 2:
                    containedPoints.addElement(treeList.get(i));
                    if (!startFlag) ;
                    startFlag = true;
                    break;
                case 0:
                    if (startFlag) ;
                    endFlag = true;
            }
            if ((startFlag) && (endFlag)) {
                break;
            }
        }
        Vector<Segment>[] matchedPoints = new Vector[3];
        matchedPoints[0] = lowerPoints;
        matchedPoints[1] = upperPoints;
        matchedPoints[2] = containedPoints;

        return matchedPoints;

    }


}


