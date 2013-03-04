package com.algorithm.segmentintersect;

import java.io.PrintStream;
import java.util.Vector;

public final class RedBlackTree
{
	 public static final int RIGHT = 0;
	 public static final int LEFT = 1;
	 public static final int RED = 0;
	 public static final int BLACK = 1;
	 private final boolean DEBUG = false;
	 private RedBlackNode root;

	 public RedBlackTree()
	 {
	     root = null;
	 }
	
	 public RedBlackTree(RedBlackNode redblacknode)
	 {
	     root = redblacknode;
	 }
	
	 public RedBlackTree(Key key)
	 {
	     this(new RedBlackNode(key));
	 }
	
	 public RedBlackNode getRoot()
	 {
	     return root;
	 }
	
	 public boolean treeContains(RedBlackNode redblacknode)
	 {
	     if(root == null)
	         return false;
	     if(redblacknode == null)
	     {
	         System.out.println("treeContains error: null node.");
	         return false;
	     }
	     RedBlackNode redblacknode1 = root.treeSearch(redblacknode.getKey());
	     return redblacknode == redblacknode1;
	 }
	
	 public RedBlackNode treeSearch(Key key)
	 {
	     if(root != null)
	         return root.treeSearch(key);
	     else
	         return null;
	 }
	
	 public Key getNodeAbove(Key key)
	 {
	     RedBlackNode redblacknode = treeSearch(key);
	     if(redblacknode != null)
	     {
	         RedBlackNode redblacknode1 = redblacknode.getNext();
	         if(redblacknode1 != null)
	             return redblacknode1.getKey();
	     }
	     return null;
	 }
	
	 public Key getNodeBelow(Key key)
	 {
	     RedBlackNode redblacknode = treeSearch(key);
	     if(redblacknode != null)
	     {
	         RedBlackNode redblacknode1 = redblacknode.getPrev();
	         if(redblacknode1 != null)
	             return redblacknode1.getKey();
	     }
	     return null;
	 }
	
	 public RedBlackNode getMinimum()
	 {
	     if(root == null)
	     {
	         System.out.println("error: root is null.");
	         return null;
	     } else
	     {
	         return root.getMinimum();
	     }
	 }
	
	 public RedBlackNode getMaximum()
	 {
	     if(root == null)
	     {
	         System.out.println("error: root is null.");
	         return null;
	     } else
	     {
	         return root.getMaximum();
	     }
	 }
	
	 public Vector<RedBlackNode> nodesBetween(double d, double d1)
	 {
	     if(root == null)
	     {
	         System.out.println("error: root is null.");
	         return null;
	     }
	     Vector<RedBlackNode> vector = new Vector<RedBlackNode>();
	     if(d > d1)
	         return vector;
	     RedBlackNode redblacknode = root;
	     if(redblacknode.getKey().getValue() >= d)
	     {
	         for(RedBlackNode redblacknode1 = redblacknode.getPrev(); redblacknode1 != null && redblacknode1.getKey().getValue() >= d; redblacknode1 = redblacknode.getPrev())
	             redblacknode = redblacknode1;
	
	     } else
	     {
	         for(redblacknode = redblacknode.getNext(); redblacknode != null && redblacknode.getKey().getValue() < d; redblacknode = redblacknode.getNext());
	     }
	     if(redblacknode == null || redblacknode.getKey().getValue() > d1)
	         return vector;
	     for(; redblacknode != null && redblacknode.getKey().getValue() <= d1; redblacknode = redblacknode.getNext())
	         vector.addElement(redblacknode);
	
	     return vector;
	 }
	
	 public Vector<RedBlackNode> allNodes()
	 {
	     if(root == null)
	         return null;
	     Vector<RedBlackNode> vector = new Vector<RedBlackNode>();
	     for(RedBlackNode redblacknode = getMinimum(); redblacknode != null; redblacknode = redblacknode.getNext())
	         vector.addElement(redblacknode);
	
	     return vector;
	 }
	
	 public void inOrderWalk()
	 {
	     if(root != null)
	         root.inOrderWalk();
	 }
	
