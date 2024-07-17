package dsaa.lab08;

import java.util.LinkedList;

public class HashTable{
	LinkedList arr[]; 
	private final static int defaultInitSize=8;
	private final static double defaultMaxLoadFactor=0.7;
	private int size;	
	private final double maxLoadFactor;
	private int added;
	public HashTable() {
		this(defaultInitSize);
	}
	public HashTable(int size) {
		this(size,defaultMaxLoadFactor);
	}


	public HashTable(int initCapacity, double maxLF) {
		
		if(initCapacity<2)
			initCapacity=2;
		arr=new LinkedList[initCapacity];
		//TODO
		maxLoadFactor=maxLF;
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

	private int hash(String name) {
        return Math.abs(name.hashCode() % arr.length);
    }
	
	@Override
	public String toString() {
		//TODO
		StringBuilder sb = new StringBuilder();
        for (LinkedList<Document> list : arr) {
            if (list != null) {
                for (Document doc : list) {
                    sb.append(doc.toString()).append("\n");
                }
            }
        }
        return sb.toString();
	}

	public Object get(Object toFind) {
		//TODO
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

