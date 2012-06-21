
/*
 * Codeable Objects by Jennifer Jacobs is licensed under a Creative Commons Attribution-ShareAlike 3.0 Unported License.
 * Based on a work at hero-worship.com/portfolio/codeable-objects.
 *
 * This file is part of the Codeable Objects Framework.
 *
 *     Codeable Objects is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Codeable Objects is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Codeable Objects.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.primitives2d;

import com.algorithm.KdCluster;
import com.datastruct.DCHalfEdge;
import com.math.CompPoint;
import com.datastruct.DoublyConnectedEdgeList;
import com.math.Geom;
import processing.core.PApplet;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;
import java.util.List;

public class GridSquare extends DoublyConnectedEdgeList {
    public CompPoint origin;
    public double size;
    public Vector<Disc> discs;
    public Vector<Disc> rejectedDiscs;
    public Vector<Disc> keptDiscs;
    public Vector<Disc> evalDiscs;





    public GridSquare(double x, double y, double size) {

        this.size =size;
        this.origin=new CompPoint(x,y);

        discs = new Vector<Disc>();



        DCHalfEdge top = new DCHalfEdge(new CompPoint(x+size,y),origin);
        DCHalfEdge left = new DCHalfEdge(top.end,new CompPoint(x,y+size));
        DCHalfEdge bottom = new DCHalfEdge(left.end,new CompPoint(x+size,y+size));
        DCHalfEdge right = new DCHalfEdge(bottom.end,top.start);




        addHalfEdge(top);
        addHalfEdge(left);
        addHalfEdge(bottom);
        addHalfEdge(right);

    }

    public void clear(){
        discs = new Vector<Disc>();

    }

    //adds a disc to the grid square
    public void addDisc(Disc disc){

        discs.addElement(disc);



    }


    //finds the optimal set of discs to remove
    public void optimizedRemoval(PApplet myParent){
        Vector<Disc> evalDiscs =  findAllNonTouchingDiscs(discs)[0];
        if(evalDiscs.size()>0)  {

            Vector<Vector<Disc>> cleanedThreads = new Vector<Vector<Disc>>();
            Collections.sort(evalDiscs,new discCmpY());
            Vector<Vector<Disc>> discThreads = new KdCluster<Disc>().buildTree(evalDiscs);

            for(int i=0;i<discThreads.size();i++){

                Vector<Disc> thread = discThreads.get(i);

                //myParent.stroke(0,255,0);
                //myParent.strokeWeight(1);
                System.out.println("\n touching order for "+i+"=");
                System.out.println("size of "+i+"="+thread.size());
                optimizeThread(thread);
                /*for(int j=0;j<thread.size();j++){
                    System.out.println(thread.get(j).numTouching);
                }*/
                // myParent.line((float)thread.get(0).origin.getX(), (float)thread.get(0).origin.getY(),(float)thread.get(thread.size()-1).origin.getX(), (float)thread.get(thread.size()-1).origin.getY());

            }
        }
    }

    private void optimizeThread(Vector<Disc> thread){
        Vector<Disc> newThread = findAllNonTouchingDiscs(thread)[0];

        if(newThread.size()==0){
            System.out.println("no more are touching in the thread!");
            System.out.println("size="+newThread.size());
            for(int i = 0;i<newThread.size();i++){
                newThread.get(i).kept=true;

            }
            return;
        }
        else{
            Collections.sort(newThread,new DiscTouch());
            if(newThread.get(0).numTouching==1){

                Disc maxOffender=newThread.get(0).discsTouching.get(0);

                newThread.get(0).removeTouching(maxOffender);

                newThread.remove(maxOffender);
                optimizeThread(newThread);
            }
            else{

                Vector<Disc> maxOffenders = new Vector<Disc>();

                int N = newThread.size()-1;

                while(N>0){
                    System.out.println("thread size="+newThread.size());
                    System.out.println("N ="+N);
                    maxOffenders.add(newThread.get(N));

                    if(newThread.get(N-1).numTouching>=newThread.get(N).numTouching){
                        N--;
                    }
                    else{
                        break;
                    }

                }
                Disc maxOffender;

                if(maxOffenders.size()==1){
                    maxOffender = maxOffenders.get(0);
                }
                else{
                    Disc chosenDisc = null;
                    //findMaxOffender(maxOffenders,0,chosenDisc);
                    System.out.println("maxOffender size="+maxOffenders.size());
                    maxOffender = findMaxOffender(maxOffenders);
                }



                for(int i = 0;i<maxOffenders.get(0).discsTouching.size();i++){
                    maxOffenders.get(0).discsTouching.get(i).removeTouching(maxOffenders.get(0));
                }
                newThread.remove(maxOffender);
                optimizeThread(newThread);
            }
        }
    }

    private Disc findMaxOffender(Vector<Disc>maxOffenders){
        int[]counts = new int[maxOffenders.size()];
        for(int i=0;i<maxOffenders.size();i++){
            int count = 0;
            for(int j=0;j<maxOffenders.get(i).discsTouching.size();j++){
                count+=maxOffenders.get(i).discsTouching.get(j).numTouching;
            }
            System.out.print("count="+count);
            counts[i]=count;

        }
        int smallest = -1;
        int numSmallest = 0;
        for(int i=0;i<maxOffenders.size();i++){
            if(smallest>counts[i]){
                smallest=counts[i];
                numSmallest=i;
            }
        }
        System.out.print("numBiggest="+numSmallest+","+smallest);
        return maxOffenders.get(numSmallest);

    }

    /*private void findMaxOffender(Vector<Disc>maxOffenders,int level,Disc chosenDisc){
          Collections.sort(maxOffenders,new LevelTouch(level));
        Vector<Disc> offenderSelects = new Vector<Disc>();

        int N = 0;
        while(N<maxOffenders.size()-1){
            Disc currentOffender = maxOffenders.get(N);
            offenderSelects.add(maxOffenders.get(N));

            if(maxOffenders.get(N+1).discsTouching.get(level).numTouching<=currentOffender.discsTouching.get(level).numTouching){
                N++;
            }
            else{
                break;
            }

        }
        if(offenderSelects.size()==1||level+1==offenderSelects.get(0).discsTouching.size()){
           chosenDisc = offenderSelects.get(0);
        }
        else{
            findMaxOffender(offenderSelects,level+1,chosenDisc);

        }

    }*/


    public Vector<Vector<Disc>> permutations(Vector<Disc> discs, Vector<Vector<Disc>> discThreads) {
        permutation("", "ab");
        permutation(new Vector<Disc>(), discs, discThreads);
        return discThreads;
    }

    private void permutation(Vector<Disc> prefix, Vector<Disc> str, Vector<Vector<Disc>>discThreads) {
        int n = str.size();
        if (n == 0){
            System.out.println("final="+prefix+"\n");
            discThreads.addElement(prefix);
        }
        else {
            for (int i = 0; i < n; i++){
                System.out.println("org prefix="+prefix);
                Vector<Disc> newStr = new Vector<Disc>();
                Vector<Disc> newPrefix= new Vector<Disc>();
                for(int j=0;j<prefix.size();j++){
                    newPrefix.add(prefix.get(j));
                }
                newPrefix.addElement(str.get(i));
                System.out.println("stringAt i="+str.get(i));
                List<Disc> strList = str.subList(0, i);
                List<Disc> strList2 = str.subList(i+1, n);


                for(int j=0;j<strList.size();j++){
                    newStr.addElement(strList.get(j));
                }
                for(int j=0;j<strList2.size();j++){
                    newStr.addElement(strList2.get(j));
                }


                System.out.println("new prefix="+newPrefix);
                System.out.println("newStr="+newStr);
                permutation(newPrefix, newStr,discThreads);
            }
        }

    }


    private  void permutation(String prefix, String str) {
        int n = str.length();
        if (n == 0) System.out.println("final="+prefix+"\n");
        else {
            for (int i = 0; i < n; i++){
                System.out.println("org prefix="+prefix);
                System.out.println("stringAt i="+str.charAt(i));
                String newPrefix = prefix + str.charAt(i);
                System.out.println("new prefix="+newPrefix);
                String newStr =str.substring(0, i) + str.substring(i+1, n);
                System.out.println("newStr="+newStr);
                permutation(newPrefix,newStr);
            }
        }

    }

    /*public static void permute(Vector<Disc> beginningVec, Vector<Disc> endingVec,Vector<Vector<Disc>> discThreads) {
        if (endingVec.size() <= 1) {
            for(int i=0;i<endingVec.size();i++){
                beginningVec.addElement(endingVec.get(i));
            }
          System.out.println(beginningVec);
          discThreads.addElement(beginningVec);
        }
        else{
            for (int i = 0; i < endingVec.size(); i++) {
                try {
                    List<Disc> newStart = endingVec.subList(0,i);
                    List<Disc> newEnd;
                    if((i+1)>endingVec.size()-1){
                      newEnd = endingVec.subList(endingVec.size()-1,endingVec.size()-1);
                    }
                    else{
                        newEnd = endingVec.subList(i + 1,endingVec.size()-1);
                    }

                     Vector<Disc> newVec = new Vector<Disc>();

                    for(int j=0;j<newStart.size();j++){
                        newVec.addElement(newStart.get(j));
                    }
                    for(int j=0;j<newEnd.size();j++){
                        newVec.addElement(newEnd.get(j));
                    }


                    beginningVec.addElement(endingVec.get(i));
                    permute(beginningVec, newVec,discThreads);
                } catch (ArrayIndexOutOfBoundsException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }*/



    //same as function below, but explicitly divides all non touching discs and touching discs to two vectors.
    public Vector<Disc>[] findAllNonTouchingDiscs(Vector<Disc> discs){
        keptDiscs = new Vector<Disc>();
        evalDiscs = new Vector<Disc>();

        for(int i=0;i<discs.size();i++){
            discs.get(i).resetTouching();
            if( checkDiscTouch(discs.get(i),discs)){
                keptDiscs.add(discs.get(i));
                discs.get(i).kept = true;
            }
            else{
                evalDiscs.add(discs.get(i));
                discs.get(i).kept = false;
            }
        }
        Vector<Disc>[] ratio = new Vector[2];
        ratio[0]=evalDiscs;
        ratio[1]=keptDiscs;
        return ratio;
    }




    //tests a vector of discs to see if any are touching, returns number touching and number not touching
    public int[] testAllNonTouchingDiscs(Vector<Disc>checkedDiscs){
        Vector<Disc> testKeptDiscs = new Vector<Disc>();
        Vector<Disc> testEvalDiscs = new Vector<Disc>();

        for(int i=0;i<checkedDiscs.size();i++){
            if( checkDiscTouch(checkedDiscs.get(i),checkedDiscs)){
                testKeptDiscs.add(checkedDiscs.get(i));

            }
            else{
                testEvalDiscs.add(checkedDiscs.get(i));

            }
        }
        int[] ratio = new int[2];
        ratio[0]=testEvalDiscs.size();
        ratio[1]=testKeptDiscs.size();
        return ratio;
    }

    //given a vector of discs, remove discs until there are none touching and then return the vector of all discs kept
    public Vector<Disc> removeTillClean(Vector<Disc>checkedDiscs){
        int numTouching = 1;
        int numKept=0;
        Vector<Disc>totalRemoved = new Vector<Disc>();
        Vector<Disc>totalKept = new Vector<Disc>();


        while (numTouching>0){
            boolean discTouches = checkDiscTouch(checkedDiscs.get(1),checkedDiscs);
            if(discTouches){
                totalKept.addElement(checkedDiscs.get(1));
            }
            else{
                totalRemoved.addElement(checkedDiscs.get(1));
            }
            checkedDiscs.removeElementAt(1);
            int[] ratio = testAllNonTouchingDiscs(checkedDiscs);
            numKept = ratio[1];
            numTouching=ratio[0];
        }

        for(int i =0;i<checkedDiscs.size();i++){
            totalKept.addElement(checkedDiscs.get(i));
        }

        return totalKept;
    }

    //checks to see if a disc touches any others in the grid square
    public boolean checkDiscTouch(Disc disc, Vector<Disc> discList){

        for(int i=0;i<discList.size();i++){
            if(discList.get(i) !=disc && disc.discOverlap(discList.get(i))){
                return false;
            }
        }
        return true;
    }

    //draws the grid square
    public void draw(PApplet myParent){
        for(int i=0;i<edges.size();i++){
            DCHalfEdge edge = edges.get(i);
            myParent.line((float)edge.start.getX(),(float)edge.start.getY(),(float)edge.end.getX(),(float)edge.end.getY());
        }
    }

}


class VectorCompare implements Comparator<Vector> {
    public int compare(Vector a, Vector b) {
        return (a.size() < b.size()) ? -1 : (a.size() > b.size()) ? 1 : 0;
    }
}

class discCmpY implements Comparator<Disc> {
    public int compare(Disc a, Disc b) {
        return a.origin.compareTo(b.origin);
    }
}

class DiscTouch implements Comparator<Disc> {
    public int compare(Disc a, Disc b) {
        return (a.numTouching < b.numTouching) ? -1 : (a.numTouching > b.numTouching) ? 1 : 0;
    }
}

class LevelTouch implements Comparator<Disc> {

    public int level;
    public LevelTouch(int level){
        this.level = level;
    }
    public int compare(Disc a, Disc b) {

        return (a.discsTouching.get(level).numTouching < b.discsTouching.get(level).numTouching) ? -1 : (a.discsTouching.get(level).numTouching > b.discsTouching.get(level).numTouching) ? 1 : 0;
    }
}

