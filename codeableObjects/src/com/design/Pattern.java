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

import com.algorithm.VoronoiGenerator;
import com.datastruct.DCFace;
import com.datastruct.DCHalfEdge;
import com.datastruct.DoublyConnectedEdgeList;
import com.math.CompPoint;
import com.math.Geom;
import com.math.PolyBoolean;

import processing.core.PApplet;
import processing.core.PFont;

import java.util.Random;
import java.util.Vector;

public class Pattern{
	private DoublyConnectedEdgeList border = new DoublyConnectedEdgeList();
	private double width;
	private double height;
	private PApplet myParent;
	private VoronoiGenerator vorGenerator;  //voronoi generator
	private Part shadeBorder;
	private Part voronoiEdges; //pattern edges for drawing
	private DoublyConnectedEdgeList faces;
	private DoublyConnectedEdgeList clippedFaces;
	private Vector<CompPoint> currentPoints; //points for constructing diagram passed in by controller
	private int outerColor = 255;
	private int innerColor = 0x9966FF;
	private int pointColor = 255;
	public double thickWeight = 0.9;
	private Vector<Tab> tabs;
	private Vector<Notch> notches;
	private DCHalfEdge leftExtreme;
	private DCHalfEdge rightExtreme;
	private Vector<DCHalfEdge> rightEdgesToRemove;
	private Vector<DCHalfEdge> leftEdgesToRemove;
	private int ribNum;
	private int thinWeight = 1;
	public double totalWidth=0;
	public double totalHeight=0;
	private String type;
	public Pattern(String type, PApplet myParent) {
		this.myParent = myParent;
		this.width = myParent.width;
		this.height = myParent.height;
		vorGenerator = new VoronoiGenerator(myParent);
		this.type = type;
	}


	public void defineVorDiagram(Part shadeBorder, Vector<CompPoint> currentPoints, double contract, double notchLimit) {      //seperate out clipping and draw methods
		//init diagram containers
		faces = new DoublyConnectedEdgeList();
		clippedFaces = new DoublyConnectedEdgeList();
		DoublyConnectedEdgeList diagram = new DoublyConnectedEdgeList();
		this.thickWeight = contract;
		//add noise to points location to prevent issue with same x or y coordinates

		double focusX = myParent.width / 2;
		double focusY = myParent.height / 2;
		this.currentPoints = currentPoints;
		this.shadeBorder = shadeBorder;
		Vector<CompPoint> rotatedPoints = new Vector<CompPoint>();
		Random randomGenerator = new Random();

		//randomly rotate points to deal with error
		for (int i = 0; i < this.currentPoints.size(); i++) {
			CompPoint point = currentPoints.get(i);
			double x = point.getX();
			double y = point.getY();

			double dx = x - focusX; //direction x
			double dy = y - focusY; //direction y
			double pointTheta = Geom.cartToPolar(dx, dy)[1];//degree of edge
			double pointR = Geom.cartToPolar(dx, dy)[0];//degree of edge


			double newTheta = pointTheta + getRandomRanged(-0.001, 0.001, randomGenerator);
			double xNew = Math.cos(newTheta * Math.PI / 180.0) * pointR;
			double yNew = Math.sin(newTheta * Math.PI / 180.0) * pointR;
			CompPoint newPoint = new CompPoint(xNew + focusX, yNew + focusY);
			rotatedPoints.addElement(newPoint);

		}

		//generate voronoi diagram

		voronoiEdges = new Part(0,0);
		// Vector<DCHalfEdge> edges = vorGenerator.getEdges(rotatedPoints, shadeBorder, myParent.width, myParent.height);
		if(rotatedPoints.size()>=2){
			vorGenerator.getEdges(rotatedPoints, shadeBorder, myParent.width, myParent.height,faces,diagram);
			voronoiEdges.edges = diagram.edges;

			//voronoiEdges.expandPart(thickWeight);
			//shadeBorder.expandPart(thickWeight);
			//System.out.println("total number of faces="+faces.faces.size());
			Part shadeBorderCopy = new Part(0,0);
			int bottomEdge = shadeBorder.edges.size()/2;
			double upperTarget = shadeBorder.edges.get(0).start.getY()+notchLimit;
			double lowerTarget = shadeBorder.edges.get(bottomEdge).start.getY()-notchLimit;

			for(int i=0;i<shadeBorder.edges.size();i++){
				DCHalfEdge currentEdge = shadeBorder.edges.get(i);
				if(currentEdge.start.getY()>upperTarget&&currentEdge.start.getY()<lowerTarget){
					shadeBorderCopy.addVertex(currentEdge.start);
				}
				if(currentEdge.end.getY()>upperTarget&&currentEdge.end.getY()<lowerTarget){
					shadeBorderCopy.addVertex(currentEdge.end);
				}
			}

			shadeBorderCopy.convertVertexesToEdges();
			double areaLimit = 30;
			for(int i=0;i<faces.faces.size();i++){
				faces.faces.get(i).orderEdges();
				DoublyConnectedEdgeList clippedFace =PolyBoolean.booleanSet(shadeBorderCopy,faces.faces.get(i),"difference");
				if(clippedFace!=null){
					DCFace newFace = new DCFace(new CompPoint(0,0));
					newFace.edges=	clippedFace.edges;
					PolyBoolean.contractPoly(newFace,contract);
					if(Geom.SignedPolygonArea(newFace)>areaLimit){
						clippedFaces.addFace(newFace);
					}

				}
			}
		}
		//PolyBoolean.contractPoly(shadeBorder,1.1);


		// this.shadeBorder.edges= newShadeBorder.edges;

	}

