package dsaa.lab07;

import java.util.LinkedList;

public class HashTable{
	LinkedList arr[]; // use pure array
	private final static int defaultInitSize=8;
	private final static double defaultMaxLoadFactor=0.7;
	private final double maxLoadFactor;
	private int added;
	public HashTable() {
		this(defaultInitSize);
	}
	public HashTable(int size) {
		this(size,defaultMaxLoadFactor);
	}


	public HashTable(int initCapacity, double maxLF) {
		// create array of LinkedLists
		arr = new LinkedList[initCapacity];
		added=0;
		this.maxLoadFactor=maxLF;
	}

	public boolean add(Object elem) {
		int hash = elem.hashCode() % arr.length;
		LinkedList list = arr[hash];
		if (list != null && list.contains(elem)) {
			return false;
		}
		if (list == null) {
			list = new LinkedList();
			arr[hash] = list;
		}
		added++;
		list.add(elem);
		double loadFactor = (double) added / arr.length;
		if (loadFactor > maxLoadFactor) {
			doubleArray();
		}
		return true;
	}

	
	private void doubleArray() {
		LinkedList newArr[] = new LinkedList[arr.length * 2];
		for (int i = 0; i < arr.length; i++) {
			LinkedList list = arr[i];
			if (list != null) {
				for (Object elem : list) {
					int hash = elem.hashCode() % newArr.length;
					LinkedList newList = newArr[hash];
					if (newList == null) {
						newList = new LinkedList();
						newArr[hash] = newList;
					}
					newList.add(elem);
				}
			}
		}
		arr = newArr;
	}


	@Override
	public String toString() {
		String res = "";
		for (int i = 0; i < arr.length; i++) {
			res += i + ":";
			LinkedList list = arr[i];
			if (list != null) {
				for (Object elem : list) {
					IWithName elemWithName = (IWithName) elem;
					res += " " + elemWithName.getName() + ",";
				}
				res = res.substring(0, res.length() - 1);
			}
			res += "\n";
		}
		return res;
	}

	public Object get(Object toFind) {
		int hash = toFind.hashCode() % arr.length;
		LinkedList list = arr[hash];
		if (list != null) {
			for (Object elem : list) {
				if (elem.equals(toFind)) {
					return elem;
				}
			}
		}
		return null;
	}
	
}