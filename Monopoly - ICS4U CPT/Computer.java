import java.util.*;

/*
  Computer class: A subclass of the player class which allows the operations of the computer opponent in the game.
*/
public class Computer extends Player{      //extends player so both classes can share the same board object
  private int cDice1;
  private int cDice2;
  public int cBalance;
  public int cPosition;
  private boolean cInJail;
  private int cJailCounter;
  private ArrayList<Integer> cInventory;           //arraylist to conatain all properties owned by the computer

  /*
    constructor
    pre: none 
    post: Computer object is created with a default balance of 1500 and position on 0(Go tile)
  */
  public Computer(){
    cDice1 = 0;       //initialize dice for computer
    cDice2 = 0;
    cBalance = 1500;
    cPosition = 0;
    cInJail = false;       //default computer as not in jail
    cJailCounter = 0;
    cInventory = new ArrayList<Integer>();      //initialize computer inventory
  }

  /*
    Execute's the computer's turn
    pre: none
    post: Computer takes turn.
  */
  public void computerTurn(){
    int totalRoll = cRollDice();            //finds combined value for both rolled dice
    System.out.println("");
    System.out.println("The computer has rolled a " + cDice1 + " and a " + cDice2);       //shows the dice the computer rolled
    System.out.println("");

    cCheckInJail();           //checks if the computer is in jail
    
    if(cInJail == false){         //moves the computer if they are not in jail
      computerMove(totalRoll);
    }

    switch(cPosition){       //switches computers position
      case 2: case 17: case 33:           //community chest tiles
        System.out.println("The computer has landed on Community Chest!\n");
        System.out.println("");
        Community communityChestCard = new Community((int) (6 * Math.random() + 1));    //generates random community chest card
        communityChestCard.readName();                           //reads card names from communitynames.txt
        System.out.println(communityChestCard.getName() + "\n");      //prints card name
        communityChestCard.getDescription();                       //prints card description
        cBalance += communityChestCard.moneyGained();           //changes computers balance based on card
        cBalance -= communityChestCard.moneyLost();
        System.out.println("");
        break;
      case 7: case 22: case 36:                    //chance tiles
        System.out.println("The computer has landed on Chance!\n");
        System.out.println("");
        Chance chanceCard = new Chance((int) (8 * Math.random() + 1));             //generates random chance card
        chanceCard.readName();                                       //reads card names from chancenames.txt
        System.out.println(chanceCard.getName() + "\n");          //prints card name
        chanceCard.getDescription();                         //prints card description
        cBalance += chanceCard.moneyGained();                //changes computer balance based on card
        cBalance -= chanceCard.moneyLost();
        if(chanceCard.cardNum == 1){                  //moves computer accordingly based on card
          cPosition = 0;
        }else{
          cPosition -= chanceCard.move();
        }
        break;
      case 0:            //go tile
        System.out.println("The computer is on go");       //prints the computer is on go
        System.out.println("");
        break;
      case 10:           //jail tile
        if(cInJail == true){           //prints respective message based on whether the computer is in jail or just visiting
          System.out.println("The computer is still in jail");
          System.out.println("");
        }else{
          System.out.println("The computer is visiting jail(Not actually in jail)");
          System.out.println("");
        }
        break;
      case 20:        //free parking tile
        System.out.println("The computer is on free parking");       //prints computer is on free parking
        System.out.println("");
        break;
      case 30:         //go to jail tile
        cPosition = 10;         //moves the computer to jail
        cInJail = true;         //sets cInJail to be true
        System.out.println("Yes! The computer has been sent to jail!");     //prints computer is in jail
        System.out.println("");
        break;
      case 4: case 38:    //tax tiles
        if(cPosition == 4){             //prints respective message and removes amount from computer's balance based on which tax tile it is
          System.out.println("Yes! The computer has landed on income tax! They have lost $200");
          cBalance -= 200;
          System.out.println("");
        }else{
          System.out.println("Yes! The computer has landed on luxury tax! They have lost $400");
          cBalance -= 400;
          System.out.println("");
        } 
        break;
      default:      //deafult to property tile as every other tile has been covered
        System.out.println("The computer is on " + monopolyBoard.properties[cPosition].name);    //prints computer location
        cBalance -= cCheckOwned();             //removes amount from balance if computer lands on a player's property
        System.out.println("");
        if(cCheckOwned() > 0){            //prints message and amount owed if computer lands on player's property
          System.out.println("Yes! The computer has landed on the your property and has paid $" + checkOwned());
          System.out.println("");
        }
        cBuyProperty();       //computer buy property process
        cBuyHouses();        //computer buy houses process
        break;
    }
  }

  /*
    Checks if the computer is in jail, allowing them to roll to get out if stuck
    pre: none
    post: boolean on whether the computer is still in jail returned
  */
  public void cCheckInJail(){
    if(cInJail == true){                     //checks if the computer is in jail 
      if(cDice1 == cDice2 || cJailCounter == 3){                  //checks if the computer has rolled a double or has been in jail for 3 turns already
        cInJail = false;                 //removes computer from jail
        System.out.println("The computer has gotten out of jail");       
        cJailCounter = 0;         //resets jail counter
        System.out.println("");
      }else{
        cJailCounter++;         //increases jail counter by 1 if computer fails to escape ail
      }
    }
  }

