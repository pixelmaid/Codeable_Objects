import com.primitive2d.*;
import com.datatype.*;
import com.ui.*;
import com.math.*;

double rotation=0;
Line line;
Polygon poly;

void setup(){
 size(1024,700);
 stroke(255,0,0);
 poly = new Polygon();
 
  /*line = new Line(0,0,100,100); 
 line.draw(this,5);
 line.translate(200,200);
 line.rotate(90,line.start);
 line.draw(this,3);*/
}

void draw(){
  background(0);
   
  poly.draw(this,5);
  println(frameRate);
  
  /*rotation++;
  println(rotation);
  line.moveTo(rotation,100);
  line.rotate(-1,line.end);
  line.draw(this,3);*/
}

void mousePressed(){
 poly.addPoint(mouseX,mouseY);
  
}


