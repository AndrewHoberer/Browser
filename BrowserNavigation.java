import java.io.*;
import java.util.EmptyStackException;
import java.net.URI;
import java.net.URISyntaxException;
public class BrowserNavigation
{
    BrowserStack forwardStack; // stack holding urls ahead of url on top of backStack
    BrowserStack backStack; // stack holding urls behind url on top of backStack
    BrowserQueue queue; // queue holding urls in order visited
    
    public BrowserNavigation() // constructor, queue is initialized in restoreLastSession based if there is saved data
    {
        backStack = new BrowserStack();
        forwardStack = new BrowserStack();
    }
    
    public void visitWebsite(String url) throws IOException
    {
        try{
            URI uri = new URI(url);
            java.awt.Desktop.getDesktop().browse(uri); // open the url in the default browser
            backStack.push(url); // push the url to the backStack
            forwardStack.clear(); // clear the forwardStack since when a new site is visited, the forward stack is reset
            queue.insert(url); // insert the url into the queue
            System.out.println("Now at " + url); // print the current url
        } catch (URISyntaxException | java.io.IOException e) {
            System.out.println("Invalid website url"); // if the url is invalid, print an error message
        }
    }
    
    public void goBack() throws IOException, URISyntaxException
    {
        try {
            String url = backStack.popBack(); // pop the url from the backStack
            forwardStack.push(url); // push the url to the forwardStack
            url = backStack.getHistory().getHead().getUrl(); // get the url of the current site
            queue.pushFront(url); // push the current url to the front of the queue
            URI uri = new URI(url);
            java.awt.Desktop.getDesktop().browse(uri); // open the url in the default browser
            System.out.println("Now at " + url); // print the current url
        } catch (EmptyStackException e) {
            System.out.println("No previous page available."); // if there is no previous page, print an error message
        }
    }
    
    public void goForward() throws IOException, URISyntaxException
    {
        try {
            String url = forwardStack.pop(); // pop the url from the forwardStack
            backStack.push(url); // push the url to the backStack
            url = backStack.getHistory().getHead().getUrl(); // get the url of the current site
            queue.pushFront(url); // push the current url to the front of the queue
            URI uri = new URI(url); 
            java.awt.Desktop.getDesktop().browse(uri); // open the url in the default browser
            System.out.println("Now at " + url); // print the current url
        } catch (EmptyStackException e) {
            System.out.println("No forward page available."); // if there is no forward page, print an error message
        }
    }
    
    public void showHistory()
    {
        String hist = queue.toString(); // get the history from the queue
        if (hist != "") // if the history is not empty, print the history
            System.out.print(hist);
        else // otherwise, print that history is empty
            System.out.println("No browsing history available.");
    }
    
    public void clearHistory()
    {
        queue.clear(); // clear all the structures
        backStack.clear();
        forwardStack.clear();
        System.out.println("Browsing history cleared."); // print that the history is cleared
    }
    
    public void closeBrowser() throws IOException
    {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("session_data.txt")); // create file session_data.txt
        forwardStack.close("forward", oos); // save the forwardStack data
        backStack.close("back", oos); // save the backStack data
        oos.writeUTF("queue"); // write the tag for the queue
        oos.writeObject(queue); // save the queue
        oos.close();
        System.out.println("Browser session saved."); // print that the session is saved
    }
    
    public void restoreLastSession() throws IOException, ClassNotFoundException
    {
        if (new File("session_data.txt").exists()) // if there is saved session data, restore the session
        {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("session_data.txt")); // open the file
            String tag = ois.readUTF();
            while(tag.equals("forward")) // read the forwardStack data
            {
                forwardStack.insert((String)ois.readObject()); // insert the url into the forwardStack
                tag = ois.readUTF(); // read the next tag
            }
            while(tag.equals("back")) // read the backStack data
            {
                backStack.insert((String)ois.readObject()); // insert the url into the backStack
                tag = ois.readUTF(); // read the next tag
            }
            queue = (BrowserQueue)ois.readObject(); // read the queue
            System.out.println("Previous session restored."); // print that the session is restored
            ois.close();
        }
        else // otherwise, create a new session
        {
            queue = new BrowserQueue(); // create a new queue
            System.out.println("No previous session found."); // print that there is no previous session
        }

    }
}