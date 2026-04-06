package Structure;

import java.util.*;

import Board.Board;
import Board.NexusSpace;
import Board.Space;
import Fighters.Heros.Hero;
import Fighters.Monsters.Monster;
import Locations.Battle;
import Locations.Marketplace;
import Util.GameData;

public class Game {
  private Board board = new Board();
  private Scanner scan = new Scanner(System.in);
  private IO io = new IO(scan);
  private String playerName;
  private int partySize;
  private List<Hero> party;
  private int monsterRespawnEveryFullRounds;
  private int fullRoundsSinceSpawnWave;
  private int heroTurnsCompleted;

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
    partySize = 3;
    party = new ArrayList<Hero>(partySize);
    addHeroesToParty(partySize);
    monsterRespawnEveryFullRounds = io.getMonsterRespawnRounds();
    fullRoundsSinceSpawnWave = 0;
    heroTurnsCompleted = 0;
    board.spawnMonsterWaveAtNexus(maxHeroLevel(party));
    System.out.println("monsters spawned at the enemy nexus (top row).\n");
    System.out.println(); // newline to break up the text flow
  }

  private int maxHeroLevel(List<Hero> heroes) {
    int L = 1;
    for (Hero h : heroes) {
      L = Math.max(L, h.getLevel());
    }
    return L;
  }

  /**
   * runs the actual game loop
   */
  public void playGame() {
    Boolean continuePlaying = true;
    do {
      System.out.println(board);
      int ah = board.getActiveHeroIndex();
      Hero acting = party.get(ah);
      System.out.println(
          "turn (round-robin): " + acting.getName() + " — H" + (ah + 1) + " in lane " + (ah + 1)
              + " (yellow on board)\n");
      Space here = board.getCurrentSpace();
      boolean canShop = here instanceof NexusSpace ns && ns.isHeroesNexus();
      String selectedString = io.getNextMove(canShop);
      switch (selectedString) {
        // moves
        case "w":
        case "a":
        case "s":
        case "d":
          boolean isValidMove = board.isValidMove(selectedString);
          if (!isValidMove) {
            System.out.println("Cannot move to that space. Try again!");
          } else {
            continuePlaying = battleIfMonstersHere(continuePlaying);
            if (continuePlaying && board.anyHeroReachedEnemyNexus()) {
              System.out.println("victory — a hero reached the enemy nexus!");
              continuePlaying = false;
            }
            if (continuePlaying) {
              continuePlaying = finishHeroTurnAndMaybeMonsterPhase(continuePlaying);
            }
          }
          break;
        // manage inventory / equip items
        case "i":
          Hero invHero = party.get(board.getActiveHeroIndex());
          continuePlaying = invHero.loopToManageInventory(io);
          if (continuePlaying) {
            continuePlaying = finishHeroTurnAndMaybeMonsterPhase(continuePlaying);
          }
          break;
        // enter market
        case "m":
          Space currSpace = board.getCurrentSpace();
          if (currSpace instanceof NexusSpace ns && ns.isHeroesNexus()) {
            Marketplace m = ns.getMarketplace();
            Hero shopper = party.get(board.getActiveHeroIndex());
            continuePlaying = m.enter(shopper);
            if (continuePlaying) {
              continuePlaying = finishHeroTurnAndMaybeMonsterPhase(continuePlaying);
            }
            break;
          } else {
            System.out.println("Shop is only on the heroes' nexus.");
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
    System.out.println("Ending the game...");
  }

  private boolean finishHeroTurnAndMaybeMonsterPhase(boolean continuePlaying) {
    board.advanceActiveHero();
    heroTurnsCompleted++;
    if (heroTurnsCompleted % Board.NUM_HEROES != 0) {
      return continuePlaying;
    }
    fullRoundsSinceSpawnWave++;
    if (fullRoundsSinceSpawnWave >= monsterRespawnEveryFullRounds) {
      fullRoundsSinceSpawnWave = 0;
      board.spawnMonsterWaveAtNexus(maxHeroLevel(party));
      System.out.println("another monster wave reached the enemy nexus.\n");
    }
    System.out.println("*** monsters move south ***\n");
    board.moveAllMonstersSouth();
    if (board.anyMonsterReachedHeroesNexus()) {
      System.out.println("defeat — monsters reached your nexus.");
      return false;
    }
    return continuePlaying;
  }

  private boolean battleIfMonstersHere(boolean continuePlaying) {
    int r = board.getActiveHeroRow();
    int c = board.getActiveHeroCol();
    List<Monster> here = board.getMonstersAt(r, c);
    if (here.isEmpty()) {
      return continuePlaying;
    }
    System.out.println("you walked into monsters — battle starts!");
    Battle b = new Battle(party, new ArrayList<Monster>(here), io);
    boolean heroesWon = b.playBattle();
    if (heroesWon) {
      board.removeMonstersAt(r, c);
    } else {
      continuePlaying = false;
    }
    return continuePlaying;
  }

  /**
   * creates a new hero using the factory based on the user's preference
   * 
   * @param partySize
   */
  private void addHeroesToParty(int partySize) {
    for (int i = 0; i < partySize; i++) {
      String heroType = io.getHeroType(i);
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
      System.out.println(
          heroTypeLongName + " " + party.get(i).getName() + " has joined the party to defend lane " + (i + 1) + "!\n");
    }
  }
}
