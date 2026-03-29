package Structure;

import java.util.List;
import java.util.Scanner;

import Fighters.Heros.Hero;

public class IO {
  public static final String validMoveOptions = "wasdicqh";
  public static final String nextMoveListWithMarket = "Please input your next move:\n" + "W/A/S/D - move\n"
      + "I/C - manage inventory (view info, equip/use items)\n" + "M - enter market\n"
      + "Q - quit game\n" + "H - Help/Information\n" + "Your move --> ";
  public static final String nextMoveListWithoutMarket = "Please input your next move:\n" + "W/A/S/D - move\n"
      + "I/C - manage inventory (view info, equip/use items)\n" + "Q - quit game\n" + "H - Help/Information\n"
      + "Your move --> ";
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
  public String getNextMove(boolean isOnMarketSpace) {
    String selection;
    if (isOnMarketSpace) {
      System.out.print(nextMoveListWithMarket);
    } else {
      System.out.print(nextMoveListWithoutMarket);
    }
    selection = sc.next();
    selection = selection.toLowerCase().trim();
    if (selection.length() == 1
        && (validMoveOptions.contains(selection) || (selection.equals("m") && isOnMarketSpace))) {
      System.out.println();
      return selection;
    } else {
      System.out.println("Invalid selection, please try again\n");
      return getNextMove(isOnMarketSpace);
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

  /**
   * pick a type of hero for instantiation
   */
  public String getHeroType() {
    System.out.print("Pick your hero type!\n" + "[s] Sorcerer (favored on dexterity and agility)\n"
        + "[p] Paladin (favored on strength and dexterity)\n" + "[w] Warrior (favored on strength and agility)\n"
        + "Enter your choice --> ");
    String heroSelection = sc.next().toLowerCase().trim();
    if (heroSelection.equals("s") || heroSelection.equals("p") || heroSelection.equals("w")) {
      return heroSelection;
    } else {
      System.out.println("Invalid selection. Please just enter a single character from the provided options.\n");
      return getHeroType();
    }
  }

  /**
   * get the index of an item in a list or go back
   * 
   * @param inputList is the list
   * @param canGoBack show back as an options
   * @param itemsType the type of items e.g. "" or "Items"
   * @return valid in bound index
   */
  public int getValidListIndex(List<? extends Object> inputList, boolean canGoBack, String itemsType) {
    if (!canGoBack && inputList.size() == 1) {
      return 0; // no other options anyways
    }
    StringBuilder sb = new StringBuilder("Pick a " + itemsType + " from the list:\n");
    for (int i = 0; i < inputList.size(); i++) {
      sb.append("(" + i + ") - " + inputList.get(i) + "\n");
    }
    if (canGoBack) {
      sb.append("(B) - Go back\n");
    }
    sb.append("Your choice --> ");
    System.out.print(sb.toString());
    try {
      String inputString = sc.next().toLowerCase().trim();
      if (canGoBack && inputString.equals("b")) {
        System.out.println();
        return -1;
      }
      int inputInt = Integer.parseInt(inputString);
      if (0 <= inputInt && inputInt < inputList.size()) {
        System.out.println();
        return inputInt;
      }
      throw new Exception("Input int is out of bounds.");
    } catch (Exception e) {
      System.out.println("Invalid selection. Please enter a valid int.\n");
      return getValidListIndex(inputList, canGoBack, itemsType);
    }
  }

  /**
   * pick an action in the market
   * 
   * @return
   */
  public String getMarketAction() {
    System.out.print("Select an action for your turn at the market!\n" + "(B) buy an item from the market\n"
        + "(S) sell an item that you don't have equipped\n" + "(R) repair an item\n" + "(E) exit the market\n"
        + "(Q) quit the game\n"
        + "Your selection --> ");
    String action = sc.next().toLowerCase().trim();
    if (action.equals("b") || action.equals("s") || action.equals("r") || action.equals("e") || action.equals("q")) {
      System.out.println();
      return action;
    } else {
      System.out.println("Invalid action selection. Try again.\n");
      return getMarketAction();
    }
  }

  // -------------------------- outputs

  /**
   * print the game description and intro
   */
  public void printGameIntro(String playerName) {
    String gameIntro = "Hi " + playerName
        + ", welcome to my legends, monsters, and heroes game! It is pretty self-explanatory -- just wander around and kill some monsters to level up your heroes and their equipment. Have fun!\n";
    System.out.println(gameIntro);
  }
}