	 public void leftRotate(RedBlackNode redblacknode)
	 {
	     if(redblacknode == null)
	     {
	         System.out.println("leftRotate error: null node.");
	         return;
	     }
	     RedBlackNode redblacknode1 = redblacknode.getRight();
	     if(redblacknode1 == null)
	     {
	         System.out.println("leftRotate error: null right child node.");
	         return;
	     }
	     redblacknode.setRight(redblacknode1.getLeft());
	     if(redblacknode1.getLeft() != null)
	         redblacknode1.getLeft().setParent(redblacknode);
	     redblacknode1.setParent(redblacknode.getParent());
	     if(redblacknode.getParent() == null)
	         root = redblacknode1;
	     else
	     if(redblacknode == redblacknode.getParent().getLeft())
	         redblacknode.getParent().setLeft(redblacknode1);
	     else
	         redblacknode.getParent().setRight(redblacknode1);
	     redblacknode1.setLeft(redblacknode);
	     redblacknode.setParent(redblacknode1);
	 }
	
	 public void rightRotate(RedBlackNode redblacknode)
	 {
	     if(redblacknode == null)
	     {
	         System.out.println("rightleftRotate error: null node.");
	         return;
	     }
	     RedBlackNode redblacknode1 = redblacknode.getLeft();
	     if(redblacknode1 == null)
	     {
	         System.out.println("rightRotate error: null left child node.");
	         return;
	     }
	     redblacknode.setLeft(redblacknode1.getRight());
	     if(redblacknode1.getRight() != null)
	         redblacknode1.getRight().setParent(redblacknode);
	     redblacknode1.setParent(redblacknode.getParent());
	     if(redblacknode.getParent() == null)
	         root = redblacknode1;
	     else
	     if(redblacknode == redblacknode.getParent().getRight())
	         redblacknode.getParent().setRight(redblacknode1);
	     else
	         redblacknode.getParent().setLeft(redblacknode1);
	     redblacknode1.setRight(redblacknode);
	     redblacknode.setParent(redblacknode1);
	 }
	