	public void insertTabs( double height,double tabWidth, double tabHeight, int sideNum){
		Notch notch1 = new Notch(1,height);
		Notch notch2 = new Notch(1,height);
		Notch notch3 = new Notch(1,height);
		Notch notch4 = new Notch(1,height);
		notch1.focus=new CompPoint(0,0);
		notch2.focus=new CompPoint(0,0);
		notch3.focus=new CompPoint(0,0);
		notch4.focus=new CompPoint(0,0);
		notches = new Vector<Notch>();
		this.addTabs(height,tabWidth,tabHeight, notch1,notch2,notch3,notch4,sideNum);
		leftExtreme = findExtremeEdge("left");
		rightExtreme = findExtremeEdge("right");
		rightEdgesToRemove= new Vector<DCHalfEdge>();
		leftEdgesToRemove= new Vector<DCHalfEdge>();
		int gapSize=6;
		int leftIndex = shadeBorder.edges.indexOf(leftExtreme);
		int rightIndex = shadeBorder.edges.indexOf(rightExtreme);
		if(leftIndex<1)
			leftIndex=1;
		if(rightIndex<shadeBorder.edges.size()/2+1)
			rightIndex=shadeBorder.edges.size()/2+1;



		for(int i=leftIndex-gapSize/2; i<leftIndex+gapSize/2+1;i++){

			DCHalfEdge edge =shadeBorder.edges.get(i);
			leftEdgesToRemove.add(edge);

		}
		for(int i=rightIndex-gapSize/2+1; i<rightIndex+gapSize/2+2;i++){
			rightEdgesToRemove.add(shadeBorder.edges.get(i));
		}

		//tabs.get(0).merge(this.shadeBorder);
		//tabs.get(1).merge(this.shadeBorder);

		double drawGap =rightExtreme.start.getX()-leftExtreme.start.getX();
		this.totalWidth=drawGap*this.ribNum;
		this.totalHeight=shadeBorder.edges.get(shadeBorder.edges.size()/2).start.getY()-shadeBorder.edges.get(0).end.getY();

	}


