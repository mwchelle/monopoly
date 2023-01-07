import java.io.*;

/*
  Board class: Contains a list of properties at their respective locations on the board
*/
public class Board{
  private File file = new File("gameboard.txt");
  private FileReader fr;
  private BufferedReader br;
  private String currentLine;
  Property[] properties;       //array of properties that represents the board with only the properties placed on it

  //Declares every single property as an objects with it own stats(i.e. name, price, rent, house cost, etc), which has resemblence to the actual monopoly board
  Property ifStatementAvenue = new Property("If-Statement Avenue", "Br", 1, 60, 2, 50);         //Brown properties
  Property elseStatementAvenue = new Property("Else-Statement Avenue", "Br", 3, 60, 4, 50);

  Property whileLoopAvenue = new Property("While-Loop Avenue", "LB", 6, 100, 6, 50);           //Light blue properties
  Property doWhileLoopAvenue = new Property("DoWhile-Loop Avenue", "LB", 8, 100, 6, 50);
  Property forLoopAvenue = new Property("For-Loop Avenue", "LB", 9, 120, 8, 50);

  Property methodsPlace = new Property("Methods Place", "Pi", 11, 140, 10, 100);              //Pink properties
  Property parametersAvenue = new Property("Parameters Avenue", "Pi", 13, 140, 10, 100);
  Property overloadingAvenue = new Property("Overloading Avenue", "Pi", 14, 160, 12, 100);

  Property integerAvenue = new Property("Integer Avenue", "Or", 16, 180, 14, 100);           //Orange properties
  Property stringAvenue = new Property("String Avenue", "Or", 18, 180, 14, 100);
  Property booleanAvenue = new Property("Boolean Avenue", "Or", 19, 200, 16, 100);

  Property classAvenue = new Property("Class Avenue", "Re", 21, 220, 18, 150);               //Red properties
  Property objectsAvenue = new Property("Objects Avenue", "Re", 23, 220, 18, 150);
  Property OOPAvenue = new Property("OOP Avenue", "Re", 24, 240, 20, 150);

  Property inheritanceAvenue = new Property("Inheritance Avenue", "Ye", 26, 260, 22, 150);   //Yellow properties
  Property polymorphismAvenue = new Property("Polymorphism Avenue", "Ye", 27, 260, 22, 150);
  Property abstractAvenue = new Property("Abstract Avenue", "Ye", 29, 280, 24, 150);

  Property arrayAvenue = new Property("Array Avenue", "Gr", 31, 300, 26, 200);               //Green properties
  Property arraylistAvenue = new Property("ArrayList Avenue", "Gr", 32, 300, 26, 200);
  Property gridAvenue = new Property("Grid Avenue", "Gr", 34, 320, 28, 200);

  Property filesPlace = new Property("Files Place", "DB", 37, 350, 35, 200);                //Dark blue properties
  Property exceptionsWalk = new Property("ExceptionsWalk", "DB", 39, 400, 50, 200);

  Property flowchartCompany = new Property("FlowChart Company", "Ap", 12, 150, 10, 0);      //Utilities properties
  Property pseudocodeWorks = new Property("Pseudocode Works", "Ap", 28, 150, 10, 0);

  Property randomRailroad = new Property("Random Railroad", "RR", 5, 200, 50, 0);           //Railroad properties
  Property cryptoRailroad = new Property("Crypto Railroad", "RR", 15, 200, 50, 0);
  Property hardwareRailroad = new Property("Hardware Railroad", "RR", 25, 200, 50, 0);
  Property phonesRailroad = new Property("Phones Railroad", "RR", 35, 200, 50, 0);

  /*
    contructor
    pre: none
    post: Board object created with an array representing the board, and all properties placed at their respective spots
  */
  public Board(){
    properties = new Property[40];            //length 40 as there are 40 tiles total
    properties[1] = ifStatementAvenue;        //placing each property on their respective tile
    properties[3]  = elseStatementAvenue;
    properties[6]  = whileLoopAvenue;
    properties[8]  = doWhileLoopAvenue;
    properties[9]  = forLoopAvenue;
    properties[11]  = methodsPlace;
    properties[13]  = parametersAvenue;
    properties[14]  = overloadingAvenue;
    properties[16]  = integerAvenue;
    properties[18]  = stringAvenue;
    properties[19]  = booleanAvenue;
    properties[21]  = classAvenue;
    properties[23]  = objectsAvenue;
    properties[24]  = OOPAvenue;
    properties[26]  = inheritanceAvenue;
    properties[27]  = polymorphismAvenue;
    properties[29]  = abstractAvenue;
    properties[31]  = arrayAvenue;
    properties[32]  = arraylistAvenue;
    properties[34]  = gridAvenue;
    properties[37]  = filesPlace;
    properties[39]  = exceptionsWalk;
    properties[12]  = flowchartCompany;
    properties[28]  = pseudocodeWorks;
    properties[5]  = randomRailroad;
    properties[15]  = cryptoRailroad;
    properties[25]  = hardwareRailroad;
    properties[35]  = phonesRailroad;
  }

  /*
    Prints a visual of the gameboard as a reference for the user
    pre: none
    post: A graphic of the gameboard with each tile named printed, catches any errors that occur
  */
  public void printBoard(){
    try{
      fr = new FileReader(file);             //initialize readers
      br = new BufferedReader(fr);
      while((currentLine = br.readLine()) != null){     //keep reading until there is no more data to read
        System.out.println(currentLine + "\n");         //prints each line of the board with correct spacing
      } 
      fr.close();                           //close readers
      br.close();
    }catch (FileNotFoundException e) {    //catches exception if the file is not found
      System.out.println("File named " + file + " not found. " + e);
    }catch (IOException e) {           //catches any other IO exceptions
      System.out.println("An error has occured.");
      System.out.println(e.getMessage());
    }
  }

}