package com.design;

import com.datastruct.DoublyConnectedEdgeList;
import com.math.CompPoint;
import processing.core.PApplet;
import java.util.Vector;

/**
 *
 * jenniferjacobs
 * Date: 4/20/12
 * Time: 1:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class ScreenManager {

    private LampShape lamp;
    private Pattern pattern;
    private Model model;


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

    private Button modelViewButton;
    private Button partViewButton;
    private Button patternViewButton;

    private Button pointsButton;
    private Button saveButton;

    private double width;
    private double height;
    private Vector<CompPoint> currentPoints;
    private PApplet myParent;

    public ScreenManager(LampShape lamp, Pattern pattern,Model model,PApplet myParent){
       this.lamp = lamp;
       this.pattern=pattern;
       this.model=model;
       this.width=myParent.width;
       this.height=myParent.height;
       this.myParent = myParent;

        float sliderX = (float)this.width - 150;
        float sliderY = (float)this.height - 600;
        float sliderW = 100;
        float sliderH = 20;


        //sliders
        ribSlider = new Slider(myParent);
        ribSlider.init(sliderX,sliderY,sliderW,sliderH,(float)lamp.ribNum,3,20,"side number");
        sliderY += sliderH + 25;

        resSlider = new Slider(myParent);
        resSlider.init(sliderX,sliderY,sliderW,sliderH,(float)lamp.curveResolution/2,10,100,"resolution");
        sliderY += sliderH + 25;

        middleWidthSlider = new Slider(myParent);
        middleWidthSlider.init(sliderX,sliderY,sliderW,sliderH,(float)(lamp.maxWidth/lamp.ptMilConversion),100,300,"width");
        sliderY += sliderH + 25;

        heightSlider = new Slider(myParent);
        heightSlider.init(sliderX,sliderY,sliderW,sliderH,(float)(lamp.maxHeight/lamp.ptMilConversion),100,300,"height");
        sliderY += sliderH + 25;


        topWidthSlider = new Slider(myParent);
        topWidthSlider.init(sliderX,sliderY,sliderW,sliderH,(float)(lamp.topWidth/lamp.ptMilConversion),50,300,"top width");
        sliderY += sliderH + 25;

        bottomWidthSlider = new Slider(myParent);
        bottomWidthSlider.init(sliderX,sliderY,sliderW,sliderH,(float)(lamp.bottomWidth/lamp.ptMilConversion),50,300,"bottom width");
        sliderY += sliderH + 25;

        bottomPosSlider = new Slider(myParent);
        bottomPosSlider.init(sliderX,sliderY,sliderW,sliderH,(float)(lamp.bottomCirclePos/lamp.ptMilConversion),20,150,"bottom base position");
        sliderY += sliderH + 25;

        topPosSlider = new Slider(myParent);
        topPosSlider.init(sliderX,sliderY,sliderW,sliderH,(float)(lamp.topCirclePos/lamp.ptMilConversion),20,150,"top base position");
        sliderY += sliderH + 25;

        bottomHoleSlider = new Slider(myParent);
        bottomHoleSlider.init(sliderX,sliderY,sliderW,sliderH,(float)(lamp.bottomHoleWidth/lamp.ptMilConversion),20,300,"bottom hole width");
        sliderY += sliderH + 25;

        topHoleSlider = new Slider(myParent);
        topHoleSlider.init(sliderX,sliderY,sliderW,sliderH,(float)(lamp.topHoleWidth/lamp.ptMilConversion),20,650,"top hole width");
        sliderY += sliderH + 20;

        patternSlider = new Slider(myParent);
        patternSlider.init(sliderX,sliderY,sliderW,sliderH,(float)1,1,10,"pattern thickness");
        sliderY += sliderH + 20;


        //buttons

        float buttonX = 20;
        float buttonY = (float)this.height - 100;
        float buttonW = 20;
        float buttonH = 20;


        modelViewButton = new Button(myParent);
        modelViewButton.init(buttonX,buttonY,buttonW,buttonH,true,false,"model");
        buttonX +=50;
        partViewButton = new Button(myParent);
        partViewButton.init(buttonX,buttonY,buttonW,buttonH,false,false,"parts");
        buttonX +=50;
        patternViewButton = new Button(myParent);
        patternViewButton.init(buttonX,buttonY,buttonW,buttonH,false,false,"pattern");
        buttonX =20;
        buttonY +=40;
        pointsButton = new Button(myParent);
        pointsButton.init(buttonX,buttonY,buttonW,buttonH,false,true,"points");
        buttonX +=50;

        saveButton = new Button(myParent);
        saveButton.init(buttonX,buttonY,buttonW,buttonH,false,false,"save");
    }

    public void draw(int screenNum,boolean drawPoints,Vector<CompPoint> currentPoints){
        this.currentPoints=currentPoints;
        recomputeLamp();
        try{
        lamp.renderLamp();
        }
        catch (java.lang.NullPointerException e){
            System.out.println("something went wrong with the base");
        }
          if(modelViewButton.getValue()){


                drawModel();
          }

        else if(partViewButton.getValue()){


            drawParts(-300,255);
        }


        if(patternViewButton.getValue()){


            drawPattern(pointsButton.getValue(),currentPoints,255);
        }



        middleWidthSlider.draw();
        heightSlider.draw();
        bottomWidthSlider.draw();
        topWidthSlider.draw();
        topHoleSlider.draw();
        bottomHoleSlider.draw();
        ribSlider.draw();
        patternSlider.draw();
        modelViewButton.draw();
        partViewButton.draw();
        resSlider.draw();
        bottomPosSlider.draw();
       topPosSlider.draw();
        patternViewButton.draw();
        pointsButton.draw();
        saveButton.draw();


    }

    //-----------------mouse methods-----------------//

    public void mousePressed(float mouseX, float mouseY){
        boolean actioned = false;

        if( middleWidthSlider.checkForMousePress(mouseX,mouseY) ) actioned = true;;
        if( heightSlider.checkForMousePress(mouseX,mouseY)) actioned = true;
        if( bottomWidthSlider.checkForMousePress(mouseX,mouseY)) actioned = true;
        if( topWidthSlider.checkForMousePress(mouseX,mouseY)) actioned = true;
        if( bottomHoleSlider.checkForMousePress(mouseX, mouseY)) actioned = true;
        if( topHoleSlider.checkForMousePress(mouseX,mouseY)) actioned = true;
        if( ribSlider.checkForMousePress(mouseX,mouseY)) actioned = true;
        if( resSlider.checkForMousePress(mouseX,mouseY)) actioned = true;
        if( bottomPosSlider.checkForMousePress(mouseX,mouseY)) actioned = true;
        if( topPosSlider.checkForMousePress(mouseX,mouseY)) actioned = true;
        if( patternSlider.checkForMousePress(mouseX, mouseY)) actioned = true;

        if( modelViewButton.checkForMousePress(mouseX,mouseY)){

            partViewButton.setValue(false);
            patternViewButton.setValue(false);
            actioned = true;
        }
        if( partViewButton.checkForMousePress(mouseX,mouseY)){

            patternViewButton.setValue(false);
            modelViewButton.setValue(false);
            actioned = true;
        }
        if( patternViewButton.checkForMousePress(mouseX,mouseY)){

            partViewButton.setValue(false);
            modelViewButton.setValue(false);

            actioned = true;
        }
        if( pointsButton.checkForMousePress(mouseX,mouseY)) actioned = true;
        if( saveButton.checkForMousePress(mouseX,mouseY)){
            print();
            actioned = true;
        }

    }

    public void mouseDragged(float mouseX, float mouseY){
        boolean actioned = false;
        if( middleWidthSlider.checkForMouseDrag(mouseX,mouseY) ) actioned = true;
        if( heightSlider.checkForMouseDrag(mouseX,mouseY) ) actioned = true;
        if( bottomWidthSlider.checkForMouseDrag(mouseX,mouseY) ) actioned = true;
        if( topWidthSlider.checkForMouseDrag(mouseX,mouseY) ) actioned = true;
        if(bottomHoleSlider.checkForMouseDrag(mouseX,mouseY) ) actioned = true;
        if(topHoleSlider.checkForMouseDrag(mouseX,mouseY) ) actioned = true;
        if(ribSlider.checkForMouseDrag(mouseX,mouseY) ) actioned = true;
        if(bottomPosSlider.checkForMouseDrag(mouseX,mouseY) ) actioned = true;
        if(topPosSlider.checkForMouseDrag(mouseX,mouseY) ) actioned = true;
        if(resSlider.checkForMouseDrag(mouseX,mouseY) ) actioned = true;
        if(patternSlider.checkForMouseDrag(mouseX,mouseY) ) actioned = true;

       /*if( !actioned ){
            patternOriginX = mouseX;
            patternOriginY = mouseY;
            println("New Origin: " + patternOriginX + " " + patternOriginY);
        } */

    }

    //-------------------------------------------------------//

    public void  print(){
        String filename;

        recomputeLamp();
        lamp.renderLamp();


        if(partViewButton.getValue()){

            filename="parts.pdf";
            myParent.beginRaw(myParent.PDF,filename);
            drawParts(0,0);
        }


        if(patternViewButton.getValue()){

          filename="pattern.pdf";
          myParent.beginRaw(myParent.PDF,filename);
          drawPattern(false,currentPoints,0);
        }
        //myParent.
        myParent.endRaw();
        saveButton.setValue(false);
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

    private void recomputeLamp(){
        this.lamp.maxWidth= middleWidthSlider.getSliderValue()*lamp.ptMilConversion;//sets the width of the middle of your lamp.
        this.lamp.maxHeight= heightSlider.getSliderValue()*lamp.ptMilConversion; // sets the height of your lamp.
        this.lamp.bottomWidth =bottomWidthSlider.getSliderValue()*lamp.ptMilConversion; //sets the width of the bottom of your lamp.
        this.lamp.topWidth=topWidthSlider.getSliderValue()*lamp.ptMilConversion; // sets the width of the top of your lamp.
        this.lamp.topHoleWidth=topHoleSlider.getSliderValue()*lamp.ptMilConversion; // sets the width of the top of your lamp.
        this.lamp.bottomHoleWidth=bottomHoleSlider.getSliderValue()*lamp.ptMilConversion; // sets the width of the top of your lamp.
        this.lamp.ribNum= (int) ribSlider.getSliderValue(); // sets the width of the top of your lamp.
        this.lamp.curveResolution= (int) resSlider.getSliderValue()*2; // sets the width of the top of your lamp.


        this.lamp.bottomCirclePos= bottomPosSlider.getSliderValue()*lamp.ptMilConversion;

        this.lamp.topCirclePos= topPosSlider.getSliderValue()*lamp.ptMilConversion; // sets the width of the top of your lamp.
        this.pattern.thickWeight= (int) patternSlider.getSliderValue();


    }
   private void drawParts(float zoom,float color){

        lamp.draw(zoom,color);

    }


    private void drawPattern(Boolean drawPoints,Vector<CompPoint> currentPoints,float color){

        DoublyConnectedEdgeList[] borders = lamp.renderLamp();
        DoublyConnectedEdgeList shadeBorder = borders[0];
       pattern.defineVorDiagram(shadeBorder, currentPoints);
       pattern.draw(drawPoints,color);

    }

    private void drawModel(){

        model.draw(lamp.maxWidth,lamp.maxHeight);
    }

}
