package com.datastruct.voronoi;

import java.util.Comparator;

public class VorEventSorter implements Comparator<VorEvent>{
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