	public void addTabs( double height, double tabWidth, double tabHeight, Notch notch1, Notch notch2, Notch notch3, Notch notch4, int sideNum){
		tabs = new Vector<Tab>();
		double gap = (tabHeight-height)/2;
		double notchSpace = 5;
		this.ribNum=sideNum;
		int bottomEdge = shadeBorder.edges.size()/2;
		Tab topTab = new Tab(shadeBorder.edges.get(0).length*(sideNum+1)+10,height,tabWidth,tabHeight,0,shadeBorder.edges.get(0).start.getY()-tabHeight+5);
		Tab bottomTab = new Tab(shadeBorder.edges.get(bottomEdge).length*(sideNum+1)+10,height,tabWidth,tabHeight,0,shadeBorder.edges.get(0).start.getY()-tabHeight*2-10);

		notch1.translate(shadeBorder.edges.get(0).start.getX(),shadeBorder.edges.get(0).start.getY()+notchSpace+gap);
		notch2.translate(shadeBorder.edges.get(0).end.getX(),shadeBorder.edges.get(0).end.getY()+notchSpace+gap);

		//System.out.println("bottom edge="+bottomEdge);

		notch3.translate(shadeBorder.edges.get(bottomEdge).end.getX(),shadeBorder.edges.get(bottomEdge).end.getY()-notch3.height-notchSpace-gap);
		notch4.translate(shadeBorder.edges.get(bottomEdge).start.getX(),shadeBorder.edges.get(bottomEdge).start.getY()-notch4.height-notchSpace-gap);

		while (!Geom.rayPointInPolygon(notch1.right.end,shadeBorder)||!Geom.rayPointInPolygon(notch1.right.start,shadeBorder)){
			notch1.translate(notch1.focus.getX()-1, notch1.focus.getY());
		}

		while (!Geom.rayPointInPolygon(notch2.left.end,shadeBorder)||!Geom.rayPointInPolygon(notch2.left.start,shadeBorder)){
			notch2.translate(notch2.focus.getX()+1, notch2.focus.getY());
		}

		while (!Geom.rayPointInPolygon(notch3.right.end,shadeBorder)|!Geom.rayPointInPolygon(notch3.right.start,shadeBorder)){
			notch3.translate(notch3.focus.getX()-1, notch3.focus.getY());
		}

		while (!Geom.rayPointInPolygon(notch4.left.end,shadeBorder)|!Geom.rayPointInPolygon(notch4.left.start,shadeBorder)){
			notch4.translate(notch4.focus.getX()+1, notch4.focus.getY());
		}

		notch1.translate(notch1.focus.getX()-5, notch1.focus.getY());
		notch2.translate(notch2.focus.getX()+5, notch2.focus.getY());
		notch3.translate(notch3.focus.getX()-5, notch3.focus.getY());
		notch4.translate(notch4.focus.getX()+5, notch4.focus.getY());

		tabs.add(topTab);
		tabs.add(bottomTab);

		notches.add(notch1);
		notches.add(notch2);

		notches.add(notch3);
		notches.add(notch4);
	}


