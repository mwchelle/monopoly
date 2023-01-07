import java.util.*;

/*
  Player class: Contains all the functions of the player, allowing them to take their turn and operate their accounts.
*/
public class Player {
  private int dice1;
  private int dice2;
  public int balance;
  public int position;
  private boolean inJail;
  private int jailCounter;
  private ArrayList<Integer> inventory;     //arraylist that contains all properties owned by the player
  private boolean endTurn;
  private Scanner sc = new Scanner(System.in);
  protected Board monopolyBoard;              //board object which the players move on

  /*
    constructor
    pre: none 
    post: Player object is created with a default balance of 1500 and position on 0(Go tile)
  */
  public Player() {
    dice1 = 0;            //initialize dice
    dice2 = 0;
    balance = 1500;            
    position = 0;
    inJail = false;        //default player as not in jail
    jailCounter = 0;
    inventory = new ArrayList<Integer>();     //initialize inventory array list
    endTurn = false;
    monopolyBoard = new Board();          //initialize board object
  }

  /*
    Prompts the user to take their turn
    pre: none
    post: User takes their turn and is prompted actions.
  */
  public void turn() {

    playerRoll();                        //allows player to roll dice
    int totalRoll = rollDice();          //rolls dice and calculates their combined value
    System.out.println("");
    System.out.println("You have rolled a " + dice1 + " and a " + dice2);       //prints dice values
    System.out.println("");

    checkInJail();                //checks if player is in jail

    if (inJail == false) {       //moves the player if the player is not in jail
      playerMove(totalRoll);
    }

    switch(position) {           //switch statement to check which type of tile the user is on based on their position
      case 2:                    //community chest tiles
      case 17:
      case 33:
        System.out.println("You have landed on Community Chest!\n");
        Community communityChestCard = new Community((int) (6 * Math.random() + 1));         //creates new random community chest card
        communityChestCard.readName();                                  //reads card names from communitynames.txt
        System.out.println(communityChestCard.getName() + "\n");      //prints card name
        communityChestCard.getDescription();                        //prints card description
        balance += communityChestCard.moneyGained();                //adds or removes money from player balance based on the car
        balance -= communityChestCard.moneyLost();
        System.out.println("");
        endTurn();                                             //end turn interface
        break;
      case 7:                    //chance tiles
      case 22:
      case 36:
        System.out.println("You have landed on Chance!\n");
        Chance chanceCard = new Chance((int) (8 * Math.random() + 1));             //creates new random chance card
        chanceCard.readName();                                              //reads card names from chancename.txt
        System.out.println(chanceCard.getName() + "\n");                 //prints car name
        chanceCard.getDescription();                            //prints card description
        balance += chanceCard.moneyGained();                   //adds or removes money from player balance based on card
        balance -= chanceCard.moneyLost();
        if (chanceCard.cardNum == 1) {                     //move the player based on card
          position = 0;
        } else {
          position -= chanceCard.move();
        }
        System.out.println("");
        endTurn();                                     //end turn interface
        break;
      case 0:              //go tile
        System.out.println("You are on go");         //Prints go message
        System.out.println("");
        endTurn();                 //end turn interface
        break;
      case 10:             //jail tile
        if (inJail == true) {            //checks if the player is visiting or in jail
          System.out.println("You are still in jail");              //prints respective message based on whether the player is in jail or not
          System.out.println("");
        } else {
          System.out.println("You are visiting jail(Not actually in jail)");
          System.out.println("");
        }
        endTurn();         //end turn interface
        break;
      case 20:       //free parking tile
        System.out.println("You are on free parking");    //prints free parking message
        System.out.println("");
        endTurn();           //end turn interface
        break;
      case 30:            //go to jail tile
        position = 10;      //moves the player to jail
        inJail = true;       //sets inJail to be true
        System.out.println("Oh no! You've been sent to jail!");
        System.out.println("");
        endTurn();       //end turn interface
        break;
      case 4:       //tax tiles
      case 38:
        if (position == 4) {              //based on which tax tile it is, print respective message, and remove respective amount from balance
          System.out.println("Oh no! You have landed on income tax! You have lost $200");
          balance -= 200;
          System.out.println("");
        } else {
          System.out.println("Oh no! You have landed on luxury tax! You have lost $400");
          balance -= 400;
          System.out.println("");
        }
        endTurn();           //end turn interface
        break;
      default:          //deafult to property tile as every other type of tile would have already been found
        balance -= checkOwned();            //removes amount owed if player lands on the computer's property
        if (checkOwned() > 0) {           //checks if any amount is owed and prints message if there is
          System.out.println("Oh no! You have landed on the computer's property and have paid $" + checkOwned());
          System.out.println("");
        }
        boolean propertyTurn = true;         
        while (propertyTurn == true) {                //loop for property interface
          System.out.println("It's your turn!\nYou are currentyly on: " + monopolyBoard.properties[position].name + "\nYour balance is: $" + balance + "\n1. Print board\n2. Buy Property\n3. Buy Houses\n4. Check owned properties\nEnter 0 to end your turn");  //prints property interface
          System.out.println("");
          int propertyInput = sc.nextInt();
          switch (propertyInput) {          //selection for property interface 
            case 1:
              monopolyBoard.printBoard();       //prints graphical image of board
              break;
            case 2:
              buyProperty();        //player buy property method
              break;
            case 3:
              buyHouses();        //player buy houses method
              break;
            case 4:
              checkProperties();
              break;
            case 0:               
              propertyTurn = false;   //breaks out of property loop and ends player turn
              break;
            default:                
              System.out.println("Invalid input, please try again");   //default error message if input is invalid
              System.out.println("");
              break;
          }
        }
        break;
    }
  }

