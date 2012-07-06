package com.datatype;

import java.awt.geom.Point2D;
import java.util.Comparator;

public class CmpY  implements Comparator<Point2D> {
	public int compare(Point2D a, Point2D b) {
		return (a.getY() < b.getY()) ? -1 : (a.getY() > b.getY()) ? 1 : 0;
	}
}