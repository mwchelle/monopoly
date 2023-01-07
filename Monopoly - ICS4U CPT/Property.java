/*
  Property class: Contains all the values of a property in Monopoly
*/
public class Property{
  String name;                //contains all values of a property in Monopoly
  String colour;
  int position;
  int cost;
  int rent;
  int houseCost;
  int numHouses;
  boolean playerOwned;
  boolean computerOwned;

  /*
    constructor
    pre: none
    post: Property created with respective name, colour, position, cost, rent cost, and house cost. Number of houses is set to 0 by default, ownership from player and computer is also set to false as default
  */
  public Property(String nm, String c, int p, int ct, int r, int hCost){
    name = nm;
    colour = c;
    position = p;
    cost = ct;
    rent = r;
    houseCost = hCost;
    numHouses = 0;
    playerOwned = false;
    computerOwned = false;
  }
  
}