  /*
    Allows the user to roll a dice and move on in the turn
    pre: none
    post: none
  */
  public void playerRoll() {
    boolean rolled = false;
    while (rolled == false) {                  //loop until player has entered the correct input to roll
      System.out.println("Press 1 to roll");
      int userRoll = sc.nextInt();
      if (userRoll == 1) {               //checks if the player has entered 1 to roll the ice
        rolled = true;
      } else {
        System.out.println("Incorrect input, please try again");     //prints erros message for invalid inputs
        System.out.println("");
      }
    }
  }

  /*
    Checks if the player is in jail, allowing them to roll to get out if stuck
    pre: none
    post: inJail is either true or false
  */
  public void checkInJail() {
    if (inJail == true) {
      if (dice1 == dice2 || jailCounter == 3) {             //checks if the user has rolled a double or if they have been in jail for 3 turns already
        inJail = false;
        System.out.println("Congratulations, you have gotten out of jail!");
        jailCounter = 0;               //resets jail counter
        System.out.println("");
      }else{
        jailCounter++;          //increases jail counter if they fail to escape jail
      }
    }
  }

  /*
    Rolls the dice and determines a randomly generated number
    pre: none
    post: total dice values returned
  */
  public int rollDice() {
    dice1 = (int) (6 * Math.random() + 1);        //generates a random number from 1 to 6 for each die
    dice2 = (int) (6 * Math.random() + 1);
    return (dice1 + dice2);             //returns the summed value of both ddice
  }

  /*
    Moves the player by the dice roll
    pre: none
    post: Player position is changed by the dice roll
  */
  public void playerMove(int totalRoll) {
    if (totalRoll + position > 39) {                //if position plus the dice roll exceed the number of tiles, set position back to the respective board value
      position = (totalRoll + position) - 40;
      passGo();          //passGo method as player has passed go in this case
    } else {
      position += totalRoll;        //add dice roll to position of the player
    }
  }

  /*
    Rewards the player for passing the "GO" square
    pre: none
    post: Player balance is increased by $200
  */
  public void passGo() {
    balance += 200;                 //adds 200 to player balance
    System.out.println("You have passed Go and have recieved $200");
    System.out.println("");
  }

  /*
    Allows player to purchase the properties if landed on and unknowed.
    pre: none
    post: property is added to player owned
  */
  public void buyProperty() {
    if (balance >= monopolyBoard.properties[position].cost) {       //checks if players balance is greater that the cost of the property
      if (monopolyBoard.properties[position].playerOwned == true) {       //checks if the player already owns the property
        System.out.println("");
        System.out.println("You already own this property");
        System.out.println("");
      } else if (monopolyBoard.properties[position].computerOwned == true) {    //checks if the computer already owns the property
      System.out.println("");
        System.out.println("The computer already owns this property");
        System.out.println("");
      } else {                 //buying the property
        inventory.add(position);         //adds property to players inventory
        System.out.println("");
        System.out.println("Congratulations, you have bought: " + monopolyBoard.properties[position].name);
        System.out.println("");
        monopolyBoard.properties[position].playerOwned = true;     //sets playerOwned of the property to be true
        balance -= monopolyBoard.properties[position].cost;        //subtracts cost of property from player balance
        boolean set = checkOwnAll(monopolyBoard.properties[position].colour);       //checks if the player owns the set of properties
        if (set == true) {             //if the set is owned, change rent values of each property in the set
          for (int i = 0; i < inventory.size(); i++) {      //loops through the players inventory to find all properties of the same set
            if (monopolyBoard.properties[inventory.get(i)].colour.equals(monopolyBoard.properties[position].colour)) {  //checks if the properties have the same colour
              monopolyBoard.properties[i].rent *= 2;           //double the rent amount as full set is owned
            }
          }
        }
      }
    } else {         //default message if player cannot afford the property
      System.out.println("You can't afford this property");
      System.out.println("");
    }
  }

