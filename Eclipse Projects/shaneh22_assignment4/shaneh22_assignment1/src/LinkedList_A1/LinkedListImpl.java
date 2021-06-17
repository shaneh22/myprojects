/**
 * COMP 410
 *See inline comment descriptions for methods not described in interface.
 *
*/
package LinkedList_A1;

public class LinkedListImpl implements LIST_Interface {
  Node sentinel; //this will be the entry point to your linked list (the head)
  int size=0;
  public LinkedListImpl(){//this constructor is needed for testing purposes. Please don't modify!
    sentinel=new Node(0); //Note that the root's data is not a true part of your data set!
  }
  
  //implement all methods in interface, and include the getRoot method we made for testing purposes. Feel free to implement private helper methods!
  public void clear() {
	  sentinel= new Node(0);
	  size=0;
  }
  public int size() {
	  return size;
  }
  public boolean isEmpty() {
	  if(size==0) {
		  return true;
	  }
	  return false;
  }
  public double get(int index) {
	  Node target=sentinel;
	  if(!isEmpty()) {
		  target=target.getNext();
	  }
	  else {
		  return Double.NaN;
	  }
	  for(int i=0;i<=size;i++) {
		  if(i==index) {
			  return target.getData();
		  }
		  else {
			  target=target.getNext();
		  }
	  }
	  return Double.NaN;
  }
  public boolean insert(double elt, int index) {
	  Node target=sentinel;
	  for(int i=0;i<=size;i++) {
		  if(i==index) {
			  Node insert = new Node(elt);
			  insert.prev=target;
			  if(index==size) {
				  insert.next=sentinel;
				  sentinel.prev=insert;
			  }
			  else {
				  insert.next=target.getNext();
				  target.getNext().prev=insert;  
			  }	
			  target.next=insert;
			  size++;
			  return true;
		  }
		  else {
			  target=target.getNext();
		  }
	  }
	  return false;
  }
  public boolean remove(int index) {
	  if(isEmpty()) {
		  return false;
	  }
	  if(size()==1&&index==0) {
		  clear();
		  return true;
	  }
	  Node target=sentinel.getNext();
	  for(int i=0;i<size;i++) {
		  if(i==index) {
			  if(index==size()-1) {
				  target.getPrev().next=sentinel;
				  sentinel.prev=target.getPrev();
			  }
			  else {
				  target.getNext().prev=target.prev;
				  target.getPrev().next=target.next;
			  }
			  size--;
			  return true;
		  }
		  else {
			  target=target.getNext();
		  }
	  }
	  return false;
  }
  public Node getRoot(){ //leave this method as is, used by the grader to grab your linkedList easily.
    return sentinel;
  }
  
}

