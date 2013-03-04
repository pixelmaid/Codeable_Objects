package com.algorithm.segmentintersect;


import java.io.PrintStream;

public class RedBlackNode
{

	 public static final int TREE_HEIGHT = 15;
	 public static final int NODE_X_OFFSET = 10;
	 public static final int NODE_Y_OFFSET = 15;
	 public static final int NODE_WIDTH = 10;
	 public static final int NODE_HEIGHT = 10;
	 public static final int RED = 0;
	 public static final int BLACK = 1;
	 private static final boolean DEBUG = false;
	 private Key key;
	 private RedBlackNode left;
	 private RedBlackNode parent;
	 private RedBlackNode right;
	 private RedBlackNode prev;
	 private RedBlackNode next;
	 private int color;
	 private int bheight;
	 
 public RedBlackNode(Key key1)
 {
     key = key1;
     parent = null;
     left = null;
     right = null;
     prev = null;
     next = null;
     color = 0;
     bheight = 0;
     key1.setNode(this);
 }

 public Key getKey()
 {
     return key;
 }

 protected void setKey(Key key1)
 {
     key = key1;
 }

 public RedBlackNode getParent()
 {
     return parent;
 }

 protected void setParent(RedBlackNode redblacknode)
 {
     parent = redblacknode;
 }

 public RedBlackNode getLeft()
 {
     return left;
 }

 protected void setLeft(RedBlackNode redblacknode)
 {
     left = redblacknode;
 }

 public RedBlackNode getRight()
 {
     return right;
 }

 protected void setRight(RedBlackNode redblacknode)
 {
     right = redblacknode;
 }

 public RedBlackNode getPrev()
 {
     return prev;
 }

 protected void setPrev(RedBlackNode redblacknode)
 {
     prev = redblacknode;
 }

 public RedBlackNode getNext()
 {
     return next;
 }

 protected void setNext(RedBlackNode redblacknode)
 {
     next = redblacknode;
 }

 public int getColor()
 {
     return color;
 }

 protected void setColor(int i)
 {
     color = i;
 }

 public int getBHeight()
 {
     return bheight;
 }

 protected void setBHeight(int i)
 {
     bheight = i;
 }

 public void inOrderWalk()
 {
     if(left != null)
         left.inOrderWalk();
     try
     {
         System.out.println("value, color, black-height: " + key.getValue() + " " + getColor() + " " + getBHeight());
     }
     catch(NullPointerException _ex)
     {
         System.out.println("inOrderWalk error: null tree.");
         return;
     }
     if(left != null)
     {
         Key key1 = left.getKey();
         System.out.println("Left: " + key1.getValue() + " " + left.getColor() + " " + left.getBHeight());
     }
     if(right != null)
     {
         Key key2 = right.getKey();
         System.out.println("Right: " + key2.getValue() + " " + right.getColor() + " " + right.getBHeight());
     }
     if(prev != null)
     {
         Key key3 = prev.getKey();
         System.out.println("Prev: " + key3.getValue() + " " + prev.getColor() + " " + prev.getBHeight());
     }
     if(next != null)
     {
         Key key4 = next.getKey();
         System.out.println("Next: " + key4.getValue() + " " + next.getColor() + " " + next.getBHeight());
     }
     System.out.println(" ");
     if(right != null)
         right.inOrderWalk();
 }

 public RedBlackNode treeSearch(Key key1)
 {
     if(key1 == null)
         return null;
     if(key1.equals(key))
         return this;
     if(key1.lessThan(key))
         if(left == null)
             return null;
         else
             return left.treeSearch(key1);
     if(right == null)
         return null;
     else
         return right.treeSearch(key1);
 }

 public RedBlackNode getMinimum()
 {
     if(left == null)
         return this;
     else
         return left.getMinimum();
 }

 public RedBlackNode getMaximum()
 {
     if(right == null)
         return this;
     else
         return right.getMaximum();
 }

 public RedBlackNode getSuccessor()
 {
     if(right != null)
         return right.getMinimum();
     RedBlackNode redblacknode = this;
     RedBlackNode redblacknode1;
     for(redblacknode1 = parent; redblacknode1 != null && redblacknode1.right == redblacknode; redblacknode1 = redblacknode1.parent)
         redblacknode = redblacknode1;

     return redblacknode1;
 }

 public RedBlackNode getPredecessor()
 {
     if(left != null)
         return left.getMaximum();
     RedBlackNode redblacknode = this;
     RedBlackNode redblacknode1;
     for(redblacknode1 = parent; redblacknode1 != null && redblacknode1.left == redblacknode; redblacknode1 = redblacknode1.parent)
         redblacknode = redblacknode1;

     return redblacknode1;
 }

 public int countBHeight()
 {
     int i = 1;
     int j = 1;
     int k = 1;
     if(left != null)
         i = left.countBHeight();
     if(right != null)
         j = right.countBHeight();
     if(i != j)
         return 0;
     if(color == 1)
         k = i + 1;
     else
         k = i;
     return k;
 }

 protected void copyDataOf(RedBlackNode redblacknode)
 {
 }

 public boolean isBetween(Object obj, Object obj1)
 {
     return true;
 }

 public boolean isBetween(double d, double d1)
 {
     double d2 = key.getValue();
     return d2 >= d && d2 <= d1;
 }

 public boolean checkRed()
 {
     if(left != null && !left.checkRed())
         return false;
     if(right != null && !right.checkRed())
         return false;
     if(color == 0)
     {
         if(left != null && left.getColor() == 0)
             return false;
         if(right != null && right.getColor() == 0)
             return false;
     }
     return true;
 }

}
