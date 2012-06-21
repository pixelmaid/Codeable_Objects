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

import com.datastruct.BinarySearchTree;
import com.math.CompPoint;


public class VorTree<E extends Comparable<? super E>> extends BinarySearchTree {
    public Arc root;
    public int size = 0;


    public VorTree() {

    }


    /*
     public void addArc(CompPoint point){
         size++;
         if (root == null) {
             this.root = new Arc(point,null,this,0);
           root.column=0;
           root.name = "ROOT";
           addNodeToLevel(root);
       
          
         }
     }
         */


    /*public Vector<Arc> insertTriLeaf(Arc par, CompPoint p, DCHalfEdge edgeLeft, DCHalfEdge edgeRight) {
		
         //arc intersection that holds the two new arcs created by new point p
         Arc leftIntersection = new Arc(new CompPoint(0,0),par,this,0);
         leftIntersection.edge = edgeLeft;//add the edge side that will be traced out by the intersection
         //set left intersection to arc intersection type and provide it with tuple of intersection arcs
         leftIntersection.setType(par.value,p);
		
         //create 3 new leaves to store the two new arcs created by the new point and the original arc
         Arc p0 = new Arc(par.value,leftIntersection,this,0);//left most arc
         Arc p1 = new Arc(p,leftIntersection,this,0); // middle arc
         Arc p2 = new Arc(par.value,par,this,0);//rightmost arc

         //reconfigure par
         par.edge = edgeRight;//add the edge side that will be traced out by the intersection
         par.setRight(p2); //right child is p2
         par.setLeft(leftIntersection); //left child is left intersection;
         //set par to arc intersection type and provide it with tuple of intersection arcs
         par.setType(p,par.value);
		
         //set children of left intersection
         leftIntersection.setLeft(p0);
         leftIntersection.setRight(p1);
		
         leftIntersection.value.name="intersection of "+p0.value.name+" and "+p1.value.name; //debugging code

		
		
		
		
         Vector<Arc> duplicates = new Vector<Arc>(2);
         //return p0 and p2 to check for circle events
         duplicates.add(p0);
         duplicates.add(p2);
		
         return duplicates;
		
     }
     */

    //finds correct vertical arc above point and returns as leaf;
    /*public Arc GetParabolaByX(CompPoint _value,double ly)
     {
         Arc par = root;
         Vector<Arc> leaves = new Vector<Arc>();
         for(int i = 0;i<this.levels.size();i++){//sloppy
             Vector currentLevel = (Vector)this.levels.get(i);
             for(int j = 0;j<currentLevel.size();j++){
                 Arc currentArc = (Arc)currentLevel.get(j);
                 if(currentArc.isLeaf){
                     leaves.addElement(currentArc);
					
                 }
             }
		
			
         myParent.print("number of leaves=");
         myParent.println(leaves.size());
			
             double y = 0;
             for(int k=0; k<leaves.size();k++){
                 CompPoint currentPoint = leaves.get(k).value;
                 double p = Math.abs((ly-currentPoint.getY())/2);
                 double relativeX = currentPoint.getX()-_value.getX();
                 double relativeYIntercept = Math.pow(relativeX,2)/4*p;
                 double yIntercept = currentPoint.getY()+p-relativeYIntercept;
                 myParent.print("y intercept=");
                 myParent.println(yIntercept);
                 if(y==0){
                     y = yIntercept;
                     par = leaves.get(k);
                 }
                 else if(yIntercept>y){
                     y = yIntercept;
                     par = leaves.get(k);
                 }
             }
			
         }
         return par;
		
     }
     */

    //finds x intersection of arcs according to parabola equation
    /*public double getXOfEdge(Arc par, double y) //possible point of error;
     {
			
		
         Arc left = par.getLeftChildArc();
         Arc right= par.getRightChildArc();

		
         CompPoint p = left.value;
         CompPoint r = right.value;
		
         double dp = 2.0 * (p.getY() - y);
         double a1 = 1.0 / dp;
         double b1 = -2.0 * p.getX() / dp;
         double c1 = y + dp / 4 + p.getX() * p.getX() / dp;
				
                dp = 2.0 * (r.getY() - y);
         double a2 = 1.0 / dp;
         double b2 = -2.0 * r.getX()/dp;
         double c2 = y + dp / 4 + r.getX() * r.getX() / dp;
				
         double a = a1 - a2;
         double b = b1 - b2;
         double c = c1 - c2;
				
         double disc = b*b - 4 * a * c;
         double x1 = (-b + Math.sqrt(disc)) / (2*a);
         double x2 = (-b - Math.sqrt(disc)) / (2*a);

         double ry;
         if(p.getY() < r.getY() ) ry =  Math.max(x1, x2);
         else ry = Math.min(x1, x2);

         return ry;
     }
	
     */


    public VorNode<E> searchArc(CompPoint _key) {

        if (root == null)
            return null;
        else
            //checkRootNode("search check");
            return (VorNode<E>) root.searchArc(_key);

    }

}
