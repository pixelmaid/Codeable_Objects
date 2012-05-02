package com.primitives2d;

import processing.core.PApplet;

import java.util.Vector;

/**
 * User: jenniferjacobs
 * Date: 4/29/12
 * Time: 12:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class Grid {
    public int size;
    public double scale;
    public Vector<GridSquare> squares;
    public Vector<Disc> discs;


    public Grid(PApplet myParent, int size, double scale,Vector<Disc>discs){
        this.size = size;
        this.scale = scale;
        this.discs = discs;
        squares = new Vector<GridSquare>();


        for(int i = 0;i<size;i++){
            for(int j = 0;j<size;j++){
                GridSquare square = new GridSquare(i*scale,j*scale,scale);
                squares.addElement(square);

            }
        }
        for(int i = 0;i<this.discs.size();i++){
            checkDisc(this.discs.get(i),myParent);
        }



    }

    public void clear(){

        for(int i = 0;i<squares.size();i++){
            GridSquare square = squares.get(i);
            square.clear();

        }
        discs = new Vector<Disc>();


    }

    public void addDisc(PApplet myParent, double discX, double discY,double radius){
        Disc disc = new Disc(discX,discY,radius);
        discs.addElement(disc);
        checkDisc(disc,myParent);



    }

    private void checkDisc(Disc disc, PApplet myParent){

        for(int i = 0;i<squares.size();i++){
            GridSquare square = squares.get(i);
            if(disc.wholeInGrid(square)){
                disc.insideGrid = true;
                square.addDisc(disc);
                square.optimizedRemoval(myParent);
                break;
            }

        }
    }

    public void draw(PApplet myParent){
        for(int i = 0;i<squares.size();i++){
            squares.get(i).draw(myParent);
        }

        for(int i = 0;i<discs.size();i++){
            discs.get(i).draw(myParent);
        }
    }

}


