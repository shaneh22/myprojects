package DiGraph_A5;

public class Edge {
	long idNum;
	String sLabel; //source
	String dLabel; //destination
	long weight;
	String eLabel; //edge label
	public Edge (long i, String s, String d, long w, String e) {
		idNum=i;
		sLabel=s;
		dLabel=d;
		weight=w;
		eLabel=e;
	}

}