	 public boolean treeInsert(RedBlackNode redblacknode)
	 {
	     if(redblacknode == null)
	         return false;
	     if(root == null)
	     {
	         root = redblacknode;
	         root.setParent(null);
	         root.setLeft(null);
	         root.setRight(null);
	         root.setPrev(null);
	         root.setNext(null);
	         root.setColor(1);
	         root.setBHeight(1);
	         return true;
	     }
	     RedBlackNode redblacknode1 = root;
	     RedBlackNode redblacknode2 = null;
	     while(redblacknode1 != null) 
	     {
	         redblacknode2 = redblacknode1;
	         if(redblacknode.getKey().lessThan(redblacknode1.getKey()))
	             redblacknode1 = redblacknode1.getLeft();
	         else
	         if(redblacknode.getKey().largerThan(redblacknode1.getKey()))
	             redblacknode1 = redblacknode1.getRight();
	         else
	             return false;
	     }
	     redblacknode.setParent(redblacknode2);
	     if(redblacknode2 == null)
	         root = redblacknode;
	     else
	     if(redblacknode.getKey().lessThan(redblacknode2.getKey()))
	         redblacknode2.setLeft(redblacknode);
	     else
	         redblacknode2.setRight(redblacknode);
	     RedBlackNode redblacknode5 = redblacknode.getPredecessor();
	     redblacknode.setPrev(redblacknode5);
	     if(redblacknode5 != null)
	         redblacknode5.setNext(redblacknode);
	     RedBlackNode redblacknode6 = redblacknode.getSuccessor();
	     redblacknode.setNext(redblacknode6);
	     if(redblacknode6 != null)
	         redblacknode6.setPrev(redblacknode);
	     redblacknode.setColor(0);
	     while(redblacknode != root && redblacknode.getParent().getColor() == 0) 
	     {
	         RedBlackNode redblacknode7 = redblacknode.getParent();
	         RedBlackNode redblacknode8 = redblacknode7.getParent();
	         redblacknode.setBHeight(redblacknode7.getBHeight());
	         if(redblacknode7 == redblacknode8.getLeft())
	         {
	             RedBlackNode redblacknode3 = redblacknode8.getRight();
	             int i = 0;
	             if(redblacknode3 == null)
	                 i = 1;
	             else
	                 i = redblacknode3.getColor();
	             if(i == 0)
	             {
	                 redblacknode7.setColor(1);
	                 redblacknode7.setBHeight(redblacknode7.getBHeight() + 1);
	                 redblacknode3.setColor(1);
	                 redblacknode3.setBHeight(redblacknode3.getBHeight() + 1);
	                 redblacknode8.setColor(0);
	                 redblacknode = redblacknode8;
	             } else
	             {
	                 if(redblacknode == redblacknode7.getRight())
	                 {
	                     redblacknode = redblacknode7;
	                     leftRotate(redblacknode);
	                     redblacknode7 = redblacknode.getParent();
	                 }
	                 redblacknode7.setColor(1);
	                 redblacknode7.setBHeight(redblacknode7.getBHeight() + 1);
	                 redblacknode8.setColor(0);
	                 redblacknode8.setBHeight(redblacknode8.getBHeight() - 1);
	                 rightRotate(redblacknode8);
	             }
	         } else
	         {
	             RedBlackNode redblacknode4 = redblacknode8.getLeft();
	             int j = 0;
	             if(redblacknode4 == null)
	                 j = 1;
	             else
	                 j = redblacknode4.getColor();
	             if(j == 0)
	             {
	                 redblacknode7.setColor(1);
	                 redblacknode7.setBHeight(redblacknode7.getBHeight() + 1);
	                 redblacknode4.setColor(1);
	                 redblacknode4.setBHeight(redblacknode4.getBHeight() + 1);
	                 redblacknode8.setColor(0);
	                 redblacknode = redblacknode8;
	             } else
	             {
	                 if(redblacknode == redblacknode7.getLeft())
	                 {
	                     redblacknode = redblacknode7;
	                     rightRotate(redblacknode);
	                     redblacknode7 = redblacknode.getParent();
	                 }
	                 redblacknode7.setColor(1);
	                 redblacknode7.setBHeight(redblacknode7.getBHeight() + 1);
	                 redblacknode8.setColor(0);
	                 redblacknode8.setBHeight(redblacknode8.getBHeight() - 1);
	                 leftRotate(redblacknode8);
	             }
	         }
	     }
	     if(root.getColor() == 0)
	     {
	         root.setColor(1);
	         root.setBHeight(root.getBHeight() + 1);
	     }
	     return true;
	 }
	
