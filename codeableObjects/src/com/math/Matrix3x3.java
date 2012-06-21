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

package com.math;

public class Matrix3x3 {
    private double[][] A = new double[3][3];

    public Matrix3x3() {

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                A[i][j] = 0;
    }

    public double get(int i, int j) {

        {
            if (!((i < 0 || i >= 3) && (j < 0 || j <= 3)))
                return A[i][j];
            else
                return 0;
        }
    }

    public void set(int i, int j, double value) {
        if (!((i < 0 || i >= 3) && (j < 0 || j <= 3)))
            A[i][j] = value;
    }
}
