package SPLT_A4;

public class BST_Node {
	String data;
	BST_Node left;
	BST_Node right;
	BST_Node par; // parent...not necessarily required, but can be useful in splay tree
	boolean justMade; // could be helpful if you change some of the return types on your BST_Node
						// insert.
	// I personally use it to indicate to my SPLT insert whether or not we increment
	// size.

	BST_Node(String data) {
		this.data = data;
		this.justMade = true;
	}

	BST_Node(String data, BST_Node left, BST_Node right, BST_Node par) { // feel free to modify this constructor to suit																		// your needs
		this.data = data;
		this.left = left;
		this.right = right;
		this.par = par;
		this.justMade = true;
	}

	// --- used for testing ----------------------------------------------
	//
	// leave these 3 methods in, as is (meaning also make sure they do in fact
	// return data,left,right respectively)

	public String getData() {
		return data;
	}

	public BST_Node getLeft() {
		return left;
	}

	public BST_Node getRight() {
		return right;
	}
	public BST_Node getPar() {
		return par;
	}
	// --- end used for testing -------------------------------------------

	public void setLeft(BST_Node node) {
		left=node;
	}
	public void setRight(BST_Node node) {
		right=node;
	}
	public void setPar(BST_Node node) {
		par=node;
	}
	public BST_Node containsNode(String s) {
		if (data.equals(s)) {
			splay(this);
			return this;
		}
		if (data.compareTo(s) > 0) {
			if (left == null) {
				splay(this);
				return this;
			}
			return left.containsNode(s);
		}
		if (data.compareTo(s) < 0) {
			if (right == null) {
				splay(this);
				return this;
			}
			return right.containsNode(s);
		}
		splay(this);
		return this;
	}
	
	public BST_Node insertNode(String s) {
		if (data.compareTo(s) > 0) {
			if (left == null) {
				left = new BST_Node(s);
				left.par=this;
				BST_Node temp =left;
				splay(left);
				justMade=true;
				return temp;
			}
			return left.insertNode(s);
		}
		if (data.compareTo(s) < 0) {
			if (right == null) {
				right = new BST_Node(s);
				right.par=this;
				BST_Node temp =right;
				splay(right);
				justMade=true;
				return temp;
			}
			return right.insertNode(s);
		}
		splay(this);
		justMade=false;
		return this;
	}
	
	public BST_Node findMin() {
		if (left != null)
			return left.findMin();
		splay(this);
		return this;
	}

	public BST_Node findMax() {
		if (right != null)
			return right.findMax();
		splay(this);
		return this;
	}
	public int getHeight() {
		int l = 0;
		int r = 0;
		if (left != null)
			l += left.getHeight() + 1;
		if (right != null)
			r += right.getHeight() + 1;
		return Integer.max(l, r);
	}
	// --- Some example methods that could be helpful
	// ------------------------------------------
	//
	// add the meat of correct implementation logic to them if you wish

	// you MAY change the signatures if you wish...names too (we will not grade on
	// delegation for this assignment)
	// make them take more or different parameters
	// have them return different types
	//
	// you may use recursive or iterative implementations

	/*
	 * public BST_Node containsNode(String s){ return false; } //note: I personally
	 * find it easiest to make this return a Node,(that being the node splayed to
	 * root), you are however free to do what you wish. public BST_Node
	 * insertNode(String s){ return false; } //Really same logic as above note
	 * public boolean removeNode(String s){ return false; } //I personal do not use
	 * the removeNode internal method in my impl since it is rather easily done in
	 * SPLT, feel free to try to delegate this out, however we do not "remove" like
	 * we do in BST public BST_Node findMin(){ return left; } public BST_Node
	 * findMax(){ return right; } public int getHeight(){ return 0; } /* private
	 * void splay(BST_Node toSplay) { return false; } //you could have this return
	 * or take in whatever you want..so long as it will do the job internally. As a
	 * caller of SPLT functions, I should really have no idea if you are
	 * "splaying or not" //I of course, will be checking with tests and by eye to
	 * make sure you are indeed splaying //Pro tip: Making individual methods for
	 * rotateLeft and rotateRight might be a good idea!
	 */

	// --- end example methods --------------------------------------

	private void splay(BST_Node node) {
		if (node.par == null) {
			return;
		}
		if (node.par.par == null) {
			zig(node);
			return;
		} else if (node.par.par.getRight()!=null && node.par.par.getRight().equals(node.par)) {
			if (node.par.getRight()!=null && node.par.getRight().equals(node)) {
				zigZigR(node);
			} else {
				zigZagR(node);
			}
		} else {
			if (node.par.getLeft()!= null && node.par.getLeft().equals(node)) {
				zigZigL(node);
			} else {
				zigZagL(node);
			}
		}
		if (node.par != null) {
			splay(node);
		}
	}

	private void zig(BST_Node node) {
		if (node.par.getRight()!=null && node.par.getRight().equals(node)) {
			rotateLeft(node);
		} else {
			rotateRight(node);
		}
	}

	private void zigZigR(BST_Node node) {
		rotateLeft(node.par);
		rotateLeft(node);
	}

	private void zigZigL(BST_Node node) {
		rotateRight(node.par);
		rotateRight(node);
	}

	private void zigZagR(BST_Node node) {
		rotateRight(node);
		rotateLeft(node);
	}

	private void zigZagL(BST_Node node) {
		rotateLeft(node);
		rotateRight(node);
	}

	private void rotateLeft(BST_Node node) {
		if(node.par.par != null && node.par.par.left != null && node.par.par.left.equals(node.par)) {
			node.par.par.setLeft(node);
		}
		else if (node.par.par!=null){
			node.par.par.right=node;
		}
		BST_Node grandpar=node.par.par;
		node.par.setRight(node.left);
		if(node.left != null) {
			node.left.setPar(node.par);
		}
		node.setLeft(node.par);
		node.par.setPar(node);
		node.setPar(grandpar);
	}

	private void rotateRight(BST_Node node) {
		if(node.par.par != null && node.par.par.left != null && node.par.par.left.equals(node.par)) {
			node.par.par.setLeft(node);
		}
		else if (node.par.par!=null){
			node.par.par.right=node;
		}
		BST_Node grandpar=node.par.par;
		node.par.setLeft(node.right);
		if(node.right != null) {
			node.right.setPar(node.par);
		}
		node.setRight(node.par);
		node.par.setPar(node);
		node.setPar(grandpar);
	}
	// --------------------------------------------------------------------
	// you may add any other methods you want to get the job done
	// --------------------------------------------------------------------

}