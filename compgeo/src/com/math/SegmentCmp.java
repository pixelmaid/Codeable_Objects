package com.math;

import java.util.Comparator;

public class SegmentCmp implements Comparator<Segment> {
    public int compare(Segment a, Segment b) {
        return (a.p1.getY() < b.p1.getY()) ? -1 : (a.p1.getY() > b.p1.getY()) ? 1 : 0;
    }
}
