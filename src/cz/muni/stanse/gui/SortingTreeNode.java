package cz.muni.stanse.gui;

import java.util.Collections;
import java.util.Comparator;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * This tree node will sort all its children according to provided comparator
 * 
 * @author radim cebis
 */
class SortingTreeNode<T> extends DefaultMutableTreeNode  {
	private final Comparator<T> comparator;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SortingTreeNode(Comparator<T> comparator) {
		this.comparator = comparator;
	}
	
	public SortingTreeNode(Object data, Comparator<T> comparator) {
		super(data);
		this.comparator = comparator;
	}
	
	@SuppressWarnings("unchecked")
	public void sort() {
		if(this.children != null)
			Collections.sort(this.children, comparator);
		
	}
			    
}