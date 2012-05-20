
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

package com.math;

import java.awt.geom.Point2D;
import java.util.Comparator;

public class CompPoint extends Point2D implements Comparable<CompPoint> {
	private double x;
	private double y;
	public String name = "foo";
	
	public CompPoint(double _x, double _y){
		x=_x;
		y=_y;
	}
	
	@Override
	public double getX() {
		// TODO Auto-generated method stub
		return x;
	}

	@Override
	public double getY() {
		// TODO Auto-generated method stub
		return y;
	}

	public void setX(double x) {
		
		this.x=x;
	}

	
	public void setY(double y) {
		
		this.y=y;
	}
	
	@Override
	public void setLocation(double x, double y) {
		// TODO Auto-generated method stub

	}
	
	public int compareToX(CompPoint o){
	        return (this.getX() < o.getX()) ? -1 : (this.getX() > o.getX()) ? 1 : 0;
		
		
	}
	
	public int compareTo(CompPoint o) { 
	        if((this.getX()==o.getX())&&(this.getY()==o.getY())){
	        	return 0;
	        }
	        
			else if(this.getY()< o.getY()){ 
				 return -1;
			 	}
	        //if y coords are same, check x 
			 else if(this.getY()==o.getY()){
				 if(this.getX()<o.getX()){
					  return -1;
					  }
				 else{
					 return 1;
					     }
					}
			 else if(this.getY()> o.getY()){ 
					return 1;
					}
			else{
				return -2;
			}
	    }
	
	
    public CompPoint difference(CompPoint p){
        CompPoint d = new CompPoint(this.getX()-p.getX(),this.getY()-p.getY());
        return d;

    }

    public CompPoint add(CompPoint p){
        CompPoint d = new CompPoint(this.getX()+p.getX(),this.getY()+p.getY());
        return d;

    }

    public CompPoint scale(double scaleVal){
        CompPoint s = new CompPoint(this.getX()*scaleVal,this.getY()*scaleVal);
        return s;
    }


	public double angle(CompPoint p){

		double x = p.x-this.x;
		double y = p.y-this.y;

		return Geom.cartToPolar(x, y)[1];
	}
}

class CmpX implements Comparator<Point2D> {
    public int compare(Point2D a, Point2D b) {
        return (a.getX() < b.getX()) ? -1 : (a.getX() > b.getX()) ? 1 : 0;
    }
}

//compares y values of two points
class CmpY implements Comparator<Point2D> {
    public int compare(Point2D a, Point2D b) {
        return (a.getY() < b.getY()) ? -1 : (a.getY() > b.getY()) ? 1 : 0;
    }
}