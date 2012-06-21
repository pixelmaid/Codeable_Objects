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

package com.datastruct.voronoi;

import com.datastruct.DCHalfEdge;
import com.math.CompPoint;

public class Arc extends VorNode implements Comparable<Arc> { //defines parabolas for generating voronoi diagram using points as sites of definition

    public boolean isLeaf;  // flag to whether arc is internal node or leaf
    public CompPoint site; //pointer to the focus point of the parabola (when it is a parabola)
    public DCHalfEdge edge; //pointer to the edge of (when arc is an internal node)
    public VorEvent circlePointer; //pointer to the circle event for when the arc disappears
    public Arc parent = null; //pointer to the parent node in the tree


    public static VorTree parentTree; //parent BST

    /*public CompPoint leftArcPoint; //not needed?
     public CompPoint rightArcPoint;*/

    private Arc left; //left child
    private Arc right; //right child


    public Arc(CompPoint site) {//constructor defines site

        super(site, null, null, 0);

        this.site = site;
        this.isLeaf = true;
        this.circlePointer = null;
        this.edge = null;
        this.parent = null;
    }


    public Arc() {//constructor for non site;
        super(new CompPoint(0, 0), null, null, 0);

        this.site = null;
        this.isLeaf = false;
        this.circlePointer = null;
        this.edge = null;
        this.parent = null;
    }

    public void setType() {//sets type to internal node
        this.isLeaf = false;
    }


    public void setLeft(Arc p) //sets the left child
    {

        left = p;
        p.parent = this;
        //p.level = this.level+1;
        //this.parentTree.addNodeToLevel(p);

    }

    public void setRight(Arc p) //sets the right child
    {
        right = p;
        p.parent = this;
        //p.level = this.level+1;
        //this.parentTree.addNodeToLevel(p);

    }

    public Arc left() //returns the left child
    {
        return left;
    }

    public Arc right() //returns the right child
    {
        return right;
    }

    public static Arc getLeft(Arc p) { //returns the closest left leaf of the tree
        return getLeftChildArc(getLeftParent(p));
    }

    public static Arc getRight(Arc p) { //returns the closest right leaf of the tree
        return getRightChildArc(getRightParent(p));
    }

    public static Arc getLeftParent(Arc p) { //returns the closest parent on the left
        if (p.parent == null) {
            return null;
        }

        Arc par = p.parent;
        Arc pLast = p;
        while (par.left() == pLast) {
            if (par.parent == null) {
                //parentTree.myParent.println("could not find left parent");
                return null;
            }

            pLast = par;
            par = par.parent;
        }
        return par;
    }

    public static Arc getRightParent(Arc p) { //returns the closest parent on the right

        Arc par = p.parent;
        Arc pLast = p;

        while (par.right() == pLast) {
            if (par.parent == null) {
                //parentTree.myParent.println("could not find right parent");
                return null;
            }

            pLast = par;
            par = par.parent;
        }
        return par;
    }

    public static Arc getLeftChildArc(Arc p) { //returns the closest leaf which is on the left of the current node

        if (p == null) {
            return null;
        }
        Arc par = p.left();
        while (!par.isLeaf) par = par.right();
        return par;

    }

    public static Arc getRightChildArc(Arc p) { //returns the closest leaf which is on the right of the current node

        if (p == null) {
            return null;
        }
        Arc par = p.right();
        while (!par.isLeaf) par = par.left();
        return par;
    }


    public int compareTo(Arc a) { //comparator statement
        return (this.site.getX() < a.site.getX()) ? -1 : (this.site.getX() > a.site.getX()) ? 1 : 0;
    }


}
