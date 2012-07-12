import processing.pdf.*;


import com.primitive2d.*;
import com.ornament.Pattern;
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


    SVGReader svgReader = new SVGReader();

  if ( svgReader.readSVGFile( "test4.svg" ) ) { //import in an svg that we will use later as a clipping shape
    polygons = svgReader.getPolygons();
    polygonsLoaded = true;
    println("Got " + polygons.size() + " polygons from the SVG file");
  }
  else {
    println("ERROR: Failed to parse the SVG file");
  }

  Polygon clippingSVG = polygons.get(0); //get the polygon from the file import
  clippingSVG.addToScreen();

  Pattern pattern = new Pattern(); //initialize a new pattern that we will fill with shapes
  pattern.addToScreen();


  double rowNum = 15; //number of rows
  double columnNum = 30; //number of columns

  for (int i=0;i<rowNum;i++) { //loop through rows

    double pos=0; //set a starting position of 0

      for (int j=0;j<columnNum;j++) { //loop through columns

      Polygon poly = new Polygon(8, 30, 0, 0); //create a new polygon
      poly.scaleX(this.width/columnNum/poly.getWidth());//scale the polygon

      poly.moveTo(pos, i*poly.getHeight()+30); //move it to the current position set by pos and the correct row height

      pos+= poly.getWidth(); //increment pos by the width of the polygon (this will result in the correct placement of subsequent polygons)

      pattern.addCopy(poly);// copy the polygon into the pattern
    }
  }

  clippingSVG.clipPattern(pattern); //clip pattern to imported svg
}

void draw() {

}