	public void draw(boolean drawPoints, float color) {
		myParent.background(100, 100, 100);
		double drawGap =rightExtreme.start.getX()-leftExtreme.start.getX();

		if (drawPoints) {  //draws points that define the diagram
			myParent.stroke(255, 255, 0);
			myParent.strokeWeight(4);
			for (int i = 0; i < currentPoints.size(); i++) {
				CompPoint point = currentPoints.get(i);

				myParent.point((float) point.getX(), (float) point.getY());

			}
		}
		for(int i=0;i<clippedFaces.faces.size();i++){
			clippedFaces.faces.get(i).draw(myParent);
		}
		/*if(notches!=null){
			for(int i =0;i<notches.size();i++){

				notches.get(i).draw(myParent,1, (int)color);
			}
		}*/

		//draw border of diagram
		try {
			for (int i = 0; i < shadeBorder.edges.size(); i++) {

				DCHalfEdge edge = shadeBorder.edges.get(i);

				//if(rightEdgesToRemove.indexOf(edge)==-1&&leftEdgesToRemove.indexOf(edge)==-1){

					float edgeStartX = (float) (edge.start.getX());
					float edgeStartY = (float) (edge.start.getY());

					float edgeEndX = (float) (edge.end.getX());
					float edgeEndY = (float) (edge.end.getY());
					myParent.stroke(color);
					myParent.strokeWeight(1);

					myParent.line(edgeEndX, edgeEndY,edgeStartX, edgeStartY);
					//myParent.line(edgeEndX+(float)drawGap, edgeEndY,edgeStartX+(float)drawGap, edgeStartY);
					//edge.drawPartners(myParent);
				//}

			}
		} catch (NullPointerException e) {
			System.out.println("you tried to draw an edge in the border that doesn't exist");
		}

		/* for(int i=0;i<shadeBorder.edges.size();i++){
        	DCHalfEdge edge = shadeBorder.edges.get(i);
		PFont font = myParent.loadFont("din_bold.vlw");
		myParent.textFont(font, 14);
		myParent.fill(255);
		myParent.text(Integer.toString(i),(float)edge.start.getX(),(float)edge.start.getY());
        }*/
		//myParent.println("drew border");
		//draw diagram
		/* try {
           for (int k = 0; k < voronoiEdges.edges.size(); k++) {

                DCHalfEdge edge = voronoiEdges.edges.get(k);

                if (edge.end != null && edge.start != null) {
                    float edgeStartX = (float) Math.round((edge.start.getX()));
                    float edgeStartY = (float) Math.round((edge.start.getY()));

                    float edgeEndX = (float) Math.round((edge.end.getX()));
                    float edgeEndY = (float) Math.round((edge.end.getY()));


                    myParent.stroke(outerColor);

                    myParent.strokeWeight(1);
                    if (edge.inner) {
                        myParent.stroke(255, 0, 0);
                    }

                    myParent.line(edgeStartX, edgeStartY, edgeEndX, edgeEndY);
                   // edge.drawPartners(myParent);
                }


            }
        } catch (NullPointerException e) {
            System.out.println("you tried to draw an edge in the voronoi that doesn't exist");
        }*/


	}



