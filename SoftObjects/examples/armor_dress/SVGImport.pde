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
String filepath= "dress_lines.svg"; //filepath of target svg file
Pattern pattern; //list to hold the polygons generated from the svg

Pattern importDress() {

 

    SVGReader svgReader = new SVGReader(); //initialize the svg reader

    if ( svgReader.readSVGFile( filepath ) ) { //load in the file
   pattern = svgReader.getPattern();
    polygonsLoaded = true;
    println("Got a pattern from the SVG file");
    pattern.centerOrigin();
    pattern.moveTo(width/2,height/2);
    pattern.setStrokeColor(0,0,0);
     // pattern.addToScreen(); //add the first polygon found to the screen

  }
  else {
    println("ERROR: Failed to parse the SVG file");
  }
  return pattern;

}


