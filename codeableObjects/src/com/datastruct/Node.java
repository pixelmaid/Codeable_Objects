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

package com.datastruct;

public class Node<E extends Comparable<? super E>> {
    public E value;
    public Node<E> parent;
    public int level;
    public int column = -2;

    public BinarySearchTree<E> parentTree;
    public Node<E> left;
    public Node<E> right;
    public String name = "foo";

    public Node(E value, Node<E> parent, BinarySearchTree<E> _parentTree, int _level) {
        this.value = value;
        this.parent = parent;
        this.parentTree = _parentTree;
        this.level = _level;

    }

    public Node(Node<E> node) {
        this.value = node.value;
        left = node.left;
        right = node.right;
    }

    public void setLeftChild(Node<E> node) {
        left = node;
        /*node.level=this.level+1;
           parentTree.addNodeToLevel(node);*/
    }

    public void setRightChild(Node<E> node) {
        right = node;
        /*node.level=this.level+1;
          parentTree.addNodeToLevel(node);*/
    }

    public void setcolumn(int column) {
        this.column = column;
    }

    public Node<E> getLeftChild() {
        return left;
    }

    public Node<E> getRightChild() {
        return right;
    }

    public Node<E> add(E _value) {
        if (this.value.compareTo(_value) == 0)
            return null;
        else if (this.value.compareTo(_value) == 1) {
            if (left == null) {
                left = new Node<E>(_value, this, this.parentTree, this.level + 1);
                parentTree.addNodeToLevel(left);
                return left;
            } else
                return left.add(_value);
        } else if (this.value.compareTo(_value) == -1) {
            if (right == null) {
                right = new Node<E>(_value, this, this.parentTree, this.level + 1);
                parentTree.addNodeToLevel(right);
                return right;
            } else
                return right.add(_value);
        } else {
            return null;
        }
    }


    public Node<E> remove(E _value, Node<E> _parent) {
        /* -1 means less than
            * 1 means greater than
            * 0 means ==
            */


        if (this.value.compareTo(_value) == 1) {
            if (left != null)
                return left.remove(_value, this);
            else
                return null;
        } else if (this.value.compareTo(_value) == -1) {
            if (right != null)
                return right.remove(_value, this);
            else
                return null;
        } else {
            //parentTree.myParent.print("remove node from levels call level=");
            //parentTree.myParent.println(this.level);


            if (left != null && right != null) {
                this.value = right.minValue();
                Node<E> minValueNode = right.remove(this.value, this);
                // parentTree.myParent.println("left right not null");

            } else if (_parent.left == this) {
                if (left != null) {
                    //	 parentTree.myParent.println("left not null");
                    _parent.left = left;
                    //	 parentTree.removeNodeFromLevels(left);

                } else {
                    //parentTree.myParent.println("left null");
                    _parent.left = right;


                }

            } else if (_parent.right == this) {
                if (left != null) {
                    // parentTree.myParent.println("right null");
                    _parent.right = left;


                } else {
                    //parentTree.myParent.println("right not null");
                    _parent.right = right;


                }

            }
            parentTree.removeNodeFromLevels(this);

            resetParent(_parent);
            /* if(_parent.parent!=null){
             if((_parent.parent.left!=_parent)&&(_parent.parent.left!=null)){
                 resetParent(_parent.parent.left);
             }
               
             if((_parent.parent.right!=_parent)&&(_parent.parent.right!=null)){
                 resetParent(_parent.parent.right);
             }
            }*/

            return this;

        }
    }


    private void resetParent(Node<E> _parent) {
        if (_parent.left != null) {
            parentTree.removeNodeFromLevels(_parent.left);
            _parent.left.parent = _parent;

            _parent.left.level = _parent.level + 1;
            parentTree.addNodeToLevel(_parent.left);
            _parent.left.resetParent(_parent.left);
        }

        if (_parent.right != null) {
            parentTree.removeNodeFromLevels(_parent.right);
            _parent.right.parent = _parent;

            _parent.right.level = _parent.level + 1;
            parentTree.addNodeToLevel(_parent.right);
            _parent.right.resetParent(_parent.right);
        }


    }

    public E minValue() {
        if (left == null)
            return value;
        else
            return left.minValue();
    }

    public Node<E> search(E _value) {
        if (this.value.compareTo(_value) == 0)
            return this;
        else if (this.value.compareTo(_value) == 1) {
            if (left == null)
                return null;
            else
                return left.search(_value);
        } else if (this.value.compareTo(_value) == -1) {
            if (right == null)
                return null;
            else
                return right.search(_value);
        }
        return null;
    }

    //searches for parent node of intended I
    public Node<E> searchVertical(E _value) {
        if (this.value.compareTo(_value) == 1) {
            if ((left == null) && (right == null)) {
                return this.parent;
            } else if (left == null) {
                return left.search(_value);
            }
        } else if (this.value.compareTo(_value) == -1) {
            if ((left == null) && (right == null)) {
                return this.parent;
            } else if (right == null)
                return null;
            else
                return right.search(_value);
        }
        return null;
    }


    //dummy compare
    public int compareTo(Node<E> o) {
        return this.compareTo(o);
    }


}