  /*
    Rolls the dice and determines a randomly generated number
    pre: none
    post: combined value of rolled dice returned
  */
  public int cRollDice(){
    cDice1 = (int) (6 * Math.random() + 1);       //randomly generate a number from 1 to 6 for each dice
    cDice2 = (int) (6 * Math.random() + 1);
    return (cDice1 + cDice2);         //return combined value of dice
  }

  /*
    Moves the computer by the dice roll
    pre: none
    post: Computer position is changed by the dice roll
  */
  public void computerMove(int totalRoll){
    if(totalRoll + cPosition > 39){                //if position plus the dice roll exceed the number of tiles, set position back to the respective board value
      cPosition = (totalRoll+ cPosition) - 40;
      cPassGo();         //passGo method as computer has passed go in this case
    }else{
      cPosition += totalRoll;  //add dice roll to position of the computer
    }
  }

  /*
    Rewards the computer for passing the "GO" square
    pre: none
    post: Computer balance is increased by $200
  */
  public void cPassGo(){
    balance += 200;                //adds 200 to computer balance
    System.out.println("The computer has passed Go and has recieved $200");
    System.out.println("");
  }
  
  /*
    Allows computer to purchase the properties if landed on and unknowed.
    pre: none
    post: property is added to computer owned
  */
  public void cBuyProperty(){
    if(cBalance >= monopolyBoard.properties[cPosition].cost){        //checks if computer balance is greater than property cost
      if(monopolyBoard.properties[cPosition].playerOwned == false && monopolyBoard.properties[cPosition].computerOwned == false){    //checks if property is already ownedd
        int randomBuy = (int) (2 * Math.random() + 1);     //random number generator from 1 to 2, computer has 50% chance to buy property if possible
        if(randomBuy == 1){
          cInventory.add(cPosition);          //adds property to computer's inventory
          System.out.println("The computer has bought: " + monopolyBoard.properties[cPosition].name);
          System.out.println("");
          monopolyBoard.properties[cPosition].computerOwned = true;       //sets computerOwned to true for the property
          cBalance -= monopolyBoard.properties[cPosition].cost;         //subtract cost of property from computer's balance
          boolean set = checkOwnAll(monopolyBoard.properties[cPosition].colour);    //checks if the computer owns the set of properties
          if(set == true){              //if the set is owned, change rent values of each property in the set
            for(int i = 0; i < cInventory.size(); i++){           //loops through the computer's inventory to find all properties of the same set
              if(monopolyBoard.properties[cInventory.get(i)].colour.equals(monopolyBoard.properties[cPosition].colour)){  //checks if the properties have the same colour
              monopolyBoard.properties[i].rent *= 2;    //double the rent amount as full set is owned
              }
            }
          }
        } 
      }
    }
  }

   /*
    Checks the ownership of a property and determines the amount of rent owed due to the houses and colour allignment
    pre: none
    post: money owed returned
  */
  public int cCheckOwned(){
    if(monopolyBoard.properties[cPosition].playerOwned == true){   //checks if the player owns the tile the computer is on
      int moneyOwed = (monopolyBoard.properties[cPosition].rent *(monopolyBoard.properties[cPosition].numHouses + 1));  //if owned by the player, return amount owed based on property and number of houses
      return moneyOwed;
    }else{
      return 0;         //default return 0 when nothing is owed
    }
  }

  /*
    Determines whether all of the colour set is owned
    pre: none
    post: boolean on whether the full set is owned returned
  */
  public boolean cCheckOwnAll(String pColour){
    int cnt = 0;
    for(int i = 0; i < cInventory.size(); i++){   //loops through all properties in computers's inventory
      if(monopolyBoard.properties[cInventory.get(i)].colour == pColour){   //checks if colours of the properties in computer's inventory matches the current colour
        cnt++;             //increases counter if colours match
      }
    }
    if((pColour.equals("Br") || pColour.equals("DB") || pColour.equals("Ap")) && cnt == 2){    //checks if the sets with two components are owned 
      return true;
    }else if(pColour.equals("RR") && cnt == 4){         //checks if the full railroad set is owned
      return true;
    }else if(cnt == 3){                //checks if the sets with three components are owned
      return true;
    }else{
      return false;             //default return false if the full set is not owned
    }
  }

  /*
    Allows computer to purchase houses if they already own the property
    pre: none
    post: Houses are build on-top of property that is already owned
  */
  public void cBuyHouses(){
    if(cInventory.size() > 0){        //checks if the computer owns any properties
      int houseGen = (int) (cInventory.size() * Math.random() + 1);        //generates a random number to pick a property owned by the computer
      if(cBalance >= monopolyBoard.properties[cInventory.get(houseGen-1)].cost){     //checks if the computer's balance is greater than the cost of a house
        int cBuy = (int) (3 * Math.random() + 1);         //generates from number from 1 to 3 so computer has a 33% chance of buying a house
        if(cBuy == 1 && monopolyBoard.properties[cInventory.get(houseGen-1)].numHouses < 5){        //if computer rolls the 33% chance and the number of houses if less than 5
          monopolyBoard.properties[cInventory.get(houseGen-1)].numHouses++;       //increase house counter for property selected
          cBalance -= monopolyBoard.properties[cInventory.get(houseGen-1)].houseCost;      //subtracts house cost from computer's balance
          System.out.println("The computer has built 1 more house on " + monopolyBoard.properties[cInventory.get(houseGen-1)].name);
          System.out.println("There is now " + monopolyBoard.properties[cInventory.get(houseGen-1)].numHouses + " house(s) on this property");
          System.out.println("");
        }
      }
    }
  }
}