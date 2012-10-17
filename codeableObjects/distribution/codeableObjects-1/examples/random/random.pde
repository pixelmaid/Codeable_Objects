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

  
  // =========GENERATE RANDOM POINTS=============== //
   
   
    int pointNum =100; //total points
     
   for (int i=0;i<pointNum;i++){
      float x = random(0,width);
      float y= random(0,height);
       pointController.addCartPoint(x,y);
  }    
}

//general processing methods
void draw() {
}

void mousePressed() {
}

void mouseDragged() {

}






