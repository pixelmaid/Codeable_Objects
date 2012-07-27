//import pdf export
import processing.pdf.*;

//import SoftObjects Library
import com.primitive2d.*;
import com.ornament.*;
import com.ornament.Pattern;
import com.ui.*;
import com.math.*;
import com.datatype.*;

ScreenManager sc; // instance of screen manager

void setup() {

  size(1024, 700, P3D); //defines the size of your drawing space

  sc = new ScreenManager(this); // intialize screen manager

  //Patterns
  int layers = 10;
  double moveDist=0;
  double shift = 360/layers;
  for (int i=0;i<layers; i++) {
    Pattern pattern = makeRorsch(10*(i+1), 5);
    int num = (i+1)*2;
      
    for (int j=0;j<num;j++) {
    
      
      Pattern mainPattern = pattern.copy();
      mainPattern.centerOrigin();
      mainPattern.moveTo(width/2,height/2);
      mainPattern.addToScreen();
     if(i!=0){
      double angle =360/(num)*j;
      mainPattern.rotate(angle+shift*i);
      Line line = new Line(new Point(width/2,height/2),moveDist,angle+shift*i);
      mainPattern.moveTo(line.end);
      
     }
    }
    moveDist+=pattern.getHeight();
  }
}
  void draw() {
  }

