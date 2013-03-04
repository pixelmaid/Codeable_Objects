package com.algorithm.segmentintersect;

import java.util.Comparator;
import java.util.PriorityQueue;


@SuppressWarnings("serial")
public class PriorityQueueAdv extends PriorityQueue<EventPointSegment> {
	
	/**
	 * 
	 */
	
	public PriorityQueueAdv(int i, Comparator<EventPointSegment> comparator)
	{
		super(i, comparator);
	}
	public EventPointSegment getNext()
	{
		EventPointSegment currentEvt = this.poll();

		while(this.peek() != null && (this.peek().getPoint().equals(currentEvt.getPoint()))){
			EventPointSegment nextEvt = this.poll() ;

			if (!nextEvt.getSegments().isEmpty())
			{
				currentEvt.addSegment(nextEvt.getSegments().get(0));
			}	
		}
		return currentEvt;
	}
		
}
