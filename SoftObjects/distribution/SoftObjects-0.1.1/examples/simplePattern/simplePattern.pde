//import pdf export
import processing.pdf.*;

//import SoftObjects Library
import com.primitive2d.*;
import com.ornament.*;
import com.ornament.Pattern;
import com.ui.*;
import com.math.*;


//Soft Objects Template//

/*Key commands:
* SPACEBAR + mouse = pan
* z + mouse = zoom
* n = reset zoom and pan
* p = save out pdf
*/

ScreenManager sc; // instance of screen manager

void setup(){
 
 size(1024,786,P3D); //defines the size of your drawing space
  
 sc = new ScreenManager(this); // intialize screen manager
 sc.setFilename("drawing");  //sets the name of any files you save

 
 int resolution=30;
 //Curves 
 Curve curve = new Curve(0,400,width/2,400,width/4,300,resolution); //creates a starting curve
 curve.addCurve(width,400,(width/4)*3,500); //does not add the curve to screen- we will use it as a reference to place other objects below
 
 int totalLines = curve.numberOfLines(); // gets the total number of individual lines in the curve
 
 for(int i=0;i<totalLines;i++){ //iterates through each line
  Curve newCurve = new Curve(0, 0,60, 0,30, 10,10);  //creates a new curve
  newCurve.addCurve(0, 0,30, -10); //adds a second curve
  newCurve.addToScreen(); //adds the curve to the screen 
  newCurve.scaleX(1.25);//scales the curve by 1.25 along the x axis   
  newCurve.moveTo(curve.getLineAt(i).start); //moves the curve to the start of each line of the master curve
  newCurve.setStrokeWeight(4); //sets the stroke weight to 4;
  newCurve.rotate(360/totalLines*i); //rotates the curve
 }

}

void draw(){
  
}

void keyPressed(){
  println("key pressed");
  
}
