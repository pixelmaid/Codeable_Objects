package com.datastruct.voronoi;

import com.datastruct.Node;
import com.math.CompPoint;

public class VorNode<E extends Comparable<? super E>> extends Node {
	public VorNode<E> left;
    public VorNode<E> right;
    public VorNode<E> parent;
    public String name ="blah";
    
	VorNode(Comparable value, VorNode parent, VorTree _parentTree,
			int _level) {
		super(value, parent, _parentTree, _level);
		this.value = value;
 		this.parent= parent;
 		this.parentTree = _parentTree;
 		this.level = _level;
 		
		
		// TODO Auto-generated constructor stub
	}
	
	

	


	
	//searches for matching arc to compPoint
	public VorNode<E> searchArc(CompPoint _value) {
		CompPoint value = (CompPoint)this.value;
		this.parentTree.myParent.print("value=");
		this.parentTree.myParent.println(_value);
		if (value.compareTo(_value)==0)
              return this;
        else if (value.compareTo(_value)==1) {
              if (left == null)
                    return null;
              else
                    return left.searchArc(_value);
        } else if (value.compareTo(_value)==-1) {
              if (right == null)
                    return null;
              else
                    return right.searchArc(_value);
        }
        return null;
  }
}
