package Structure;

import java.util.List;
import java.util.Scanner;

import Fighters.Heros.Hero;
import Items.Equippable;
import Items.Weapon;

public class IO {
  public static final String validMoveOptions = "wasdiqh";
  public static final String nextMoveListWithMarket = "Please input your next move:\n" + "W/A/S/D - move\n"
      + "I - manage inventory (view info, equip/use items)\n" + "M - enter market\n"
      + "Q - quit game\n" + "H - help/information\n" + "Your move --> ";
  public static final String nextMoveListWithoutMarket = "Please input your next move:\n" + "W/A/S/D - move\n"
      + "I - manage inventory (view info, equip/use items)\n" + "Q - quit game\n" + "H - help/information\n"
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
    selection = sc.nextLine();
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
    String name = sc.nextLine();
    return name;
  }

  /**
   * gets the size of the party (1-3)
   * 
   * @return int size of party
   */
  public int getPartySize() {
    System.out.print("Pick your party size [1-3]: ");
    String partySizeString = sc.nextLine();
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
    String heroSelection = sc.nextLine().toLowerCase().trim();
    if (heroSelection.equals("s") || heroSelection.equals("p") || heroSelection.equals("w")) {
      return heroSelection;
    } else {
      System.out.println("Invalid selection. Please just enter a single character from the provided options.\n");
      return getHeroType();
    }
  }

  /**
   * get the index of an item in a list or go back for marketplace and getting a
   * hero
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
    System.out.println("Pick a " + itemsType + " from the list:");
    printListWithIndices(inputList);
    if (canGoBack) {
      System.out.println("(B) - Go back");
    }
    System.out.print("Your choice --> ");
    try {
      String inputString = sc.nextLine().toLowerCase().trim();
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

  public Object[] getListIndexorEquippingOptionForManagingInventory(Hero h) {
    Inventory inventory = h.getInventory();
    while (true) {
      System.out.println(h.toLongStringWithInventory());
      if (inventory.size() == 0) {
        return new Object[] { "b", Integer.valueOf(-1) };
      }
      System.out.println("Actions you can do with those items:");
      System.out.print("(E #) equip item of specified number\n" + "(E2 #) equip item with two hands\n"
          + "(U #) unequip item of specified number\n"
          + "(R #) remove item of specified number from inventory\n" + "(B) go back\n" + "(Q) quit game\n"
          + "Your choice --> ");

      String input = sc.nextLine().trim().toLowerCase();

      try {
        if (input.equals("b"))
          return new Object[] { "b", Integer.valueOf(-1) };
        if (input.equals("q"))
          return new Object[] { "q", Integer.valueOf(-1) };

        String[] parts = input.split(" ");
        if (parts.length != 2)
          throw new Exception();

        int index = Integer.parseInt(parts[1]);
        if (index < 0 || index >= inventory.size())
          throw new Exception();

        switch (parts[0]) {
          case "r":
            return new Object[] { "r", Integer.valueOf(index) };
          case "e":
            if (!(inventory.get(index) instanceof Equippable)) {
              System.out.print("Cannot equip spells and potions. ");
              throw new Exception();
            }
            return new Object[] { "e", Integer.valueOf(index) };
          case "e2":
            if (inventory.get(index) instanceof Weapon) {
              return new Object[] { "e2", Integer.valueOf(index) };
            } else {
              System.out.print("Cannot equip this item in two hands. ");
              throw new Exception();
            }
          case "u":
            if (!(inventory.get(index) instanceof Equippable)) {
              System.out.print("Cannot unequip spells and potions. ");
              throw new Exception();
            }
            return new Object[] { "u", Integer.valueOf(index) };
        }

      } catch (Exception e) {
        System.out.println("Invalid selection.\n");
      }
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
    String action = sc.nextLine().toLowerCase().trim();
    if (action.equals("b") || action.equals("s") || action.equals("r") || action.equals("e") || action.equals("q")) {
      System.out.println();
      return action;
    } else {
      System.out.println("Invalid action selection. Try again.\n");
      return getMarketAction();
    }
  }

  public String getHeroBattleAction(Hero h) {
    while (true) {
      System.out.println(h.toLongStringWithInventory());
      System.out.print("Select an action for your turn at battle\n" + "(A) - attack a monster with an equipped weapon\n"
          + "(M) - cast a magic spell\n" + "(P) - use a potion\n" + "(I) - manage inventory\n"
          + "(S) - show stats for awake fighters\n" + "(Q) - quit\n" + "Your choice --> ");
      String input = sc.nextLine().trim().toLowerCase();
      if (input.length() == 1 && "iaspmq".contains(input)) {
        return input;
      } else {
        System.out.println("Invalid input. Try again.\n");
      }
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

  /**
   * @param inputList list to print
   */
  public static void printListWithIndices(List<? extends Object> inputList) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < inputList.size(); i++) {
      sb.append("(" + i + ") - " + inputList.get(i) + "\n");
    }
    System.out.print(sb.toString());
  }

  public void printHelpMenu() {
    StringBuilder sb = new StringBuilder();
    sb.append("----------------- HELP START -----------------\n");
    // Objective
    sb.append("GOAL:\n - Collect riches and experience by battling monsters and rule the map\n\n");
    // movements
    sb.append("WORLD CONTROLS:\n - W: move up\n - A: move left\n - S: move down\n - D: move right\n\n");
    // tile types
    sb.append(
        "TILE TYPES:\n - P: player's current location\n - M: market tile (can only enter a market when on this space)\n - X: inaccessible space\n - C: you've conquered the monsters here already \n - (empty): common space where you encounter monsters with a random chance\n\n");
    // Hero classes
    sb.append(
        "HERO CLASSES:\n - Sorcerer - favors dexterity and agility\n - Paladin: favors strength and dexterity\n - Warrior: favors strength and agility\n\n");
    sb.append(
        "LEVEL UP:\n - Requires experience = current_level * 10\n - All stats increase by 5%\n - Favored stats increase by additional 5%\n - HP rests to level * 100\n - MP increase by 10%\n\n");
    sb.append("----------------- HELP END -----------------\n\n");
    System.out.println(sb.toString());
  }
}
