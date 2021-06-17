package SPLT_A4;

public class SPLT implements SPLT_Interface{
  private BST_Node root;
  private int size;
  
  public SPLT() {
    this.size = 0;
  } 
  
  public BST_Node getRoot() { //please keep this in here! I need your root node to test your tree!
    return root;
  }  
	public void insert(String s) {
		if (empty()) {
			root = new BST_Node(s);
			size +=1;
			return;
		}
		root=root.insertNode(s);
		if(root.justMade){
			size++;	
		}
	}

	public String findMin() {
		if (empty()) {
			return null;
		} else {
			root=root.findMin();
			return root.data;
		}
	}
	
	public String findMax() {
		if (empty()) {
			return null;
		} else {
			root=root.findMax();
			return root.data;
		}
	}

	public boolean empty() {
		if (size == 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean contains(String s) {
		if (empty()) {
			return false;
		}
		root=root.containsNode(s);
		return root.data.equals(s);
	}

	public int size() {
		return size;
	}

	public int height() {
		if (empty()) {
			return -1;
		} else {
			return root.getHeight();
		}
	}
	public void remove(String s) {
		if(contains(s)) {
			BST_Node L = root.getLeft();
			BST_Node R = root.getRight();
			if(L==null && R==null) {
				root=null;
				size=0;
			}
			else if(L==null) {
				R.par=null;
				root=R;
				size--;
			}
			else if(R==null) {
				L.par=null;
				root=L;
				size--;
			}
			else {
				L.par=null;
				R.par=null;
				L=L.findMax();
				L.right=R;
				L.getRight().setPar(L);
				root=L;
				size-=1;
			}
		}
	}

}