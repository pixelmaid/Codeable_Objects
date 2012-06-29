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

package com.datatype;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class BinarySearchTree<E extends Comparable<? super E>> {
    public PApplet myParent;
    public Node<E> root;
    public Vector<Vector<Node<E>>> levels = new Vector<Vector<Node<E>>>(0);

    public int size = 0;
    private int i;


    public BinarySearchTree() {

    }


    public Node<E> add(E value) {
        size++;
        if (root == null) {
            root = new Node<E>(value, null, this, 0);
            root.column = 0;
            root.name = "ROOT";
            addNodeToLevel(root);

            return root;
        } else {
            Node<E> node = root.add(value);
            //myParent.println(node.level);
            return node;
        }


    }


    public void addNodeToLevel(Node<E> node) {
        int level = node.level;
        int column = -1;


        //myParent.print("nodeLevel=");
        //myParent.println(node.level);

        if (levels.size() > level) {
            //myParent.println("level exists");

            Vector<Node<E>> selectedLevel = levels.get(level);
            //myParent.print("selected Level =");
            //myParent.println(selectedLevel);

            //myParent.print("num of elements =");
            //myParent.println(selectedLevel.size());

            if (selectedLevel.size() == 0) {
                selectedLevel.insertElementAt(node, 0);

            } else if (node.value.compareTo(selectedLevel.get(0).value) == -1) {
                //myParent.println("goes on start");
                selectedLevel.insertElementAt(node, 0);

            } else if (node.value.compareTo(selectedLevel.get(selectedLevel.size() - 1).value) == 1) {
                //myParent.println("checking for end");
                selectedLevel.addElement(node);


            } else {
                for (int i = 0; i < selectedLevel.size(); i++) {

                    if ((node.value.compareTo(selectedLevel.get(i).value) == 1) && (node.value.compareTo(selectedLevel.get(i + 1).value) == -1)) {
                        //myParent.println("insert in");
                        selectedLevel.insertElementAt(node, i + 1);


                    }

                }
            }

            for (int i = 0; i < selectedLevel.size(); i++) {

                selectedLevel.get(i).column = i;
            }


        } else {
            //myParent.println("add new level");
            Vector<Node<E>> newLevel = new Vector<Node<E>>(0);
            newLevel.addElement(node);
            addLevel(newLevel);
            column = 0;
            node.column = column;


        }


    }

    //debugging function
    private void checkNodesforNull() {
        List<E> treeList = this.toList();
        for (int i = 0; i < levels.size(); i++) {
            for (int j = 0; j < levels.get(i).size(); j++) {
                Node<E> testnode = levels.get(i).get(j);
                testnode.column = j;
                if (testnode == null) {
                    myParent.print("node null at level ");
                    myParent.print(i);
                    myParent.print("column ");
                    myParent.println(j);
                }

                if (treeList.indexOf(testnode.value) == -1) {
                    myParent.print("node is not in tree at level ");
                    myParent.print(i);
                    myParent.print(" column ");
                    myParent.println(j);
                }
            }
        }
    }

    //debugging function
    private void checkRootNode(String alert) {
        myParent.print("root column, " + alert + "=");
        myParent.println(root.column);
        if (root.column != 0) {
            myParent.println("!!!ALERT!!! " + alert);
            myParent.println("===================");
        }
    }

    private boolean addLevel(Vector<Node<E>> level) {
        levels.addElement(level);
        //myParent.print("number of levels=");
        //myParent.println(levels.size());
        //myParent.println(levels.get(0));
        return true;
    }

    public boolean remove(E _value) {
        if (root == null)
            return false;
        else {
            if (root.value.compareTo(_value) == 0) {
                //rebuild tree!

                E rootValue = root.value;
                Vector<Vector<Node<E>>> subLevels = levels;
                levels = new Vector<Vector<Node<E>>>(0);
                root = null;
                size = 0;


                subLevels.remove(0);

                for (int i = 0; i < subLevels.size(); i++) {
                    Vector<Node<E>> row = subLevels.get(i);
                    for (int j = 0; j < row.size(); j++) {
                        this.add(row.get(j).value);
                    }
                }
                subLevels = null;

                return true;

            } else {
                int size = this.toList().size();
                // myParent.print("size=");
                // myParent.println(size);

                Node<E> removedNode = root.remove(_value, null);
                // myParent.print("removed node=");
                // myParent.println(removedNode);
                removeZeroedLevels();

                size--;
                // myParent.print("size=");
                // myParent.println(size);

                return true;
            }
        }

    }

    //called when node is removed to remove it from level and column lists
    public void removeNodeFromLevels(Node<E> node) {
        int level = node.level;
        //myParent.print("node level=");
        //myParent.println(level);
        int column = node.column;


        if (levels.size() > level) {
            //myParent.print("node level size=");
            //myParent.println(levels.get(level).size());
            Vector<Node<E>> selectedLevel = levels.get(level);
            selectedLevel.removeElement(node);

            for (i = column; i < selectedLevel.size(); i++) {
                selectedLevel.get(i).column = i;
            }

            //myParent.print("node level size removed=");
            //myParent.println(levels.get(level).size());

            //myParent.print("currentNumLevels=");
            //myParent.println(levels.size());
        }


    }


    public void removeZeroedLevels() {//removes any levels that have zero elements
        for (i = 0; i < levels.size(); i++) {
            Vector<Node<E>> levelList = levels.get(i);

            if (levelList.size() == 0) { //element was last element in list, list must be removed

                //myParent.print("level to be removedLevel=");
                //myParent.println(i);
                levels.removeElement(levelList);
                levelList = null;

                //myParent.println("element removed");
                //myParent.print("newNumLevels=");
                //myParent.println(levels.size());

            }
        }
    }


    private Node<E> insert(Node<E> node, E value) {
        Node<E> result = new Node<E>(node);
        int compare = result.value.compareTo(value);

        if (compare == 0) {
            return result;
        }


        if (compare > 0) {
            if (result.getLeftChild() != null) {
                result.setLeftChild(insert(result.getLeftChild(), value));
            } else {
                result.setLeftChild(new Node<E>(value, node, this, result.getLeftChild().level));
                size++;
            }
        } else if (compare < 0) {
            if (result.getRightChild() != null) {
                result.setRightChild(insert(result.getRightChild(), value));
            } else {
                result.setRightChild(new Node<E>(value, node, this, result.getRightChild().level));
                size++;
            }
        }

        return result;
    }

    public Node<E> search(E _key) {

        if (root == null)
            return null;
        else
            //checkRootNode("search check");
            return root.search(_key);

    }


    public List<E> toList() {
        List<E> result = new ArrayList<E>();
        treeToList(root, result);
        return result;
    }


    private void treeToList(Node<E> node, List<E> goal) {
        if (node != null) {
            treeToList(node.getLeftChild(), goal);
            goal.add(node.value);
            treeToList(node.getRightChild(), goal);
        }
    }

}

