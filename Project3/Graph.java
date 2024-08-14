import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;


public class Graph {
	
	public Node[] V;
	public List<ArrayList<Integer>> adj; // adjacency matrix with nodeID
	public int numNodes;
	public Map<Integer, Node> nodeMap;
	public Region region;
	
	public Graph(Region region) {
		this.region = region;
		nodeMap = new HashMap<Integer, Node>();
		numNodes = region.intersectionMap.size();
		V = new Node[numNodes];
		adj = new ArrayList<ArrayList<Integer>>(numNodes);
	}

	
	public void buildGraph() {		
		int i = 0;
		Node node;
		Intersection intersection,intersection1,intersection2;
		
		// build nodeMap by creating new node and connecting Intersection
		for(String key: region.intersectionMap.keySet()) {
			node = new Node(i);
			intersection = region.intersectionMap.get(key);
			node.intersection = intersection;
			intersection.nodeID = i;
			V[i] = node;
			nodeMap.put(i, node);
			i++;
		}
		
		buildAdjacency();
		
	}
	
	public double getWeight(int node1ID, int node2ID) {
		Node node1 = nodeMap.get(node1ID);
		Node node2 = nodeMap.get(node2ID);
		
		Intersection inter1 = node1.intersection;
		Intersection inter2 = node2.intersection;
		String roadID = getRoadID(inter1.intersectionID, inter2.intersectionID);
		//System.out.println("inter1ID:" + inter1.intersectionID + ",inter2ID:" + inter2.intersectionID + ",roadID:" + roadID);
		Road road = region.roadMap.get(roadID);
		
		return road.distance;		
	}
	
	public String getRoadID(String inter1ID, String inter2ID) {
		Key key = new Key(inter1ID, inter2ID);
		String roadID = region.intersectionsToRoadMap.get(key);

		if (roadID == null) {
			key = new Key(inter2ID, inter1ID);
			roadID = region.intersectionsToRoadMap.get(key);		
		}
		
		
		return roadID;			
		
	}
	
	public void printMap() {
		int counter =0;
		for (Key key1: region.intersectionsToRoadMap.keySet()) {
			counter++;
			String value = region.intersectionsToRoadMap.get(key1);
			System.out.println(counter + ":<" + key1 + ">:" + value);
			
		}
	}
	
	public void buildAdjacency() {
		int node1ID, node2ID;
		
		for(Node node: V) {
			adj.add(new ArrayList<Integer>());
		}
		
		for(Road road: region.roads) {
			node1ID = region.intersectionMap.get(road.intersection1ID).nodeID;
			node2ID = region.intersectionMap.get(road.intersection2ID).nodeID;			
			adj.get(node1ID).add(node2ID);
			adj.get(node2ID).add(node1ID);	
		}	
		
	}
	
	public Node getNode(String intersectionID) {
		Intersection intersection = region.intersectionMap.get(intersectionID);
		return nodeMap.get(intersection.nodeID);
	}	

	
	public void dijkstra(Node s, Node e) {
		PriorityQueue<Node> Q;
		Set<Node> S;
		Node u,v;
		
		initialize_single_source(s);
		S = new HashSet<Node>();
		
		Q = buildQueue();
		long counter = 0;
		System.out.println("Dijastra begins..");
		while (!Q.isEmpty()) {
			counter++;
			if (!Q.contains(e)) { // early termination. endNode is already processed.
				return;
			}
			
			if (counter % 20000 == 0) {
				System.out.println("Processing Dijastra with early termination. Please wait...");
				System.out.println("Number of nodes processed:" + counter);
			}
			u = Q.remove();
			S.add(u);
			
			for (int vID: adj.get(u.nodeID)) {
				v = nodeMap.get(vID);				
				if (Q.contains(v)) {
					Q.remove(v); // remove v
					relax(u,v);	 // update v			
					Q.add(v);    // put v back to Q
				}
			}			
		}
		
		//printS(S);
	}

	/*
	public void printS(Set<Node> S) {
		for (Node n: S) {
			System.out.println(n.nodeID + ":" + n.intersection.intersectionID + ":" + n.d);
		}
		
	}
	*/
	
	public void relax(Node u, Node v) {
		//System.out.println("u.node id:" + u.nodeID + ", v.node ID:" + v.nodeID);
		if (v.d > u.d + getWeight(u.nodeID, v.nodeID)) {
			v.d = u.d + getWeight(u.nodeID, v.nodeID);
			v.pi = u;			
		}		
		
	}
	
	public PriorityQueue<Node> buildQueue() {
		PriorityQueue<Node> Q = new PriorityQueue<Node>(new Node_Comparator());
		for (Node v: V) {
			Q.add(v);
		}
		return Q;
	}
	
	public void initialize_single_source(Node s) {
		/*
		 * for each vertex v in G.V
		 * 		v.d = infinite
		 * 		v.pi = NIL
		 * s.d = 0 
		 */		
		for (Node v: V) {
			v.d = Integer.MAX_VALUE;
			v.pi = null;			
		}
		s.d = 0;		
	}
	
	class Node_Comparator implements Comparator<Node> {
		public int compare(Node node1, Node node2)
		{			
			return (int)(node1.d - node2.d);
		}
	}
	
	public List<Intersection> getShortedPath(Node startNode, Node endNode) {
		Stack<Node> shortestNodeStack = new Stack<Node>();
		Node tempNode = endNode;
		
		List<Intersection> pathList = new ArrayList<Intersection>();
		
		// insert node to stack
		while(tempNode != null) {
			shortestNodeStack.push(tempNode);
			tempNode = tempNode.pi;				
		}		
		
		
		System.out.println("The Dijkstra's path from " + startNode.intersection.intersectionID + " to " + endNode.intersection.intersectionID + ":");
		// pop node and print node
		while (!shortestNodeStack.empty()) {
			Node node = shortestNodeStack.pop();
			pathList.add(node.intersection);
			System.out.println(node.intersection.intersectionID);
		}		
		
		double distance = endNode.d;
		distance = distance/1609.344;
		System.out.println("Total distance traveled (in miles):" + distance);
		//System.out.println("size:" + pathList.size());
		return pathList;
		
		
		
		
	}
	

}