  /*
    Allows user to purchase houses if they already own the property
    pre: none
    post: Houses are build on-top of property that is already owned
  */
  public void buyHouses() {
    if (inventory.size() == 0) {             //checks if the player doesn't own any properties
      System.out.println("You have no properties to build houses on");
      System.out.println("");
    } else {
      boolean houseChoose = true;
      while (houseChoose == true) {             //loop for house buying menu
        System.out.println("You currently own these properties: \n");   
        for (int i = 0; i < inventory.size(); i++) {             //loops through player inventory to display all owned properties
          System.out.println(monopolyBoard.properties[inventory.get(i)].name + "\n");
        }
        System.out.println("Select the number that corresponds to the property you want to build on(the first property would be number 1, and increases by one for each property down the list)\nEnter 0 to exit property selection");  //house buying interface
        boolean propertyChoice = true;
        while (propertyChoice == true) {      //loops for property selection
          int choice = sc.nextInt();
          if (choice < 0 || choice > inventory.size()) {            //checks if choice is out of bounds
            System.out.println("Invalid choice, please try again");     //displys error message if choice is out of bounds
            System.out.println("");
          } else if (choice == 0) {       //breaks out of loops to return to property menu
            propertyChoice = false;
            houseChoose = false;
            break;
          } else {
            if (balance >= monopolyBoard.properties[inventory.get(choice - 1)].houseCost && monopolyBoard.properties[inventory.get(choice - 1)].numHouses < 5) {       //checks if user balance is greater than the respective house cost, and if there are less than 5 houses on the property
              monopolyBoard.properties[inventory.get(choice - 1)].numHouses++;          //increases number of houses on respective property
              balance -= monopolyBoard.properties[inventory.get(choice - 1)].houseCost;         //decreases balance by cost of house
              System.out.println("Congratulations, you have built 1 more house on " + monopolyBoard.properties[inventory.get(choice - 1)].name);
              System.out.println("There is now " + monopolyBoard.properties[inventory.get(choice - 1)].numHouses + " house(s) on this property");
              System.out.println("");
            } else if (monopolyBoard.properties[inventory.get(choice - 1)].numHouses == 5) {       
              System.out.println("You have already built the maximum amount of houses on this property"); //displays message if there are already 5 houses on the property
              System.out.println("");
            } else {            
              System.out.println("You don't have enough money to build a house on this property"); //displayes message if user cannot afford the house
              System.out.println("");
            }
            propertyChoice = false;   //exits property selection after house is built
          }
        }

      }

    }
  }

  /*
    Checks the ownership of a property and determines the amount of rent owed due to the houses and colour allignment
    pre: none
    post: Amount of money owed
  */
  public int checkOwned() {
    if (monopolyBoard.properties[position].computerOwned == true) {       //checks if the computer owns the tile the player is on
      int moneyOwed = (monopolyBoard.properties[position].rent * (monopolyBoard.properties[position].numHouses + 1));    //if owned by the computer, return amount owed based on property and number of houses
      return moneyOwed;
    } else {
      return 0;        //default return 0 when nothing is owed
    }
  }

  /*
    Prints out all the properties the player owns
    pre: none
    post: Prints all properties owned by the player
  */
  public void checkProperties(){
    if(inventory.size() == 0){
      System.out.println("You own no properties");
    }else{
      System.out.println("You currently own these properties: \n");   
      for (int i = 0; i < inventory.size(); i++) {             //loops through player inventory to display all owned properties
        System.out.println(monopolyBoard.properties[inventory.get(i)].name + "\n");
      }
    }
  }

  /*
    Determines whether all of the colour set is owned
    pre: none
    post: boolean on whether the full set is owned returned
  */
  public boolean checkOwnAll(String pColour) {
    int cnt = 0;
    for (int i = 0; i < inventory.size(); i++) {          //loops through all properties in player's inventory
      if (monopolyBoard.properties[inventory.get(i)].colour == pColour) {         //checks if colours of the properties in player's inventory matches the current colour
        cnt++;         //increases counter if colours match
      }
    }
    if ((pColour.equals("Br") || pColour.equals("DB") || pColour.equals("Ap")) && cnt == 2) {       //checks if the sets with two components are owned 
      return true;
    } else if (pColour.equals("RR") && cnt == 4) {    //checks if the full railroad set is owned
      return true;
    } else if (cnt == 3) {            //checks if the sets with three components are owned
      return true;
    } else {                //default return false if the full set is not owned
      return false;
    }
  }

  /*
    Ends the player's turn once all turn functions are exhausted or executed
    pre: none
    post: turn moves onto the next player
  */
  public void endTurn() {
    do {       //loop for end turn interface
      System.out.println("It's your turn!\nEnter 1 to print the board, 2 to check your properties or 0 to end your turn");        //prints end turn interface and balance
      System.out.println("Your current balance is: $" + balance);
      System.out.println("");
      int userInput = sc.nextInt();
      if (userInput == 0) {                   //ends the players turn
        endTurn = true;
      } else if (userInput == 1) {              //prints a graphic of the board
        monopolyBoard.printBoard();
      } else if(userInput == 2){
        checkProperties();
      } else {
        System.out.println("Invalid input, please try again");
      }
    } while (endTurn == false);
    endTurn = false;    //sets endTurn to false for next loop when playing reaches an end turn interface
  }

}