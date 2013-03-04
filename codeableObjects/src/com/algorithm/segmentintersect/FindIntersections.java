package com.algorithm.segmentintersect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Vector;

import com.datastruct.DCHalfEdge;
import com.datastruct.DoublyConnectedEdgeList;
import com.math.CompPoint;

import processing.core.PApplet;

public class FindIntersections {
	
	
	private PriorityQueueAdv queue;
	
	private RedBlackTree sweepLineStatus;
	
	private ArrayList<Intersection> intersections;
	
	public FindIntersections() {
		Comparator <EventPointSegment> comparator = new EventPointSegmentComparator();
		queue = new PriorityQueueAdv(10, comparator);
		
		sweepLineStatus = new RedBlackTree();
		
		
		intersections = new ArrayList<Intersection>();
		
	}
	
	

	//finds the intersections between different sets of edges
	public ArrayList<com.algorithm.segmentintersect.Segment> FindIntersectionSets(Vector<DoublyConnectedEdgeList> edgeSets) {
		ArrayList<Segment> segments = new ArrayList<Segment>();
		
		for(int i=0;i<edgeSets.size();i++){
			edgeSets.get(i).rotate(9.587, new CompPoint(0,0));
			Vector<DCHalfEdge> set= edgeSets.get(i).edges;
			System.out.println(i);
			for(int j=0;j<set.size();j++){
				
				Segment segment = new Segment(set.get(j));
				segment.setSet(i);
				segment.setEdge(j);
				//segment.draw(drawArea);
				segments.add(segment);
				
			}
		}
		
		
		ArrayList<Intersection> intersections = this.findAllIntersections(segments); // get all intersections
		ArrayList<com.algorithm.segmentintersect.Segment> intersectedSegments = new ArrayList<com.algorithm.segmentintersect.Segment>(); //vector to hold intersections that are between two or more different sets
		
		for(int i =0;i<intersections.size();i++){
			Intersection intersection = intersections.get(i);
			ArrayList<Segment> intSegments = intersection.getSegments();
			
			int startingSet = intSegments.get(0).getSet();
			//System.out.println("starting set="+startingSet);
			innerloop:
			for(int j=0;j<intSegments.size();j++){
				//System.out.println("compare set="+intSegments.get(j).getSet());
				if(intSegments.get(j).getSet()!=startingSet){
					intersectedSegments.addAll(intersection.getSegments());
					break;
				}
				
			}
			//System.out.println("=============\n");
			
		}
		/*Collection noDup = new LinkedHashSet(intersectedSegments);
		intersectedSegments.clear();
		intersectedSegments.addAll(noDup);*/
		/*drawArea.stroke(255,0,0);
		drawArea.strokeWeight(5);
		drawArea.println(intersections.size());*/
		/*for(int i=0;i<intersections.size();i++){
			drawArea.point((float)intersections.get(i).getX(),(float)intersections.get(i).getY());
		}*/
		return intersectedSegments;
	
	}
	
	public ArrayList<Intersection> findAllIntersections(ArrayList<Segment> segments) {
		
		
		
		int nbSegments = segments.size();
		
		long timerStart = System.currentTimeMillis();
		
		// add all endpoints in the event queue, with the segment if the point is an upper endpoint
		for(int i=0; i<nbSegments; i++){
			EventPointSegment EpS1 = new EventPointSegment(segments.get(i).getUpperEndpoint(), segments.get(i));
			queue.add(EpS1);
			EventPointSegment EpS2 = new EventPointSegment(segments.get(i).getLowerEndpoint());
			queue.add(EpS2);
			
		}
		
		while(!queue.isEmpty()){
			EventPointSegment newEvent = queue.getNext();
			HandleEventPoint(newEvent);
		}
		
		long timerEnd = System.currentTimeMillis();
		long runningTime = Math.abs(timerEnd - timerStart);

		//System.out.println("Running time to find intersections : "+runningTime+" ms");
		return intersections;
	}
	
