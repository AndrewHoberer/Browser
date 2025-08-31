import java.io.Serializable;
public class BrowserQueue implements Serializable
{
    BrowserArrayList history;
    
    public BrowserQueue(){history = new BrowserArrayList();} // Constructor
    
    public BrowserArrayList getHistory(){return history;} // Accessor
    
    public void insert(String url){history.insert(url);} // insert a url into the history
    
    public void clear(){history.clear();} // clear the history
    
    public void pushFront(String url){history.pushFront(url);} // push a url to the front of the history
    
    public String toString() // return the history as a string
    {
        String hist = "";
        for (String i : history) // iterate through the history
        {
            hist = i + "\n" + hist;
        }
        return hist;
    }
}