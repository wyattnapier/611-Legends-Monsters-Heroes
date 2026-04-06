package Board;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Fighters.Monsters.Monster;
import Util.ColorString;
import Util.GameData;

public class Board {
  public static final int NUM_HEROES = 3;
  private final int NUM_BOARD_ROWS = 8;
  private final int NUM_BOARD_COLS = 8;
  private Space[][] board = new Space[NUM_BOARD_ROWS][NUM_BOARD_COLS];
  private Random generator;
  // lane i uses hero start col HERO_LANE_COL[i] (left nexus tile); cols are
  // {0,1},{3,4},{6,7}
  private static final int[] HERO_LANE_LEFT_COL = { 0, 3, 6 };
  private int[] heroRow = new int[NUM_HEROES];
  private int[] heroCol = new int[NUM_HEROES];
  private int activeHero = 0;
  private List<MonsterOnBoard> worldMonsters = new ArrayList<>();

  /**
   * create 8x8 board with:
   * 20% each (Bush, Cave, and Koulou)
   * 40% plain spaces
   * nexus tile in top row and bottom row
   * inaccessible tiles in columns 2 and 5 (0 based indexing)
   */
  public Board() {
    generator = new Random();
    for (int i = 0; i < NUM_BOARD_ROWS; i++) {
      for (int j = 0; j < NUM_BOARD_COLS; j++) {
        double rand = generator.nextDouble();
        Space currSpace;
        if (j == 2 || j == 5) {
          currSpace = new InaccessibleSpace(i, j); // column of inaccessible spaces at indices 2 and 5
        } else if (i == 0) {
          currSpace = new NexusSpace(i, j, false); // monsters' nexus row
        } else if (i == 7) {
          currSpace = new NexusSpace(i, j, true); // heroes' nexus (shop)
        }
        // now randomly assign tiles
        else if (rand < 0.32) {
          currSpace = new PlainSpace(i, j);
        } else if (rand < 0.40) {
          currSpace = new ObstacleSpace(i, j);
        } else if (rand < 0.60) {
          currSpace = new BushSpace(i, j);
        } else if (rand < 0.8) {
          currSpace = new CaveSpace(i, j);
        } else {
          currSpace = new KoulouSpace(i, j);
        }
        board[i][j] = currSpace;
      }
    }
    for (int h = 0; h < NUM_HEROES; h++) {
      heroRow[h] = 7;
      heroCol[h] = HERO_LANE_LEFT_COL[h];
    }
    activeHero = 0;
  }

  public int getActiveHeroIndex() {
    return activeHero;
  }

  public void advanceActiveHero() {
    activeHero = (activeHero + 1) % NUM_HEROES;
  }

  private boolean destinationInHeroLane(int heroIndex, int c) {
    int left = HERO_LANE_LEFT_COL[heroIndex];
    return c == left || c == left + 1;
  }

  private boolean anotherHeroBlocks(int r, int c, int movingHero) {
    for (int h = 0; h < NUM_HEROES; h++) {
      if (h != movingHero && heroRow[h] == r && heroCol[h] == c) {
        return true;
      }
    }
    return false;
  }

  /**
   * checks is a move is valid and updates player location if so
   * 
   * @param direction w/a/s/d
   * @return true if can move (and moves current hero) and false otherwise
   */
  public boolean isValidMove(String direction) {
    int r = heroRow[activeHero];
    int c = heroCol[activeHero];
    switch (direction) {
      case "w":
        return canMoveActiveHeroTo(r - 1, c);
      case "a":
        return canMoveActiveHeroTo(r, c - 1);
      case "s":
        return canMoveActiveHeroTo(r + 1, c);
      case "d":
        return canMoveActiveHeroTo(r, c + 1);
      default:
        return false;
    }
  }

  public boolean canMoveActiveHeroTo(int r, int c) {
    if (!(indexIsOnBoard(r) && indexIsOnBoard(c))) {
      return false;
    }
    if (!destinationInHeroLane(activeHero, c)) {
      return false;
    }
    if (board[r][c].getSpaceType() == BoardSpaceOption.INACCESSIBLE) {
      return false;
    }
    if (board[r][c].getSpaceType() == BoardSpaceOption.OBSTACLE) {
      return false;
    }
    if (anotherHeroBlocks(r, c, activeHero)) {
      return false;
    }
    heroRow[activeHero] = r;
    heroCol[activeHero] = c;
    return true;
  }

  /**
   * checks if index is on the square board
   * 
   * @param boardIndex
   * @return true if on board false if out of bounds
   */
  private boolean indexIsOnBoard(int boardIndex) {
    return 0 <= boardIndex && boardIndex < NUM_BOARD_ROWS;
  }

  public Space getCurrentSpace() {
    return board[heroRow[activeHero]][heroCol[activeHero]];
  }

  public BoardSpaceOption getCurrentSpaceType() {
    return board[heroRow[activeHero]][heroCol[activeHero]].getSpaceType();
  }