	 public boolean treeDelete(RedBlackNode redblacknode)
	 {
	     int i = 1;
	     if(redblacknode == null)
	         return false;
	     if(root == null)
	     {
	         System.out.println("treeDelete error: empty tree");
	         return false;
	     }
	     if(!treeContains(redblacknode))
	     {
	         System.out.println("treeDelete error: node not in the tree");
	         return false;
	     }
	     if(root.getLeft() == null && root.getRight() == null)
	     {
	         root = null;
	         return true;
	     }
	     boolean flag = false;
	     boolean flag1 = false;
	     boolean flag2 = false;
	     RedBlackNode redblacknode2;
	     if(redblacknode.getRight() != null)
	     {
	         redblacknode2 = redblacknode.getNext();
	         if(redblacknode2 == redblacknode.getRight())
	             flag2 = true;
	         flag = true;
	     } else
	     if(redblacknode.getLeft() != null)
	     {
	         redblacknode2 = redblacknode.getPrev();
	         if(redblacknode2 == redblacknode.getLeft())
	             flag2 = true;
	         flag1 = true;
	     } else
	     {
	         redblacknode2 = redblacknode;
	     }
	     RedBlackNode redblacknode1;
	     if(redblacknode2.getLeft() != null)
	     {
	         redblacknode1 = redblacknode2.getLeft();
	         i = 1;
	     } else
	     {
	         redblacknode1 = redblacknode2.getRight();
	         i = 0;
	     }
	     if(redblacknode1 != null && !flag2)
	         redblacknode1.setParent(redblacknode2.getParent());
	     if(!flag2)
	         if(redblacknode2 == redblacknode2.getParent().getLeft())
	         {
	             redblacknode2.getParent().setLeft(redblacknode1);
	             i = 1;
	         } else
	         {
	             redblacknode2.getParent().setRight(redblacknode1);
	             i = 0;
	         }
	     RedBlackNode redblacknode3 = null;
	     if(flag2)
	         redblacknode3 = redblacknode2;
	     else
	         redblacknode3 = redblacknode2.getParent();
	     int j = redblacknode2.getColor();
	     if(redblacknode2 != redblacknode)
	     {
	         RedBlackNode redblacknode4 = redblacknode.getParent();
	         RedBlackNode redblacknode6 = redblacknode.getLeft();
	         RedBlackNode redblacknode8 = redblacknode.getRight();
	         redblacknode2.setParent(redblacknode4);
	         if(redblacknode4 != null)
	             if(redblacknode == redblacknode4.getLeft())
	                 redblacknode4.setLeft(redblacknode2);
	             else
	                 redblacknode4.setRight(redblacknode2);
	         if(flag)
	         {
	             redblacknode2.setLeft(redblacknode6);
	             if(redblacknode6 != null)
	                 redblacknode6.setParent(redblacknode2);
	             if(!flag2)
	             {
	                 redblacknode2.setRight(redblacknode8);
	                 if(redblacknode8 != null)
	                     redblacknode8.setParent(redblacknode2);
	             }
	             RedBlackNode redblacknode9 = redblacknode.getPrev();
	             redblacknode2.setPrev(redblacknode9);
	             if(redblacknode9 != null)
	                 redblacknode9.setNext(redblacknode2);
	         } else
	         if(flag1)
	         {
	             redblacknode2.setRight(null);
	             if(!flag2)
	             {
	                 redblacknode2.setLeft(redblacknode6);
	                 if(redblacknode6 != null)
	                     redblacknode6.setParent(redblacknode2);
	             }
	             RedBlackNode redblacknode10 = redblacknode.getNext();
	             redblacknode2.setNext(redblacknode10);
	             if(redblacknode10 != null)
	                 redblacknode10.setPrev(redblacknode2);
	         }
	         redblacknode2.setBHeight(redblacknode.getBHeight());
	         redblacknode2.setColor(redblacknode.getColor());
	         if(root == redblacknode)
	             root = redblacknode2;
	     } else
	     {
	         RedBlackNode redblacknode5 = redblacknode.getPrev();
	         RedBlackNode redblacknode7 = redblacknode.getNext();
	         if(redblacknode5 != null)
	             redblacknode5.setNext(redblacknode7);
	         if(redblacknode7 != null)
	             redblacknode7.setPrev(redblacknode5);
	     }
	     if(j == 1)
	         RBDeleteFixUp(redblacknode1, i, redblacknode3);
	     return true;
	 }
	
