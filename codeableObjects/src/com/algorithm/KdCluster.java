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

import java.util.Vector;

/**
 * User: jenniferjacobs
 * Date: 4/30/12
 * Time: 8:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class KdCluster<E extends Comparable<? super E>> {


    public Vector<Vector<E>> buildTree(Vector<E> dataPoints) {

        //Clear the old tree structure
        Vector<Vector<E>> clusters = new Vector<Vector<E>>();

        //Create the temporary data structures
        int N = dataPoints.size();
        boolean[] processed = new boolean[N];
        for (int i = 0; i < N; i++) {
            processed[i] = false;
        }


        //The main search loop
        for (int p = 0; p < N; p++) {
            Vector<E> que = new Vector<E>();
            //If p has yet to be processed then add it to the que and start a new search
            if (!processed[p]) {

                E newPoint = dataPoints.get(p);
                que.addElement(newPoint);
                processed[p] = true;
                System.out.println("got new Point");
                for (int q = 0; q < que.size(); q++) {

                    for (int i = 0; i < N; i++) {

                        if (!processed[i]) {
                            int dist = que.get(q).compareTo(dataPoints.get(i));
                            System.out.println("dist=" + dist);

                            //If the point is less than the threshold and has not been used then add it to the current que
                            if (dist == 1) {

                                E newPointB = dataPoints.get(i);

                                que.addElement(newPointB);
                                processed[i] = true;
                            }

                        }

                    }
                }
                //Add que as a new cluster set

                clusters.addElement(que);
            }


        }

        return clusters;
    }


}
