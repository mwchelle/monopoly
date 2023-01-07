import java.util.*;
import java.io.*;

/*
The Community class is a subclass of Cards which draws and returns the player a community chest card.
*/
public class Community extends Cards{
  ArrayList<String> communityNames; // Declares an array list of all community chest names
  Random r=new Random();
  
  /*
  This is the constructor, it inherits the super class information and creates a new set a cards.
  pre: none
  post: Community cards created
  */
  public Community(int number){
    super(number); 
    communityNames=new ArrayList<String>(); // Initializes array
  }

  /*
  The readName method reads all of the card names into the array list deck of cards.
  pre: none
  post: names are assigned to the array list.
  */
  public void readName(){
    File userFile=new File("communitynames.txt"); // creates a new file
    FileReader in; // initializes the file reader
    BufferedReader readFile; // initialize a buffered reader
    String textLine; // reads each line of text from the file

    try{ // try-catch initiated to throw any possible errors
      in=new FileReader(userFile); // creates a filereader for source.txt
      readFile=new BufferedReader(in);
      while ((textLine=readFile.readLine())!= null){ // reads each line of text until there is no more words
        String[] cnames=textLine.split(" "); // splits each word from where a space is made and adds it to the arraylist
        for (int i=0;i<cnames.length;i++){
          communityNames.add(cnames[i]);
        }
      }
      readFile.close(); // closes both the FileReader and BufferedReader classes
      in.close();
  } catch (FileNotFoundException e){ // catches cases where the file name is     incorrect and/or doesn't exist
      System.out.println("File does not exist or could not be found.");
      System.err.println("FileNotFoundException: "+e.getMessage());
  } catch (IOException e){ // catches other IOExceptions
      System.out.println("Problem reading file.");
      System.out.println("IOException: "+e.getMessage());
  }
}

  /*
  The getName method returns the card name to the player.
  pre: none
  post: name of card returned.
  */
  public String getName(){
    return communityNames.get(cardNum-1);
  }

  /*
  The getDescription method returns the card description to the player.
  pre: none
  post: description is printed.
  */
  public void getDescription(){
    switch (cardNum){ // checks the card number given and uses a switch statement to print out the correct description
      case 1:
      System.out.println("Doctor's fee. Pay $50.");
      break;

      case 2:
      System.out.println("From sale of a stock, you get $50.");
      break;

      case 3:
      System.out.println("Holiday fund matures. Receive $100.");
      break;

      case 4:
      System.out.println("Income tax refund, receive $20.");
      break;

      case 5:
      System.out.println("Life insurance matures. Collect $100. ");
      break;

      case 6:
      System.out.println("It's your birthday! Collect $10");
      break;

      case 7:
      System.out.println("Pay school fees of $25.");
      break;

      case 8:
      System.out.println("Oh no! One of your properties needs repairs for damages. Pay $150.");
      break;
    }
    System.out.println("");
  }

  /*
  The getDeckname method returns the deck name to the player.
  pre: none
  post: deck name is returned.
  */
  public String getDeckname(){
    String deckName="Community"; // Returns that this is the community deck
    return deckName;
  }

  /*
  The moneylost method returns the amount of money deducted due to the card to the player.
  pre: none
  post: negative balance is returned.
  */
  public int moneyLost(){
    int lost=0; // set to default 0, in case the chance pulled does not deduct money
    switch (cardNum){ // uses a switch statement to account for only cards with losses
      case 1:
      lost=50;
      break;

      case 7:
      lost=25;
      break;

      case 8:
      lost=150;
      break;
    }
    return lost;
  }

  /*
  The moneygained method returns the amount of money added due to the card to the player.
  pre: none
  post: positive balance is returned.
  */
  public int moneyGained(){
    int gain=0; // set to default 0, in case the chance pulled does not increase money
    switch (cardNum){ // uses a switch statement to account for only cards with gains
      case 2:
      gain=50;
      break;

      case 3:
      gain=100;
      break;

      case 4:
      gain=20;
      break;

      case 5:
      gain=100;
      break;

      case 6:
      gain=10;
      break;
    }
    return gain;
  }

  /*
  The move method returns the amount of positions moved due to the card to the player.
  pre: none
  post: moved squares  is returned.
  */
  public int move(){
    int move=0;
    return move; // none of the community cards move the player
  }
}