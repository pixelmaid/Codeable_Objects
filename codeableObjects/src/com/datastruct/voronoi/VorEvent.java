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

import com.math.CompPoint;

public class VorEvent implements Comparable<VorEvent> {
    public CompPoint point; //site associated with event
    public Arc arc; // parabola associated with event
    public String type; //site event or circle event i.e "site" or "circle"
    public double y; //y value of site associated with event

    public VorEvent(CompPoint _point, String _type) {
        point = _point;
        type = _type;
    }


    public int compareTo(VorEvent o) { //compares events based on y coordinates
    	if(this.y>o.y){
        	return 1;
        }
        if(this.y<o.y){
        	return -1;
        }
        else{
        	return 0;
        }
    }


}

