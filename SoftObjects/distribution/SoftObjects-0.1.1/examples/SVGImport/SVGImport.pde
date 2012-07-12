import com.primitive2d.*;
import com.ornament.*;
import com.datatype.*;
import com.primitive2d.connector.*;
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

boolean polygonsLoaded = false; //bpolean to verify that the svg was loaded
String filepath= "test3.svg"; //filepath of target svg file
Vector<Polygon>polygons; //list to hold the polygons generated from the svg

void setup() {

  size(1024, 786, P3D); //defines the size of your drawing space

  sc = new ScreenManager(this); // intialize screen manager
  sc.setFilename("drawing");  //sets the name of any files you save

    SVGReader svgReader = new SVGReader(); //initialize the svg reader

    if ( svgReader.readSVGFile( filepath ) ) { //load in the file
    polygons = svgReader.getPolygons();
    polygonsLoaded = true;
    println("Got " + polygons.size() + " polygons from the SVG file");
  }
  else {
    println("ERROR: Failed to parse the SVG file");
  }

  polygons.get(0).addToScreen(); //add the first polygon found to the screen
}

void draw() {
}


