//import pdf export
import processing.pdf.*;

//import SoftObjects Library
import com.primitive2d.*;
import com.ornament.*;
import com.ornament.Pattern;
import com.ui.*;
import com.math.*;


//Soft Objects Primitives demonstration//

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
 
 //Polygons
 Polygon pentagon = new Polygon(5,100,100,400); //creates a new polygon primitive with 5 sides, each 20 pixels long at a starting position of 10,10
 pentagon.addToScreen(); //adds the polygon to the screen
 pentagon.setStrokeWeight(5); //sets the stroke weight of the polygon to 5 pixels
 
 //Curves 
 Curve curve = new Curve(200,200,400,200,300,100); //creates a curve at a starting point of 200,200, an ending point of 400,200, a control point at 300,100
 curve.addToScreen(); 
 curve.addCurve(600,200,500,300);//adds on an additional curve;
 curve.drawPoints();//shows the points on the curve

 
 //Patterns
 Pattern turtlePattern = new Pattern(); //initializing a pattern primitive
 turtlePattern.addToScreen();

 turtlePattern.forward(100); //drawing with a turtle in a pattern (you can also draw with the turtle in a polygon)
 turtlePattern.right(90);
 turtlePattern.forward(100);
 turtlePattern.right(90);
 turtlePattern.forward(100);
 turtlePattern.right(90);
 turtlePattern.forward(100);
 turtlePattern.right(90);
 
 turtlePattern.centerOrigin(); // moves the origin to the center of the primitive
 turtlePattern.moveTo(width/2,height/2); //moves the pattern to the center of the screen
 
 turtlePattern.setStrokeColor(255,0,0); //sets the color of the pattern to red
}

void draw(){
  
}
