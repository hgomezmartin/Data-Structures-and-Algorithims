package dsaa.lab09;

import java.util.HashSet;
import java.util.Set;

public class DisjointSetLinkedList implements DisjointSetDataStructure {

	private class Element{
		Element representant;
        Element next; //next in list
        Element last;
        int length; //store the lenght of the list
        int value; //vslue of element
	}
	
	private static final int NULL=-1; //define constant null that is -1
	
	Element elements[]; 
	
	public DisjointSetLinkedList(int size) {
		//TODO
		elements = new Element[size]; //initialize the array of elementd with the specified size
	}
	
	@Override
	public void makeSet(int item) {
		//TODO
		Element newNode = new Element(); //new element 
        newNode.representant = newNode; //set the representative of the new node to itself
        newNode.next = null; //next pointer to itself 
        newNode.length = 1; //initialize the length to 1
        newNode.last = newNode; //set last pointer to new node
        newNode.value = item; //set value of new node to the item
        elements[item] = newNode; //store new node in the elements array at the index item
	}

	@Override
	public int findSet(int item) {
		//TODO
		return elements[item].representant.value; //return value of representative of the element at index item
	}

	@Override
	public boolean union(int itemA, int itemB) { //to unite the sets
		//TODO
		Element repA = elements[itemA].representant; //get representative of a
        Element repB = elements[itemB].representant;
        
        if (repA.value == repB.value) //are in the same set
            return false; //no union
        
        if (repB.length > repA.length) { //length b is greater than a
            Element tmp = repA; //swap to keep the smaller set under the larger one
            repA = repB;
            repB = tmp;
        }

        Element lastX = repA.last; //get last element of set a
        lastX.next = repB; //link the last element of set A to the first element of set b
        repA.last = repB.last; //update last element of set a to be the last element of set b
        
        while (repB != null) { // Traverse through all elements of set B
            repA.length++; //increment length of set a
            repB.representant = repA; //set the representative of each element in set b to repA
            repB = repB.next; //move to next element in set b
        }
        
        return true; //true if union is sucessfull
	}

	
	@Override
	public String toString() {
		//TODO
		StringBuilder result = new StringBuilder("Disjoint sets as linked list:\n");
        Set<Integer> visited = new HashSet<>(); //visited representaatives (keep track)
        for (int i = 0; i < elements.length; i++){
            Element rep = elements[i].representant; //get the reperesentative of the actual
            if (!visited.contains(rep.value)){ //if not visited
                visited.add(rep.value); //add to the visited
                while (rep != null){ //we traverse through all the elements of the set
                    result.append(rep.value); //append value of current element to result string
                    rep = rep.next; //move to the next element in the list
                    if (rep != null) //is is another elmnt
                        result.append(", "); //aopend a , and space
                }
                if (i < elements.length - 2) // if is not the last elemnt
                    result.append("\n"); //add a newline char
            }
        }
        return result.toString(); // result string
    }

}