	public void HandleEventPoint(EventPointSegment evtPointSeg){	
		// Get event point
		CompPoint evtPoint = evtPointSeg.getPoint();
		// Get segments linked to this event point
		ArrayList<Segment> evtSet = evtPointSeg.getSegments();	
		
		ArrayList<Segment> uSet = new ArrayList<Segment>();	
		ArrayList<Segment> lcSet = new ArrayList<Segment>();	
		ArrayList<Segment> lSet = new ArrayList<Segment>();	
		ArrayList<Segment> cSet = new ArrayList<Segment>();	

//		System.out.println("Handling p : {X = "+evtPoint.getX()+ ", Y = "+evtPoint.getY()+"}");
		
		// Build set U(evtPoint) : set of segments whose upper endpoint is evtPoint
		if(!evtSet.isEmpty())
		{
			for(int i=0; i<evtSet.size(); i++)
			{
				if(evtSet.get(i).getUpperEndpoint().equals(evtPoint))
				{
					uSet.add(evtSet.get(i));
				}
			}
		}
		
		// Find all segments stored in the tree that contain evtPoint
		lcSet = SegmentsContainingEventPoint(sweepLineStatus, evtPointSeg);
		for(int i = 0; i<lcSet.size(); i++)
		{
			if(lcSet.get(i).getLowerEndpoint().equals(evtPoint))
			{
				// Set of segments whose lowerEndpoint is evtPoint
				lSet.add(lcSet.get(i));
			} else {
				// Set of segments who contain evtPoint in their interior
				cSet.add(lcSet.get(i));
			}
		}
		
		// Report intersection
		if(uSet.size() + lSet.size() + cSet.size() > 1)
		{
			evtPointSeg.isIntersection();
			Intersection newIntersection = new Intersection(evtPoint.getX(), evtPoint.getY()); //where intersection is reported			
			ArrayList<Segment> segments = new ArrayList<Segment>();
			segments.addAll(uSet);
			segments.addAll(lSet);
			segments.addAll(cSet);
			newIntersection.setSegments(segments);
			for(int i=0;i<segments.size();i++){
				segments.get(i).addIntersection(newIntersection);
			}
			intersections.add(newIntersection);
		}
		
		// Delete segments lSet and cSet from tree
		for(int i = 0; i<lSet.size(); i++)
		{
//			lSet.get(i).printSegment();
			sweepLineStatus.treeDelete(sweepLineStatus.treeSearch(lSet.get(i)));
		}
		for(int i = 0; i<cSet.size(); i++)
		{
//			cSet.get(i).printSegment();
			sweepLineStatus.treeDelete(sweepLineStatus.treeSearch(cSet.get(i)));
		}
		
		// Make sure the segments in the tree are in good order (order of intersection with the sweep line just below the event point)
		double minVal = -1;

		try {
			for(int i = 0; i<sweepLineStatus.allNodes().size() ; i++)
			{
				Segment segment = ((Segment) sweepLineStatus.allNodes().get(i).getKey());
				if (segment.getValue() < minVal || minVal == -1)
				{
					minVal = segment.getValue();
				}
				sweepLineStatus.treeDelete(sweepLineStatus.treeSearch(segment));
				if(segment.isHorizontal())
				{
					segment.updateValue(minVal + 0.0002F);
				} else {
					segment.updateValue(evtPoint.getY() - 0.02F);
				}
				treeInsertBlock(segment);
				
				
			}
		} catch (NullPointerException ex){}
		
		// Insert segments from uSet and cSet in tree
		Segment horizontalSegment = null;
		for(int i = 0; i<uSet.size(); i++)
		{	
			Segment segment = uSet.get(i);
			segment.updateValue(evtPoint.getY() - 0.02F);
			if (segment.getValue() < minVal || minVal == -1)
			{
				minVal = segment.getValue();
			}
			if(segment.isHorizontal())
			{
				horizontalSegment = segment;
			} else {
				treeInsertBlock(segment);
			}
		}
		for(int i = 0; i<cSet.size(); i++)
		{
			Segment segment = cSet.get(i);
			segment.updateValue(evtPoint.getY() - 0.02F);
			if (segment.getValue() < minVal || minVal == -1)
			{
				minVal = segment.getValue();
			}
			if(segment.isHorizontal())
			{
				horizontalSegment = segment;
			} else {
				treeInsertBlock(segment);
			}	
		}
		if(horizontalSegment != null)
		{
			horizontalSegment.updateValue(minVal + 0.0002F);
			treeInsertBlock(horizontalSegment);
		}
		
		if(uSet.size() + cSet.size() == 0)
		{
			// Find sl and sr, left and right segments neighbors of evtPoint in tree
//			System.out.println("---CASE 9---");
	
			try {
				Segment sTmp = new Segment(new double[]{evtPoint.getY(), evtPoint.getY()}, new double[]{evtPoint.getY(), evtPoint.getY() +50}, 2);
				treeInsertBlock(sTmp);
				Segment sl = (Segment) sweepLineStatus.treeSearch(sTmp).getNext().getKey();
				Segment sr = (Segment) sweepLineStatus.treeSearch(sTmp).getPrev().getKey();
				sweepLineStatus.treeDelete(sweepLineStatus.treeSearch(sTmp));
				if(sr.getId() != -1 && sl.getId() != -1)
				{
					FindNewEvent(sl, sr, evtPoint);
				}

			} catch (NullPointerException ex) {}
			
		} else {
//			System.out.println("---CASE 11---");
			ArrayList<Segment> unionSet = new ArrayList<Segment>();
			unionSet.addAll(uSet);
			unionSet.addAll(cSet);
			
			Segment sPrime = unionSet.get(0);

			// find the leftmost segment in unionSet :
			for(int i = 1; i<unionSet.size(); i++)
			{
				if(unionSet.get(i).getValue() < sPrime.getValue())
				{
					sPrime = unionSet.get(i);
				}
			}
		
			// find left neighbor of sPrime in tree :
			try {
				Segment sl = (Segment) sweepLineStatus.treeSearch(sPrime).getNext().getKey();
				FindNewEvent(sl, sPrime, evtPoint);
				
			} catch (NullPointerException ex) {}
			
			
			Segment sSecond = unionSet.get(0);
			// find the rightmost segment in unionSet :
			for(int j = 1; j<unionSet.size(); j++)
			{
				if(unionSet.get(j).getValue() > sSecond.getValue())
				{
					sSecond = unionSet.get(j);
				}
			}			
			// find right neighbor of sSecond in tree :          
			try {
				Segment sr = (Segment) sweepLineStatus.treeSearch(sSecond).getPrev().getKey();
				FindNewEvent(sSecond, sr, evtPoint);
				
			} catch (NullPointerException ex) {}
		}
	}
	
