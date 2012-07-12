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
 sc.setFilename("foo");  //sets the name of any files you save
 
 //draw your shapes here!
 
 
}

void draw(){
  
}
