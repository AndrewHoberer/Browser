import java.io.*;
import java.util.EmptyStackException;
public class BrowserStack
{
    private BrowserLinkedList history;
    
    public BrowserStack(){history = new BrowserLinkedList();} // Constructor
    
    public BrowserLinkedList getHistory(){return history;} // Accessor
    
    public String pop() throws EmptyStackException // remove the head node
    {
        if (history.getHead() != null) // if there is a head node
            return history.removeHead().getUrl(); // return the url of the head node
        else
            throw new EmptyStackException(); // if there is no head node, throw an exception
    }

    public String popBack() throws EmptyStackException // remove the head node used for checking if goBack is possible since it requires two nodes instead of one,
                                                       // current url and previous url
    {
        if (history.getHead() != null && history.getHead().getNext() != null) // if there are at least two nodes
            return history.removeHead().getUrl(); // return the url of the head node
        else
            throw new EmptyStackException(); // if there are not at least two nodes, throw an exception
    }
    
    public void push(String s){history.setHead(s);} // insert a url to the front of the stack

    public void insert(String s){history.insert(s);} // insert a url to the back the stack
    
    public void clear(){history = new BrowserLinkedList();} // clear the stack
    
    public void close(String tag, ObjectOutputStream oos) throws IOException // save the stack data to a file
    {
        for (String s : history) // iterate through the stack
        {
            oos.writeUTF(tag); // write the tag for the string, forward or back
            oos.writeObject(s); // write the string to the file
        }
    }
}