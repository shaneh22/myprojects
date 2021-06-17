package DiGraph_A5;

import java.util.HashMap;

import MinBinHeap_A3.EntryPair;
import MinBinHeap_A3.MinBinHeap;

public class DiGraph implements DiGraphInterface {
	HashMap<String,Node> nodes= new HashMap<String,Node>();
	HashMap<Long,Boolean> nodeIds= new HashMap<Long,Boolean>();
	HashMap<Long,Edge> edges= new HashMap<Long,Edge>();

  // in here go all your data and methods for the graph
	public boolean addNode (long id, String label) {
		Node node = new Node(id,label);
		if(nodes.containsKey(label)|| nodeIds.get(id) != null|| id < 0) {
			return false;
		}
		nodes.put(label, node);
		nodeIds.put(id,true);
		return true;
	}
	public boolean addEdge(long idNum, String sLabel, String dLabel, long weight, String eLabel) {
		if(edges.containsKey(idNum)|| idNum <0 || nodes.get(sLabel)==null ||nodes.get(dLabel) ==null) {
			return false;
		}
		if(nodes.get(sLabel).sourceEdges.containsKey(dLabel)) {
			return false;
		}
		Edge edge = new Edge(idNum, sLabel, dLabel, weight, eLabel);
		edges.put(idNum, edge);
		nodes.get(sLabel).addSourceEdge(edge);
		nodes.get(dLabel).addDestinationEdge(edge);
		return true;
	}
	
	public ShortestPathInfo[] shortestPath(String label) {
		if(nodes.get(label)==null) {
			return null;
		}
		ShortestPathInfo[] table = new ShortestPathInfo[(int) numNodes()];
		if(numNodes()==1) {
			table[0]=new ShortestPathInfo(label,0);
			return table;
		}
		MinBinHeap pq = new MinBinHeap((int)numEdges()); //so that the array size can be efficient and greater than the constant 10000
		HashMap<Node, Boolean> isKnown=new HashMap<Node,Boolean>();
		HashMap<Node, Long> totalDistances= new HashMap<Node, Long>();
		for(Node node:nodes.values()) {
			isKnown.put(node,false);
			totalDistances.put(node,Long.MAX_VALUE);
		}
		pq.insert(new EntryPair(label,(long) 0));
		Node currentNode;
		
		while(pq.size()>0) {
			currentNode=nodes.get(pq.getMin().value);
			if(pq.getMin().priority<totalDistances.get(currentNode))
				totalDistances.put(currentNode,pq.getMin().priority);
			pq.delMin();
			if(isKnown.get(currentNode)) {continue;}
			isKnown.put(currentNode,true);
			for(Edge edge:currentNode.sourceEdges.values()) {
				Node a = nodes.get(edge.dLabel);
				if(!isKnown.get(a) && totalDistances.get(a)>totalDistances.get(currentNode)+edge.weight) {
					totalDistances.replace(a, totalDistances.get(currentNode)+edge.weight);
					pq.insert(new EntryPair(a.label,totalDistances.get(currentNode)+edge.weight));
				}
			}
		}
		int i=0;
		for(Node node:nodes.values()) {
			long distance = totalDistances.get(node);
			if(distance == Long.MAX_VALUE) {
				distance=-1;
			}
			table[i]=new ShortestPathInfo(node.label,distance);
			i++;
		}
		return table;
	}

  public DiGraph ( ) { // default constructor
    // explicitly include this
    // we need to have the default constructor
    // if you then write others, this one will still be there
  }

  public boolean delNode(String label) {
	  	if(nodes.get(label)==null) {
	  		return false;
	  	}
		Object[] map = nodes.get(label).sourceEdges.values().toArray();
		for(int i=0; i>map.length;i++) {
			delEdge(((Edge)map[i]).sLabel,((Edge)map[i]).dLabel);
		}
		Object[] map2 = nodes.get(label).destinationEdges.values().toArray();
		for(int i=0; i>map2.length;i++) {
			delEdge(((Edge)map2[i]).sLabel,((Edge)map2[i]).dLabel);
		}
		nodeIds.remove(nodes.get(label).idNum);
		nodes.remove(label);
		return true;
	}


public boolean delEdge(String sLabel, String dLabel) {
	Node node = nodes.get(sLabel);
	if(node!=null) {
		Edge edge = node.sourceEdges.get(dLabel);
		if(edge!=null) {
			nodes.get(sLabel).sourceEdges.remove(dLabel);
			nodes.get(dLabel).destinationEdges.remove(sLabel);
			edges.remove(edge.idNum);
			return true;
		}
	}
	return false;
}


@Override
public long numNodes() {
	return nodes.size();
}



@Override
public long numEdges() {
	return edges.size();
}
  
  
  // rest of your code to implement the various operations
}