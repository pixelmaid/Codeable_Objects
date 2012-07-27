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
Vector<Pattern> hexagonRows = new Vector();
void setup(){
 
 size(1024,786,P3D); //defines the size of your drawing space
  
 sc = new ScreenManager(this); // intialize screen manager
 sc.setFilename("foo");  //sets the name of any files you save
 
 //draw your shapes here!
  Pattern dressPattern = importDress();
 
 
  double increase = 1.5/((double)dressPattern.getAllLines().size());
  double scaleVal= 0.5;
  
  
  Vector<Line> lines = dressPattern.getAllLines();
  Collections.sort(lines);
  for(int i=0;i<lines.size();i++){
    if(i<=10){
        scaleVal +=increase;
    }
    if(i>10 && i<=20){
    
      scaleVal -=(increase*1.5);
    }
    else{
       scaleVal +=increase;
    }
     hexRow(lines.get(i),scaleVal,i);
 
  }
  
  for(int i=0;i<hexagonRows.size();i++){
    hexagonRows.get(i).addToScreen();
    
  }
    
 
}

void draw(){
  
}

void hexRow(Line targetLine, double scaleVal, int num){
  
  Pattern hexagonRow = new Pattern();
  
  Point startPoint=null;
  Point endPoint=null;
  if(targetLine.end.getX()<targetLine.start.getX()){
    startPoint = targetLine.end;
     endPoint = targetLine.start;
     
  }
  else{
     startPoint = targetLine.start;
     endPoint = targetLine.end;
  }
  double dist = 0;
  int hexNum= ceil((float)((endPoint.getX()-startPoint.getX())/makeHex(scaleVal).getWidth()));
  
  
 
  for(int i=0;i<hexNum;i++){
  Polygon hex = makeHex(scaleVal);

    hex.moveTo(startPoint.getX()+dist,startPoint.getY());
    hexagonRow.addCopy(hex);
   
    dist+=hex.getWidth();
  }
   
   Rectangle clippingRectangle = new Rectangle(0,0,targetLine.getLength(),hexagonRow.getHeight());
   hexagonRow.scaleX(targetLine.getLength()/hexagonRow.getWidth());
   hexagonRow.setOriginUpperLeft();
   hexagonRow.moveTo(0,0);
   
   clippingRectangle.moveTo(0,0);
   
   
   //hexagonRow.clipTo(clippingRectangle);
   hexagonRow.addToScreen();
   sc.setFilename("scaleOutline"+num+"_");
   sc.print();
    hexagonRow.removeFromScreen();
    
   
  
  
   clippingRectangle.addToScreen(); 
  
   sc.setFilename("woodOutline"+num+"_");
   sc.print();
   clippingRectangle.scaleY(1.5);
    clippingRectangle.moveTo(0,0);
   sc.setFilename("fabricOutline"+num+"_");
   sc.print();
    clippingRectangle.removeFromScreen();
    
    
   hexagonRow.moveTo(startPoint.getX()-(hexagonRow.getWidth()-targetLine.getLength())/2,startPoint.getY());  
   hexagonRows.add(hexagonRow);
  
}


Polygon makeHex(double scaleSize){
  
    Polygon hex = new Polygon(6,100,0,0);
  hex.rotate(90);
  hex.scaleX(0.5);
  hex.scaleX(scaleSize);
  hex.scaleY(scaleSize);
  hex.centerOrigin();
  hex.removeLineAt(5);
  hex.removeLineAt(4);
  hex.getLineAt(0).start.setY(hex.getLineAt(0).start.getY()+hex.getLineAt(0).getLength()/2);
   hex.getLineAt(3).end.setY(hex.getLineAt(3).end.getY()+hex.getLineAt(3).getLength()/2);
hex.setOriginUpperLeft();


return hex;
}