	public void FindNewEvent(Segment sl, Segment sr, CompPoint p)
	{

		CompPoint inter = inter2Segments(sl, sr);
		if ( inter != null && (inter.getY() < p.getY() || (Math.abs(inter.getY() - p.getY()) <= 1.000001E-002F && inter.getX() > p.getX())))
				{			
					EventPointSegment newPoint = new EventPointSegment(inter);
					if(!queue.contains(newPoint))
					{
						queue.add(newPoint);
					}
				}
	}
	
	public ArrayList<Segment> SegmentsContainingEventPoint(RedBlackTree redblacktree, EventPointSegment evtPointSeg)
    {
		ArrayList<Segment> result = new ArrayList<Segment>();
		
        if(redblacktree == null)
            return result;
        Vector vector = new Vector();
        vector = redblacktree.allNodes();
        if(vector == null)
            return result;
        for(Enumeration enumeration = vector.elements(); enumeration.hasMoreElements();)
        {
            RedBlackNode redblacknode = (RedBlackNode)enumeration.nextElement();
            Segment segment = (Segment)redblacknode.getKey();
            if(segment.containsPoint(evtPointSeg.getPoint()))
            {
//            	segment.printSegment();
//                eventpoint.addIntersectionSegment(segment);
            	
            	result.add(segment);
            }
        }
        return result;
    }

    public void treeInsertBlock(Segment segment)
    {
        RedBlackNode redblacknode = new RedBlackNode(segment);
        if(sweepLineStatus.treeContains(redblacknode))
        {
            System.out.println("Error: Segment must already be in the tree");
            return;
        }
        double d = 0.0F;
        if (segment.isVertical())
        {
        	sweepLineStatus.treeInsert(redblacknode);
        	return;
        }
        do
        {
            segment.setValue(segment.getValue() - d);
            if(sweepLineStatus.treeInsert(redblacknode))
                break;
            segment.setValue(segment.getValue() + 2F * d);
            if(sweepLineStatus.treeInsert(redblacknode))
                break;
            segment.setValue(segment.getValue() - d);
            d += 0.5F;
        } while(true);
    }

	
	public static CompPoint inter2Segments(Segment s1, Segment s2){
		CompPoint A = s1.getLowerEndpoint();
		CompPoint B = s1.getUpperEndpoint();
		CompPoint C = s2.getLowerEndpoint();
		CompPoint D = s2.getUpperEndpoint();
		
		double Ax = A.getX();
		double Ay = A.getY();
		double Bx = B.getX();
		double By = B.getY();
		double Cx = C.getX();
		double Cy = C.getY();
		double Dx = D.getX();
		double Dy = D.getY();
		
		double Sx;
		double Sy;
 
		if(Ax==Bx)
		{
			if(Cx==Dx) return null;
			else
			{
				double pCD = (Cy-Dy)/(Cx-Dx);
				Sx = Ax;
				Sy = pCD*(Ax-Cx)+Cy;
			}
		}
		else
		{
			if(Cx==Dx)
			{
				double pAB = (Ay-By)/(Ax-Bx);
				Sx = Cx;
				Sy = pAB*(Cx-Ax)+Ay;
			}
			else
			{
				double pCD = (Cy-Dy)/(Cx-Dx);
				double pAB = (Ay-By)/(Ax-Bx);
				double oCD = Cy-pCD*Cx;
				double oAB = Ay-pAB*Ax;
				Sx = (oAB-oCD)/(pCD-pAB);
				Sy = pCD*Sx+oCD;
			}
		}
		if((Sx<Ax && Sx<Bx)|(Sx>Ax && Sx>Bx) | (Sx<Cx && Sx<Dx)|(Sx>Cx && Sx>Dx)
				| (Sy<Ay && Sy<By)|(Sy>Ay && Sy>By) | (Sy<Cy && Sy<Dy)|(Sy>Cy && Sy>Dy))
			{
				return null;
			} else {
				return new CompPoint((double)Sx,(double)Sy);
			}
	}
    

}
