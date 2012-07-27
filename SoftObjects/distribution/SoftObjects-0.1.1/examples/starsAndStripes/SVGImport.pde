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


boolean polygonsLoaded = false; //bpolean to verify that the svg was loaded
String filepath= "pants.svg"; //filepath of target svg file

Polygon importPants() {

 

    SVGReader svgReader = new SVGReader(); //initialize the svg reader
 Polygon poly = null;
    if ( svgReader.readSVGFile( filepath ) ) { //load in the file
  poly = svgReader.getPolygons().get(0);
    polygonsLoaded = true;
    println("Got a polygon from the SVG file");
    poly.centerOrigin();
    poly.moveTo(width/2,height/2);
    poly.setStrokeColor(200,200,200);
    poly.addToScreen();
 // pants.fill=true;
  //pants.setFillColor(200,200,200);

  }
  else {
    println("ERROR: Failed to parse the SVG file");
  }
  return poly;

}


