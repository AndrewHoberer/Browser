import java.io.Serializable;
import java.util.Iterator;
public class BrowserArrayList implements Iterable<String>, Serializable
{
    private static final long serialVersionUID = 7892233087913840875L;
    private int maxSize;
    private int currentSize = 0;
    private String list[];
    private int head = 0; // starting index since the list is circular
	private int modCount = 0; // for iterator
    
    public BrowserArrayList() // Constructors
    {
        maxSize = 10;
        list = new String[10];
    }
    
    public int getMaxSize(){return maxSize;} // Accessors
    public int getCurrentSize(){return currentSize;}
    
    public void insert(String s)
    {
        modCount++;
        if(currentSize >= maxSize) // if the array is full, resize
            resize();
        list[(head + currentSize) % maxSize] = s; // add the string to the end of the array
        currentSize++;
    }
    
    private void resize()
    {
        String oldArray[] = list;
        list = new String[maxSize * 2]; // double the size of the array
        for (int i = 0; i < currentSize; i++) // copy the old array to the new array starting at index 0 of new array
            list[i] = oldArray[(i + head) % maxSize];
        maxSize *= 2;
        head = 0;
    }
    
    protected void pushFront(String s)
    {
        for(int i = 0; i < currentSize; i++) // search array for the string
        {
            if(list[(i + head) % maxSize].equals(s)) // if the string is found, push to the back of the array and move the rest of the array between up
            {
                modCount++;
                while (i < currentSize)
                {
                    list[(i + head) % maxSize] = list[(i + head + 1) % maxSize]; // move the rest of the array up to the gap left by moved string
                    i++;
                }
                list[(head + currentSize - 1) % maxSize] = s; // place string in last index
                break;
            }
        }
    }
    
    public void removeUrl(String s)
    {
        if(list[head].equals(s)) // if the string is at the head, remove using removeHead
            removeHead(s);
        else
        {
            for(int i = 0; i < currentSize; i++) // otherwise search the array for the string
            {
                if(list[(i + head) % maxSize].equals(s)) // if the string is found, remove it and move the rest of the array down
                {
                    modCount++;
                    while(i < currentSize) // move the rest of the array down to the gap left by the removed string
                    {
                        list[(i + head) % maxSize] = list[(i + head + 1) % maxSize];
                        i++;
                    }
                    currentSize--;
                    break;
                }
            }
        }
    }
    
    public void removeHead(String s)
    {
        if(currentSize > 0) // when removing the head, the other indices don't need to be moved, instead just move the head index up one
        {
            modCount++;
            currentSize--;
            head++;
        }
    }
    
    public void clear(){currentSize = 0;head = 0;} // Instead of deleting all the data, just reset the head and current size to clear the array
    
    @Override
	public Iterator<String> iterator(){return new QueueIterator();} // Iterator for the array
    
    private class QueueIterator implements Iterator<String>
	{
	    private int current = head;
	    private int expectedModCount = modCount;
	    private boolean canRemove = false;
	    
	    @Override
	    public boolean hasNext(){return current < head + currentSize;} // if the current index is less than the head index plus the current size, there is another element
	    
	    @Override
	    public String next()
	    {
	        if(expectedModCount != modCount || !hasNext()) // if the array has been modified or there are no more elements, return null
	            return null;
	        String s = list[current % maxSize]; // return the string at the current index then increment the index
	        current++;
	        canRemove = true; // The element can be removed since it has been passed over
	        return s;
	    }
	    
	    @Override
	    public void remove()
	    {
	        if(expectedModCount == modCount && canRemove) // if the array has not been modified and the element can be removed
	        {
	            removeUrl(list[(current - 1) % maxSize]); // remove the element at the current index minus one
                current--; // decrement the index since the element has been removed
	            expectedModCount++;
	            canRemove = false;
	        }
	    }
	}
}