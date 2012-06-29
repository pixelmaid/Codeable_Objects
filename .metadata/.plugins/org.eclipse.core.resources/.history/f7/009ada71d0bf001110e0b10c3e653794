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

package com.math;

import com.primitives2d.Disc;
import com.datastruct.DCHalfEdge;
import com.datastruct.DoublyConnectedEdgeList;
import processing.core.PApplet;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Vector;

public class Geom {

    public static CompPoint polarToCart(double r, double theta) {
        double x = Math.cos(theta * Math.PI / 180.0) * r;
        double y = Math.sin(theta * Math.PI / 180.0) * r;

        /*double[] xY = new double[2];
          xY[0]=x;
          xY[1] = y;
          return xY;*/
        return new CompPoint(x, y);

    }

    public static double[] cartToPolar(double x, double y) {

        double r = 0.0;
        double theta = 0.0;

        r = Math.sqrt((x * x) + (y * y));

        int type = 0;
        if (x > 0 && y >= 0) type = 1;
        if (x > 0 && y < 0) type = 2;
        if (x < 0) type = 3;
        if (x == 0 && y > 0) type = 4;
        if (x == 0 && y < 0) type = 5;
        if (x == 0 && y == 0) type = 6;

        //Find theta
        switch (type) {
            case (1):
                theta = Math.atan(y / x) * (180.0 / Math.PI);
                break;
            case (2):
                theta = (Math.atan(y / x) + 2 * Math.PI) * (180.0 / Math.PI);
                break;
            case (3):
                theta = (Math.atan(y / x) + Math.PI) * (180.0 / Math.PI);
                break;
            case (4):
                theta = (Math.PI / 2.0) * (180.0 / Math.PI);
                break;
            case (5):
                theta = ((3 * Math.PI) / 2.0) * (180.0 / Math.PI);
                break;
            case (6):
                theta = 0.0;
                break;
            default:
                theta = 0.0;
                break;
        }

        double[] rTheta = new double[2];
        rTheta[0] = r;
        rTheta[1] = theta;

        return rTheta;
    }


    public static DCHalfEdge getIntersectedEdge(CompPoint focus, CompPoint direction, DCHalfEdge parentEdge, DoublyConnectedEdgeList border) {


        DCHalfEdge edge = new DCHalfEdge(focus, direction);

        double dx = direction.getX() - focus.getX(); //direction x
        double dy = direction.getY() - focus.getY(); //direction y

        DCHalfEdge borderEdge = null; //selected border edge for intersection;

        double[] thetas = border.getBorderPoints(focus); //degrees of all points along the border

        double edgeTheta = Geom.cartToPolar(dx, dy)[1];//degree of edge
        double selectedTheta = -1;
        //myParent.println("\n\n\n edgeTheta="+edgeTheta+"\n\n\n");

        for (int i = 0; i < thetas.length; i++) { //iterate through all edges and look for correct point of intersection
            //myParent.println("edge "+i+" theta="+thetas[i]+" x="+border.edges.get(i).start.getX()+" y="+border.edges.get(i).start.getY());
            int after = i + 1;
            if (i == thetas.length - 1) {
                after = 0;
            }

            if (thetas[after] > thetas[i]) {//special case where angle of previous edge is greater than angle of current edge
                //myParent.println("call special case");
                if (edgeTheta >= thetas[after] || edgeTheta < thetas[i]) {//detects quadrant of intersection for special case


                    if (edgeTheta > thetas[i]) {
                        borderEdge = border.edges.get(i - 1);

                        selectedTheta = thetas[i];
                    } else {
                        borderEdge = border.edges.get(i);


                        selectedTheta = thetas[after];
                    }

                    /*myParent.println("\n\n\n edgeTheta="+edgeTheta);
                         myParent.println("theta i="+thetas[i]);
                         myParent.println("theta after="+thetas[after]);
                         myParent.println("selected theta="+selectedTheta);
                         myParent.println("start x,y,="+focus.getX()+","+focus.getY());
                         myParent.println("end x,y,="+direction.getX()+","+direction.getY());
                         myParent.println("intersection x,y,="+intersection.getX()+","+intersection.getY()+"\n\n\n");*/

                    parentEdge.infiniteEdge = 1;
                    break;


                }
            } else {//otherwise all thetas should be greater than the preceeding theta

                if (edgeTheta <= thetas[i] && edgeTheta > thetas[after]) {//detects quadrant of intersection

                    borderEdge = border.edges.get(i);
                    selectedTheta = thetas[i];
                    break;


                }
            }
        }


        return borderEdge;


    }

