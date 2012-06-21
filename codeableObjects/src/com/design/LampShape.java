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

package com.design;

import com.datastruct.DCHalfEdge;
import com.datastruct.DCHalfEdge3d;
import com.datastruct.DoublyConnectedEdgeList;
import com.math.CompPoint;
import com.math.CompPoint3d;
import processing.core.PApplet;


import java.util.Vector;

public class LampShape {

	public Model model; //storage container of 3d model;

	public double ptMilConversion = 2.834654597337315; //converts from points to millimeters

	public int ribNum = 8; //number of ribs of the lamp

	private double ribWidth = 11.445 * ptMilConversion; //width of each rib

	public double topCirclePos = 60 * ptMilConversion; //position for the top circle

	public double bottomCirclePos = 240 * ptMilConversion; //position for the bottom circle

	public int curveResolution = 100;//resolution of the curve of the lamp;

	private double[][] radArray; //contains the radiuses of all of the cylinders

	public double maxWidth = 100 * ptMilConversion; //max width and height of the lamp
	public double maxHeight = 300 * ptMilConversion;

	public double topWidth = 20 * ptMilConversion; //cutoff range for top and bottom (must be less than half of the cylinder number or will error out)
	public double bottomWidth = 5 * ptMilConversion;

	public double topHoleWidth = 10 * ptMilConversion;
	public double bottomHoleWidth = 3 * ptMilConversion;

	private double ribNotchOffset = 1 * ptMilConversion;


	public double notchWidth = 8.92 * ptMilConversion;
	public double notchHeight = 5.64 * ptMilConversion;

	//arrays to store calculated lamp dimensions
	private double[][] xMainTop;
	private double[][] zMainTop;
	private double[][] xMainBottom;
	private double[][] zMainBottom;
	private double[] yMainTop;
	private double[] yMainBottom;


	private PApplet myParent;

	private Shade shade;

	private Rib rib;

	public Base bottomBase;

	private Base topBase;

	public LampShape(PApplet myParent, Model model) {
		this.myParent = myParent;
		this.model = model;

	}


	public DoublyConnectedEdgeList[] renderLamp() {


		/*need to calc width and height */
		shade = new Shade(0, maxHeight);
		rib = new Rib(0, maxHeight);
		bottomBase = new Base(0, 0, "bottom");
		topBase = new Base(0, 0, "top");


		calculateDimensions();
		generateRib();
		generateShade();


		shade.translate(myParent.width / 2, myParent.height / 2);

		//add notches
		rib.addNotches(notchWidth, notchHeight, ribNotchOffset, topCirclePos, bottomCirclePos);
		calculateDimensions();

		generateBases(ribNum);


		bottomBase.addNotches(notchWidth * 2, notchHeight, ribNotchOffset, ribNum,false);
		topBase.addNotches(notchWidth * 2, notchHeight, ribNotchOffset, ribNum,false);

		bottomBase.generateHole(bottomHoleWidth);

		topBase.generateHole(topHoleWidth);

		rib.translate(myParent.width / 2, 0);


		//return all parts

		Part[] borders = new Part[4];
		borders[0] = shade;
		borders[1] = rib;
		borders[2] = topBase;
		borders[3] = bottomBase;

		return borders;
	}

