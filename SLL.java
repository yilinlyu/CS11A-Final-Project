/**
 * The singly linked list (SLL) object is defined in this file
 */

public class SLL<T> {
	
	// Field
	public int size; //size of the SLL
	public SLN<T> head; //first node of the SLL
	public SLN<T> tail; //last node of the SLL
	
	
	// Constructor
	/**
	 * This method constructs a new empty SLL. 
	 */
	public SLL() {
		this.size = 0;
		this.head = null;
		this.tail = null;
	}
	
	
	// Methods
	/**
	 * This method inserts a new SLN with the given data to the end of the SLL. 
	 * @param data the data that the new SLN should contain
	 */
	public void insert(T data) {
		SLN<T> newNode = new SLN<T>(data); //creating the newNode that stores specified data
		if (size==0) { //if size==0 then set head and tail to be newNode
			this.head = newNode;
			this.tail = newNode;
		} else { //if size>0 then let tail.next point to the newNode and reset tail
			this.tail.next = newNode; //add newNode behind the tail
			tail = tail.next; //let tail point to the newNode
		}
		size++; //increment size
	}
	
	/**
	 * This method removes and returns the first instance of a node with the specified data. 
	 * @param data first instance of the node that stores this data will be removed
	 * @return the first instance of node with specified data
	 */
	public SLN<T> remove(T data) {
		if (this.size==0) { //throws exception is SLL is empty
			throw new IndexOutOfBoundsException();
		}
		SLN<T> curr = this.head;
		if (curr.data.equals(data)) { //special case: remove from front
			this.head = this.head.next; //set head to the second node
			size--;
			return curr;			
		} else { //more common case: remove from middle or end
			SLN<T> prev = this.head; //previous to the current node
			curr = curr.next; //current node to test
			boolean found = false; //whether a node is removed yet
			while ((curr!=null) & (!found)) { //while no node has been removed and is not at the end of the SLL
				if (!curr.data.equals(data)) { //if doesn't equal, move towards the end of the SLL
					prev = curr;
					curr = curr.next;
				} else { //if equal, remove current node and set found to be true
					prev.next = curr.next;
					found = true;
				}
			}	
			if (curr!=null) { //only decrement size if actually removed a node
				size--;
			}
			if (curr.next==null) { //update tail if removed the end
				this.tail = prev;
			}
			return curr;
		}
	}
	
	/**
	 * This method removes and returns the node at the given index.  
	 * @param index the index at which the node will be removed
	 * @return the node removed
	 */
	public SLN<T> remove(int index) {
		if (index>(size-1) | index<0) { //throws exception is index cannot be found or SLL is empty
			throw new IndexOutOfBoundsException();
		}
		SLN<T> prev = this.head; //node before removed node
		if (index==0) { //remove head
			this.head = prev.next;
			size--;
			if (this.size==1) { //update tail if size=1
				this.tail = null;
			}
			return prev;
		}
		
		//remove from middle or end
		for (int i=1; i<index; i++) {
			prev = prev.next;
		}
		SLN<T> curr = prev.next; //removed node
		prev.next = curr.next; //reset pointer from prev to curr.next
		size--;
		if (this.size==(index+1)) { //update tail if removed from the end
			this.tail = prev;
		}
		return curr;
	}
	
	/**
	 * This method removes and returns the first node in the SLL. 
	 * @return the first node in the SLL
	 */
	public SLN<T> remove() {
		if (this.size==0) { //throws exception if SLL is empty
			throw new IndexOutOfBoundsException();
		}
		SLN<T> curr = this.head;
		this.head = this.head.next;
		curr.next = null;
		size--;
		if (this.size==0) { //if only 1 node in SLL
			this.tail = null;
		}
		return curr;
	}
	
	/**
	 * This method returns a String representation of all the elements in the SLL from head to tail. 
	 * @return a string representation of all the elements in the SLL
	 */
	public String toString() {
		SLN<T> curr = this.head;
		String s = "";
		if (this.size==0) {
			return s;
		}
		s = s+curr.toString();
		curr = curr.next;
		while (curr!=null) { //while current is still a node...
			s = s+(" "+curr.toString());
			curr = curr.next;
		}
		return s;
	}
	
	
}
