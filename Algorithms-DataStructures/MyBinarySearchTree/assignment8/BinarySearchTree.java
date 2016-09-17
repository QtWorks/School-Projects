package assignment8;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * A Binary Search Tree used for quickly obtaining sorted information
 * 
 * @authors Joshua Callahan
 *
 * @param <Type>
 *            - Any object that extends Comparable
 */
public class BinarySearchTree<Type extends Comparable<? super Type>> implements
		SortedSet {

	// The root is the node at the top of the tree.
	private BSTNode root;
	private int size = 0;

	public BinarySearchTree() {
		root = null;
	}

	/**
	 * Ensures that this set contains the specified item.
	 * 
	 * @param item
	 *            - the item whose presence is ensured in this set
	 * @return true if this set changed as a result of this method call (that
	 *         is, if the input item was actually inserted); otherwise, returns
	 *         false
	 * @throws NullPointerException
	 *             if the item is null
	 */
	@Override
	public boolean add(Comparable item) {
		if (item == null)
			throw new NullPointerException();
		// A new node is created if the BST is empty.
		if (root == null) {
			root = new BSTNode(item);
			size++;
		}
		// The recursive method is called.
		else if (addRecursive(item, root)) {
			size++;
			return true;
		}
		return false;
	}

	/**
	 * Recursively ensures that this set contains the specified item.
	 * 
	 * @param item
	 *            - the item whose presence is ensured in this set
	 * @param newRoot
	 *            - the node to be examined
	 * @return true if this set changed as a result of this method call (that
	 *         is, if the input item was actually inserted); otherwise, returns
	 *         false
	 * @throws NullPointerException
	 *             if the item is null
	 */
	private boolean addRecursive(Comparable item,
			BinarySearchTree<Type>.BSTNode newRoot) {
		// Return false if the BST already contains the item.
		if (item.compareTo(newRoot.data) == 0)
			return false;
		// Does the new item go to the left or right?
		if (item.compareTo(newRoot.data) < 0) {
			// If we found an empty spot (null), put the item there
			if (newRoot.left == null) {
				newRoot.left = new BSTNode(item);
				return true;
			} else
				// Inspect the deeper layer to the left.
				return addRecursive(item, newRoot.left);
		} else {
			// If we found an empty spot (null), put the item there
			if (newRoot.right == null) {
				newRoot.right = new BSTNode(item);
				return true;
			}

			else
				// Inspect the deeper layer to the right.
				return addRecursive(item, newRoot.right);
		}
	}

	/**
	 * Ensures that this set contains all items in the specified collection.
	 * 
	 * @param items
	 *            - the collection of items whose presence is ensured in this
	 *            set
	 * @return true if this set changed as a result of this method call (that
	 *         is, if any item in the input collection was actually inserted);
	 *         otherwise, returns false
	 * @throws NullPointerException
	 *             if any of the items is null
	 */
	@Override
	public boolean addAll(Collection items) {
		for (Object item : items) {
			if (item == null)
				throw new NullPointerException();
			if (!this.add((Comparable) item))
				return false;
		}
		return true;
	}

	/**
	 * Removes all items from this set. The set will be empty after this method
	 * call.
	 */
	@Override
	public void clear() {
		this.root = null;
		size = 0;
	}

	/**
	 * Determines if there is an item in this set that is equal to the specified
	 * item.
	 * 
	 * @param item
	 *            - the item sought in this set
	 * @return true if there is an item in this set that is equal to the input
	 *         item; otherwise, returns false
	 * @throws NullPointerException
	 *             if the item is null
	 */
	@Override
	public boolean contains(Comparable item) {
		if (item == null)
			throw new NullPointerException();
		// Check the root node
		if (root.data.compareTo(item) == 0)
			return true;
		else
			return containsRecursive(item, root);
	}

	/**
	 * Recursively determines if there is an item in this set that is equal to
	 * the specified item.
	 * 
	 * @param item
	 *            - the item sought in this set
	 * @param newRoot
	 *            - the next node to be inspected
	 * @return true if there is an item in this set that is equal to the input
	 *         item; otherwise, returns false
	 */
	private boolean containsRecursive(Comparable item,
			BinarySearchTree<Type>.BSTNode newRoot) {

		if (item.compareTo(newRoot.data) == 0)
			return true;
		if (item.compareTo(newRoot.data) < 0) {
			// If we found an empty spot (null), the item is not contained in
			// the BST
			if (newRoot.left == null)
				return false;
			else
				return containsRecursive(item, newRoot.left);
		} else {
			// If we found an empty spot (null), the item is not contained in
			// the BST
			if (newRoot.right == null)
				return false;
			else
				return containsRecursive(item, newRoot.right);
		}
	}

	/**
	 * Determines if for each item in the specified collection, there is an item
	 * in this set that is equal to it.
	 * 
	 * @param items
	 *            - the collection of items sought in this set
	 * @return true if for each item in the specified collection, there is an
	 *         item in this set that is equal to it; otherwise, returns false
	 * @throws NullPointerException
	 *             if any of the items is null
	 */
	@Override
	public boolean containsAll(Collection items) {
		for (Object item : items) {
			if (item == null)
				throw new NullPointerException();
			if (!this.contains((Comparable) item))
				return false;
		}
		return true;
	}

	/**
	 * Returns the first (i.e., smallest) item in this set.
	 * 
	 * @throws NoSuchElementException
	 *             if the set is empty
	 */
	@Override
	public Comparable first() throws NoSuchElementException {
		if (isEmpty())
			throw new NoSuchElementException();
		return (getLeast(root)).data;
	}

	/**
	 * Returns true if this set contains no items.
	 */
	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Returns the last (i.e., largest) item in this set.
	 * 
	 * @throws NoSuchElementException
	 *             if the set is empty
	 */
	@Override
	public Comparable last() throws NoSuchElementException {
		if (isEmpty())
			throw new NoSuchElementException();
		return getGreatest(root).data;
	}

	/**
	 * Helper method to get the leftmost leaf from a node.
	 * 
	 * @param b
	 *            Node
	 * @return least Node
	 */
	public BSTNode getLeast(BSTNode b) {
		if (b.left == null) {
			return b;
		} else {
			return getLeast(b.left);
		}
	}

	/**
	 * Helper method to get the rightmost leaf from a node.
	 * 
	 * @param b
	 *            Node
	 * @return greatest Node
	 */
	public BSTNode getGreatest(BSTNode b) {
		if (b.right == null)
			return b;
		else {
			return getGreatest(b.right);
		}
	}

	/**
	 * Ensures that this set does not contain the specified item.
	 * 
	 * @param item
	 *            - the item whose absence is ensured in this set
	 * @return true if this set changed as a result of this method call (that
	 *         is, if the input item was actually removed); otherwise, returns
	 *         false
	 * @throws NullPointerException
	 *             if the item is null
	 */
	@Override
	public boolean remove(Comparable item) {
		// Removes the root if it is the only element in the BST
		if (root.right == null && root.left == null) {
			clear();
			return true;
		} else if (item == null)
			throw new NullPointerException();

		else {
			// Begin searching for the element to be removed recursively
			BSTNode temp = removeRecursive(item, root);
			// Remove the root if necessary
			if (item.equals(root.data)) {
				root = temp;
			}
			size--;
			return true;
		}
	}

	/**
	 * Recursively removes the specified item from the BST
	 * 
	 * @param item
	 *            - the item whose absence is ensured in this set
	 * @return true if this set changed as a result of this method call (that
	 *         is, if the input item was actually removed); otherwise, returns
	 *         false
	 * @throws NullPointerException
	 *             if the item is null
	 */
	private BSTNode removeRecursive(Comparable item,
			BinarySearchTree<Type>.BSTNode parent) {

		// Parent of tree
		if (item.compareTo(parent.data) == 0) {
			// The item has been located. Various situations are dealt with.
			if (parent.isLeaf()) {
				parent = null;
			} else if (parent.hasOnlyLeftChild()) {
				if (parent.data.compareTo(root.data) == 0) {
					parent = parent.left;
				}
			} else if (parent.hasOnlyRightChild()) {
				if (parent.data.compareTo(root.data) == 0)
					parent = parent.right;
			} else {
				twoChildRemoval(parent);
			}

		} else if (item.compareTo(parent.data) < 0) {
			// The item has been found in the Left Child
			if (parent.left != null) {
				if (item.compareTo(parent.left.data) == 0) {
					if (parent.left.isLeaf()) {
						parent.left = null;
					} else if (parent.left.hasOnlyLeftChild()) {
						parent.left = parent.left.left;
					} else if (parent.left.hasOnlyRightChild()) {
						parent.left = parent.left.right;
					} else {
						twoChildRemoval(parent.left);
					}
				}
				// The item has been found in the Left Left Grandchild
				else if (parent.left.left != null
						&& item.compareTo(parent.left.left.data) == 0) {
					if (parent.left.left.isLeaf()) {
						parent.left.left = null;
					} else if (parent.left.left.hasOnlyLeftChild()) {
						parent.left.left = parent.left.left.left;
					} else if (parent.left.left.hasOnlyRightChild()) {
						parent.left.left = parent.left.right;
					} else {
						twoChildRemoval(parent.left.left);
					}
				}
				// The item has been found in the Left Right Grandchild
				else if (parent.left.right != null
						&& item.compareTo(parent.left.right.data) == 0) {
					if (parent.left.right.isLeaf()) {
						parent.left.right = null;
					} else if (parent.left.right.hasOnlyLeftChild()) {
						parent.left.right = parent.left.right.left;
					} else if (parent.hasOnlyRightChild()) {
						parent.left.right = parent.left.right.right;
					} else {
						twoChildRemoval(parent.left.right);
					}
				} else {
					return removeRecursive(item, parent.left);
				}
			} else {
				return null;
			}

		} else {
			// The item has been found in the Right Child
			if (parent.right != null) {
				if (item.compareTo(parent.right.data) == 0) {
					if (parent.right.isLeaf()) {
						parent.right = null;
					} else if (parent.right.hasOnlyLeftChild()) {
						parent.right = parent.right.left;
					} else if (parent.right.hasOnlyRightChild()) {
						parent.right = parent.right.right;
					} else {
						twoChildRemoval(parent.right);
					}
				}
				// The item has been found in the Right Left Grandchild
				else if (parent.right.left != null
						&& item.compareTo(parent.right.left.data) == 0) {
					if (parent.right.left.isLeaf()) {
						parent.right.left = null;
					} else if (parent.right.left.hasOnlyLeftChild()) {
						parent.right.left = parent.right.left.left;
					} else if (parent.right.left.hasOnlyRightChild()) {
						parent.right.left = parent.right.left.right;
					} else {
						twoChildRemoval(parent.right.left);
					}
				}
				// The item has been found in the Right Right Grandchild
				else if (parent.right.right != null
						&& item.compareTo(parent.right.right.data) == 0) {
					if (parent.right.right.isLeaf()) {
						parent.right.right = null;
					} else if (parent.right.right.hasOnlyLeftChild()) {
						parent.right.right = parent.right.right.left;
					} else if (parent.right.right.hasOnlyRightChild()) {
						parent.right.right = parent.right.right.right;
					} else {
						twoChildRemoval(parent.right.right);
					}
				} else {
					return removeRecursive(item, parent.right);
				}

			} else {
				return null;
			}

		}

		return parent;
	}

	/**
	 * A helper method to recursively remove a Node with two children.
	 * 
	 * @param parent
	 *            Node
	 */
	private void twoChildRemoval(BSTNode parent) {
		BSTNode temp = getLeast(parent.right);
		parent.data = temp.data;
		parent.right = removeRecursive(temp.data, parent.right);
	}

	/**
	 * Ensures that this set does not contain any of the items in the specified
	 * collection.
	 * 
	 * @param items
	 *            - the collection of items whose absence is ensured in this set
	 * @return true if this set changed as a result of this method call (that
	 *         is, if any item in the input collection was actually removed);
	 *         otherwise, returns false
	 * @throws NullPointerException
	 *             if any of the items is null
	 */
	@Override
	public boolean removeAll(Collection items) {
		// TODO Auto-generated method stub
		for (Object item : items) {
			if (item == null)
				throw new NullPointerException();
			if (!this.remove((Comparable) item))
				return false;
		}
		return true;
	}

	/**
	 * Returns the number of items in this set.
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Returns an ArrayList containing all of the items in this set, in sorted
	 * order.
	 */
	@Override
	public ArrayList toArrayList() {
		ArrayList arr = new ArrayList();
		toArrayListRecursive(arr, root);
		return arr;
	}

	/**
	 * Recursively adds element data to an array in order.
	 * 
	 * @param arr
	 *            ArrayList to add items to
	 * @param temp
	 *            Node to inspect
	 */
	private void toArrayListRecursive(ArrayList arr,
			BinarySearchTree<Type>.BSTNode temp) {
		if (temp != null) {
			// Inspect left first.
			toArrayListRecursive(arr, temp.left);
			// Next add item to the array.
			arr.add(temp.data);
			// Finally inspect right.
			toArrayListRecursive(arr, temp.right);
		} else {
			return;
		}
	}

	/**
	 * Helper driver method to find data in a BST
	 * 
	 * @param item
	 *            to search for
	 * @return Node containing item
	 */
	public BSTNode find(Comparable item) {
		if (item.compareTo(root) == 0) {
			return root;
		} else {
			return findRecursive(item, root);
		}
	}

	/**
	 * Recursive helper method to find data in a BST
	 * 
	 * @param item
	 *            to search for
	 * @param parent
	 *            Node to search
	 * @return Node containing item
	 */
	private BSTNode findRecursive(Comparable item, BSTNode parent) {
		int cmp = item.compareTo(parent);
		if (cmp == 0) {
			return parent;
		} else if (cmp < 0) {
			return findRecursive(item, parent.left);
		} else if (cmp > 0) {
			return findRecursive(item, parent.right);
		} else {
			return null;
		}

	}

	/**
	 * Driver for writing this tree to a dot file
	 * 
	 * @param filename
	 *            to write
	 */
	public void writeDot(String filename) {
		try {
			// PrintWriter(FileWriter) will write output to a file
			PrintWriter output = new PrintWriter(new FileWriter(filename));

			// Set up the dot graph and properties
			output.println("digraph BST {");
			output.println("node [shape=record]");

			if (root != null)
				writeDotRecursive(root, output);
			// Close the graph
			output.println("}");
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Recursive method for writing the tree to a dot file
	 * 
	 * @param n
	 *            Node to inspect
	 * @param output
	 * @throws Exception
	 */
	private void writeDotRecursive(BSTNode n, PrintWriter output)
			throws Exception {
		output.println(n.data + "[label=\"<L> |<D> " + n.data + "|<R> \"]");
		if (n.left != null) {
			// write the left subtree
			writeDotRecursive(n.left, output);

			// then make a link between n and the left subtree
			output.println(n.data + ":L -> " + n.left.data + ":D");
		}
		if (n.right != null) {
			// write the left subtree
			writeDotRecursive(n.right, output);

			// then make a link between n and the right subtree
			output.println(n.data + ":R -> " + n.right.data + ":D");
		}

	}

	/**
	 * Returns the root Node's data
	 * 
	 * @return the root's data
	 */
	public Comparable getRoot() {
		return root.data;
	}

	/**
	 * A nested class for holding sets of data. Nodes contain pointers to right
	 * and left Nodes.
	 */
	class BSTNode {
		Comparable data;
		BSTNode left;
		BSTNode right;

		/**
		 * A constructor for nodes that contain item C
		 * 
		 * @param c
		 *            data
		 */
		public BSTNode(Comparable c) {
			data = c;
			left = null;
			right = null;
		}

		/**
		 * Returns the data from the elemnt in string form.
		 * @return String data
		 */
		@Override
		public String toString() {
			return data.toString();
		}
		/**
		 * Helper method for determining if a Node is a leaf
		 * @return true if so
		 */
		private boolean isLeaf() {
			return (this.right == null && this.left == null);
		}
		/**
		 * Helper method for determining if a Node has only a left child
		 * @return true if so
		 */
		private boolean hasOnlyLeftChild() {
			return (this.right == null && this.left != null);
		}
		/**
		 * Helper method for determining if a Node has only a right child
		 * @return true if so
		 */
		private boolean hasOnlyRightChild() {
			return (this.left == null && this.right != null);
		}

	}
}