	 private void RBDeleteFixUp(RedBlackNode redblacknode, int i, RedBlackNode redblacknode1)
	 {
	     int j;
	     if(redblacknode == null)
	         j = 1;
	     else
	         j = redblacknode.getColor();
	     while(redblacknode != root && j == 1) 
	     {
	         int k1;
	         RedBlackNode redblacknode2;
	         if(redblacknode == null)
	         {
	             k1 = i;
	             redblacknode2 = redblacknode1;
	         } else
	         {
	             redblacknode2 = redblacknode.getParent();
	             if(redblacknode == redblacknode2.getLeft())
	                 k1 = 1;
	             else
	                 k1 = 0;
	         }
	         if(k1 == 1)
	         {
	             RedBlackNode redblacknode3 = redblacknode2.getRight();
	             if(redblacknode3.getColor() == 0)
	             {
	                 redblacknode3.setColor(1);
	                 redblacknode3.setBHeight(redblacknode3.getBHeight() + 1);
	                 redblacknode2.setColor(0);
	                 redblacknode2.setBHeight(redblacknode2.getBHeight() - 1);
	                 leftRotate(redblacknode2);
	                 redblacknode3 = redblacknode2.getRight();
	             }
	             int k;
	             if(redblacknode3.getLeft() == null)
	                 k = 1;
	             else
	                 k = redblacknode3.getLeft().getColor();
	             int i1;
	             if(redblacknode3.getRight() == null)
	                 i1 = 1;
	             else
	                 i1 = redblacknode3.getRight().getColor();
	             if(k == 1 && i1 == 1)
	             {
	                 redblacknode3.setColor(0);
	                 redblacknode3.setBHeight(redblacknode3.getBHeight() - 1);
	                 redblacknode2.setBHeight(redblacknode2.getBHeight() - 1);
	                 redblacknode = redblacknode2;
	             } else
	             {
	                 if(i1 == 1)
	                 {
	                     redblacknode3.getLeft().setColor(1);
	                     redblacknode3.setColor(0);
	                     redblacknode3.setBHeight(redblacknode3.getBHeight() - 1);
	                     rightRotate(redblacknode3);
	                     redblacknode3 = redblacknode2.getRight();
	                     redblacknode3.setBHeight(redblacknode3.getBHeight() + 1);
	                 }
	                 redblacknode3.setColor(redblacknode2.getColor());
	                 redblacknode3.getRight().setColor(1);
	                 if(redblacknode2.getColor() == 1)
	                 {
	                     redblacknode2.setBHeight(redblacknode2.getBHeight() - 1);
	                     redblacknode3.setBHeight(redblacknode3.getBHeight() + 1);
	                 }
	                 redblacknode2.setColor(1);
	                 redblacknode3.getRight().setBHeight(redblacknode3.getRight().getBHeight() + 1);
	                 leftRotate(redblacknode2);
	                 redblacknode = root;
	             }
	         } else
	         {
	             RedBlackNode redblacknode4 = redblacknode2.getLeft();
	             if(redblacknode4.getColor() == 0)
	             {
	                 redblacknode4.setColor(1);
	                 redblacknode4.setBHeight(redblacknode4.getBHeight() + 1);
	                 redblacknode2.setColor(0);
	                 redblacknode2.setBHeight(redblacknode2.getBHeight() - 1);
	                 rightRotate(redblacknode2);
	                 redblacknode4 = redblacknode2.getLeft();
	             }
	             int l;
	             if(redblacknode4.getLeft() == null)
	                 l = 1;
	             else
	                 l = redblacknode4.getLeft().getColor();
	             int j1;
	             if(redblacknode4.getRight() == null)
	                 j1 = 1;
	             else
	                 j1 = redblacknode4.getRight().getColor();
	             if(l == 1 && j1 == 1)
	             {
	                 redblacknode4.setColor(0);
	                 redblacknode4.setBHeight(redblacknode4.getBHeight() - 1);
	                 redblacknode2.setBHeight(redblacknode2.getBHeight() - 1);
	                 redblacknode = redblacknode2;
	             } else
	             {
	                 if(l == 1)
	                 {
	                     redblacknode4.getRight().setColor(1);
	                     redblacknode4.setColor(0);
	                     redblacknode4.setBHeight(redblacknode4.getBHeight() - 1);
	                     leftRotate(redblacknode4);
	                     redblacknode4 = redblacknode2.getLeft();
	                     redblacknode4.setBHeight(redblacknode4.getBHeight() + 1);
	                 }
	                 redblacknode4.setColor(redblacknode2.getColor());
	                 redblacknode4.getLeft().setColor(1);
	                 if(redblacknode2.getColor() == 1)
	                 {
	                     redblacknode2.setBHeight(redblacknode2.getBHeight() - 1);
	                     redblacknode4.setBHeight(redblacknode4.getBHeight() + 1);
	                 }
	                 redblacknode2.setColor(1);
	                 redblacknode4.getLeft().setBHeight(redblacknode4.getLeft().getBHeight() + 1);
	                 rightRotate(redblacknode2);
	                 redblacknode = root;
	             }
	         }
	         if(redblacknode == null)
	             j = 1;
	         else
	             j = redblacknode.getColor();
	     }
	     if(redblacknode.getColor() == 0)
	     {
	         redblacknode.setColor(1);
	         redblacknode.setBHeight(redblacknode.getBHeight() + 1);
	     }
	 }
	