  public int getActiveHeroRow() {
    return heroRow[activeHero];
  }

  public int getActiveHeroCol() {
    return heroCol[activeHero];
  }

  public int getHeroRow(int heroIndex) {
    return heroRow[heroIndex];
  }

  public int getHeroCol(int heroIndex) {
    return heroCol[heroIndex];
  }

  // right-hand nexus cell per lane on row 0 (cols 1, 4, 7)
  public void spawnMonsterWaveAtNexus(int maxHeroLevel) {
    int level = Math.max(1, maxHeroLevel);
    int[] laneSpawnCols = { 1, 4, 7 };
    for (int c : laneSpawnCols) {
      Monster m = rollMonsterForLevel(level);
      if (m != null) {
        worldMonsters.add(new MonsterOnBoard(0, c, m));
      }
    }
  }

  private Monster rollMonsterForLevel(int level) {
    int L = level;
    Monster m = GameData.monsters.random(L);
    while (m == null && L > 1) {
      L--;
      m = GameData.monsters.random(L);
    }
    return m;
  }

  public int countMonstersAt(int r, int c) {
    int n = 0;
    for (MonsterOnBoard mob : worldMonsters) {
      if (mob.getRow() == r && mob.getCol() == c) {
        n++;
      }
    }
    return n;
  }

  public List<Monster> getMonstersAt(int r, int c) {
    List<Monster> out = new ArrayList<>();
    for (MonsterOnBoard mob : worldMonsters) {
      if (mob.getRow() == r && mob.getCol() == c) {
        out.add(mob.getMonster());
      }
    }
    return out;
  }

  public void removeMonstersAt(int r, int c) {
    worldMonsters.removeIf(mob -> mob.getRow() == r && mob.getCol() == c);
  }

  private boolean isPlayableColumn(int c) {
    return c >= 0 && c < NUM_BOARD_COLS && c != 2 && c != 5;
  }

  public boolean anyHeroReachedEnemyNexus() {
    for (int h = 0; h < NUM_HEROES; h++) {
      if (heroRow[h] == 0 && isPlayableColumn(heroCol[h])) {
        return true;
      }
    }
    return false;
  }

  public boolean anyMonsterReachedHeroesNexus() {
    for (MonsterOnBoard mob : worldMonsters) {
      if (mob.getRow() == 7 && isPlayableColumn(mob.getCol())) {
        return true;
      }
    }
    return false;
  }

  // each mob tries one step south; terrain blocks, heroes don't (can share tile)
  public void moveAllMonstersSouth() {
    for (MonsterOnBoard mob : worldMonsters) {
      int r = mob.getRow();
      int c = mob.getCol();
      int nr = r + 1;
      if (!indexIsOnBoard(nr) || !indexIsOnBoard(c)) {
        continue;
      }
      if (!isPlayableColumn(c)) {
        continue;
      }
      BoardSpaceOption t = board[nr][c].getSpaceType();
      if (t == BoardSpaceOption.INACCESSIBLE || t == BoardSpaceOption.OBSTACLE) {
        continue;
      }
      mob.setPosition(nr, c);
    }
  }

  public String toString() {
    String horizontalDividerChunk = "+---";
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < NUM_BOARD_ROWS; i++) {
      // make horizontal divider
      for (int j = 0; j < NUM_BOARD_COLS; j++) {
        sb.append(horizontalDividerChunk);
      }
      sb.append("+\n");
      // add the cell with stuff in it
      for (int j = 0; j < NUM_BOARD_COLS; j++) {
        sb.append("|" + spaceToString(i, j));
      }
      sb.append("|\n");
    }
    // add final horizontal divider
    for (int j = 0; j < NUM_BOARD_COLS; j++) {
      sb.append(horizontalDividerChunk);
    }
    sb.append("+\n");
    return sb.toString();
  }

  /**
   * checks if player is at current spot or not and adjusts toString by that
   * 
   * @param r
   * @param c
   * @return
   */
  public String spaceToString(int r, int c) {
    String backgroundColor = board[r][c].getBackgroundColor();
    int heroOnCell = -1;
    for (int h = 0; h < NUM_HEROES; h++) {
      if (heroRow[h] == r && heroCol[h] == c) {
        heroOnCell = h;
        break;
      }
    }
    boolean mon = countMonstersAt(r, c) > 0;
    if (heroOnCell >= 0 && mon) {
      String digit = Integer.toString(heroOnCell + 1);
      return ColorString.YELLOW + digit + "M " + ColorString.RESET;
    }
    if (heroOnCell >= 0) {
      String digit = Integer.toString(heroOnCell + 1);
      if (heroOnCell == activeHero) {
        return ColorString.YELLOW + "H" + digit + " " + ColorString.RESET;
      }
      return ColorString.CYAN + "H" + digit + " " + ColorString.RESET;
    }
    if (mon) {
      return ColorString.RED + " M " + ColorString.RESET;
    }
    return backgroundColor + " " + board[r][c].toString() + " " + ColorString.RESET;
  }
}
