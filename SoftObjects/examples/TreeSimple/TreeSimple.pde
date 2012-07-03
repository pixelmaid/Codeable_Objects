import com.primitive2d.*;
import com.datatype.*;
import com.ui.*;
import com.pattern.*;
import com.math.*;

Tree tree = new Tree();

void setup(){
 size(1024,700);

 tree.setLimit(1);
 tree.setGrowthRate(0.8);
 tree.setStartAngle(270);
 tree.setAngleChange(20);
 tree.setStartingHeight(50);
 tree.setOrigin(width/2,height);

 tree.generate();
 background(0);
  stroke(255,0,0);
  tree.draw(this,1);
}


