package project.pkg2_infixpostfix;

public class Stack<T> {

    private Node<T> top;

    public void push(T data) {
        Node<T> newNode = new Node<T>(data);
        if (this.top == null) {
            this.top = newNode;
        } else {
            newNode.setNext(top);
            this.top = newNode;
        }

    }

    public Node<T> pop() {
        Node<T> oldNode = this.top;
        this.top = this.top.getNext();
        return oldNode;

    }

    public Node<T> peak() {

        return this.top;

    }

    public boolean isEmpty() {
        return (this.top == null);
    }

    public void clear() {
        this.top = null;
    }

    public int length() {
        Node<T> curr = this.top;
        int len = 0;
        while (curr != null) {
            len++;
            curr = curr.getNext();
        }
        return len;
    }

    @Override
    public String toString() {
        Node<T> curr = this.top;
        String res = "Top ==> ";
        while (curr != null) {

            res += curr.getData() + " ==> ";
            curr = curr.getNext();
        }
        res += "NULL";
        return res;
    }

}