	 public boolean checkHeight()
	 {
	     RedBlackNode redblacknode = root;
	     boolean flag = false;
	     while(redblacknode != null) 
	         if(redblacknode.getLeft() != null && !flag)
	             redblacknode = redblacknode.getLeft();
	         else
	         if(redblacknode.getRight() != null && !flag)
	             redblacknode = redblacknode.getRight();
	         else
	         if(redblacknode == root)
	         {
	             redblacknode = null;
	         } else
	         {
	             int i;
	             if(redblacknode.getLeft() == null)
	                 i = 0;
	             else
	                 i = redblacknode.getLeft().getBHeight();
	             int j;
	             if(redblacknode.getRight() == null)
	                 j = 0;
	             else
	                 j = redblacknode.getRight().getBHeight();
	             if(i != j)
	             {
	                 System.out.println("lh not equal to rh");
	                 return false;
	             }
	             if(redblacknode.getColor() == 0)
	             {
	                 if(redblacknode.getBHeight() < i)
	                 {
	                     System.out.println("red: bh less than expected");
	                     return false;
	                 }
	                 if(redblacknode.getBHeight() > i)
	                 {
	                     System.out.println("red: bh larger than expected");
	                     return false;
	                 }
	             } else
	             {
	                 if(redblacknode.getBHeight() < i + 1)
	                 {
	                     System.out.println("black: bh less than expected");
	                     return false;
	                 }
	                 if(redblacknode.getBHeight() > i + 1)
	                 {
	                     System.out.println("black: bh larger than expected");
	                     return false;
	                 }
	             }
	             if(redblacknode == redblacknode.getParent().getLeft())
	             {
	                 if(redblacknode.getParent().getRight() != null)
	                 {
	                     redblacknode = redblacknode.getParent().getRight();
	                     flag = false;
	                 } else
	                 {
	                     redblacknode = redblacknode.getParent();
	                     flag = true;
	                 }
	             } else
	             {
	                 redblacknode = redblacknode.getParent();
	                 flag = true;
	             }
	         }
	     return true;
	 }
	
	 public boolean isInOrder()
	 {
	     if(root == null)
	         return true;
	     RedBlackNode redblacknode = root.getMinimum();
	     Key key = redblacknode.getKey();
	     Object obj = null;
	     for(RedBlackNode redblacknode1 = redblacknode.getNext(); redblacknode1 != null; redblacknode1 = redblacknode1.getNext())
	     {
	         Key key1 = redblacknode1.getKey();
	         if(key.largerThan(key1) || key.equals(key1))
	             return false;
	         key = key1;
	     }
	
	     RedBlackNode redblacknode3 = root.getMaximum();
	     key = redblacknode3.getKey();
	     for(RedBlackNode redblacknode2 = redblacknode3.getPrev(); redblacknode2 != null; redblacknode2 = redblacknode2.getPrev())
	     {
	         Key key2 = redblacknode2.getKey();
	         if(key.lessThan(key2) || key.equals(key2))
	             return false;
	         key = key2;
	     }
	
	     return true;
	 }
	
	 public boolean checkTree()
	 {
	     boolean flag = true;
	     if(!isInOrder())
	     {
	         System.out.println("The tree is not in order.");
	         flag = false;
	     }
	     if(!root.checkRed())
	     {
	         System.out.println("Some red nodes have red children");
	         flag = false;
	     }
	     if(!checkHeight())
	     {
	         System.out.println("The tree is not balanced in height.");
	         flag = false;
	     }
	     return flag;
	 }


}
