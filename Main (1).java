import java.util.Scanner;
import java.io.*;
import java.net.URISyntaxException;
public class Main
{
	public static void main(String[] args) throws IOException, ClassNotFoundException, URISyntaxException{
	    Scanner scnr = new Scanner(System.in);
	    int read; // variable to navigate menu
	    String url; // variable to store url for visiting website
		BrowserNavigation browser = new BrowserNavigation();
		browser.restoreLastSession(); // check if there is saved data
		
		System.out.println("Menu:\n1: Visit Website\n2: Go Back\n3: Go Forward\n4: Show Browsing History\n5: Clear Browsing History\n6: Save Browser\n7: Exit");
		while (!scnr.hasNextInt()){ // loop until an integer is entered
			System.out.println("Invalid input");
			scnr.nextLine();
		}
			read = scnr.nextInt();
		while (read != 7) // loop until 7 is entered
		{
		    switch(read){
		        case 1: // visit website
		            System.out.println("Enter website url: ");
		            url = scnr.next();
		            browser.visitWebsite(url);
		            break;
		        case 2: // go back
		            browser.goBack();
		            break;
		        case 3: // go forward
		            browser.goForward();
		            break;
		        case 4: // show browsing history
		            browser.showHistory();
		            break;
		        case 5: // clear browsing history
		            browser.clearHistory();
		            break;
		        case 6: // save browser
		            browser.closeBrowser();
		            break;
		    }
			System.out.println("Menu:\n1: Visit Website\n2: Go Back\n3: Go Forward\n4: Show Browsing History\n5: Clear Browsing History\n6: Save Browser\n7: Exit");
			while (!scnr.hasNextInt()){ // loop until an integer is entered
				System.out.println("Invalid input");
				scnr.next();
			}
				read = scnr.nextInt();
		}
		scnr.close(); // close scanner
	}
}
