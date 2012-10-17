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

/*This example generates a lamp with a polar rose design*/

import processing.pdf.*;
import com.design.Controller;

//instance of the library controller
Controller pointController;


void setup() {

  size(1024, 700, P3D); //size of your screen
   /* if you are using the processing 2.0 beta, switch your renderer from P3D to OPENGL
  this may effect the drawing of the 3d model, but will allow you to correctly save out pdfs. We 
  are working to address the changes implemented by the new version of processing*/

  pointController = new Controller(this,"paper"); //initialize the library- enter "wood for wooden lamp, "paper" for paper version


//==========Generate SINE WAVE===============================//


  double freq = 1.5;                          //Frequency of the sine wave
  int numPoints =25;                      //The number of points that will be drawn for each wave
  int xStep = width/numPoints;             //The x increment at each for loop update
  double vUpdate = (360.0/numPoints)*freq;  //The increment used to update the sine wave value at each for loop update
  double amp = 0;                           //The amplitude of the sine wave (will be set later)
  float v = 0;                             //The value of the sine wave (will be set later)
  double xOrig = 10;                         //The x origin that the sine wave will be drawn from (will be set later)
  double yOrig = 0;                         //The y origin that the sine wave will be drawn from (will be set later)
  double xPos = 0;                          //The x position of a point, updated at each for loop update
  double yPos = 0;                          //The y position of a point, updated at each for loop update

  //Set if you want to draw all three regions and if the points are visible

  boolean drawCenterWaves = true;
  boolean drawTopWaves = true;
  boolean drawBottomWaves = true;

  if ( drawCenterWaves ) {
    v = 0;
    //xOrig = 0;
    yOrig = height/2.0;
    xPos = 0;
    yPos = 0;
    amp = height/8;
    for (int i=0; i<numPoints; i++) {
      yPos = sin(v*DEG_TO_RAD)*amp;
      pointController.addCartPoint(xOrig+xPos, yOrig+yPos);

      yPos = sin((v+180)*DEG_TO_RAD)*amp;
      pointController.addCartPoint(xOrig+xPos, yOrig+yPos);

      yPos = cos(v*DEG_TO_RAD)*amp;
      pointController.addCartPoint(xOrig+xPos, yOrig+yPos);

      yPos = cos((v+180)*DEG_TO_RAD)*amp;
      pointController.addCartPoint(xOrig+xPos, yOrig+yPos);

      xPos += xStep;
      v += vUpdate;
    }
  }

  if ( drawTopWaves ) {
    v = 0;
    //xOrig = 0;
    yOrig = height/4.0;
    xPos = 0;
    yPos = 0;
    amp = height/8;

    for (int i=0; i<numPoints; i++) {
      yPos = sin(v*DEG_TO_RAD)*amp;
      pointController.addCartPoint(xOrig+xPos, yOrig+yPos);

      yPos = sin((v+180)*DEG_TO_RAD)*amp;
      pointController.addCartPoint(xOrig+xPos, yOrig+yPos);

      yPos = cos((v)*DEG_TO_RAD)*amp;
      pointController.addCartPoint(xOrig+xPos, yOrig+yPos);

      yPos = cos((v+180)*DEG_TO_RAD)*amp;
      pointController.addCartPoint(xOrig+xPos, yOrig+yPos);

      xPos += xStep;
      v += vUpdate;
    }
  }

  if ( drawBottomWaves ) {
    v = 0;
    //xOrig = 0;
    yOrig = height-(height/4.0);
    xPos = 0;
    yPos = 0;
    amp = height/8;

    for (int i=0; i<numPoints; i++) {
      yPos = sin(v*DEG_TO_RAD)*amp;
      pointController.addCartPoint(xOrig+xPos, yOrig+yPos);

      yPos = sin((v+180)*DEG_TO_RAD)*amp;
      pointController.addCartPoint(xOrig+xPos, yOrig+yPos);

      yPos = cos((v)*DEG_TO_RAD)*amp;
      pointController.addCartPoint(xOrig+xPos, yOrig+yPos);

      yPos = cos((v+180)*DEG_TO_RAD)*amp;
      pointController.addCartPoint(xOrig+xPos, yOrig+yPos);

      xPos += xStep;
      v += vUpdate;
    }
  }
}

//general processing methods
void draw() {
}

void mousePressed() {
}

void mouseDragged() {

}






