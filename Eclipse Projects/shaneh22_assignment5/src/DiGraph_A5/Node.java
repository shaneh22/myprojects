package DiGraph_A5;

import java.util.HashMap;

public class Node {
	long idNum;
	String label;
	HashMap<String,Edge> sourceEdges = new HashMap<String,Edge>();
	HashMap<String,Edge> destinationEdges = new HashMap<String,Edge>();
	public Node (long id, String s) {
		idNum=id;
		label=s;
	}
	public void addSourceEdge(Edge e) {
		sourceEdges.put(e.dLabel,e);
	}
	public void addDestinationEdge(Edge e) {
		destinationEdges.put(e.sLabel,e);
	}

}
