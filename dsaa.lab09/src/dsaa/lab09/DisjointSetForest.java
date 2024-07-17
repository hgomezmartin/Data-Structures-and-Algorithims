package dsaa.lab09;

public class DisjointSetForest implements DisjointSetDataStructure {
	
	private class Element{
		int rank;
		int parent;
	}

	Element []arr;
	
	public DisjointSetForest(int size) {
		//TODO
		arr = new Element[size];
	}
	
	@Override
	public void makeSet(int item) {
		//TODO
		Element e = new Element(); //create element
        e.parent = item; //add item to parent
        e.rank = 0; //establish rank 0
        arr[item] = e; //add to the array 
		
	}
	
	private void link(int x, int y){ //link or uite 2 sets
		Element eX = arr[x]; //get elemtn x
	    Element eY = arr[y]; //get element y
	    if (eX.rank > eY.rank) 
	        eY.parent = x; //make x parent of y
	    else{
	        eX.parent = y; //make y parent of x
	        if (eX.rank == eY.rank) //if equal 
	            eY.rank++; //increment rank of y
	        }
	    }

	@Override
	public int findSet(int item) {
		//TODO
		Element e = arr[item]; //get elemnt at index item
        if (item != e.parent) //if item is not its own parent
            e.parent = findSet(e.parent); //rescursuve finde the root
        
        return e.parent; //return root of the set
	}

	@Override
	public boolean union(int itemA, int itemB) {
		//TODO
		int setX = findSet(itemA); //find root of the set containing itema
        int setY = findSet(itemB); //find root of the set containing itemb
        if (setX == setY){ //if are in the same set
            return false; //no union is performed
        }
        link(setX, setY); //link the two sets 
        return true; //return true union successful
	}

	
	@Override
	public String toString() {
		//TODO
		StringBuilder result = new StringBuilder("Disjoint sets as forest:\n");
        for (int i = 0; i < arr.length; i++){
            Element e = arr[i];
            result.append(i).append(" -> ").append(e.parent);
            if (i < arr.length - 1)
                result.append("\n");
        }
        return result.toString();
	}
}
