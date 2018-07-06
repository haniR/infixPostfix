package project.pkg2_infixpostfix;



public class Node<T> implements Comparable<T> {

	protected Node<T> next;
	protected T Data;

	public Node(T data) {
		this.next = null;
		this.Data = data;
	}

	public Node<T> getNext() {
		return next;
	}

	public void setNext(Node next) {
		this.next = next;
	}

	public T getData() {
		return Data;
	}

	public void setData(T data) {
		Data = data;
	}

	@Override
	public String toString() {
		return "Node Data : [" + this.Data +"]";
	}
	 
	public int compareTo(T data) {
		Object thisDat = (Object) this.Data;
		Integer thy = (Integer) thisDat;
		Object dat = data;
		Integer thou = (Integer) dat;
		
		if (thy > thou) {
			return 1;
		} else if (thou > thy) {
			return -1;
		} else {
			return 0;
		}
	}

}
