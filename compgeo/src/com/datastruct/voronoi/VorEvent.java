package com.datastruct.voronoi;

import com.math.CompPoint;

public class VorEvent implements Comparable<VorEvent> {
	public CompPoint point; //site associated with event
	public Arc arc; // parabola associated with event
	public String type; //site event or circle event i.e "site" or "circle"
	public double y; //y value of site associated with event
	
	public VorEvent(CompPoint _point,String _type){
			point=_point;
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

