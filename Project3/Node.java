
public class Node {
	int nodeID;
	Node pi; // parentNode	
	int edgeID;
	Edge edge;
	double d;
	Intersection intersection;
	
	public Node(int i) {
		nodeID = i;
		edgeID = -1;
		d = Double.MAX_VALUE;
		pi = null;
	}

}
