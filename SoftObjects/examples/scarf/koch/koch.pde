//import pdf export
import processing.pdf.*;

//import SoftObjects Library
import com.primitive2d.*;
import com.ornament.*;
import com.ornament.Pattern;
import com.ui.*;
import com.math.*;
import com.datatype.*;


//Soft Objects Template//

/*Key commands:
 * SPACEBAR + mouse = pan
 * z + mouse = zoom
 * n = reset zoom and pan
 * p = save out pdf
 */


ScreenManager sc; // instance of screen manager

 //global variables we will modify:

 String fractalShape;
 double fractalAngle;
 int fractalLevel;

int fractalNum;
double fractalGap;


void setup() {

  size(1024, 786, P3D); //defines the size of your drawing space

  sc = new ScreenManager(this); // intialize screen manager
  sc.setFilename("koch");  //sets the name of any files you save
 
 //MODIFY THESE PARAMETERS:
 fractalNum=2;//number of fractals in the scarf
 fractalGap=20;//gap between the two sides of the fractal
 
 fractalShape="triangle";//shape of the fractal (triangle or rectangle);
 fractalAngle=60;//angle of change in the fractal
 fractalLevel=5;//number of recursions (complexity of fractal)
  
 generateFractal();
 
}

void draw() {
}

