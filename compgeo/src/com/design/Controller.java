/*
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
import processing.core.PApplet;

import java.util.Vector;

public class Controller {


	private PApplet myParent; //proccessing applet
	private Vector<CompPoint> currentPoints;  // vector that holds all points passed in by the user
	private LampShape lamp;  // lamp object
    private Pattern pattern; //voronoi pattern container
    private Model model; //3d model container
    private ScreenManager screenManager;




	private boolean drawPoints = false; //toggles whether points are drawn or not

     //Controller class for interfacing between library and processing. User only calls methods from this class.
	public Controller(PApplet myParent){

		this.myParent = myParent;



		currentPoints = new Vector<CompPoint>();

        this.pattern=new Pattern(myParent);

        this.model = new Model(myParent);

        this.lamp=new LampShape(myParent,this.model);


	}


    //-----------------sets calls for lamp parameters-----------------//

	public void setWidth(float width){
		this.lamp.maxWidth=width*this.lamp.ptMilConversion;
	}

	public void setHeight(float height){
		this.lamp.maxHeight=height*this.lamp.ptMilConversion;
	}

    public void setNotchWidth(float width){
        this.lamp.notchHeight=width*this.lamp.ptMilConversion;
    }

    public void setNotchHeight(float height){
        this.lamp.notchHeight=height*this.lamp.ptMilConversion;
    }

	public void setTopWidth(float top){
		
			this.lamp.topWidth = top*this.lamp.ptMilConversion;
		
	}

	public void setBottomWidth(float bottom){
		this.lamp.bottomWidth= bottom*this.lamp.ptMilConversion;
	}

	public void setResolution(int resolution){
		this.lamp.curveResolution=resolution;
	}

	public void setSideNum(int side){
		this.lamp.ribNum = side;
	}


	public void setTopCirclePos(float pos){
		this.lamp.topCirclePos=pos*this.lamp.ptMilConversion;
	}

	public void setBottomCirclePos(float pos){
		this.lamp.bottomCirclePos=pos*this.lamp.ptMilConversion;
	}

	public void setTopHoleWidth(float width){
		this.lamp.topHoleWidth=width*this.lamp.ptMilConversion/2;
	}

	public void setBottomHoleWidth(float width){
		this.lamp.bottomHoleWidth=width*this.lamp.ptMilConversion/2;
	}

    //----------------------------------------------------//


    //-----------------point managment methods-----------------//
    //adds new point to system

	public void addPoint(double x, double y){
		currentPoints.addElement(new CompPoint(x,y));

	}


    //clears all points from vector
	public void clearAllPoints(){
		currentPoints = new Vector<CompPoint>();

	}

    //removes a specific point from vector
	public void removePoint(double x, double y){
		CompPoint tempPoint   = new CompPoint(x,y);

		for (int i=0;i<currentPoints.size();i++){
			CompPoint point = currentPoints.get(i);
			if((point.getX()==tempPoint.getX())&&(point.getY()==tempPoint.getY())){
				currentPoints.remove(i);
			}
		}
	}

    //enables user to show or hide points
	public void drawPoints(boolean set){
		drawPoints = set;
	}
    //----------------------------------------------------//



    //-----------------draw and print methods-----------------//
    //draws the current screen passed in by the user
	public void drawScreen(int screenNum){
        screenManager.draw(screenNum,drawPoints,currentPoints);

    }
    //prints the current screen

    //----------------------------------------------------//

    //-----------------mouse methods-----------------//

    public void mousePressed(float mouseX, float mouseY){
        screenManager.mousePressed(mouseX,mouseY);
    }

    public void mouseDragged(float mouseX, float mouseY){
        screenManager.mouseDragged(mouseX,mouseY);

    }

    //-------------------------------------------------------//


	public void renderAll(){
		if(currentPoints.size()==0){
			currentPoints.add(new CompPoint(0,0));
		}
		DoublyConnectedEdgeList [] borders = lamp.renderLamp();
        DoublyConnectedEdgeList shadeBorder = borders[0];
		pattern.defineVorDiagram(shadeBorder, currentPoints);

        screenManager= new ScreenManager(lamp,pattern,model,myParent);


	}




}
