package dsaa.lab10;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.Stack;

public class Graph {
	int arr[][]; // 2D array to represent the adjacency matrix of the graph
	// TODO? Collection to map Document to index of vertex
	// TODO? You can change it
	HashMap<String, Integer> name2Int;
	@SuppressWarnings("unchecked")
	// TODO? Collection to map index of vertex to Document
	// TODO? You can change it
	Entry<String, Document>[] arrDoc;

	//TODO The argument type depend on a selected collection in the Main class
	public Graph(SortedMap<String, Document> internet) {
		int size = internet.size(); //number of documents
		arr = new int[size][size]; //initialize the adjency matrix
		name2Int = new HashMap<>(); 
		arrDoc = (Map.Entry<String, Document>[]) new Map.Entry[size]; //initialize the array for index to document mapping
		// TODO

		int index = 0; //asign indices to each document
		for (Map.Entry<String, Document> entry : internet.entrySet()) {
			name2Int.put(entry.getKey(), index);
			arrDoc[index] = entry;
			index++;
		}

		//initialize the adjacency matrix with default values
		for (int i = 0; i < size; i++) {
			Arrays.fill(arr[i], Integer.MAX_VALUE); //set to infinite
			arr[i][i] = 0; //0 for the same vlue = 0 (diagonal 0 for adjacency matrix)
		}

		//fill the adjacency matrix with weights from the links in each document
		for (int i = 0; i < size; i++) {
			Document doc = arrDoc[i].getValue(); //get doc with index i
			for (Link link : doc.link.values()) {
				Integer linkIndex = name2Int.get(link.ref); //get the index of the linked document
				if (linkIndex != null) {
					arr[i][linkIndex] = link.weight; //set the weight in the adjacency matrix
				}
			}
		}
	}

	public String bfs(String start) {
		// TODO
		Integer startIndex = name2Int.get(start); //get start index from the document name
		if (startIndex == null)
			return null;

		boolean[] visited = new boolean[arr.length]; //track visited vertices
		Queue<Integer> queue = new LinkedList<>(); //queue for the bfs
		StringBuilder result = new StringBuilder(); //store the result of the traversal

		queue.add(startIndex); //add the start index to queue
		visited[startIndex] = true; //mark the start index as visited

		while (!queue.isEmpty()) {
			int current = queue.poll(); //dequeue a vertex
			result.append(arrDoc[current].getKey()).append(", "); //add vertex to the result
			for (int i = 0; i < arr.length; i++) {
				if (arr[current][i] != Integer.MAX_VALUE && !visited[i]) { //check for unxisited neighbors
					visited[i] = true; //mark neighbor
					queue.add(i); //enqueue the neighbor
				}
			}
		}
		if (result.length() > 0)
			result.setLength(result.length() - 2); //remove , and space
		return result.toString(); //return bfs result
	}

	public String dfs(String start) {
		// TODO
		Integer startIndex = name2Int.get(start); //get start index from the document name
		if (startIndex == null)
			return null;

		boolean[] visited = new boolean[arr.length]; //trak visited vertices
		Stack<Integer> stack = new Stack<>(); //stack for dfs
		StringBuilder result = new StringBuilder(); //store the result of the traversal

		stack.push(startIndex); //push the start index onto the stack
		while (!stack.isEmpty()) {
			int current = stack.pop(); //pop a vertex
			if (!visited[current]) {
				visited[current] = true; //mark vertex as visited
				result.append(arrDoc[current].getKey()).append(", "); //add the vertex to the result
				for (int i = arr.length - 1; i >= 0; i--) {
					if (arr[current][i] != Integer.MAX_VALUE && !visited[i]) { //check for unvisited neighbors
						stack.push(i); //push the neighbor onto the stack
					}
				}
			}
		}
		if (result.length() > 0)
			result.setLength(result.length() - 2); //remove the trailing , and space
		return result.toString(); //return the neighbor onto the stack
	}

	//count the number of conected components in graph 
	public int connectedComponents() {
		// TODO
		DisjointSetForest dsf = new DisjointSetForest(arr.length); //initialized disjoint set forest
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length; j++) {
				if (arr[i][j] != Integer.MAX_VALUE && i != j) { //check for connecterd vertices
					dsf.union(i, j); //union the sets of the connected vertices
				}
			}
		}
		return dsf.countSets(); //return the number of disjoin sets (connected components)
	}
}
