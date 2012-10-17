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
import com.file.FileReadWrite;
import com.math.CompPoint;
import com.math.Geom;

import processing.core.PApplet;
import processing.core.PFont;

import java.math.BigDecimal;
import java.util.Vector;

/**
 * jenniferjacobs
 * Date: 4/20/12
 * Time: 1:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class ScreenManager {

	private LampShape lamp;
	private Pattern pattern;
	private Model model;
	private Part shadeBorder;
	private double notchLimit;
	
	private double minTarget=0;
	private double maxTarget=0.5;

	private Slider notchWidthSlider;
	private Slider notchHeightSlider;

	private Slider middleWidthSlider;
	private Slider heightSlider;
	private Slider bottomWidthSlider;
	private Slider topWidthSlider;
	private Slider ribSlider;
	private Slider bottomHoleSlider;
	private Slider topHoleSlider;
	private Slider bottomPosSlider;
	private Slider topPosSlider;
	private Slider resSlider;
	private Slider patternSlider;
	//private Slider minTargetSlider;
	private Slider maxTargetSlider;

	private Button modelViewButton;
	private Button partViewButton;
	private Button patternViewButton;
	private Button baseTypeButton;

	private Button pointsButton;
	private Button saveButton;

	private double width;
	private double height;
	private Vector<CompPoint> currentPoints;
	private PApplet myParent;
	private double milToInchConversion=25.4;

	public String paperBaseType = "top";
	private FileReadWrite file;
	private String type;

	public ScreenManager(String type, LampShape lamp, Pattern pattern, Model model,Part shadeBorder, PApplet myParent, double notchLimit, String baseSide) {
		this.type = type;
		this.lamp = lamp;
		this.pattern = pattern;
		this.model = model;
		this.shadeBorder = shadeBorder;
		this.width = myParent.width;
		this.height = myParent.height;
		this.myParent = myParent;

		float sliderX = (float) this.width - 180;
		float sliderY = 80;
		float sliderW = 100;
		float sliderH = 20;
		this.notchLimit = notchLimit;
		
		file = new FileReadWrite("params.txt",myParent);
		
		double[] vars =file.readFile();
		
		

		//sliders
		
		middleWidthSlider = new Slider(myParent);
		middleWidthSlider.init(sliderX, sliderY, sliderW, sliderH, (float) (vars[0]), 100, 300, "width", "mm");
		sliderY += sliderH + 25;
		
		heightSlider = new Slider(myParent);
		heightSlider.init(sliderX, sliderY, sliderW, sliderH, (float) (vars[1]), 100, 300, "height", "mm");
		sliderY += sliderH + 25;
		
		bottomWidthSlider = new Slider(myParent);
		bottomWidthSlider.init(sliderX, sliderY, sliderW, sliderH, (float) (vars[2]), 50, 300, "bottom width", "mm");
		sliderY += sliderH + 25;
		
		topWidthSlider = new Slider(myParent);
		topWidthSlider.init(sliderX, sliderY, sliderW, sliderH, (float) (vars[3] ), 50, 300, "top width", "mm");
		sliderY += sliderH + 25;

		ribSlider = new Slider(myParent);
		ribSlider.init(sliderX, sliderY, sliderW, sliderH, (float) vars[4], 4, 20, "side number", "");
		sliderY += sliderH + 25;


		topPosSlider = new Slider(myParent);
		topPosSlider.init(sliderX, sliderY, sliderW, sliderH, (float) (vars[5]), 0, 150, "top base position", "mm");
		sliderY += sliderH + 25;
		
		bottomPosSlider = new Slider(myParent);
		bottomPosSlider.init(sliderX, sliderY, sliderW, sliderH, (float) (vars[6] ),5, 150, "bottom base position", "mm");
		sliderY += sliderH + 25;
		
		notchWidthSlider = new Slider(myParent);
		notchWidthSlider.init(sliderX, sliderY, sliderW, sliderH, (float) (vars[7]), 1, 10, "notch width", "mm");
		sliderY += sliderH + 20;

		notchHeightSlider = new Slider(myParent);
		notchHeightSlider.init(sliderX, sliderY, sliderW, sliderH, (float) (vars[8]), 1, 10, "notch height", "mm");
		sliderY += sliderH + 20;
		

		topHoleSlider = new Slider(myParent);
		topHoleSlider.init(sliderX, sliderY, sliderW, sliderH, (float) (vars[9]), 10, 280, "top hole width", "mm");
		sliderY += sliderH + 20;

		bottomHoleSlider = new Slider(myParent);
		bottomHoleSlider.init(sliderX, sliderY, sliderW, sliderH, (float) (vars[10]), 10, 280, "bottom hole width", "mm");
		sliderY += sliderH + 25;

		patternSlider = new Slider(myParent);
		patternSlider.init(sliderX, sliderY, sliderW, sliderH, (float) vars[11], (float)0.3,(float) 1, "pattern thickness", "");
		sliderY += sliderH + 20;
		
	
		maxTargetSlider = new Slider(myParent);
		maxTargetSlider.init(sliderX, sliderY, sliderW, sliderH, (float) vars[12], (float)0,(float) 0.5, "maxTarget", "");
		sliderY += sliderH + 20;

	
		resSlider = new Slider(myParent);
		resSlider.init(sliderX, sliderY, sliderW, sliderH, (float) lamp.curveResolution / 2, 10, 100, "resolution", "");
		sliderY += sliderH + 25;


		//buttons

		float buttonX = 20;
		float buttonY = (float) this.height - 100;
		float buttonW = 20;
		float buttonH = 20;


		modelViewButton = new Button(myParent);
		modelViewButton.init(buttonX, buttonY, buttonW, buttonH, true, false, "model");
		buttonX += 50;
		partViewButton = new Button(myParent);
		partViewButton.init(buttonX, buttonY, buttonW, buttonH, false, false, "parts");
		buttonX += 50;
		patternViewButton = new Button(myParent);
		patternViewButton.init(buttonX, buttonY, buttonW, buttonH, false, false, "pattern");
		buttonX = 20;
		buttonY += 40;
		pointsButton = new Button(myParent);
		pointsButton.init(buttonX, buttonY, buttonW, buttonH, false, true, "points");
		buttonX += 50;
		baseTypeButton = new Button(myParent);
		boolean baseButtonOn = false;
		if(baseSide=="top"){
			baseButtonOn=true;
		}
		else{
			baseButtonOn=false;
		}
		baseTypeButton.init(buttonX, buttonY, buttonW, buttonH, true, true, "baseSide");
		buttonX += 100;

		saveButton = new Button(myParent);
		saveButton.init(buttonX, buttonY, buttonW, buttonH, false, false, "save");
	}

	public void setBaseType(Boolean actioned){
		baseTypeButton.setValue(actioned);
		if (baseTypeButton.getValue()) {


			paperBaseType="bottom";
			baseTypeButton.name="light in bottom";
		}
		else{
			paperBaseType="top";
			baseTypeButton.name="light in top";
		}
	}

	public void draw(boolean drawPoints, Vector<CompPoint> currentPoints) {
		this.currentPoints = currentPoints;
		recomputeLamp();
		
			lamp.renderLamp();
		
			
		
		if (modelViewButton.getValue()) {


			drawModel();
		} else if (partViewButton.getValue()) {


			drawParts(-300, 255,true,true);
		}


		if (patternViewButton.getValue()) {


			drawPattern(pointsButton.getValue(), currentPoints, 255,patternSlider.getSliderValue());
		}

		if (!baseTypeButton.getValue()) {


			paperBaseType="bottom";
			baseTypeButton.name="light in bottom";
		}
		else{
			paperBaseType="top";
			baseTypeButton.name="light in top";
		}


		middleWidthSlider.draw();
		heightSlider.draw();
		bottomWidthSlider.draw();
		topWidthSlider.draw();
		topHoleSlider.draw();
		bottomHoleSlider.draw();
		ribSlider.draw();
		patternSlider.draw();
		//minTargetSlider.draw();
		maxTargetSlider.draw();
		modelViewButton.draw();
		partViewButton.draw();
		//resSlider.draw();
		bottomPosSlider.draw();
		topPosSlider.draw();
		notchWidthSlider.draw();
		notchHeightSlider.draw();
		patternViewButton.draw();
		pointsButton.draw();
		baseTypeButton.draw();
		saveButton.draw();


	}

	//-----------------mouse methods-----------------//

	public void mousePressed(float mouseX, float mouseY) {
		boolean actioned = false;

		if (middleWidthSlider.checkForMousePress(mouseX, mouseY)) actioned = true;
		
		if (heightSlider.checkForMousePress(mouseX, mouseY)) actioned = true;
		if (bottomWidthSlider.checkForMousePress(mouseX, mouseY)) actioned = true;
		if (topWidthSlider.checkForMousePress(mouseX, mouseY)) actioned = true;
		if (bottomHoleSlider.checkForMousePress(mouseX, mouseY)) actioned = true;
		if (topHoleSlider.checkForMousePress(mouseX, mouseY)) actioned = true;
		if (ribSlider.checkForMousePress(mouseX, mouseY)) actioned = true;
		if (resSlider.checkForMousePress(mouseX, mouseY)) actioned = true;
		if (bottomPosSlider.checkForMousePress(mouseX, mouseY)) actioned = true;
		if (topPosSlider.checkForMousePress(mouseX, mouseY)) actioned = true;
		if (patternSlider.checkForMousePress(mouseX, mouseY)) actioned = true;
		//if (minTargetSlider.checkForMousePress(mouseX, mouseY)) actioned = true;
		if (maxTargetSlider.checkForMousePress(mouseX, mouseY)) actioned = true;
		if (notchWidthSlider.checkForMousePress(mouseX, mouseY)) actioned = true;
		if (notchHeightSlider.checkForMousePress(mouseX, mouseY)) actioned = true;

		if (modelViewButton.checkForMousePress(mouseX, mouseY)) {

			partViewButton.setValue(false);
			patternViewButton.setValue(false);
			actioned = true;
		}
		if (partViewButton.checkForMousePress(mouseX, mouseY)) {

			patternViewButton.setValue(false);
			modelViewButton.setValue(false);
			actioned = true;
		}
		if (patternViewButton.checkForMousePress(mouseX, mouseY)) {

			partViewButton.setValue(false);
			modelViewButton.setValue(false);

			actioned = true;
		}
		if (pointsButton.checkForMousePress(mouseX, mouseY)) actioned = true;
		if (baseTypeButton.checkForMousePress(mouseX, mouseY)) actioned = true;
		if (saveButton.checkForMousePress(mouseX, mouseY)) {
			print();
			actioned = true;
		}

	}

	public void mouseDragged(float mouseX, float mouseY) {
		boolean actioned = false;
		if (middleWidthSlider.checkForMouseDrag(mouseX, mouseY)) actioned = true;
		if (heightSlider.checkForMouseDrag(mouseX, mouseY)) actioned = true;
		if (bottomWidthSlider.checkForMouseDrag(mouseX, mouseY)) actioned = true;
		if (topWidthSlider.checkForMouseDrag(mouseX, mouseY)) actioned = true;
		if (bottomHoleSlider.checkForMouseDrag(mouseX, mouseY)) actioned = true;
		if (topHoleSlider.checkForMouseDrag(mouseX, mouseY)) actioned = true;
		if (ribSlider.checkForMouseDrag(mouseX, mouseY)) actioned = true;
		if (bottomPosSlider.checkForMouseDrag(mouseX, mouseY)) actioned = true;
		if (topPosSlider.checkForMouseDrag(mouseX, mouseY)) actioned = true;
		if (resSlider.checkForMouseDrag(mouseX, mouseY)) actioned = true;
		if (patternSlider.checkForMouseDrag(mouseX, mouseY)) actioned = true;
		//if (minTargetSlider.checkForMouseDrag(mouseX, mouseY)) actioned = true;
		if (maxTargetSlider.checkForMouseDrag(mouseX, mouseY)) actioned = true;
		if (notchWidthSlider.checkForMouseDrag(mouseX, mouseY)) actioned = true;
		if (notchHeightSlider.checkForMouseDrag(mouseX, mouseY)) actioned = true;

		/*if( !actioned ){
           patternOriginX = mouseX;
           patternOriginY = mouseY;
           println("New Origin: " + patternOriginX + " " + patternOriginY);
       } */

	}

	//-------------------------------------------------------//

	public void print() {
		String filename;

		recomputeLamp();
		lamp.renderLamp();


		if(type=="paper"){


			filename = "shade.pdf";
			myParent.beginRaw(myParent.PDF, filename);
			printPattern(false, currentPoints, 0,patternSlider.getSliderValue(),true,false);
			//drawParts(0, 0,true,false);
			myParent.endRaw();


			filename = "pattern.pdf";
			myParent.beginRaw(myParent.PDF, filename);
			printPattern(false, currentPoints, 0,patternSlider.getSliderValue(),false,false);
			myParent.endRaw();


			filename = "parts.pdf";
			myParent.beginRaw(myParent.PDF, filename);
			printPattern(false, currentPoints, 0,patternSlider.getSliderValue(),false,true);
			//drawParts(0, 0,false,true);
			myParent.endRaw();
		}
		if(type=="wood"){


			filename = "shade.pdf";
			myParent.beginRaw(myParent.PDF, filename);
			
			drawParts(0, 0,true,false);
			myParent.endRaw();


			filename = "pattern.pdf";
			myParent.beginRaw(myParent.PDF, filename);
			drawPattern(false, currentPoints, 0,patternSlider.getSliderValue());
			myParent.endRaw();


			filename = "parts.pdf";
			myParent.beginRaw(myParent.PDF, filename);
			drawParts(0, 0,false,true);
			myParent.endRaw();
		}

		saveButton.setValue(false);
		double [] vars = new double[14];
		
		vars[0]= middleWidthSlider.getSliderValue();
		vars[1]=heightSlider.getSliderValue();
		vars[2]=bottomWidthSlider.getSliderValue();
		vars[3]=topWidthSlider.getSliderValue();
		vars[4]=ribSlider.getSliderValue();
		vars[5]=topPosSlider.getSliderValue();
		vars[6]=bottomPosSlider.getSliderValue();
		vars[7]=notchWidthSlider.getSliderValue();
		vars[8]=notchHeightSlider.getSliderValue();
		vars[9]= topHoleSlider.getSliderValue();
		vars[10]= bottomHoleSlider.getSliderValue();
		vars[11] = patternSlider.getSliderValue();
		vars[12]= maxTargetSlider.getSliderValue();
		vars[13]=resSlider.getSliderValue();
		
		
		file.writeFile(vars);
		
		myParent.exit();



	}

	/*

    public void saveFiles(int screenNum){
        myParent.loop();
        if(screenNum==0){

        }
        if(screenNum==1){
            myParent.pushMatrix();
            myParent.translate(0,0,0);
            String filename1="rib.pdf";
            String filename2="topBase.pdf";
            String filename3="bottomBase.pdf";
            String filename4="shade.pdf";
            myParent.popMatrix();


            lamp.saveRib(filename1);
            lamp.savetopBase(filename2);
            lamp.savebottomBase(filename3);
            lamp.saveShade(filename4);
        }

        if(screenNum==2){
            String filename5="pattern.pdf";
            this.drawDiagram(true);
            myParent.loop();
        }


    }
	 */

	private void recomputeLamp() {
		this.lamp.maxWidth = middleWidthSlider.getSliderValue() * lamp.ptMilConversion;//sets the width of the middle of your lamp.
		this.lamp.maxHeight = heightSlider.getSliderValue() * lamp.ptMilConversion; // sets the height of your lamp.
		this.lamp.bottomWidth = bottomWidthSlider.getSliderValue() * lamp.ptMilConversion; //sets the width of the bottom of your lamp.
		this.lamp.topWidth = topWidthSlider.getSliderValue() * lamp.ptMilConversion; // sets the width of the top of your lamp.
		this.lamp.topHoleWidth = topHoleSlider.getSliderValue() * lamp.ptMilConversion; // sets the width of the top of your lamp.
		this.lamp.bottomHoleWidth = bottomHoleSlider.getSliderValue() * lamp.ptMilConversion; // sets the width of the bottom of your lamp.
		this.lamp.ribNum = (int) ribSlider.getSliderValue(); // sets the width of the top of your lamp.
		this.lamp.curveResolution = (int) resSlider.getSliderValue() * 2; // sets the width of the top of your lamp.
		this.lamp.notchWidth = notchWidthSlider.getSliderValue() * lamp.ptMilConversion; // sets the width of the top of your lamp.
		this.lamp.notchHeight = notchHeightSlider.getSliderValue() * lamp.ptMilConversion; // sets the width of the top of your lamp.


		this.lamp.bottomCirclePos = bottomPosSlider.getSliderValue() * lamp.ptMilConversion;

		this.lamp.topCirclePos = topPosSlider.getSliderValue() * lamp.ptMilConversion; // sets the width of the top of your lamp.
		this.pattern.thickWeight = (int) patternSlider.getSliderValue();


	}

	private void drawParts(float zoom, float color, boolean shadeDraw, boolean partsDraw) {
		DoublyConnectedEdgeList[] borders = lamp.renderLamp();
		shadeBorder.edges = borders[0].edges;
		lamp.draw(zoom, color,shadeDraw,partsDraw);
		PFont font = myParent.loadFont("din_bold.vlw");
		pattern.insertTabs(shadeBorder,30,25,40,this.lamp.ribNum);
		myParent.textFont(font, 14);
		myParent.fill(255);
		this.myParent.text("Total Width="+(float)Geom.round(pattern.totalWidth/lamp.ptMilConversion,2,BigDecimal.ROUND_HALF_UP)+" mm",20,20);
		this.myParent.text("Total Height="+(float)Geom.round(pattern.totalHeight/lamp.ptMilConversion,2,BigDecimal.ROUND_HALF_UP)+" mm",20,40);

	}




	private void drawPattern(Boolean drawPoints, Vector<CompPoint> currentPoints, float color, float thickness) {

		DoublyConnectedEdgeList[] borders = lamp.renderLamp();
		shadeBorder.edges = borders[0].edges;
		pattern.defineVorDiagram(shadeBorder, currentPoints,thickness,0, maxTargetSlider.getSliderValue(),notchLimit);
		pattern.insertTabs(shadeBorder, 30,25,40,this.lamp.ribNum);
		pattern.draw(drawPoints, color);
		PFont font = myParent.loadFont("din_bold.vlw");
		myParent.textFont(font, 14);
		myParent.fill(255);
		this.myParent.text("Total Width="+(float)Geom.round(pattern.totalWidth/lamp.ptMilConversion,2,BigDecimal.ROUND_HALF_UP)+" mm",20,20);
		this.myParent.text("Total Height="+(float)Geom.round(pattern.totalHeight/lamp.ptMilConversion,2,BigDecimal.ROUND_HALF_UP)+" mm",20,40);

	}

	private void printPattern(Boolean drawPoints, Vector<CompPoint> currentPoints, float color, float thickness, boolean drawShade, boolean drawBase) {

		if(drawBase){
			pattern.printBase( color, this.lamp.generatePaperBase(this.paperBaseType));
		}else{
			DoublyConnectedEdgeList[] borders = lamp.renderLamp();
			shadeBorder.edges = borders[0].edges;
			pattern.defineVorDiagram(shadeBorder, currentPoints,thickness,0,maxTargetSlider.getSliderValue(),notchLimit);
			pattern.insertTabs(shadeBorder,30,25,40,this.lamp.ribNum);
			pattern.print(color,drawShade,this.lamp.generatePaperBase(this.paperBaseType),drawBase);

		}
	}

	private void drawModel() {
		DoublyConnectedEdgeList[] borders = lamp.renderLamp();
		shadeBorder.edges = borders[0].edges;
		model.draw(lamp.maxWidth, lamp.maxHeight);
		PFont font = myParent.loadFont("din_bold.vlw");
		pattern.insertTabs(shadeBorder, 30,25,40,this.lamp.ribNum);
		myParent.textFont(font, 14);
		myParent.fill(255);
		this.myParent.text("Total Width="+(float)Geom.round(pattern.totalWidth/lamp.ptMilConversion,2,BigDecimal.ROUND_HALF_UP)+" mm",20,20);
		this.myParent.text("Total Height="+(float)Geom.round(pattern.totalHeight/lamp.ptMilConversion,2,BigDecimal.ROUND_HALF_UP)+" mm",20,40);
	}

}
