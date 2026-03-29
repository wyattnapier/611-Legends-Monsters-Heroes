package GameStructure;

import java.util.Scanner;

public class IO {
  public static final String validMoveOptions = "wasdicmqh";
  public static final String nextMoveList = "Pleae input your next move:\n" + "W/A/S/D - move\n"
      + "I/C - manage inventory (view info, equip/use items)\n" + "M - enter market if on market tile\n"
      + "Q - quit game\n" + "H - Help/Information\n" + "Your move --> ";
  private Scanner sc;

  public IO(Scanner scan) {
    sc = scan;
  }

  // -------------------------- inputs

  /**
   * get the next move from the player
   * 
   * @return one char move
   */
  public String getNextMove() {
    String selection;
    System.out.print(nextMoveList);
    selection = sc.next();
    selection = selection.toLowerCase().trim();
    if (selection.length() == 1 && validMoveOptions.contains(selection)) {
      return selection;
    } else {
      System.out.println("Invalid selection, please try again\n");
      return getNextMove();
    }
  }

  /**
   * gets the users name
   * 
   * @return String of player's name
   */
  public String getPlayerName() {
    System.out.print("Enter your name: ");
    String name = sc.next();
    return name;
  }

  /**
   * gets the size of the party (1-3)
   * 
   * @return int size of party
   */
  public int getPartySize() {
    System.out.print("Pick your party size [1-3]: ");
    String partySizeString = sc.next();
    int partySize = 0;
    try {
      partySize = Integer.parseInt(partySizeString);
    } catch (NumberFormatException e) {
      System.out.println("Must input an int. Try again.");
      return getPartySize();
    }
    if (1 <= partySize && partySize <= 3) {
      return partySize;
    } else {
      System.out.println("Invalid party size. Try again.");
      return getPartySize();
    }
  }

  // -------------------------- outputs

  /**
   * print the game description and intro
   */
  public void printGameIntro(String playerName) {
    String gameIntro = "\nHi " + playerName
        + ", welcome to my legends, monsters, and heroes game! It is pretty self-explanatory -- just wander around and kill some monsters to level up your heroes and their equipment. Have fun!";
    System.out.println(gameIntro);
  }
}
