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

import com.datastruct.DoublyConnectedEdgeList;
import com.math.CompPoint;
import com.math.Geom;
import processing.core.PApplet;


import java.awt.event.MouseEvent;
import java.util.Vector;

public class Controller {


    private PApplet myParent; //proccessing applet
    private Vector<CompPoint> currentPoints;  // vector that holds all points passed in by the user
    private LampShape lamp;  // lamp object
    private Pattern pattern; //voronoi pattern container
    private Model model; //3d model container
    private Part shadeBorder;
    private ScreenManager screenManager;
    private double notchLimit = 20;
    private String baseType = "top";
    private boolean drawPoints = false; //toggles whether points are drawn or not
    private String type ="wood";

    //Controller class for interfacing between library and processing. User only calls methods from this class.
    public Controller(PApplet myParent, String type) {
    	this.type=type;
        this.myParent = myParent;


        currentPoints = new Vector<CompPoint>();

        this.pattern = new Pattern(type,myParent);

        this.model = new Model(myParent);

        this.lamp = new LampShape(myParent, this.model);
        
        //this should be defined differently
        this.shadeBorder = new Part(0,0);
        myParent.registerDraw(this);
        myParent.registerMouseEvent(this);
        

    }


    //-----------------sets calls for lamp parameters-----------------//

    public void setWidth(float width) {
        if(width<100){
            width=100;
        }

        if(width>300){
            width=300;
        }
        this.lamp.maxWidth = width * this.lamp.ptMilConversion;
    }

    public void setHeight(float height) {

        if(height<100){
            height=100;
        }

        if(height>300){
            height=300;
        }
        this.lamp.maxHeight = height * this.lamp.ptMilConversion;
    }

    public void setNotchWidth(float width) {

        if(width<1){
            width=1;
        }

        if(width>10){
            width=10;
        }

        this.lamp.notchHeight = width * this.lamp.ptMilConversion;
    }

    public void setNotchHeight(float height) {

        if(height<1){
            height=1;
        }

        if(height>10){
            height=10;
        }
        this.lamp.notchHeight = height * this.lamp.ptMilConversion;
    }

    public void setTopWidth(float top) {

        if(top<50){
           top=50;
        }

        if(top>300){
           top=300;
        }

        this.lamp.topWidth = top * this.lamp.ptMilConversion;

    }

    public void setBottomWidth(float bottom) {
        if(bottom<50){
            bottom=50;
        }

        if(bottom>300){
            bottom=300;
        }

        this.lamp.bottomWidth = bottom * this.lamp.ptMilConversion;
    }

    public void setResolution(int resolution) {

        if(resolution<10){
            resolution=10;
        }

        if(resolution>100){
            resolution=100;
        }


        this.lamp.curveResolution = resolution;
    }

    public void setSideNum(int side) {

        if(side<4){
            side=4;
        }

        if(side>20){
            side=20;
        }
        this.lamp.ribNum = side;
    }


    public void setTopCirclePos(float pos) {
        if(pos<5){
            pos=5;
        }

        if(pos>150){
            pos=150;
        }

        this.lamp.topCirclePos = pos * this.lamp.ptMilConversion;
    }

    public void setBottomCirclePos(float pos) {

        if(pos<5){
            pos=5;
        }

        if(pos>150){
            pos=150;
        }

        this.lamp.bottomCirclePos = pos * this.lamp.ptMilConversion;
    }

    public void setTopHoleWidth(float width) {

        if(width<10){
            width=10;
        }

        if(width>280){
            width=280;
        }
        this.lamp.topHoleWidth = width * this.lamp.ptMilConversion / 2;
    }

    public void setBottomHoleWidth(float width) {

        if(width<10){
            width=10;
        }

        if(width>280){
            width=280;
        }

        this.lamp.bottomHoleWidth = width * this.lamp.ptMilConversion / 2;
    }

    public void setPatternWeight(float weight){

        if(weight<0.2){
            weight=(float)0.2;
        }

        if(weight>0.95){
            weight=(float)0.95;
        }
        this.pattern.thickWeight=weight;
    }
    
    public void setBaseSide(String type){

        if(type!="bottom"&&type!="top"){
        	type = "top";
        }
        if(type=="bottom"){
        	this.screenManager.setBaseType(false);
        }
        else{
        	this.screenManager.setBaseType(true);
        }
        this.baseType = type;
        
    }

    //----------------------------------------------------//


    //----processing applet registered methods---------//

    public void draw() {

        if(myParent.frameCount==1){
            this.renderAll();
        }
        //println(frameRate);
        this.drawScreen(); // draws the selected screen for you to preview according to the screenNum variable


    }

    public void mouseEvent(MouseEvent event) {
        int x = event.getX();
        int y = event.getY();

        switch (event.getID()) {
            case MouseEvent.MOUSE_PRESSED:
                this.mousePressed(x,y);
                break;
            case MouseEvent.MOUSE_RELEASED:
                // do something for mouse released
                break;
            case MouseEvent.MOUSE_CLICKED:
                // do something for mouse clicked
                break;
            case MouseEvent.MOUSE_DRAGGED:
                this.mouseDragged(x,y);

                break;
            case MouseEvent.MOUSE_MOVED:
                // umm... forgot
                break;
        }
    }

    //-----------------point managment methods-----------------//
    //adds new point to system

    public void addCartPoint(double x, double y) {
        currentPoints.addElement(new CompPoint(x, y));

    }

    public void addPolarPoint(float orgX, float orgY, float theta, float rad) {

        double xPos = Math.cos(theta)*rad+orgX;
        double yPos =  Math.sin(theta)*rad+orgY;

        currentPoints.addElement(new CompPoint(xPos, yPos));

    }


    //clears all points from vector
    public void clearAllPoints() {
        currentPoints = new Vector<CompPoint>();

    }

    //removes a specific point from vector
    public void removePoint(double x, double y) {
        CompPoint tempPoint = new CompPoint(x, y);

        for (int i = 0; i < currentPoints.size(); i++) {
            CompPoint point = currentPoints.get(i);
            if ((point.getX() == tempPoint.getX()) && (point.getY() == tempPoint.getY())) {
                currentPoints.remove(i);
            }
        }
    }

    //enables user to show or hide points
    public void drawPoints(boolean set) {
        drawPoints = set;
    }
    //----------------------------------------------------//


    //-----------------draw and print methods-----------------//
    //draws the current screen passed in by the user
    public void drawScreen() {
        screenManager.draw(drawPoints, currentPoints);

    }
    //prints the current screen

    //----------------------------------------------------//

    //-----------------mouse methods-----------------//

    public void mousePressed(float mouseX, float mouseY) {
        screenManager.mousePressed(mouseX, mouseY);
    }

    public void mouseDragged(float mouseX, float mouseY) {
        screenManager.mouseDragged(mouseX, mouseY);

    }

    //-------------------------------------------------------//


    public void renderAll() {
        if (currentPoints.size() == 0) {
            currentPoints.add(new CompPoint(0, 0));
        }
        DoublyConnectedEdgeList[] borders = lamp.renderLamp();
        shadeBorder.edges = borders[0].edges;
        pattern.defineVorDiagram(shadeBorder, currentPoints,this.pattern.thickWeight,notchLimit);

        screenManager = new ScreenManager(this.type,lamp, pattern, model, shadeBorder, myParent,notchLimit,baseType);


    }


}
