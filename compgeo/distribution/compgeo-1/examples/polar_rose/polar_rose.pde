import processing.pdf.*;

//import fullscreen.*;

/*This example generates a lamp with no design. Add some points to make it more interesting */

import com.design.Controller;

//global variables
Controller pointController;
int screenNum = 0; //0 for 3d model, 1 for toolpaths, 2 for pattern


void setup() {
  size(1024, 800, P3D); //size of your screen
  
  // Create the fullscreen object
  //FullScreen fs = new FullScreen(this); 
  
  // enter fullscreen mode
  //fs.enter(); 
  
  
  pointController = new Controller(this); //intialize the library

 //CODE FOR CONTROLING LAMP SHAPE ///
  //All units are in millimeters

    pointController.setWidth(170);//sets the width of the middle of your lamp.
  pointController.setHeight(200); // sets the height of your lamp.
  pointController.setBottomWidth(80); //sets the width of the bottom of your lamp.
  pointController.setTopWidth(80); // sets the width of the top of your lamp.

  pointController.setSideNum(6);//sets the number of sides of your lamp.
  pointController.setTopCirclePos(20);//sets the vertical position of your top base
  pointController.setBottomCirclePos(20);//sets the vertical position of your bottom base
  
  pointController.setNotchWidth(8.92);//sets the width of your notches for the press fit
  pointController.setNotchHeight( 5.64);//sets the height of your notches for the press fit

  pointController.setTopHoleWidth(80);//sets the width of the opening in the top base of your lamp
  pointController.setBottomHoleWidth(24);//sets the width of the opening in the bottom base of your lamp
  //NOTE!! Depending on which side your light fixture will be installed, you must set the hole on bottom or top of your lamp to the diameter of the light fixture

  //////////////////////////////////
  
  
  
  // =========GENERATE SPIRAL=============== //
   
   
    int centerLimit = 300; // variable to control the maximum diameter of the spiral
  float theta = 20; //like the diameter of your circle, but increases with every point in your spiral, producing the spiral effect.

 float k=6;


  //this will draw one spiral 
  for (int i = 0; i < centerLimit; i++)
   {
     float interval = TWO_PI/float(centerLimit);
     theta += interval;
     float r =  100*cos(k*theta); 
      
     float x = r*cos(theta)+width/2;
     float y = r*sin(theta)+height/2;
     stroke(0,255,0);
     strokeWeight(4);
     pointController.addPoint(x,y);

      
   }
   pointController.renderAll(); // intializes all of the parameters set above.
}

void draw() {
  
//println(frameRate);
  pointController.drawScreen(screenNum); // draws the selected screen for you to preview according to the screenNum variable
 
 
}



void mousePressed(){
  pointController.mousePressed(mouseX,mouseY);
}

void mouseDragged(){
  pointController.mouseDragged(mouseX,mouseY);
}

void keyPressed() {
 
}




void drawPoint(double orgX, double orgY, float theta, double diameter) { //function that generates and adds circular points

  double xPos = sin(theta)*diameter+orgX;
  double yPos = cos(theta)*diameter+orgY;
  strokeWeight(3);
  pointController.addPoint(xPos, yPos);
}






