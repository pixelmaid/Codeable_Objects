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
 double spiralStart;
 double spiralEnd;
 double increment;
 double startX; 
 double startY; 

void setup(){
 
 size(1024,786,P3D); //defines the size of your drawing space
  
 sc = new ScreenManager(this); // intialize screen manager
 sc.setFilename("voronoi");  //sets the name of any files you save
 
 
 //MODIFY THESE PARAMETERS:
 spiralStart = 0; 
 spiralEnd =500;
 increment = 2;
 startX=1150;
 startY=200;
 
 addSpiral(startX,startY, spiralStart, spiralEnd, increment); //creates the spiral
 
 generatePattern();
 
}
 
 
 void draw(){
  
}
