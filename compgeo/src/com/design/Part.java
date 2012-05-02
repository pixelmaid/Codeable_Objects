package com.design;

import com.datastruct.DCHalfEdge;
import com.datastruct.DoublyConnectedEdgeList;
import com.math.CompPoint;
import com.math.Geom;

import java.util.Vector;

public class Part extends DoublyConnectedEdgeList {
		public double width;
		public double height;
		public CompPoint focus;
		
	public Part(double width, double height){
			this.width = width;
			this.height = height;
			//focus starts in the center by default
			this.focus=new CompPoint(width/2,height/2);
			
		}
		
		//translates all edges to a new point;
		public void translate(double x, double y){
			for(int i = 0; i<edges.size();i++){
				DCHalfEdge currentEdge = edges.get(i);
				currentEdge.translate(x, y,focus);
			}
			focus = new CompPoint(x,y);
		}
		
		//rotates all edges around the focus by an increment of theta;
		public void rotate(double theta, CompPoint _focus){
			for(int i = 0; i<edges.size();i++){
				DCHalfEdge currentEdge = edges.get(i);
				CompPoint start = currentEdge.start;
				CompPoint end = currentEdge.end;

				double[] startRT = Geom.cartToPolar(start.getX() - _focus.getX(), start.getY() - _focus.getY());
				double[] endRT = Geom.cartToPolar(end.getX() - _focus.getX(), end.getY() - _focus.getY());
				double startTheta = startRT[1];
				double startR = startRT[0];
				
				double endTheta = endRT[1];
				double endR = endRT[0];
				
				double newStartTheta = startTheta+theta;
				double newEndTheta= endTheta+theta;
				currentEdge.rotate(startR,newStartTheta,endR,newEndTheta,_focus);
				
				
				//System.out.println("startX="+start.getX()+" start y="+start.getY()+" r="+startR+" theta="+startTheta+" new theta="+newStartTheta+"  newX="+(newStart.getX()+_focus.getX())+" newY="+(newStart.getY()+_focus.getY()));
				
			}
		}
		
		
		
		
		public DCHalfEdge findMerge (DCHalfEdge edge,Part border){ //finds the appropriate intersection for a merge;

			System.out.println("border size="+border.edges.size());

			boolean startInPolygon = Geom.pointInComPolygon(edge.start, border);
			boolean endInPolygon = Geom.pointInComPolygon(edge.end, border);

			System.out.println("start in polygon ="+startInPolygon);
			System.out.println("end in polygon ="+endInPolygon);
			
			
			if(!startInPolygon && !endInPolygon){
			
				
				Vector<DCHalfEdge> intersectedEdges = Geom.lineIntersectsPolygon(edge, border);
				
				if(intersectedEdges.size()>0){
					
					if(intersectedEdges.size()==1){
						System.out.println("segment is tangent");
					
						CompPoint intersection1 = Geom.findIntersectionPoint(edge, intersectedEdges.get(0));
						System.out.println("intersected edge num="+border.edges.indexOf(edge.intersectedEdge));
						return edge;
					}
					else if(intersectedEdges.size()==2){
						System.out.println("segment intersects twice");
						CompPoint intersection1 = Geom.findIntersectionPoint(edge, intersectedEdges.get(0));
						CompPoint intersection2 = Geom.findIntersectionPoint(edge, intersectedEdges.get(1));
						return new DCHalfEdge(intersection1,intersection2);
					}
					else if(intersectedEdges.size()==4){
						System.out.println("segment intersects 4");
						CompPoint intersection1 = Geom.findIntersectionPoint(edge, intersectedEdges.get(0));
						CompPoint intersection2 = Geom.findIntersectionPoint(edge, intersectedEdges.get(2));
						return new DCHalfEdge(intersection1,intersection2);
					}
					else{
						System.out.println("segment intersects "+intersectedEdges.size());
						return edge;
					}
						
					
				}
				else{
					System.out.println("segment does not intersect");
					return null;
				}
			}
			
			
			else if(startInPolygon && endInPolygon){
				System.out.println("segment is inside of polygon");
				return edge;
			}
			else{
				if(startInPolygon && !endInPolygon){
					System.out.println("start is in polygon");
					edge.end = Geom.getIntersectedEdgePoint(edge.start, edge.end, edge, border);
				}
				else if (!startInPolygon && endInPolygon){
					System.out.println("end is in polygon");
					edge.start = Geom.getIntersectedEdgePoint(edge.end, edge.start, edge, border);
					
				}
				System.out.println("intersectedEdge="+edge.intersectedEdge);
				return edge;

			}
		}
		
		
		public Part returnCopy(){
			Part copy = new Part(this.width,this.height);
			return copy;
		}
		
		
		
		
	

}
