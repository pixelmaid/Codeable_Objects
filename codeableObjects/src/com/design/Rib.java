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

package com.design;

public class Rib extends Part {

    public int topNotchPos = 1;
    public int bottomNotchPos = 1;

    public Rib(double width, double height) {
        super(width, height);
    }

    public void addNotches(double notchWidth, double notchHeight, double ribNotchOffset, double topCirclePos, double bottomCirclePos) {
        Notch notch1 = new Notch(notchWidth, notchHeight);
        Notch notch2 = new Notch(notchWidth, notchHeight);

        this.setNotch(notch1, topCirclePos, ribNotchOffset, true);
        this.setNotch(notch2, height - bottomCirclePos, ribNotchOffset, false);


    }


    private void setNotch(Notch notch, double pos, double ribNotchOffset, boolean top) {
        int topEdgeNum = 1;
        int bottomEdgeNum = 1;
        for (int i = 1; i < edges.size() / 2 - 1; i++) {

            if (edges.get(i).start.getY() <= pos && edges.get(i + 1).start.getY() >= pos) {
                topEdgeNum = i;
                //System.out.println("found level at"+i);
            }

            if (edges.get(i).end.getY() <= pos && edges.get(i + 1).end.getY() >= pos) {
                bottomEdgeNum = i;
                //System.out.println("found level at"+i);
            }


        }
        if (top) {
            topNotchPos = topEdgeNum;
        } else {
            bottomNotchPos = topEdgeNum;
        }
        notch.translate(this.edges.get(topEdgeNum).start.getX() + ribNotchOffset, this.edges.get(topEdgeNum).start.getY());
        notch.merge(this, topEdgeNum, bottomEdgeNum);
    }

/*	public void addNotches(double notchWidth, double notchHeight, double ribNotchOffset,double topCirclePos,double bottomCirclePos){
		Notch notch1 = new Notch(notchWidth,notchHeight);
		Notch notch2 = new Notch(notchWidth,notchHeight);
		int topEdgeNum=1;
		int topEdgeNum=1;
		for(int i=1;i<edges.size()/2-1;i++){
			System.out.println("rib edge y="+edges.get(i).start.getY());
			System.out.println("notch offset ="+topCirclePos);
			
			if(edges.get(i).start.getY()<=topCirclePos && edges.get(i+1).start.getY()>=topCirclePos ){
					edgeNum=i;
					System.out.println("found level at"+i);
				}
			
			
		}
		notch1.translate(this.edges.get(edgeNum).start.getX()+ribNotchOffset,this.edges.get(edgeNum).start.getY());
		notch1.merge(this);
			
	}*/

}