    public static CompPoint getIntersectedEdgePoint(CompPoint focus, CompPoint direction, DCHalfEdge parentEdge, DCHalfEdge borderEdge) {


        CompPoint intersection = null;

        DCHalfEdge edge = new DCHalfEdge(focus, direction);

        double dx = direction.getX() - focus.getX(); //direction x
        double dy = direction.getY() - focus.getY(); //direction y

        
        edge.intersectedEdge = borderEdge;
        
        //System.out.println("intersected edge="+border.edges.indexOf(borderEdge));
        intersection = findIntersectionPoint(edge, borderEdge);
        //myParent.println("\n\n\n selectedTheta="+selectedTheta+"\n\n\n");

        return intersection;
    }

  //ray determined point in polygon (returns bool)
    public static boolean rayPointInPolygon(CompPoint q, DoublyConnectedEdgeList p){
    	
    	char type = rayTypePointInPolygon(q, p);
    	if(type !='o'){
    			return true;
    	}
    	else{
    		return false;
    	}
    }
    
    //ray determined point in polygon (returns type)
    
    public static char rayTypePointInPolygon(CompPoint q, DoublyConnectedEdgeList p){
    	double width = 5000;
    	int i,i1; //point index; i1 = 1-1 mod n;
    	int n = p.edges.size();
    	int d=2; //dimension index
    	int Rcross = 0; //number of right edge/ray crossings
    	int Lcross = 0; //number of left edge/ray crossings
    	boolean Rstrad, Lstrad; //flags that indicate the edge straddles the x axis 
    	//Vector<CompPoint> p = new Vector<CompPoint>();
    	
    	// for each edge in poly, see if crosses rays
    	/*for(i=0;i<n;i++){
    		DCHalfEdge edge = p.edge.get(i);
    		CompPoint newPoint = new CompPoint(edge.start.getX()-q.getX(),edge.start.getY()-q.getY());
    		
    	}*/
    	
    	
    	//check if p is a vertex of the polygon
    	for(i=0; i<n;i++){
    		i1 = (i+n-1)%n;
    		DCHalfEdge edge = p.edges.get(i);
    		if(q.compareTo(edge.start)==0||q.compareTo(edge.end)==0 ){
    			return 'v';
    		}
    	}
    		
    		DCHalfEdge rEdge = new DCHalfEdge(q,new CompPoint(width,q.getY()));
    		DCHalfEdge lEdge = new DCHalfEdge(q,new CompPoint(0,q.getY()));

    		Rcross = Geom.edgeIntersectsPolygon(rEdge, p).size();
    		Lcross = Geom.edgeIntersectsPolygon(lEdge, p).size();
    		//System.out.println("Rcross = "+Rcross+",Lcross="+Lcross);
    		/*i1=(i+n-1)%n;
    		DCHalfEdge edge1 = p.edges.get(i1);
    		Rstrad = (edge.start.getY()>q.getY())!=(edge1.start.getY()>q.getY());
    		Lstrad = (edge.start.getY()<q.getY())!=(edge1.start.getY()<q.getY());
    		
    		if(Rstrad|| Lstrad){
    			double x = (edge.start.getX()*edge1.start.getY()-edge1.start.getX()*edge.start.getY())/(edge1.start.getY()-edge.start.getY());
    			
    			if(Rstrad && x> q.getX()){
    				Rcross++;
    			}
    			if(Lstrad && x> q.getX()){
    				Lcross++;
    			}
    			
    		}
    	}
    	*/
    		// q is on an edge if L/Rcross counts are not the same parity
    		if((Rcross % 2) != (Lcross %2)){
				return 'e';
			}
    		
    		if((Rcross %2)==1){
    			return 'i';
    		}
    		else{
    			return 'o';
    		}
    		
    	
    }
    
    

