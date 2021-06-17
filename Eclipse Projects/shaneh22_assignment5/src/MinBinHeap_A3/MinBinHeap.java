package MinBinHeap_A3;

public class MinBinHeap implements Heap_Interface {
  private EntryPair[] array; //load this array
  private int size=0;
  //private static final int arraySize = 10000; //Everything in the array will initially 
                                              //be null. This is ok! Just build out 
                                              //from array[1]
  public MinBinHeap(int num) {
	  this.array = new EntryPair[num];
	  array[0]= new EntryPair(null, (long)-100000);
  }
    
  //Please do not remove or modify this method! Used to test your entire Heap.
  @Override
  public EntryPair[] getHeap() { 
    return this.array;
  }
  
  /*
  Interface: The BHEAP will provide this collection of operations:

  insert
    in: an EntryPair object, containing the priority and string, 
        assume no duplicate priorities will be inserted 
    return: void
  delMin
    in: nothing
    return: void
  getMin
    in: nothing
    return: an element (an EntryPair object)
  size
    in: nothing
    return: integer 0 or greater
  build
    in: array of elements that need to be in the heap
    return: void
    (assume for a build that the bheap will start empty)
*/
  public EntryPair getMin() {
	  if(size>0) {
		  return array[1];
	  }
	  return null;
  }
  public int size() {
	  return size;
  }
  public void insert(EntryPair pair) {
	  size++;
	  array[size]=pair;
	  int compare=size;
	  while(compare>0 && pair.getPriority()<array[(int)(compare/2)].getPriority()) {
		  array[compare]=array[(int)(compare/2)];
		  array[(int)(compare/2)]=pair;
		  compare=(int)(compare/2);
	  }
	  
  }
  
  public void delMin() {
	  if(size==0) {
		  return;
	  }
	  if(size==1) {
		  size=0;
		  array[1]=null;
		  return;
	  }
	  array[1]=array[size];
	  array[size]=null;
	  size--;
	  int compare = 1;
	  int leaf=0;
	  EntryPair temp;
	  if(isLeaf(1)) {
		  return;
	  }
	  while(!isLeaf(compare)) {
		  if(array[(compare*2)+1] == null || array[compare*2].getPriority()<array[(compare*2)+1].getPriority()) {
			  leaf=compare*2;
		  }
		  else {
			  leaf=(compare*2)+1;
		  }
		  if(array[compare].getPriority()>array[leaf].getPriority()) {
			  temp=array[leaf];
			  array[leaf]=array[compare];
			  array[compare]=temp;
			  compare=leaf;
		  }
		  else {
			  return;
		  }
	  }
	  
  }
  public void build(EntryPair[] entries) {
	  if(entries.length==0) {
		  return;
	  }
	  for(int i=1;i<=entries.length;i++) {
		  array[i]=entries[i-1];
		  size++;
	  }
	  if(size==1) {
		  return;
	  }
	  int parent = (int)(size()/2);
	  int compare;
	  int leaf=0;
	  EntryPair temp;
	  boolean done =false;
	  for(int i=parent;i>0;i--) {
		  compare=i;
		  done=false;
		  while(!done) {
			  if(isLeaf(compare)) {
				  done=true;
			  }
			  else {
				  if(array[(compare*2)+1] == null || array[compare*2].getPriority()<array[(compare*2)+1].getPriority()) {
					  leaf=compare*2;
				  }
				  else {
					  leaf=(compare*2)+1;
				  }
				  if(array[compare].getPriority()>array[leaf].getPriority()) {
					  temp=array[compare];
					  array[compare]=array[leaf];
					  array[leaf]=temp;
					  compare=leaf;
				  }
				  else {
					  done=true;
				  }	
		  }
		  }
	  }
	  
	  
  }
  public boolean isLeaf(int index) {
	  int middle = ((int)(size()/2));
	  if(index>middle) {
		  return true;
	  }
	  else {
		  return false;
	  }
  }
  
  
  
  
}