	public void calculateDimensions() {

		model.clearEdges();
		double angle;

		xMainTop = new double[curveResolution][];
		zMainTop = new double[curveResolution][];
		xMainBottom = new double[curveResolution][];
		zMainBottom = new double[curveResolution][];
		yMainTop = new double[curveResolution];
		yMainBottom = new double[curveResolution];


		DoublyConnectedEdgeList radEdges = new DoublyConnectedEdgeList();

		CompPoint top = new CompPoint((maxWidth - topWidth) / 2, 0);
		CompPoint middle = new CompPoint(0, maxHeight / 2);
		CompPoint bottom = new CompPoint((maxWidth - bottomWidth) / 2, maxHeight);

		double y1 = top.getY();
		double x1 = top.getX();
		double y2 = middle.getY();
		double x2 = middle.getX();
		double y3 = bottom.getY();
		double x3 = bottom.getX();


		double denom = (y1 - y2) * (y1 - y3) * (y2 - y3);
		double a = (y3 * (x2 - x1) + y2 * (x1 - x3) + y1 * (x3 - x2)) / denom;
		double b = (Math.pow((float) y3, 2.0) * (x1 - x2) + Math.pow((float) y2, 2.0) * (x3 - x1) + Math.pow((float) y1, 2.0) * (x2 - x3)) / denom;
		double c = (y2 * y3 * (y2 - y3) * x1 + y3 * y1 * (y3 - y1) * x2 + y1 * y2 * (y1 - y2) * x3) / denom;


		for (int i = 0; i < curveResolution + 1; i++) {
			double startPointY = (maxHeight / curveResolution) * i;

			double startPointX = a * startPointY * startPointY + b * startPointY + c;

			double endPointX = maxWidth / 2;
			CompPoint start = new CompPoint(startPointX, startPointY);
			CompPoint end = new CompPoint(endPointX, startPointY);

			DCHalfEdge rad = new DCHalfEdge(start, end);
			//rad.translate(width/2, height/2, new CompPoint(maxWidth/2, maxHeight/2));
			radEdges.addHalfEdge(rad);
		}


		//get the x and z position on a circle for all the sides
		for (int j = 0; j < curveResolution - 1; j++) {
			double[] xTop = new double[ribNum + 1];
			double[] zTop = new double[ribNum + 1];
			double[] xBottom = new double[ribNum + 1];
			double[] zBottom = new double[ribNum + 1];
			double currentTopRad = 0;
			double currentBottomRad = 0;
			DCHalfEdge edge = radEdges.edges.get(j);
			DCHalfEdge edgeAfter = radEdges.edges.get(j + 1);


			currentTopRad = Math.abs((float) (edge.start.getX() - edge.end.getX()));


			currentBottomRad = Math.abs((float) (edgeAfter.start.getX() - edgeAfter.end.getX()));

			yMainTop[j] = edge.start.getY();
			yMainBottom[j] = edgeAfter.start.getY();

			double angleOffset = -0.5;

			for (double i = angleOffset; i < ribNum + angleOffset + 1; i++) {
				angle = (Math.PI * 2) / (ribNum) * i;


				xTop[(int) (i - angleOffset)] = Math.sin(angle) * currentTopRad;
				zTop[(int) (i - angleOffset)] = Math.cos(angle) * currentTopRad;

				xBottom[(int) (i - angleOffset)] = Math.sin(angle) * currentBottomRad;
				zBottom[(int) (i - angleOffset)] = Math.cos(angle) * currentBottomRad;

			}


			xMainTop[j] = xTop;
			xMainBottom[j] = xBottom;
			zMainTop[j] = zTop;
			zMainBottom[j] = zBottom;

		}

		for (int l = 0; l < curveResolution - 1; l++) {
			for (int i = 0; i < ribNum + 1; i++) {

				CompPoint3d topVertex = new CompPoint3d(xMainTop[l][i], yMainTop[l], zMainTop[l][i]);
				CompPoint3d bottomVertex = new CompPoint3d(xMainBottom[l][i], yMainBottom[l], zMainBottom[l][i]); //will need to save these to a vector
				model.addEdge(new DCHalfEdge3d(topVertex, bottomVertex));


				if (l == rib.bottomNotchPos) {

					CompPoint3d topBottomNotchVertex = new CompPoint3d(xMainTop[l][i], yMainTop[l], zMainTop[l][i]);
					CompPoint3d bottomBottomNotchVertex = new CompPoint3d(xMainTop[l][i], (double) (yMainTop[l] + notchHeight), zMainTop[l][i]);
					model.addBottomEdge(new DCHalfEdge3d(topBottomNotchVertex, bottomBottomNotchVertex));


				}

				if (l == rib.topNotchPos) {
					CompPoint3d topTopNotchVertex = new CompPoint3d(xMainTop[l][i], yMainTop[l], zMainTop[l][i]);
					CompPoint3d topBottomNotchVertex = new CompPoint3d(xMainTop[l][i], yMainTop[l] + notchHeight, zMainTop[l][i]);
					model.addTopEdge(new DCHalfEdge3d(topTopNotchVertex, topBottomNotchVertex));

				}
			}
		}


	}

	private void generateShade() {


		DCHalfEdge topEdge = new DCHalfEdge(new CompPoint(xMainTop[0][1], yMainTop[0]), new CompPoint(xMainTop[0][0], yMainTop[0]));
		shade.addEdgeWithPartner(topEdge);


		//myParent.println("left edge");
		for (int j = 0; j < curveResolution - 1; j++) {

			DCHalfEdge prevEdge = shade.edges.get(shade.edges.size() - 1);
			double dx = xMainBottom[j][0] - xMainTop[j][0];
			double dy = yMainBottom[j] - yMainTop[j];
			double edgeHeight = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));

			double yTop = prevEdge.end.getY();
			double yBottom = prevEdge.end.getY() + edgeHeight;