    //uses winding number algorithim, works for irregular convex polygons
    public static boolean pointInComPolygon(CompPoint testPoint, DoublyConnectedEdgeList border) {
        int angleCount = 0;

        for (int i = border.edges.size() - 1; i >= 0; i--) {
            DCHalfEdge edge = border.edges.get(i);
            if (edge.start.getY() >= edge.end.getY()) {
                if (leftOn(edge.start, edge.end, testPoint)) {
                    angleCount++;
                }
            } else {
                if (leftOn(edge.end, edge.start, testPoint)) {
                    angleCount--;
                }
            }
        }

        if (angleCount != 0) {
            return true;
        } else {
            return false;
        }

    }

    //determines if a given point is in a polygon defined by a doubly connected edge list
    //only works for regular polygons
    public static boolean pointInPolygon(CompPoint testPoint, DoublyConnectedEdgeList border) {

        for (int i = border.edges.size() - 1; i >= 0; i--) {
            DCHalfEdge edge = border.edges.get(i);
            if (leftOn(edge.start, edge.end, testPoint)) {
                return false;
            }
        }
        return true;
    }


    //determines if a segment intersects a polygon defined by a doubly connected edge list and returns edges of intersection if they exist
    public static Vector<DCHalfEdge> edgeIntersectsPolygon(DCHalfEdge edge, DoublyConnectedEdgeList border) {
        Vector<DCHalfEdge> intersectedEdges = new Vector<DCHalfEdge>();
        for (int i = border.edges.size() - 1; i >= 0; i--) {
            DCHalfEdge borderEdge = border.edges.get(i);
            if (lineIntersect(borderEdge.start, borderEdge.end, edge.start, edge.end)) {
                intersectedEdges.addElement(borderEdge);
            }
        }
        return intersectedEdges;
    }


    //detects if a line intersects an edge but does not return the point(s) of intersection
    public static boolean lineIntersect(CompPoint edgeStart, CompPoint edgeEnd, CompPoint testPointStart, CompPoint testPointEnd) {


        if (intersectProp(edgeStart, edgeEnd, testPointStart, testPointEnd))
            return true;
        else if (between(edgeStart, edgeEnd, testPointStart) ||
                between(edgeStart, edgeEnd, testPointEnd) ||
                between(testPointStart, testPointEnd, edgeStart) ||
                between(testPointStart, testPointEnd, edgeEnd))
            return true;
        else {
            return false;

        }

    }


    public static boolean between(CompPoint edgeStart, CompPoint edgeEnd, CompPoint testPoint) {
        if (!collinear(edgeStart, edgeEnd, testPoint)) {
            return false;
        }
        if (edgeStart.getX() != edgeEnd.getX()) {
            return (edgeStart.getX() <= testPoint.getX()) && (testPoint.getX() <= edgeEnd.getX()) ||
                    (edgeStart.getX() >= testPoint.getX()) && (testPoint.getX() >= edgeEnd.getX());
        } else {
            return (edgeStart.getY() <= testPoint.getY()) && (testPoint.getY() <= edgeEnd.getY()) ||
                    (edgeStart.getY() >= testPoint.getY()) && (testPoint.getY() >= edgeEnd.getY());
        }

    }
    


