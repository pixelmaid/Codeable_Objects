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

package com.datastruct.voronoi;

import com.datastruct.Node;
import com.math.CompPoint;

public class VorNode<E extends Comparable<? super E>> extends Node {
    public VorNode<E> left;
    public VorNode<E> right;
    public VorNode<E> parent;
    public String name = "blah";

    VorNode(Comparable value, VorNode parent, VorTree _parentTree,
            int _level) {
        super(value, parent, _parentTree, _level);
        this.value = value;
        this.parent = parent;
        this.parentTree = _parentTree;
        this.level = _level;


        // TODO Auto-generated constructor stub
    }


    //searches for matching arc to compPoint
    public VorNode<E> searchArc(CompPoint _value) {
        CompPoint value = (CompPoint) this.value;
        this.parentTree.myParent.print("value=");
        this.parentTree.myParent.println(_value);
        if (value.compareTo(_value) == 0)
            return this;
        else if (value.compareTo(_value) == 1) {
            if (left == null)
                return null;
            else
                return left.searchArc(_value);
        } else if (value.compareTo(_value) == -1) {
            if (right == null)
                return null;
            else
                return right.searchArc(_value);
        }
        return null;
    }
}
