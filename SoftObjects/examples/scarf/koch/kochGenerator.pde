
void generateFractal() {

  Polygon clip =getClip();
  
  double fractalWidth=clip.getWidth()/fractalNum;
  double pos=0;
  Pattern masterPattern = new Pattern();

  for(int i=0;i<fractalNum;i++){
     Fractal fractal1 = new Fractal(pos,0-fractalGap,fractalWidth+pos,0-fractalGap);
     
    fractal1.setAngle(fractalAngle);

    fractal1.setLevelLimit(fractalLevel);
		
    fractal1.setSeedShape(fractalShape);
     
     fractal1.generate();
     pos+=fractalWidth;
     masterPattern.addCopy(fractal1);
   }
 
  Pattern master2 = masterPattern.copy();
  master2.rotate(180);
  master2.moveBy(clip.getWidth(),0);
  masterPattern.addCopy(master2);
  masterPattern.addToScreen();
  masterPattern.centerOrigin();
  clip.centerOrigin();
  clip.moveTo(width/2,height/2);
  masterPattern.moveTo(width/2,height/2);
  masterPattern.clipTo(clip);
 masterPattern.addCopy(clip);
 masterPattern.setStrokeWeight(4);
  sc.setZoom(-300);
}




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

