package Structure;

import java.util.*;

import Board.Board;
import Board.HeroBuffAtSpace;
import Board.NexusSpace;
import Board.Space;
import Fighters.Attribute;
import Fighters.Stats;
import Fighters.Heros.Hero;
import Fighters.Monsters.Monster;
import Items.Potion;
import Locations.Marketplace;
import Util.GameData;

public class Game {
  private Board board;
  private Scanner scan = new Scanner(System.in);
  private IO io = new IO(scan);
  private String playerName;
  public static final int partySize = 3;
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
    party = new ArrayList<Hero>(partySize);
    addHeroesToParty(partySize);
    monsterRespawnEveryFullRounds = io.getMonsterRespawnRounds();
    fullRoundsSinceSpawnWave = 0;
    heroTurnsCompleted = 0;
    board = new Board(party);
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
          "turn (round-robin): " + acting.getName() + " — H" + (ah + 1) + " in lane "
              + (board.getLaneFromColumn(acting.getCol()) + 1)
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
          Map<Attribute, Integer> oldBuffs = new HashMap<>();
          if (here instanceof HeroBuffAtSpace buffSpace) {
            oldBuffs = buffSpace.getHeroBuff(); // get the current buffs so we can take them away after moving
          }
          boolean isValidMove = board.isValidMove(selectedString);
          if (!isValidMove) {
            System.out.println("Cannot move to that space. Try again!");
          } else {
            // update buffs (remove old buffs and add new buffs)
            Stats heroStats = acting.getHeroStats(); // returns reference so updates actual stats
            oldBuffs.forEach((key, value) -> {
              heroStats.set(key, heroStats.get(key) - value);
            });
            if (board.getCurrentSpace() instanceof HeroBuffAtSpace newBuffSpace) {
              newBuffSpace.getHeroBuff().forEach((key, value) -> {
                heroStats.set(key, heroStats.get(key) + value);
                System.out
                    .println(acting.getName() + " entered a "
                        + board.getCurrentSpace().getSpaceType().toString().toLowerCase()
                        + " and gained a temporary " + value + " point boost to their " + key.toString().toLowerCase()
                        + "!\n");
              });
            }

            hintIfMonstersShareTile();
            if (continuePlaying && board.anyHeroReachedEnemyNexus()) {
              System.out.println("victory — a hero reached the enemy nexus!");
              continuePlaying = false;
            }
            if (continuePlaying) {
              continuePlaying = finishHeroTurnAndMaybeMonsterPhase(continuePlaying);
            }
          }
          break;
        case "f":
          List<Monster> inRange = board.getMonstersInHeroAttackRange(board.getActiveHeroRow(),
              board.getActiveHeroCol());
          if (inRange.isEmpty()) {
            System.out.println("no monsters in attack range.\n");
          } else {
            int idx = io.getWorldAttackTargetIndex(inRange);
            Monster target = inRange.get(idx);
            int raw = acting.previewBoardAttackDamageBeforeMitigation();
            System.out.println(
                "damage roll (before dodge/defense): " + raw + " — uses strength with tile buffs + equipped weapon\n");
            acting.attackOnBoardMonster(target);
            if (target.isAwake()) {
              System.out.println(target.getName() + " has " + target.getFighterHp() + " hp left.\n");
            } else {
              System.out.println(target.getName() + " was defeated!\n");
            }
            board.removeMonsterIfDead(target);
            if (continuePlaying) {
              continuePlaying = finishHeroTurnAndMaybeMonsterPhase(continuePlaying);
            }
          }
          break;
        case "u": {
          Inventory potions = acting.getInventory().filterByItemClass(Potion.class);
          if (potions.isEmpty()) {
            System.out.println("You don't have any potions in your inventory.\n");
            break;
          }
          Potion toUse;
          if (potions.size() == 1) {
            toUse = (Potion) potions.get(0);
          } else {
            int potionIdx = io.getValidListIndex(potions, false, "potion");
            toUse = (Potion) potions.get(potionIdx);
          }
          toUse.consumeItem(acting);
          if (continuePlaying) {
            continuePlaying = finishHeroTurnAndMaybeMonsterPhase(continuePlaying);
          }
          break;
        }
        // teleport to first hero found in selected lane
        case "t":
          int actingHeroLane = board.getLaneFromColumn(board.getActiveHeroCol()); // 0-indexed
          int laneToTeleportTo = io.getTeleportDestinationLane(actingHeroLane); // 1-indexed
          boolean hasTeleported = false;
          for (Hero otherHero : party) {
            if (otherHero == acting) {
              continue;
            }
            int otherHeroLane = board.getLaneFromColumn(otherHero.getCol());
            if (laneToTeleportTo - 1 == otherHeroLane
                && (board.canMoveActiveHeroTo(otherHero.getRow(), otherHero.getCol() - 1, true) ||
                    board.canMoveActiveHeroTo(otherHero.getRow(), otherHero.getCol() + 1, true) ||
                    board.canMoveActiveHeroTo(otherHero.getRow() + 1, otherHero.getCol(), true) ||
                    board.canMoveActiveHeroTo(otherHero.getRow() - 1, otherHero.getCol(), true))) {
              hasTeleported = true;
              System.out.println(
                  acting.getName() + " teleported to " + otherHero.getName() + " in lane " + laneToTeleportTo + "!\n");
              if (continuePlaying) {
                continuePlaying = finishHeroTurnAndMaybeMonsterPhase(continuePlaying);
              }
              break;
            }
          }
          if (!hasTeleported)
            System.out.println("Couldn't teleport to that lane. Try again.\n");
          break;
        // remove obstacle from space
        case "o":
          String direction = io.getCardinalDirection();
          if (direction == "b") {
            break;
          }
          boolean successfulRemoval = board.removeObjectIfPossible(direction);
          if (successfulRemoval) {
            continuePlaying = finishHeroTurnAndMaybeMonsterPhase(continuePlaying);
          } else {
            System.out.println("Failed to remove the obstacle. Try again or pick another move.\n");
          }
          break;
        // recall to nexus
        case "r":
          int homeNexusCol = Board.HERO_LANE_LEFT_COL[ah];
          // tries left and right nexus slots in the lane
          if (board.canMoveActiveHeroTo(board.NUM_BOARD_ROWS - 1, homeNexusCol, false) ||
              board.canMoveActiveHeroTo(board.NUM_BOARD_ROWS - 1, homeNexusCol + 1, false)) {
            System.out.println(acting.getName() + " was recalled to their nexus!\n");
            if (continuePlaying) {
              continuePlaying = finishHeroTurnAndMaybeMonsterPhase(continuePlaying);
            }
          } else {
            System.out.println(acting.getName() + " can't be recalled to their nexus right now. Try again!");
          }
          break;
        // manage inventory / equip items
        case "i":
          Hero invHero = party.get(board.getActiveHeroIndex());
          if (invHero.getInventory().isEmpty()) {
            System.out.println(invHero.getName() + "'s inventory is empty.\n");
            break;
          }
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
    if (heroTurnsCompleted % partySize != 0) {
      return continuePlaying;
    }
    fullRoundsSinceSpawnWave++;
    if (fullRoundsSinceSpawnWave >= monsterRespawnEveryFullRounds) {
      fullRoundsSinceSpawnWave = 0;
      board.spawnMonsterWaveAtNexus(maxHeroLevel(party));
      System.out.println("another monster wave reached the enemy nexus.\n");
    }
    System.out.println("*** monster phase (move / sidestep) ***\n");
    board.runMonsterMovementPhase();
    if (board.anyMonsterReachedHeroesNexus()) {
      System.out.println("defeat — monsters reached your nexus.");
      return false;
    }
    return continuePlaying;
  }

  private void hintIfMonstersShareTile() {
    int r = board.getActiveHeroRow();
    int c = board.getActiveHeroCol();
    if (!board.getMonstersAt(r, c).isEmpty()) {
      System.out.println("monsters on this tile — use f to attack.\n");
    }
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
