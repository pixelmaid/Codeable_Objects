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

/*This example generates a lamp with a circle design*/

import processing.pdf.*;
import com.design.Controller;

//instance of the library controller
Controller pointController;


void setup() {

  size(1024, 700, P3D); //size of your screen
 /* if you are using the processing 2.0 beta, switch your renderer from P3D to OPENGL
  this may effect the drawing of the 3d model, but will allow you to correctly save out pdfs. We 
  are working to address the changes implemented by the new version of processing*/
  
  pointController = new Controller(this, "wood"); //intialize the library

  //CODE FOR CONTROLING LAMP SHAPE ///
  //All units are in millimeters with exception of resolution, side number and pattern weight

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

  pointController.setPatternWeight(0.8);//sets the weight of your pattern;
  //NOTE!! Depending on which side your light fixture will be installed, you must set the hole on bottom or top of your lamp to the diameter of the light fixture

  //////////////////////////////////



  // =========GENERATE CIRCLE=============== //

  int rad = 150; //radius of circle


  pointController.addCartPoint(width/2, height/2); // adds a new point to your pattern at the center of the screen

  int drawLimit = 20; // we will define the circles by a set of evenly spaced points. This variable controls the number of points in your circles

  for (int i=0;i<drawLimit;i++) { //loop over the number of points in the circle

    float theta = i*(float)Math.PI*2/drawLimit; // determines the degree position of your current point in radians
    pointController.addPolarPoint(width/2, height/2, theta, rad);// adds the point to your design
  }
}


//general processing methods
void draw() {
}

void mousePressed() {
}

void mouseDragged() {
}

