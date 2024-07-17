package dsaa.lab11;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
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
		arrDoc = (Map.Entry<String, Document>[])new Map.Entry[size];; //initialize the array for index to document mapping
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
	
	public String DijkstraSSSP(String startVertexStr) {
	    //check if the starting vertex exists in the graph
	    if (!name2Int.containsKey(startVertexStr)) {
	        return "error\n";//error if is not found
	    }

	    int startVertex = name2Int.get(startVertexStr);//retrieve the integer index of the starting vertex
	    int size = arr.length; //number of vertices in the graph

	    //qrrays to store distances and predecessors
	    int[] distance = new int[size]; //distance from start vertex to each vertex
	    int[] previous = new int[size];//previous vertex on the shortest path
	    boolean[] visited = new boolean[size]; //initialize visited array

	    //initialize all distances to infinity
	    Arrays.fill(distance, Integer.MAX_VALUE);
	    Arrays.fill(previous, -1); //Initialize previous array with -1 (indicating no predecessor)

	    //set distance of starting vertex to 0
	    distance[startVertex] = 0;

	    //iterate through all vertices at most size-1 times (relaxation steps)
	    for (int i = 0; i < size - 1; i++) {
	        int minIndex = -1;
	        int minDistance = Integer.MAX_VALUE;

	        //find the closest unvisited vertex
	        for (int j = 0; j < size; j++) {
	            if (!visited[j] && distance[j] < minDistance) {
	                minDistance = distance[j];
	                minIndex = j;
	            }
	        }

	        //if no unvisited vertex is found, exit the loop
	        if (minIndex == -1) {
	            break;
	        }

	        //mark the minimum distance vertex as visited
	        visited[minIndex] = true;

	        // update distances of adjacent vertices
	        for (int j = 0; j < size; j++) {
	            if (arr[minIndex][j] != Integer.MAX_VALUE && !visited[j]) { // Check for valid edge
	                int newDistance = distance[minIndex] + arr[minIndex][j];
	                if (newDistance < distance[j]) {
	                    distance[j] = newDistance;
	                    previous[j] = minIndex;
	                }
	            }
	        }
	    }

	    // Build the result string
	    StringBuilder result = new StringBuilder();
	    for (int i = 0; i < size; i++) {
	    	//no path found to this vertex
	        if (distance[i] == Integer.MAX_VALUE) {
	            result.append("no path to ").append(arrDoc[i].getKey()).append("\n");
	        } else {
	        	//construct the path and append the total distance
	            result.append(constructPath(i, previous)).append("=").append(distance[i]).append("\n");
	        }
	    }
	    return result.toString();
	}

	private String constructPath(int vertex, int[] previous) {
	    StringBuilder path = new StringBuilder();
	    int current = vertex;
	    while (current != -1) {
	        path.insert(0, arrDoc[current].getKey() + "->");
	        current = previous[current]; //move to the previous vertex on the path
	    }
	    // remove the trailing "->"
	    path.setLength(path.length() - 2);
	    return path.toString();
	}
}