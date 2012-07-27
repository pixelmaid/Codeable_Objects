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

void setup(){
 
 size(1024,786,P3D); //defines the size of your drawing space
  
 sc = new ScreenManager(this); // intialize screen manager
 sc.setFilename("foo");  //sets the name of any files you save
 
 //draw your shapes here!
 
  Polygon pants = importPants();
 
 
 

 
 Pattern stripePattern1 = stripes(10,pants.getHeight(),6,20);
 stripePattern1.rotate(90);
 stripePattern1.centerOrigin();
 stripePattern1.moveTo(width/2,height/2);
 
stripePattern1.addToScreen();
Pattern starPattern1 = starColumn(20,30,pants.getHeight());
 starPattern1.centerOrigin();
starPattern1.moveTo(width/2,height/2);
 starPattern1.moveBy(0-stripePattern1.getWidth()/2,0);
 
 
 
 
Pattern leftStripes = sideStripes(pants.getHeight());
Pattern rightStripes = sideStripes(pants.getHeight());

leftStripes.scaleX(0.2);
 leftStripes.setOriginUpperRight();
 leftStripes.moveTo(stripePattern1.getExtremeTopPoint().getX()-stripePattern1.getWidth(),stripePattern1.getExtremeTopPoint().getY());
leftStripes.addToScreen();

rightStripes.scaleX(0.2);
 rightStripes.setOriginUpperLeft();
 rightStripes.moveTo(stripePattern1.getExtremeTopPoint().getX(),stripePattern1.getExtremeTopPoint().getY());
rightStripes.addToScreen();

starPattern1.addToScreen();


}

void draw(){
  
}


Pattern starColumn(int num, double rad, double height){
 Pattern starPattern = new Pattern();
 
 
 for(int i=0;i<num;i++){
    Polygon star = star(rad);
    star.moveTo(0,(height/num)*i);
    starPattern.addPolygon(star);
 }
 
 return starPattern;
  
  
}

Polygon star(double rad){
  Point originPoint = new Point(0,0);
  
  Polygon star = new Polygon();
  for(int i=0;i<10;i++){
    if(Geom.isEven(i)){
       Point point = new Line(originPoint,rad,i*(360/10)).end;
       star.addPoint(point.getX(),point.getY());
       println(i);
    }
    else{
     Point point = new Line(originPoint,rad/2,i*(360/10)).end;
      star.addPoint(point.getX(),point.getY());
       println(i);
    }
  }
    star.closePoly();
    star.centerOrigin();
    star.rotate(-18);
     star.centerOrigin();
     star.setFillColor(255,255,255);
     star.setStrokeWeight(0);
     star.fill=true;
    println(star.getAllLines().size());
    return star;
  
}

Pattern sideStripes(double targetHeight){
  Pattern leftStripes = new Pattern();
  double prevHeight =0;
double patternGap = 100;
 
while (prevHeight < targetHeight){
  int stripeNum = round(random(1,10));
  double stripeHeight = 100/((double)stripeNum);  
  double gap = stripeHeight*2;
  Pattern stripePattern = stripes(stripeNum,targetHeight, stripeHeight,gap);
  stripePattern.moveBy(0,prevHeight);
  prevHeight += stripePattern.getHeight()+patternGap;
   for(int i=0;i<stripePattern.getAllPolygons().size();i++){
     leftStripes.addPolygon(stripePattern.getPolygonAt(i));
   }
   
  leftStripes.setStrokeWeight(10);
}
return leftStripes;

}

Pattern stripes(int num, double width, double height, double gap){
 Pattern stripePattern = new Pattern();
 
 
 for(int i=0;i<num;i++){
    Polygon stripe = stripe(width,height);
    stripe.moveTo(0,(gap)*i);
    stripePattern.addPolygon(stripe);
 }
 
 return stripePattern;
  
  
}

Polygon stripe(double width, double height){
 
  
  Rectangle stripe = new Rectangle(0,0,width,height);
  
     stripe.setFillColor(255,0,0);
     stripe.setStrokeWeight(1);
     stripe.setStrokeColor(0,0,255);
     stripe.fill=true;
   
    return stripe;
  
}