	public void print(float color,boolean justShade, Base base, boolean justBase) {

		myParent.background(100, 100, 100);
		double drawGap =rightExtreme.start.getX()-leftExtreme.start.getX();
		float widthOffset = (float)(drawGap*ribNum/2);

		for(int k=0;k<ribNum;k++){
			float newDrawGap = (float)drawGap*k;
			for(int i =0;i<notches.size();i++){

				notches.get(i).drawOffset(myParent,1, (int)color, newDrawGap-widthOffset);
			}
		}
		if(!justShade){
		for(int k=0;k<ribNum;k++){
			for(int i=0;i<clippedFaces.faces.size();i++){
				clippedFaces.faces.get(i).drawOffset(myParent,drawGap*k-widthOffset);
				}
			}
		}
		//draw border of diagram
		try {

			for(int k=0;k<ribNum;k++){
				float newDrawGap = (float)drawGap*k;
				for (int i = 0; i < shadeBorder.edges.size(); i++) {


					DCHalfEdge edge = shadeBorder.edges.get(i);
					if(k==0){
						if(rightEdgesToRemove.indexOf(edge)==-1){

							float edgeStartX = (float) (edge.start.getX());
							float edgeStartY = (float) (edge.start.getY());

							float edgeEndX = (float) (edge.end.getX());
							float edgeEndY = (float) (edge.end.getY());
							myParent.stroke(color);
							myParent.strokeWeight(1);


							myParent.line(edgeStartX+newDrawGap-widthOffset, edgeStartY,edgeEndX+newDrawGap-widthOffset, edgeEndY);
							//edge.drawPartners(myParent);
						}
					}
					if(k==ribNum-1){
						if(leftEdgesToRemove.indexOf(edge)==-1){

							float edgeStartX = (float) (edge.start.getX());
							float edgeStartY = (float) (edge.start.getY());

							float edgeEndX = (float) (edge.end.getX());
							float edgeEndY = (float) (edge.end.getY());
							myParent.stroke(color);
							myParent.strokeWeight(1);


							myParent.line(edgeStartX+newDrawGap-widthOffset, edgeStartY,edgeEndX+newDrawGap-widthOffset, edgeEndY);
							//edge.drawPartners(myParent);
						}
					}
					else{
						if(rightEdgesToRemove.indexOf(edge)==-1&& leftEdgesToRemove.indexOf(edge)==-1){

							float edgeStartX = (float) (edge.start.getX());
							float edgeStartY = (float) (edge.start.getY());

							float edgeEndX = (float) (edge.end.getX());
							float edgeEndY = (float) (edge.end.getY());
							myParent.stroke(color);
							myParent.strokeWeight(1);


							myParent.line(edgeStartX+newDrawGap-widthOffset, edgeStartY,edgeEndX+newDrawGap-widthOffset, edgeEndY);
							//edge.drawPartners(myParent);
						}
					}
				}
			}
		} catch (NullPointerException e) {
			System.out.println("you tried to draw an edge in the border that doesn't exist");
		}
		if(!justShade){
		tabs.get(0).drawOffset(myParent,1, (int)color,0);
		tabs.get(1).drawOffset(myParent,1, (int)color,0);
		
		//base.translate(baseX,baseY);
		}
	
	}

	
	public void printBase( float color, Base base ){
		myParent.background(100, 100, 100);
		double drawGap =rightExtreme.start.getX()-leftExtreme.start.getX();
		float widthOffset = (float)(drawGap*ribNum/2);

			double baseX= this.shadeBorder.edges.get(shadeBorder.edges.size()/2).start.getX()+this.shadeBorder.edges.get(shadeBorder.edges.size()/2).length/2-widthOffset;
			double baseY= this.shadeBorder.edges.get(shadeBorder.edges.size()/2).start.getY()+base.findRad();
			myParent.pushMatrix();
			myParent.translate(myParent.width/2, myParent.height/2);
			base.draw(myParent, 1, (int)color);
			myParent.popMatrix();
		
	}
	
	
	private DCHalfEdge findExtremeEdge(String side){
		DCHalfEdge extreme=null;
		if(side=="left"){
			extreme=this.shadeBorder.edges.get(1);
			for(int i=1;i<this.shadeBorder.edges.size()/2;i++){
				DCHalfEdge currentEdge = this.shadeBorder.edges.get(i);
				CompPoint max;
				if(extreme.start.getX()<extreme.end.getX()){
					max=extreme.start;
				}
				else{
					max=extreme.end;
				}
				if (currentEdge.start.getX()<max.getX()||currentEdge.end.getX()<max.getX()){
					extreme=currentEdge;
				}
			}

		}
		if(side=="right"){
			extreme=this.shadeBorder.edges.get(this.shadeBorder.edges.size()/2+1);
			for(int i=this.shadeBorder.edges.size()/2+1;i<this.shadeBorder.edges.size();i++){
				DCHalfEdge currentEdge = this.shadeBorder.edges.get(i);
				CompPoint max;
				if(extreme.start.getX()>extreme.end.getX()){
					max=extreme.start;
				}
				else{
					max=extreme.end;
				}
				if (currentEdge.start.getX()>max.getX()||currentEdge.end.getX()>max.getX()){
					extreme=currentEdge;
				}
			}
		}
		return extreme;
	}
	//generates random gaussian double
	private double getRandomRanged(double aStart, double aEnd, Random aRandom) {
		if (aStart > aEnd) {
			throw new IllegalArgumentException("Start cannot exceed End.");
		}
		//get the range, casting to long to avoid overflow problems
		double range = aEnd - aStart;
		// compute a fraction of the range, 0 <= frac < range
		double fraction = (range * aRandom.nextGaussian());
		double randomNumber = (fraction + aStart);
		return randomNumber;
	}


}