			DCHalfEdge leftSide = new DCHalfEdge(new CompPoint(xMainTop[j][0], yTop), new CompPoint(xMainBottom[j][0], yBottom));
			shade.addEdgeWithPartner(leftSide);
		}

		DCHalfEdge prevBottomEdge = shade.edges.get(shade.edges.size() - 1);
		DCHalfEdge bottomEdge = new DCHalfEdge(new CompPoint(xMainBottom[curveResolution - 2][0], prevBottomEdge.end.getY()), new CompPoint(xMainBottom[curveResolution - 2][1], prevBottomEdge.end.getY()));
		shade.addEdgeWithPartner(bottomEdge);


		//myParent.println("right edge");
		for (int j = curveResolution - 2; j >= 0; j--) {

			DCHalfEdge prevEdge = shade.edges.get(shade.edges.size() - 1);
			double dx = xMainBottom[j][0] - xMainTop[j][0];
			double dy = yMainTop[j] - yMainBottom[j];
			double edgeHeight = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));

			double yTop = prevEdge.end.getY();
			double yBottom = prevEdge.end.getY() - edgeHeight;


			DCHalfEdge rightSide = new DCHalfEdge(new CompPoint(xMainBottom[j][1], yTop), new CompPoint(xMainTop[j][1], yBottom));
			shade.addEdgeWithPartner(rightSide);
		}
		shade.completePartners();
	}


	private void generateRib() {


		//create top of rib
		DCHalfEdge ribTop = new DCHalfEdge(new CompPoint(zMainTop[0][0] + ribWidth, yMainTop[0]), new CompPoint(zMainTop[0][0], yMainTop[0]));
		rib.addHalfEdge(ribTop);


		//myParent.println("left edge");
		for (int j = 0; j < curveResolution - 1; j++) {

			DCHalfEdge leftRibSide = new DCHalfEdge(new CompPoint(zMainTop[j][0], yMainTop[j]), new CompPoint(zMainBottom[j][0], yMainBottom[j]));
			rib.addHalfEdge(leftRibSide);

		}

		DCHalfEdge ribBottom = new DCHalfEdge(new CompPoint(zMainBottom[curveResolution - 2][0], yMainBottom[curveResolution - 2]), new CompPoint(zMainBottom[curveResolution - 2][0] + ribWidth, yMainBottom[curveResolution - 2]));
		rib.addHalfEdge(ribBottom);


		//myParent.println("right edge");
		for (int j = curveResolution - 2; j >= 0; j--) {

			DCHalfEdge rightRibSide = new DCHalfEdge(new CompPoint(zMainBottom[j][0] + ribWidth, yMainBottom[j]), new CompPoint(zMainTop[j][0] + ribWidth, yMainTop[j]));
			rib.addHalfEdge(rightRibSide);
		}

	}


	private void generateBases(int sides) {


		for (int i = 0; i < sides + 1; i++) {
			int after = i + 1;
			if (i == sides) {
				after = 0;
			}


			DCHalfEdge outerBottomCircle = new DCHalfEdge(new CompPoint(xMainTop[rib.bottomNotchPos][i], zMainTop[rib.bottomNotchPos][i]), new CompPoint(xMainTop[rib.bottomNotchPos][after], zMainTop[rib.bottomNotchPos][after]));
			//DCHalfEdge innerBottomCircle = new DCHalfEdge(new CompPoint(xMainTop[bottomCutoff-5][i], zMainTop[bottomCutoff-5][i]), new CompPoint(xMainTop[bottomCutoff-5][after], zMainTop[bottomCutoff-5][after]));

			DCHalfEdge outerTopCircle = new DCHalfEdge(new CompPoint(xMainTop[rib.topNotchPos][i], zMainTop[rib.topNotchPos][i]), new CompPoint(xMainTop[rib.topNotchPos][after], zMainTop[rib.topNotchPos][after]));
			//DCHalfEdge innerTopCircle = new DCHalfEdge(new CompPoint(xMainTop[topCutoff-5][i], zMainTop[topCutoff-5][i]),new CompPoint(xMainTop[topCutoff-5][after], zMainTop[topCutoff-5][after]));

			bottomBase.addHalfEdge(outerBottomCircle);
			//bottomBase.addHalfEdge(innerBottomCircle);

			topBase.addHalfEdge(outerTopCircle);
			//topBase.addHalfEdge(innerTopCircle);

		}
	}

	public Base generatePaperBase(String type) {
		Base paperBase = new Base(0,0,type);

		if(type=="bottom"){

			for (int i = 0; i < this.ribNum + 1; i++) {
				int after = i + 1;
				if (i == this.ribNum) {
					after = 0;
				}


				DCHalfEdge outerBottomCircle = new DCHalfEdge(new CompPoint(xMainTop[rib.bottomNotchPos][i], zMainTop[rib.bottomNotchPos][i]), new CompPoint(xMainTop[rib.bottomNotchPos][after], zMainTop[rib.bottomNotchPos][after]));
				//DCHalfEdge innerBottomCircle = new DCHalfEdge(new CompPoint(xMainTop[bottomCutoff-5][i], zMainTop[bottomCutoff-5][i]), new CompPoint(xMainTop[bottomCutoff-5][after], zMainTop[bottomCutoff-5][after]));


				paperBase.addHalfEdge(outerBottomCircle);

				//bottomBase.addHalfEdge(innerBottomCircle);



			}
			double tabWidth = -100;
			paperBase.largeTabs(tabWidth);

			paperBase.generateHole(bottomHoleWidth);
			//paperBase.rotate(180,paperBase.focus);
		}
		if(type == "top"){
			for (int i = 0; i < this.ribNum + 1; i++) {
				int after = i + 1;
				if (i == this.ribNum) {
					after = 0;
				}

				DCHalfEdge outerTopCircle = new DCHalfEdge(new CompPoint(xMainTop[rib.topNotchPos][i], zMainTop[rib.topNotchPos][i]), new CompPoint(xMainTop[rib.topNotchPos][after], zMainTop[rib.topNotchPos][after]));
				//DCHalfEdge innerTopCircle = new DCHalfEdge(new CompPoint(xMainTop[topCutoff-5][i], zMainTop[topCutoff-5][i]),new CompPoint(xMainTop[topCutoff-5][after], zMainTop[topCutoff-5][after]));



				paperBase.addHalfEdge(outerTopCircle);






			}
			double tabWidth = -100;
			paperBase.largeTabs(tabWidth);

			paperBase.generateHole(topHoleWidth);
			//paperBase.rotate(180,paperBase.focus);
		}

		//paperBase.addNotches(notchWidth * 2, notchHeight, ribNotchOffset, ribNum,true);

		return paperBase;
	}


	//draw method for all of the parts of the lamp
	public void draw(float zoom, float color,boolean shadeDraw, boolean partsDraw) {
		myParent.background(100, 100, 100);

		if(shadeDraw){
			myParent.pushMatrix();
			myParent.translate(0, 0, zoom);
			for (int j = 0; j < shade.edges.size(); j++) {
				DCHalfEdge edge = shade.edges.get(j);
				float edgeStartX = (float) (edge.start.getX());
				float edgeStartY = (float) (edge.start.getY());

				float edgeEndX = (float) (edge.end.getX());
				float edgeEndY = (float) (edge.end.getY());
				myParent.stroke(color);
				myParent.strokeWeight(3);

				myParent.line(edgeStartX, edgeStartY, edgeEndX, edgeEndY);


			}
			myParent.popMatrix();
		}
		if(partsDraw){
			myParent.pushMatrix();
			myParent.translate(myParent.width / 2 - 350, myParent.height / 2, zoom);
			for (int j = 0; j < rib.edges.size(); j++) {
				DCHalfEdge edge = rib.edges.get(j);
				float edgeStartX = (float) (edge.start.getX());
				float edgeStartY = (float) (edge.start.getY());

				float edgeEndX = (float) (edge.end.getX());
				float edgeEndY = (float) (edge.end.getY());

				myParent.stroke(color);
				myParent.strokeWeight(3);
				if (edge.inner) {
					myParent.stroke(255, 0, 0);
				}
				myParent.line(edgeStartX, edgeStartY, edgeEndX, edgeEndY);


			}
			myParent.popMatrix();

			myParent.pushMatrix();
			myParent.translate(100, 40, zoom);
			for (int j = 0; j < bottomBase.edges.size(); j++) {
				DCHalfEdge edge = bottomBase.edges.get(j);
				float edgeStartX = (float) (edge.start.getX());
				float edgeStartY = (float) (edge.start.getY());

				float edgeEndX = (float) (edge.end.getX());
				float edgeEndY = (float) (edge.end.getY());
				myParent.stroke(color);
				myParent.strokeWeight(3);
				if (edge.inner) {
					myParent.stroke(255, 0, 0);
				}
				myParent.line(edgeStartX, edgeStartY, edgeEndX, edgeEndY);
				myParent.stroke(255, 0, 0);

			}
			myParent.popMatrix();


			myParent.pushMatrix();
			myParent.translate(100, myParent.height - 150, zoom);
			for (int j = 0; j < topBase.edges.size(); j++) {

				DCHalfEdge edge = topBase.edges.get(j);
				float edgeStartX = (float) (edge.start.getX());
				float edgeStartY = (float) (edge.start.getY());

				float edgeEndX = (float) (edge.end.getX());
				float edgeEndY = (float) (edge.end.getY());
				myParent.stroke(color);
				myParent.strokeWeight(3);
				if (edge.inner) {
					myParent.stroke(255, 0, 0);
				}
				myParent.line(edgeStartX, edgeStartY, edgeEndX, edgeEndY);
				myParent.stroke(255, 0, 0);

			}
			myParent.popMatrix();
		}
	}




}
