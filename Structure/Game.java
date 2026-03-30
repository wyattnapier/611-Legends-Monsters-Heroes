package Structure;

import java.util.*;

import Board.Board;
import Board.MarketSpace;
import Board.Space;
import Fighters.Heros.Hero;
import Locations.Marketplace;
import Util.GameData;

public class Game {
  private Board board = new Board();
  private Scanner scan = new Scanner(System.in);
  private IO io = new IO(scan);
  private String playerName;
  private int partySize;
  private List<Hero> party;

  /**
   * method that main can call to trigger start of game
   */
  public void start() {
    gameSetup(); // get the game set up
    playGame(); // initiate the gameplay loop
  }

  /**
   * sets up game and gets info about player
   */
  public void gameSetup() {
    playerName = io.getPlayerName();
    io.printGameIntro(playerName);
    partySize = io.getPartySize();
    party = new ArrayList<Hero>(partySize);
    addHeroesToParty(partySize);
    System.out.println(); // newline to break up the text flow
  }

  /**
   * runs the actual game loop
   */
  public void playGame() {
    Boolean continuePlaying = true;
    do {
      System.out.println(board);
      Boolean isOnMarketSpace = board.getCurrentSpaceType() == "MARKET";
      String selectedString = io.getNextMove(isOnMarketSpace);
      switch (selectedString) {
        // moves
        case "w":
        case "a":
        case "s":
        case "d":
          boolean isValidMove = board.isValidMove(selectedString);
          if (!isValidMove) {
            System.out.println("Cannot move to that space. Try again!");
          }
          // TODO: probabilistically start a battle here
          break;
        // manage inventory / equip items
        case "i":
          Hero h = party.get(io.getValidListIndex(party, false, "hero"));
          continuePlaying = h.loopToManageInventory(io);
          break;
        // enter market
        case "m":
          Space currSpace = board.getCurrentSpace();
          if (currSpace instanceof MarketSpace ms) {
            Marketplace m = ms.getMarketplace();
            int heroIndex = io.getValidListIndex(party, false, "hero");
            continuePlaying = m.enter(party.get(heroIndex)); // can quit from here
            break;
          } else {
            System.out.println("You cannot enter a market because you aren't on a market space.");
            break;
          }
        case "h":
          io.printHelpMenu();
          break;
        // quit
        case "q":
          continuePlaying = false;
          break;
        default:
          System.out.println("Invalid move. Try again.");
          break;
      }
    } while (continuePlaying);
    System.out.println("Quitting the game...");
  }

  /**
   * creates a new hero using the factory based on the user's preference
   * 
   * @param partySize
   */
  private void addHeroesToParty(int partySize) {
    for (int i = 0; i < partySize; i++) {
      String heroType = io.getHeroType();
      String heroTypeLongName = "";
      switch (heroType) {
        case "p":
          party.add(i, GameData.paladins.random());
          heroTypeLongName = "Paladin";
          break;
        case "s":
          party.add(i, GameData.sorcerers.random());
          heroTypeLongName = "Sorcerer";
          break;
        case "w":
          party.add(i, GameData.warriors.random());
          heroTypeLongName = "Warrior";
          break;
        default:
          System.out.println("No heroes found under that category");
      }
      System.out.println(heroTypeLongName + " " + party.get(i).getName() + " has joined the party!\n");
    }
  }
}
