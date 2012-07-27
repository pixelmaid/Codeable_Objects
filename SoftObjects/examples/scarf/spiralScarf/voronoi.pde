boolean polygonsLoaded = false; //boolean to verify that the svg was loaded
String filepath= "scarf_template.svg"; //filepath of target svg file
Vector<Polygon>polygons; //list to hold the polygons generated from the svg
Vector<Point> points = new Vector();

Polygon getClip() {
  
    SVGReader svgReader = new SVGReader(); //initialize the svg reader

    if ( svgReader.readSVGFile( filepath ) ) { //load in the file
    polygons = svgReader.getPolygons();
    polygonsLoaded = true;
    println("Got " + polygons.size() + " polygons from the SVG file");
  }
  else {
    println("ERROR: Failed to parse the SVG file");
  }

  return polygons.get(0); //add the first polygon found to the screen
}

void addSpiral(double x, double y, double startRad, double endRad, double increment ){
 
 double centerLimit = endRad; // variable to control the maximum diameter of the spiral
  double rad = startRad; //like the radius of your circle, but increases with every point in your spiral, producing the spiral effect.
  //this will draw one spiral 
  for(int i=0;i<centerLimit;i++){     
        rad +=increment; //change to alter the tightness of your spiral
       Point point = new Point(x,y, rad, rad);// adds the point to your design
        points.add(point);
      }
  
}


void generatePattern(){
 

 Polygon clip = getClip();
 VoronoiGenerator vG = new VoronoiGenerator();
 
 
Pattern pattern = vG.getEdges(points, (int)clip.getWidth(), (int)clip.getHeight());
pattern.setStrokeWeight(5);
pattern.addToScreen();


clip.setOriginUpperLeft();
clip.moveTo(pattern.getOrigin());

pattern.clipTo(clip);
pattern.centerOrigin();
clip.centerOrigin();
pattern.addCopy(clip);
pattern.centerOrigin();  
pattern.moveTo(width/2,height/2);



sc.setZoom(-300);

 
 
}



