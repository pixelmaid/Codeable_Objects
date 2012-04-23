package com.design;

import processing.core.PApplet;
import processing.core.PFont;

/**
 * Created with IntelliJ IDEA.
 * User: jenniferjacobs
 * Date: 4/22/12
 * Time: 12:10 AM
 * To change this template use File | Settings | File Templates.
 */
public class Button {

    private float x;
    private float y;
    private float width;
    private float height;
    private boolean on;
    private String name;
    private PApplet myParent;
    private PFont font;
    private boolean toggle=false;

    public Button(PApplet myParent){
        x = 0;
        y = 0;
        width = 0;
        height = 0;
        on = false;
        this.myParent = myParent;
        font = myParent.loadFont("din_bold.vlw");
        myParent.textFont(font,14);
    }

    public void init(float x,float y,float width,float height, boolean on, boolean toggle,String name){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.on = on;
        this.toggle = toggle;
        this.name = name;
    }

    public void draw(){
        myParent.noFill();
        myParent.strokeWeight(2);
        myParent.stroke(255, 255, 255);
        myParent.rect(x, y, width, height);
       if( on ){


            myParent.fill(255,255,0);
           }

       else  myParent.fill(255, 0, 0);
       myParent.noStroke();
       myParent.rect(x + 1, y + 1, width-2, height - 2);

       myParent.fill(255);

       myParent.text(name,x,y+height+15);

    }

    public boolean checkForMousePress(float mouseX,float mouseY){
        if( mouseX >= x && mouseX < x+width && mouseY > y && mouseY < y+height){
            if(toggle){
                if(on){
                    on=false;
                }
                else{
                    on=true;
                }
            }
            else{
                on = true;
           }
        }
        return on;
    }


    public void setValue(boolean value){
        on = value;
    }

    public boolean getValue(){
        return  on;
    }


}
