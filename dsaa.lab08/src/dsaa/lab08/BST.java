package dsaa.lab08;

import java.util.NoSuchElementException;

public class BST<T extends Comparable<T>> {
	private class Node {
		T value;
		Node left, right, parent;

		public Node(T v) {
			value = v;
		}

		public Node(T value, Node left, Node right, Node parent) {
			super();
			this.value = value;
			this.left = left;
			this.right = right;
			this.parent = parent;
		}
	}

	private Node root = null;
	private int size = 0;

	public BST() {
		size = 0;
	}

	public T getElement(T toFind) { //find an element in the BST
		// TODO
		Node current = root; //from root
		while (current != null) { //if still not null
			int cmp = toFind.compareTo(current.value); //compare the value to the elemnt to find
			if (cmp > 0) current = current.right; //if tofind is greater, move to the right
			else if (cmp < 0) current = current.left; //if tofind is smaller, move to the left
			else return current.value; //if is equal, it is!, return the value
		}
		return null;
	}

	public T successor(T elem) { //find the inorder successor of a given elmnt
		// TODO
		Node current = root; //start from root
		T successor = null; 
		while (current != null) { //if still not null
			int cmp = elem.compareTo(current.value); //compare the elmnt with the current nodes value
			if (cmp < 0) { //if is less that the current nodes value
				if (successor == null) { //if sucessor is not set
					successor = current.value; //set the current nodes value as sucessor
				}
				int cmp_successor_to_current = ((Comparable<T>) successor).compareTo(current.value);//compare the succesor with current node value
				if (cmp_successor_to_current > 0) successor = current.value; //if sucessor is greater (bigger) updte the sucessor
				current = current.left; //move to the left child
			}
			else { //if is greater (bigger) or equal to the current node value
				current = current.right; //move to the right child
			}
		}
		return successor; //return sucessor
	}

	//in-order
	public String toStringInOrder() { //to get the BST as a string in in-order traversal
		// TODO
		String ret = toStringInOrder(root); //get in-order string from root
		if (ret == "") return ""; //if result is empry, return an empty string
		return ret.substring(0, ret.length() - 2); //remove the comma and space
	}
	
	private String toStringInOrder(Node node) { //helper in-order traversal
		if (node == null) return ""; //if node null, return an empty string
		return toStringInOrder(node.left) + node.value + ", " + toStringInOrder(node.right); //traverse left, visit node, then right
	}

	//pre-order
	public String toStringPreOrder() { //to get the BST as a string in pre-order traversal
		// TODO
		String ret = toStringPreOrder(root); //get pre-order string from the root
		if (ret == "") return ""; //if result is empty return empty string
		return ret.substring(0, ret.length() - 2);  //remove comma and space
	}
	
	private String toStringPreOrder(Node node) { //helper pre-order traversal
		if (node == null) return ""; //if node null, return empty string
		return node.value + ", " + toStringPreOrder(node.left) + toStringPreOrder(node.right); // visit node, traverse left, then right
	}

	//post-order
	public String toStringPostOrder() { //to get the BST as a string in post-order traversal
		// TODO
		String ret = toStringPostOrder(root);  //get post-order string from the root
		if (ret == "") return ""; //if result is empty return empty string
		return ret.substring(0, ret.length() - 2); //remove comma and space
	}
	
	private String toStringPostOrder(Node node) { //helper post-order traversal
		if (node == null) return ""; //if node is null return empty string
		return toStringPostOrder(node.left) + toStringPostOrder(node.right) + node.value + ", "; //traverse left, right, then visit node
	}

