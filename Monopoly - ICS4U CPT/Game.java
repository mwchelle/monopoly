import java.util.*;
import java.io.*;

/*
  Game class: Creates the opening menu for Speed Monopoly as well as the main game loop for when the game is running
*/
public class Game{
  private File instructions = new File("instructions.txt");  //file for printing intructions
  private File menuScreen = new File("splashscreen.txt"); // file for printing splash screen
  private FileReader fr;
  private BufferedReader br;
  private String currentLine;

  boolean gameOver;
  Player player;            
  Computer computer;          
  private Scanner sc = new Scanner(System.in);

  /*
    constructor
    pre: none 
    post: Game object created with one player and one computer
  */
  public Game(){
    gameOver = false;         //gameOver is set to false by default
    player = new Player();          //initialize player object
    computer = new Computer();      //initialize computer object
  }

  /*
    Creates the opening menu for the user to interact with
    pre: none
    post: menu interface created with the option to play the game, read the intructions, or exit
  */
  public void menu(){
    splashScreen();
    System.out.println("Welcome to Chen's Speed Monopoly!");
    System.out.println("What would you like to do:\n1. Play\n2. Read Instructions\n3. Exit Game");     //prints the interface
    int selection = sc.nextInt();
    System.out.println("");
    switch(selection){         //selection statement for menu
      case 1:            
        runGame();       //starts the game
        menu();          //loops to menu after game is over
        break;
      case 2:            
        instructions();  //prints the instructions to Speed Monopoly
        menu();          //loops to menu after intructions are printed so 
        break;
      case 3:
        System.out.println("Thanks for visiting!");       //prints exit message
        break;
      default:
        System.out.println("Invaild selection, please try again");     //defaults to this error message if input is not valid
        System.out.println("");
        menu();                     //loops back to menu to allow user to try again
    }
  }

  /*
    Main game loop to run the player's turn and computer's turn
    pre: none
    post: Speed Monopoly game is created and run
  */
  public void runGame(){
    while(gameOver == false){       //loops the game until one player loses/is bankrupt
      player.turn();                //player turn
      if(player.position != 0 && player.position != 2 && player.position != 4 && player.position != 7 && player.position != 10 && player.position != 17 && player.position != 20 && player.position != 22 && player.position != 30 && player.position != 33 && player.position != 36 && player.position != 38){          //only runs if the player landed on a property tile to prevent errors
        computer.cBalance += player.checkOwned();        //Adds the amount owed to the computer if there is any amount owed
      }
      boolean noMoney = checkGameOver();            //checks if the game is over(if user lost)
      if(noMoney == true){                          //if true, print losing message and break out of game loop
        System.out.println("Oh no! You have run out of money! You have lost! :(");
        System.out.println("");
        break;
      }
      computer.computerTurn();          //computer turn
      if(computer.cPosition != 0 && computer.cPosition != 2 && computer.cPosition != 4 && computer.cPosition != 7 && computer.cPosition != 10 && computer.cPosition != 17 && computer.cPosition != 20 && computer.cPosition != 22 && computer.cPosition != 30 && computer.cPosition != 33 && computer.cPosition != 36 && computer.cPosition != 38){        //only runs if the computer landed on a property tile to prevent errors
        player.balance += computer.cCheckOwned();              //Adds the amount owed the the player if there is any amount owed
      }
      noMoney = checkGameOver();              //checks if the game is over(if computer lost);
      if(noMoney == true){                    //if true, print winning message and break out of game loop
        System.out.println("The computer has run out of money! Congratulations, you have won!");
        System.out.println("");
        break;
      }
    }

  }

  /*
    Checks both player's balances to ensure that neither are bankrupt and the game may continue
    pre: none
    post: boolean determining if game is over returned
  */
  private boolean checkGameOver(){
    if (player.balance < 0 || computer.cBalance < 0){         //checks if either the player's or computer's balance is less than 0
      return true;
    }else{                              //default return false if balances are both above 0
      return false;
    }
  }

  /*
    Instructions are printed out for the player, outlining how speed Monopoly works.
    pre: none
    post: Instructions are printed
  */
  public void instructions(){
    try{
      fr = new FileReader(instructions);             //initialize readers
      br = new BufferedReader(fr);
      while((currentLine = br.readLine()) != null){     //keep reading until there is no more data to read
        System.out.println(currentLine + "\n");         //Prints each line of the intructions
      } 
      fr.close();                           //close readers
      br.close();
    }catch (FileNotFoundException e) {    //catches exception if the file is not found
      System.out.println("File named " + instructions + " not found. " + e);
    }catch (IOException e) {           //catches any other IO exceptions
      System.out.println("An error has occured.");
      System.out.println(e.getMessage());
    }
  }

  public void splashScreen(){
    try{
      fr = new FileReader(menuScreen);             //initialize readers
      br = new BufferedReader(fr);
      while((currentLine = br.readLine()) != null){     //keep reading until there is no more data to read
        System.out.println(currentLine + "\n");         //Prints each line of the intructions
      } 
      fr.close();                           //close readers
      br.close();
    }catch (FileNotFoundException e) {    //catches exception if the file is not found
      System.out.println("File named " + menuScreen + " not found. " + e);
    }catch (IOException e) {           //catches any other IO exceptions
      System.out.println("An error has occured.");
      System.out.println(e.getMessage());
    }
  }

}