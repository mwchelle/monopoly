/* 
The Cards class declares all key information shared between cards in monopoly.
*/
abstract class Cards{
  protected String[] cardNames; // array for the card names/descriptions
  protected String[] cardDescriptions;
  protected int deckSize; // total cards per deck
  protected String deckName;
  protected int cardNum; // generates a card number for each unique pull

  /*
  This is the constructor, it assigns the deck information to the object
  pre: none 
  post: The card decks are created
  */
  public Cards(int number){
    deckSize=6; // 12 per deck
    cardNum=number;
  }

  /*
  This method provides reads the possible cards for each deck
  pre: none 
  post: names are read
  */
  abstract void readName();


  /*
  This method returns the name of the card pulled
  pre: none 
  post: name is returned
  */
  abstract String getName();

  /*
  This method returns the description of the card pulled
  pre: none 
  post: description is returned
  */
  abstract void getDescription(); 

  /*
  This method returns the deckname
  pre: none 
  post: deckname is returned
  */
  abstract String getDeckname();

  /*
  This method returns the balance lost
  pre: none 
  post: moneylost is returned
  */
  abstract int moneyLost();

  /*
  This method returns the balance gained
  pre: none 
  post: moneygained is returned
  */
  abstract int moneyGained();

  /*
  This method returns the move
  pre: none 
  post: move is returned
  */
  abstract int move();
}