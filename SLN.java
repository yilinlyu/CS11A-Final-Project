/**
 * Nicole Tai
 * taijiayue1997@brandeis.edu
 * The singly linked node (SLN) object is defined in this file
 */

public class SLN<T> {

	// Fields
	public T data; //the data field stores the specific value of the node
	public SLN<T> next; //the next field stores a pointer to the next node
	
	
	// Constructor
	/**
	 * This method constructs a new SLN. 
	 * @param data the specific data the node should store in its data field
	 */
	public SLN(T data) {
		if (data==null) {
			throw new IllegalArgumentException();
		}
		this.data = data;
		this.next = null;
	}
	
	
	// Methods
	/**
	 * This method returns the String representation of the data item. 
	 * @return a string representation of the node
	 */
	public String toString() {
		return this.data.toString();
	}
	
}