	public boolean add(T elem) {
		// TODO
		if (root == null) { //is tree empty
            root = new Node(elem); //create a new root node
            size++; //size incremented by one
            return true; //true to idicate that the eement is added
        }
        Node current = root; //start from root node
        Node parent = null;
        int cmp; //variable for comparison
        while (current != null) { //until we reach null
            parent = current; //update parent node
            cmp = elem.compareTo(current.value); //compare elem wuth the current node value
            if (cmp == 0) { //if is equal to the nodes value
                return false; // Element already exists in the tree
            } else if (cmp < 0) { //if is less than current node value
                current = current.left; //move to left
            } else { //if is greater (bigger) than current node
                current = current.right; //moce to right
            } 
        }
        // Create and insert the new node
        Node newNode = new Node(elem); //create new node with elemnt
        newNode.parent = parent; //set the parent of the new node
        if (elem.compareTo(parent.value) < 0) { //if elem is less than parent value
            parent.left = newNode; //set new node as left child 
        } else { //if elem is greater than parent value
            parent.right = newNode; //set new node as right child
        }
        size++; //increment the size by one
        return true; //true if was added
	}

	public T remove(T value) { //to remove a element from the BST
		// TODO
		Node nodeToRemove = findNode(root, value); //find the node to remove
	    if (nodeToRemove == null) { //if is null
	        return null; // Element not found
	    }
	    T removedValue = nodeToRemove.value; //store the value to be removed
	    if (nodeToRemove.left == null && nodeToRemove.right == null) { //if has no childs
	        deleteNodeWithNoChildren(nodeToRemove); //call method to delete a node with no childs
	    } else if (nodeToRemove.left == null || nodeToRemove.right == null) { //if has one child
	        deleteNodeWithOneChild(nodeToRemove); //call the method to delete a node with one child
	    } else { //if has two childs
	        deleteNodeWithTwoChildren(nodeToRemove); //call the method to delete a node with two childs
	    }
	    size--; //decrease the size by one
	    return removedValue;
	}

	private void deleteNodeWithNoChildren(Node node) { //deletes node with no childs
	    if (node == root) { //if the node is the root
	        root = null; //set the root to null
	    } else if (node == node.parent.left) { //if the node is left child
	        node.parent.left = null; //set parents left child to null
	    } else { //id is tight child
	        node.parent.right = null; //parent right child to null
	    }
	}


	private void deleteNodeWithOneChild(Node node) { // delete a node with one child
	    Node child = (node.left != null) ? node.left : node.right; //get the non null child
	    if (node == root) { //if the node is the root
	        root = child; //set child as the new root
	    } else if (node == node.parent.left) { //if he node is the left child
	        node.parent.left = child; //set the parents left child to the nodes child
	    } else { //if the node is the right child
	        node.parent.right = child; //set the parentd right child to the nodes child
	    }
	    if (child != null) { //if child is not null
	        child.parent = node.parent; //set the childs partent to the nodes parent
	    }
	}
    
	private void deleteNodeWithTwoChildren(Node node) { //method to delete a node with two children
	    Node successor = findSuccessor(node); //find the in-order successor
	    node.value = successor.value; //replace the nodes value with the sucessors value
	    if (successor == successor.parent.left) { //if sucessor is the left chuild
	        successor.parent.left = successor.right; //set the partents left child to the sucessors right child
	    } else { //if the sucessor is the right child
	        successor.parent.right = successor.right; //set the parent right child to the sucessor right child
	    }
	    if (successor.right != null) { //if the sucessor right child is not null
	        successor.right.parent = successor.parent; // Set the right child's parent to the successor's parent
	    }
	}

	private Node findSuccessor(Node node) { //method to find a sucessor of a node 
	    Node current = node.right; //start drom right child
	    while (current.left != null) { //loop to find the leftmost node
	        current = current.left; //move to left child
	    }
	    return current; //trturn sucessor
	}
	
	private Node findNode(Node node, T value) { //method to find with specific value
	    if (node == null || node.value.equals(value)) { //if the node is null or the value matches
	        return node; //return node
	    }
	    if (((Comparable<T>) value).compareTo(node.value) < 0) { //if value is less than nodes value
	        return findNode(node.left, value); //recursively search in the left subtree
	    } else { //if value is greater that or equal to the nodes value
	        return findNode(node.right, value); //recursively search in the right subtree
	    }
	}
	
    
	public void clear() {
		// TODO
		root = null; //root to null & size 0 (clear the BST)
		size = 0;
	}

	public int size() {
		// TODO
		return size;
	}

}