    public static boolean intersectProp(CompPoint edgeStart, CompPoint edgeEnd, CompPoint testPointStart, CompPoint testPointEnd) {
        if (collinear(edgeStart, edgeEnd, testPointStart) ||
                collinear(edgeStart, edgeEnd, testPointEnd) ||
                collinear(testPointStart, testPointEnd, edgeStart) ||
                collinear(testPointStart, testPointEnd, edgeEnd)) {
            return false;
        }

        return Xor(left(edgeStart, edgeEnd, testPointStart), left(edgeStart, edgeEnd, testPointEnd))
                && Xor(left(testPointStart, testPointEnd, edgeStart), left(testPointStart, testPointEnd, edgeEnd));

    }

    public static boolean Xor(boolean x, boolean y) {

        return !x ^ !y;
    }

    public static boolean leftOn(CompPoint edgeStart, CompPoint edgeEnd, CompPoint testPoint) {
        return area2(edgeStart, edgeEnd, testPoint) >= 0;

    }


    public static boolean left(CompPoint edgeStart, CompPoint edgeEnd, CompPoint testPoint) {
        return area2(edgeStart, edgeEnd, testPoint) > 0;

    }

    public static boolean collinear(CompPoint edgeStart, CompPoint edgeEnd, CompPoint testPoint) {
        return area2(edgeStart, edgeEnd, testPoint) == 0;

    }

    public static double area2(CompPoint a, CompPoint b, CompPoint c) {
        double area = ((b.getX() - a.getX()) * (c.getY() - a.getY())) - ((c.getX() - a.getX()) * (b.getY() - a.getY()));
        return area;

    }


    //finds the point of intersection between two edges that are known to intersect
    public static CompPoint findIntersectionPoint(DCHalfEdge edge, DCHalfEdge borderEdge) {
        double mx = 0;
        double my = 0;

        if (Double.isInfinite(borderEdge.m)) {//check to see if slope is undefined (line is vertical)
            mx = borderEdge.start.getX();
            my = (mx * edge.m) + edge.b;
        } else if (Double.isNaN(borderEdge.m)) {//check to see if slope is NaN (line is horizontal)
            my = borderEdge.start.getY();
            mx = (my - edge.b) / edge.m;

        } else {
            mx = (edge.b - borderEdge.b) / (borderEdge.m - edge.m);//line has a slope
            my = (mx * edge.m) + edge.b;
        }

        CompPoint intersection = new CompPoint(mx, my);

        return intersection;
    }

    
    //finds the point of intersection between a line and an edge
    public static CompPoint findIntersectionPoint(DCHalfEdge borderEdge, CompPoint point, double m) {
        double mx = 0;
        double my = 0;
        double b = m*point.getX()-point.getY();
        
        
        if (Double.isInfinite(borderEdge.m)) {//check to see if slope is undefined (line is vertical)
            mx = borderEdge.start.getX();
            my = (mx * m) + b;
        } else if (Double.isNaN(borderEdge.m)) {//check to see if slope is NaN (line is horizontal)
            my = borderEdge.start.getY();
            mx = (my - b) / m;

        } else {
            mx = (b - borderEdge.b) / (borderEdge.m - m);//line has a slope
            my = (mx * m) + b;
        }

        CompPoint intersection = new CompPoint(mx, my);

        return intersection;
    }

    public static boolean discEdgeIntersect(Disc disc, DCHalfEdge edge) {
        Vec2d seg_a = new Vec2d(edge.start.getX(), edge.start.getY());
        Vec2d seg_b = new Vec2d(edge.end.getX(), edge.end.getY());
        CompPoint closest = closestPoint(seg_a, seg_b, disc.origin);
        double dist = new DCHalfEdge(closest, disc.origin).length;
        if (dist > disc.radius) {
            return false;
        } else {
            return true;
        }

    }

    public static double clamp(double X, double Min, double Max) {
        if (X > Max)
            X = Max;
        else if (X < Min)
            X = Min;

        return X;
    }


