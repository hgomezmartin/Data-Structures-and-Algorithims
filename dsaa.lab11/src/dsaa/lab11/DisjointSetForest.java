package dsaa.lab11;

import java.util.HashSet;
import java.util.Set;

public class DisjointSetForest implements DisjointSetDataStructure {

	class Element {
		int rank;
		int parent;

		Element(int parent) {
			this.rank = 0;
			this.parent = parent;
		}
	}

	Element[] arr;

	public DisjointSetForest(int size) {
		arr = new Element[size];
		for (int i = 0; i < size; i++) {
			arr[i] = new Element(i);
		}
	}

	@Override
	public void makeSet(int item) {
		// TODO
		arr[item] = new Element(item);
	}

	@Override
	public int findSet(int item) {
		// TODO
		if (arr[item].parent != item) {
			arr[item].parent = findSet(arr[item].parent);
		}
		return arr[item].parent;
	}

	@Override
	public boolean union(int itemA, int itemB) {
		// TODO
		int rootA = findSet(itemA);
		int rootB = findSet(itemB);
		if (rootA != rootB) {
			if (arr[rootA].rank > arr[rootB].rank) {
				arr[rootB].parent = rootA;
			} else if (arr[rootA].rank < arr[rootB].rank) {
				arr[rootA].parent = rootB;
			} else {
				arr[rootB].parent = rootA;
				arr[rootA].rank++;
			}
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		// TODO
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			sb.append(i).append(" -> ").append(arr[i].parent).append("\n");
		}
			return sb.toString();
	}

	@Override
	public int countSets() {
		// TODO
		Set<Integer> uniqueParents = new HashSet<>();
        for (Element e : arr) {
            uniqueParents.add(findSet(e.parent));
        }
        return uniqueParents.size();
	}
}