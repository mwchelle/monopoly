import java.util.*;
import java.io.*;

/*
The Chance class is a subclass of Cards which draws and returns the player a chance card.
*/
public class Chance extends Cards{
  ArrayList<String> chanceNames; // Declares an array list of all chance names
  Random r=new Random();
  
  /*
  This is the constructor, it inherits the super class information and creates a new set a cards.
  pre: none
  post: Chance cards created
  */
  public Chance(int number){
    super(number); 
    chanceNames=new ArrayList<String>(); // Initializes Array
  }

  /*
  The readName method reads all of the card names into the array list deck of cards.
  pre: none
  post: names are assigned to the array list.
  */
  public void readName(){
    File userFile=new File("chancenames.txt"); // creates a new file
    FileReader in; // initializes the file reader
    BufferedReader readFile; // initialize a buffered reader
    String textLine; // reads each line of text from the file

    try{ // try-catch initiated to throw any possible errors
      in=new FileReader(userFile); // creates a filereader for source.txt
      readFile=new BufferedReader(in);
      while ((textLine=readFile.readLine())!= null){ // reads each line of text until there is no more words
        String[] cNames=textLine.split(" "); // splits each word from where a space is made and adds it to the arraylist
        for (int i=0;i<cNames.length;i++){
          chanceNames.add(cNames[i]);
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
    return chanceNames.get(cardNum-1);
  }

  /*
  The getDescription method returns the card description to the player.
  pre: none
  post: description is printed.
  */
  public void getDescription(){
    switch (cardNum){ // checks the card number given and uses a switch statement to print out the correct description
      case 1:
      System.out.println("Go to go! Collect an extra $200.");
      break;

      case 2:
      System.out.println("Bank Pays you a Dividend of $50.");
      break;

      case 3:
      System.out.println("Go Back 3 Spaces");
      break;

      case 4:
      System.out.println("Make General Repairs on all Your Property. Pay $50 to get a consultation.");
      break;

      case 5:
      System.out.println("Speeding fine $15");
      break;

      case 6:
      System.out.println("You are Elected Chairman of the Board. Pay $50.");
      break;

      case 7:
      System.out.println("Your Building Loan Matures. Collect $150.");
      break;

      case 8:
      System.out.println("You won the lottery! Collect $300!");
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
    String deckName="Chance"; // returns that this is the Chance deck
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
      case 4:
      lost=50;
      break;

      case 5:
      lost=15;
      break;

      case 6:
      lost=50;
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
      case 1:
      gain=200;
      break;

      case 2:
      gain=50;
      break;

      case 7:
      gain=150;
      break;

      case 8:
      gain=300;
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
    switch (cardNum){
      case 3: // applies to only case 3, where squares are changed
      move=3;
      break;
    }
    return move;
  }
}