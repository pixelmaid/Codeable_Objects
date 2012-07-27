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


void generatePattern(){
 
 
 Polygon clip = getClip();
 VoronoiGenerator vG = new VoronoiGenerator();
 

 for(int i=0;i<complexity;i++){
   double x= random(0,(float)clip.getWidth());
   double y=random(0,(float)clip.getHeight());
   Point p = new Point(x,y);
   points.add(p);
 }
 
Pattern pattern = vG.getEdges(points, (int)clip.getWidth(), (int)clip.getHeight());
pattern.setStrokeWeight(5);
pattern.addToScreen();

pattern.moveTo(width/2,height/2);
clip.setOriginUpperLeft();
clip.moveTo(width/2,height/2);

pattern.clipTo(clip);
pattern.centerOrigin();
clip.centerOrigin();
clip.moveTo(width/2,height/2);
pattern.moveTo(width/2,height/2);

pattern.addCopy(clip);
sc.setZoom(-300);

 
 
}



