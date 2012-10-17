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

  
  // =========GENERATE POLAR ROSE=============== //
  /*a rose or rhodonea curve is a sinusoid plotted in polar coordinates. 
  *Up to similarity, these curves can all be expressed by a polar equation of the form r= cos(k*theta)
  */
  
  float radius = 200;
  int centerLimit = 150; // variable to control the maximum diameter of the rose
  float theta = 20; //can modify but doesn't have much effect on the design

  float k=6; //modify to change the number petals of the rose

  //this will draw one polar rose
  for (int i = 0; i < centerLimit; i++)
   {
     float interval = TWO_PI/float(centerLimit);
     theta += interval;
     float rad =  radius*cos(k*theta);

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