    public static CompPoint closestPoint(Vec2d seg_a, Vec2d seg_b, CompPoint circleOrigin) {


        Vec2d seg_v = seg_b.sub(seg_a);
        Vec2d pt_v = new Vec2d(circleOrigin.getX(), circleOrigin.getY()).sub(seg_a);
        Vec2d seg_v_unit = seg_v.div(seg_v.Length());
        double proj = pt_v.Dot(seg_v_unit);
        CompPoint closest;

        if (proj <= 0) {
            closest = new CompPoint(seg_a.x, seg_b.x);
        } else if (proj >= seg_v.Length()) {
            closest = new CompPoint(seg_b.x, seg_b.x);
        } else {
            Vec2d proj_v = seg_v_unit.mul(proj);
            Vec2d newVec = proj_v.add(seg_a);
            closest = new CompPoint(newVec.x, newVec.y);
        }
        return closest;

    }

    public static double distance(CompPoint a, CompPoint b) {

        double distance = Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
        return distance;

    }
  
 //find area of a polygon
    public static double SignedPolygonArea(DoublyConnectedEdgeList poly)
    {
	
    	int i,j;
    	double area = 0;
    	int N = poly.edges.size();
    	for (i=0;i<N;i++) {
    		j = (i + 1) % N;
    		area += poly.edges.get(i).start.getX() * poly.edges.get(j).start.getY();
    		area -= poly.edges.get(i).start.getY() * poly.edges.get(j).start.getX();
    	}
	area /= 2.0;
	
   //return(area);
  return(area < 0 ? -area : area); //for unsigned
}
 
 
//find centroid of a polygon

public static CompPoint findCentroid(DoublyConnectedEdgeList polygon)
{
	double cx=0,cy=0;
	double A= SignedPolygonArea(polygon);
	//System.out.println("signedArea="+A);
	CompPoint res;
	int i,j;
	int N = polygon.edges.size();
	double factor=0;
	Vector<CompPoint> verticies = new Vector<CompPoint>(0);
	
	for (i=N-1;i>=0;i--) {
		verticies.add(polygon.edges.get(i).start);
		//System.out.println(polygon.edges.get(i).start.getX()+","+polygon.edges.get(i).start.getY());
	}
	verticies.add(polygon.edges.get(N-1).start);
	//System.out.println("added");
	
	
	for (i=0;i<verticies.size();i++) {
		j = i+1;
		if(j>=verticies.size()){
			j=0;
		}
		factor=(verticies.get(i).getX()*verticies.get(j).getY()-verticies.get(j).getX()*verticies.get(i).getY());
		cx+=(verticies.get(i).getX()+verticies.get(j).getX())*factor;
		cy+=(verticies.get(i).getY()+verticies.get(j).getY())*factor;
	}
	A*=6.0f;
	factor=1/A;
	cx*=factor;
	cy*=factor;
	res = new CompPoint(cx,cy);
	//System.out.println("centroid="+cx+","+cy);
	return res;
} 
    
    
    //remove duplicate vertices from a polygon (accepts a dcedge list)
    public static Vector<CompPoint> removeDuplicateVerts(DoublyConnectedEdgeList poly){
    	Vector <CompPoint> verticies = new Vector<CompPoint>(0);
    	for(int i=0;i<poly.edges.size();i++)
    	{
    		verticies.add(poly.edges.get(i).start);
    		verticies.add(poly.edges.get(i).end);
    	}
    	return removeDuplicateVerts(verticies);
    }
    
    
    //remove duplicate vertices from a polygon (accepts a vector of points)
    public static Vector<CompPoint> removeDuplicateVerts(Vector<CompPoint> verticies){
    	//System.out.println("org#="+verticies.size());
    	Collection noDup = new LinkedHashSet(verticies);
    	verticies.clear();
    	verticies.addAll(noDup);
    	//System.out.println("new#="+noDup.size());

    	return verticies;
    	/*Vector <CompPoint> newVerts = new Vector<CompPoint>(0);
    	
    	for(int i=0;i<verticies.size();i++){
    		for(int j=0;j<verticies.size();j++){
    			if(j!=i){
    				System.out.println("found dup");
    			}
    		}
    	}*/
    	
    }
}
