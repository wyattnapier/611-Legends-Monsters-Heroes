package Structure;

import java.util.List;
import java.util.Scanner;

import Fighters.Heros.Hero;
import Fighters.Monsters.Monster;
import Items.Equippable;
import Items.Weapon;

public class IO {
  public static final String validMoveOptions = "wasdtoirqfhuc";
  public static final String nextMoveListWithNexusShop = "Please input your next move:\n" + "W/A/S/D - move\n"
      + "T - teleport to another lane\n" + "O - remove adjacent obstacle\n"
      + "F - attack a monster in range (same lane, up to 1 tile away)\n"
      + "C - cast a spell on a monster in range (same range as attack)\n"
      + "U - use a potion from this hero's inventory (ends turn)\n"
      + "I - manage inventory (view info, equip/unequip; does not end turn)\n" + "M - nexus shop\n"
      + "R - recall hero to nexus\n"
      + "Q - quit game\n" + "H - help/information\n" + "Your move --> ";
  public static final String nextMoveListWithoutNexusShop = "Please input your next move:\n" + "W/A/S/D - move\n"
      + "T - teleport to another lane\n" + "O - remove adjacent obstacle\n"
      + "F - attack a monster in range (same lane, up to 1 tile away)\n"
      + "C - cast a spell on a monster in range (same range as attack)\n"
      + "U - use a potion from this hero's inventory (ends turn)\n"
      + "I - manage inventory (view info, equip/unequip; does not end turn)\n" + "R - recall hero to nexus\n"
      + "Q - quit game\n"
      + "H - help/information\n"
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
  public String getNextMove(boolean onHeroesNexus) {
    String selection;
    if (onHeroesNexus) {
      System.out.print(nextMoveListWithNexusShop);
    } else {
      System.out.print(nextMoveListWithoutNexusShop);
    }
    selection = sc.nextLine();
    selection = selection.toLowerCase().trim();
    if (selection.length() == 1
        && (validMoveOptions.contains(selection) || (selection.equals("m") && onHeroesNexus))) {
      System.out.println();
      return selection;
    } else {
      System.out.println("Invalid selection, please try again\n");
      return getNextMove(onHeroesNexus);
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
  // e/m/h -> full rounds between nexus spawns (round = all 3 heroes act, then
  // monsters move)
  public int getMonsterRespawnRounds() {
    while (true) {
      System.out.print(
          "Monster respawn rate (extra wave at enemy nexus after this many full rounds):\n"
              + "[e] easy — every 6 rounds\n[m] medium — every 4 rounds\n[h] hard — every 2 rounds\nChoice --> ");
      String s = sc.nextLine().toLowerCase().trim();
      if (s.equals("e")) {
        return 6;
      }
      if (s.equals("m")) {
        return 4;
      }
      if (s.equals("h")) {
        return 2;
      }
      System.out.println("invalid, use e, m, or h.\n");
    }
  }

  public String getHeroType(int laneIndex) {
    while (true) {
      int laneIndexReadable = laneIndex + 1;
      System.out.print("Pick your hero type to fight in lane " + laneIndexReadable + "!\n"
          + "[s] Sorcerer (favored on dexterity and agility)\n"
          + "[p] Paladin (favored on strength and dexterity)\n" + "[w] Warrior (favored on strength and agility)\n"
          + "Enter your choice --> ");
      String heroSelection = sc.nextLine().toLowerCase().trim();
      if (heroSelection.equals("s") || heroSelection.equals("p") || heroSelection.equals("w")) {
        return heroSelection;
      } else {
        System.out.println("Invalid selection. Please just enter a single character from the provided options.\n");
      }
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
        while (true) {
          System.out.print("(B) go back  (Q) quit game\nYour choice --> ");
          String input = sc.nextLine().trim().toLowerCase();
          if (input.equals("b")) {
            return new Object[] { "b", Integer.valueOf(-1) };
          }
          if (input.equals("q")) {
            return new Object[] { "q", Integer.valueOf(-1) };
          }
          System.out.println("Invalid selection.\n");
        }
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

  /**
   * @param activeHeroLane 1 indexed hero lane
   * @return int for teleportation destination
   */
  public int getTeleportDestinationLane(int activeHeroLane) {
    activeHeroLane++; // make it 1 indexed
    while (true) {
      try {
        StringBuilder sb = new StringBuilder();
        sb.append("Choose a lane to teleport to from the list to the right. The leftmost lane is lane 1. Options: [");
        boolean first = true;
        for (int i = 1; i <= 3; i++) {
          if (i != activeHeroLane) {
            if (!first) {
              sb.append("/");
            }
            sb.append(i);
            first = false;
          }
        }
        sb.append("]\nYour choice: ");
        System.out.print(sb.toString());
        String inputString = sc.nextLine().toLowerCase().trim();
        int inputInt = Integer.parseInt(inputString);
        if (1 <= inputInt && inputInt <= 3 && inputInt != activeHeroLane) {
          return inputInt;
        } else {
          System.out.println("You cannot teleport within your same lane or outside of the board\n");
        }
      } catch (Exception e) {
        System.out.println("You must input a number");
      }
    }
  }

  public int getWorldAttackTargetIndex(List<Monster> inRange) {
    if (inRange.isEmpty()) {
      return -1;
    }
    while (true) {
      System.out.println("monsters in range:");
      for (int i = 0; i < inRange.size(); i++) {
        Monster m = inRange.get(i);
        System.out.println("(" + i + ") " + m.getName() + " — hp " + m.getFighterHp());
      }
      if (inRange.size() == 1) {
        System.out.println();
        return 0;
      }
      System.out.print("Pick index of monster to attack (0-" + (inRange.size() - 1) + ") --> ");
      try {
        int inputInt = Integer.parseInt(sc.nextLine().trim());
        if (0 <= inputInt && inputInt < inRange.size()) {
          System.out.println();
          return inputInt;
        }
      } catch (NumberFormatException ignored) {
      }
      System.out.println("Invalid selection. Please enter a valid index.\n");
    }
  }

  public String getCardinalDirection() {
    String validInputs = "wasdb";
    while (true) {
      System.out.print("Input the direction [W/A/S/D] that the obstacle you want to remove is in or 'B' to go back: ");
      String input = sc.nextLine().toLowerCase().trim();
      if (input.length() == 1 && validInputs.contains(input)) {
        return input;
      }
      System.out.println("Input a valid direction for obstacle removal.\n");
    }
  }

  // -------------------------- outputs

  /**
   * print the game description and intro
   */
  public void printGameIntro(String playerName) {
    String gameIntro = "Hi " + playerName
        + ", welcome to my Legends - Valor game. Each hero must defend their nexus and attempt to reach the opposing monster's nexus. Use your nexus as a marketplace. Good luck and have fun!\n";
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
    sb.append(
        "WORLD CONTROLS:\n - W/A/S/D: move the current hero (yellow H1/H2/H3 on the map; cyan = other heroes)\n"
            + " - F: attack a monster in range (same lane, Chebyshev distance at most 1)\n"
            + " - C: cast a spell on a monster in range (same range as attack)\n"
            + " - I: view inventory, equip or unequip (does not end turn)\n"
            + " - U: use a potion from the current hero's inventory (ends turn)\n"
            + " - turns rotate H1 -> H2 -> H3 -> H1 after each action\n\n");
    sb.append(
        "TILE TYPES:\n - H1/H2/H3: your heroes (one per lane)\n - M (red): monsters on that tile\n - N/P/B/C/K/I/O: space types\n\n");
    // Hero classes
    sb.append(
        "HERO CLASSES:\n - Sorcerer - favors dexterity and agility\n - Paladin: favors strength and dexterity\n - Warrior: favors strength and agility\n\n");
    sb.append(
        "LEVEL UP:\n - Requires experience = current_level * 10\n - All stats increase by 5%\n - Favored stats increase by additional 5%\n - HP rests to level * 100\n - MP increase by 10%\n\n");
    sb.append("----------------- HELP END -----------------\n\n");
    System.out.println(sb.toString());
  }
}
