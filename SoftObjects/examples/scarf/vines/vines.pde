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

void setup() {

  size(1024, 786, P3D); //defines the size of your drawing space

  sc = new ScreenManager(this); // intialize screen manager
  sc.setFilename("vines");  //sets the name of any files you save


  //MODIFY THESE PARAMETERS:
  rowWidth=105;//sets the width between the rows of vines

  stemWidth = 200;//sets the width of the vine sections
  stemHeight=20;//sets the height of the vine sections

  leafSize=0.5;//sets the size of the leaf (percentage)
  leftLeafRotation=30;//sets the rotation of the left leaf
  rightLeafRotation=30;//sets the rotation of the right leaf

  flowerSize=1;//sets the size of the flower (percentage)
  flowerRotation=60;//sets the rotation of the flower
  petalNum=6;//sets the number of petals on the flower

  generateVines();
}

void draw() {
}

