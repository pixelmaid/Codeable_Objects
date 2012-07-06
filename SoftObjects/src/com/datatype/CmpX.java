package com.datatype;

import java.awt.geom.Point2D;
import java.util.Comparator;

public class CmpX  implements Comparator<Point2D> {
	public int compare(Point2D a, Point2D b) {
		return (a.getX() < b.getX()) ? -1 : (a.getX() > b.getX()) ? 1 : 0;
	}
}