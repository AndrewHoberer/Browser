import java.util.Iterator;
public class BrowserLinkedList implements Iterable<String>
{
	private Node head;
	private Node tail;
	private int modCount = 0;

	public BrowserLinkedList(){head = null;} // Constructors
	public BrowserLinkedList(Node n){head = n; tail = n;}
	
	public Node getHead(){return head;} // Accessors
	public Node getTail(){return tail;}

	public void setHead(String s)  // Mutator
	{
	    modCount++;
	    Node n = new Node(s); // create new node with string
	    if (head != null) // if there is already a head node
	    {
    	    n.setNext(head); // set the new node's next to the head
    	    head.setPrev(n); // set the head's previous to the new node
	    }
	    else // if there is no head node the head is also the tail
	        tail = n;
	    head = n; // set the head to the new node
	}
	
	public void insert(String s) // insert at the end of the list
	{
	    modCount++;
	    if (head != null) // if there is already a head node
	    {
    	    Node n = new Node(s); // create new node with string
    	    tail.setNext(n); // set the tail's next to the new node
    	    n.setPrev(tail); // set the new node's previous to the tail
    	    tail = n; // set the tail to the new node
	    }
	    else // if there is no head node
	        setHead(s);
	}
	
	public Node removeHead() // remove the head node
	{
	    if (head != null) // only remove if there is a head node
	    {
	        modCount++;
    	    Node ptr = head; // create a pointer to the head
    	    head = head.next; // set the head to the next node
			if (head == null) // if the only node is removed, there is no head or tail in the array
				tail = null;
        	ptr.setNext(null);
			if (head != null){
        		head.setPrev(null);
			}
        	return ptr; // return the removed node
	    }
	    else
	        return null;
	}
	
	public Node removeUrl(String s)
	{
	    if (head != null) // only remove if the list isn't empty
	    {
	        modCount++;
    	    Node ptr = head;
    	    if (head.url.equals(s)) // if removing first node
    	    {
    	        return removeHead();
    	    }
    	    while (ptr.next.next != null) // if removing any other node
    	    {
    	        ptr = ptr.next;
    	        if(ptr.url.equals(s))
    	        {
    	            ptr.prev.setNext(ptr.next);
    	            ptr.next.setPrev(ptr.prev);
    	            ptr.setPrev(null);
    	            ptr.setNext(null);
    	            return ptr;
    	        }
    	    }
    	    if (ptr.next.url.equals(s)) // if removing tail node
    	    {
    	        tail = ptr;
    	        ptr = ptr.next;
    	        ptr.prev.setNext(null);
    	        ptr.setPrev(null);
    	        return ptr;
    	    }
	    }
        return null;
	}
	
	@Override
	public Iterator<String> iterator(){return new StackIterator();} // Iterator for the linked list
	
	protected class Node // Node class
	{
		private String url;
		private Node next = null;
		private Node prev = null;

		public Node() {} // Constructors
		public Node(String s){url = s;}

		public String getUrl(){return url;} // Accessors
		public Node getNext(){return next;}
		public Node getPrev() {return prev;}

		public void setUrl(String s) {url = s;} // Mutators
		public void setNext(Node n) {next = n;}
		public void setPrev (Node n) {prev = n;}
	}
	
	private class StackIterator implements Iterator<String> // Iterator for the linked list
	{
	    private Node current = head;
	    private int expectedModCount = modCount;
	    private boolean canRemove = false;
	    
	    @Override
	    public boolean hasNext(){return current != null;} // if the current node is not null, there is another element
	    
	    @Override
	    public String next()
	    {
	        if(expectedModCount != modCount || !hasNext()) // if the list has been modified or there are no more elements,
	            return null;
	        String s = current.url; // return the current node's url
	        current = current.next; // move to the next node
	        canRemove = true; // the element can be removed since it has been passed over
	        return s;
	    }
	    
	    @Override
	    public void remove()
	    {
	        if(expectedModCount == modCount && canRemove) // if the list has not been modified and the element can be removed 
	        {
	            removeUrl(current.prev.url); // remove the previous node
	            expectedModCount++;
	            canRemove = false;
	        }
	    }
	}
	
}