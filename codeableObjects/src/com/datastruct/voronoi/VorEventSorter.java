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

import java.util.Comparator;

public class VorEventSorter implements Comparator<VorEvent> {
	 public int compare(VorEvent a, VorEvent b) {
		 if(a.point.getY()< b.point.getY()){ 
		       return 1;
		     }
		     //if line is horizontal, leftmost point is start point
		     else if(a.point.getY()==b.point.getY()){
		       if(a.point.getX()<b.point.getX()){
		    	   return -1;
		       }
		       else{
		    	   return 1;
		       }
		     }
		     else if(a.point.getY()> b.point.getY()){ 
		    	 return -1;
		     }
		     else{
		    	 return 0;
		     }

	    }
}