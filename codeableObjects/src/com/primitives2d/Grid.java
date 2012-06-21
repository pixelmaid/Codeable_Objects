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

import processing.core.PApplet;

import java.util.Vector;

public class Grid {
    public int size;
    public double scale;
    public Vector<GridSquare> squares;
    public Vector<Disc> discs;


    public Grid(PApplet myParent, int size, double scale, Vector<Disc> discs) {
        this.size = size;
        this.scale = scale;
        this.discs = discs;
        squares = new Vector<GridSquare>();


        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                GridSquare square = new GridSquare(i * scale, j * scale, scale);
                squares.addElement(square);

            }
        }
        for (int i = 0; i < this.discs.size(); i++) {
            checkDisc(this.discs.get(i), myParent);
        }


    }

    public void clear() {

        for (int i = 0; i < squares.size(); i++) {
            GridSquare square = squares.get(i);
            square.clear();

        }
        discs = new Vector<Disc>();


    }

    public void addDisc(PApplet myParent, double discX, double discY, double radius) {
        Disc disc = new Disc(discX, discY, radius);
        discs.addElement(disc);
        checkDisc(disc, myParent);


    }

    private void checkDisc(Disc disc, PApplet myParent) {

        for (int i = 0; i < squares.size(); i++) {
            GridSquare square = squares.get(i);
            if (disc.wholeInGrid(square)) {
                disc.insideGrid = true;
                square.addDisc(disc);
                square.optimizedRemoval(myParent);
                break;
            }

        }
    }

    public void draw(PApplet myParent) {
        for (int i = 0; i < squares.size(); i++) {
            squares.get(i).draw(myParent);
        }

        for (int i = 0; i < discs.size(); i++) {
            discs.get(i).draw(myParent);
        }
    }

}


