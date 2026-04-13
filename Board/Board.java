package Board;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Fighters.Heros.Hero;
import Fighters.Monsters.Monster;
import Util.ColorString;
import Util.GameData;

public class Board {
  public final int NUM_BOARD_ROWS = 8;
  public final int NUM_BOARD_COLS = 8;
  private Space[][] board = new Space[NUM_BOARD_ROWS][NUM_BOARD_COLS];
  private Random generator;
  // lane i uses hero start col HERO_LANE_COL[i] (left nexus tile); cols are
  // {0,1},{3,4},{6,7}
  public static final int[] HERO_LANE_LEFT_COL = { 0, 3, 6 };
  private int activeHero = 0;
  private List<Monster> worldMonsters = new ArrayList<>();
  private List<Hero> worldHeroes;

  /**
   * create 8x8 board with:
   * 20% each (Bush, Cave, and Koulou)
   * 40% plain spaces
   * nexus tile in top row and bottom row
   * inaccessible tiles in columns 2 and 5 (0 based indexing)
   */
  public Board(List<Hero> worldHeroes) {
    generator = new Random();
    this.worldHeroes = worldHeroes;
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
          if (hasObstacleNeighborAlreadyPlaced(i, j)) {
            currSpace = new PlainSpace(i, j);
          } else {
            currSpace = new ObstacleSpace(i, j);
          }
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
    setHeroesStartingPoint();
    activeHero = 0;
  }

  /**
   * set each hero to correct starting point
   */
  private void setHeroesStartingPoint() {
    for (int i = 0; i < worldHeroes.size(); i++) {
      worldHeroes.get(i).setPosition(NUM_BOARD_ROWS - 1, HERO_LANE_LEFT_COL[i]);
    }
  }

  // ensure obstacles do not block the path
  private boolean hasObstacleNeighborAlreadyPlaced(int i, int j) {
    for (int dr = -1; dr <= 1; dr++) {
      for (int dc = -1; dc <= 1; dc++) {
        if (dr == 0 && dc == 0) {
          continue;
        }
        int nr = i + dr;
        int nc = j + dc;
        if (nr < 0 || nc < 0 || nr >= NUM_BOARD_ROWS || nc >= NUM_BOARD_COLS) {
          continue;
        }
        if (nr > i || (nr == i && nc >= j)) {
          continue;
        }
        Space s = board[nr][nc];
        if (s != null && s.getSpaceType() == BoardSpaceOption.OBSTACLE) {
          return true;
        }
      }
    }
    return false;
  }

  public int getActiveHeroIndex() {
    return activeHero;
  }

  public void advanceActiveHero() {
    activeHero = (activeHero + 1) % worldHeroes.size();
  }

  private boolean destinationInCurrentLane(int c) {
    int lane = getLaneFromColumn(getActiveHeroCol());
    int left = HERO_LANE_LEFT_COL[lane];
    return c == left || c == left + 1;
  }

  private boolean anotherHeroBlocks(int r, int c, int movingHero) {
    for (int h = 0; h < worldHeroes.size(); h++) {
      if (h != movingHero && worldHeroes.get(h).getRow() == r && worldHeroes.get(h).getCol() == c) {
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
    int r = getActiveHeroRow();
    int c = getActiveHeroCol();
    switch (direction) {
      case "w":
        return canMoveActiveHeroTo(r - 1, c, false);
      case "a":
        return canMoveActiveHeroTo(r, c - 1, false);
      case "s":
        return canMoveActiveHeroTo(r + 1, c, false);
      case "d":
        return canMoveActiveHeroTo(r, c + 1, false);
      default:
        return false;
    }
  }

  /**
   * 
   * @param r             destination row
   * @param c             destination column
   * @param isTeleporting if the hero is moving via teleport
   * @return
   */
  public boolean canMoveActiveHeroTo(int r, int c, boolean isTeleporting) {
    if (!(indexIsOnBoard(r) && indexIsOnBoard(c))) {
      return false;
    }
    if (!destinationInCurrentLane(c) && !isTeleporting) {
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
    if (heroMovesPastMonsterInLane(r, c)) {
      return false;
    }
    Hero h = worldHeroes.get(activeHero);
    h.setPosition(r, c);
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
    return board[getActiveHeroRow()][getActiveHeroCol()];
  }

  public int getActiveHeroRow() {
    return worldHeroes.get(activeHero).getRow();
  }

  public int getActiveHeroCol() {
    return worldHeroes.get(activeHero).getCol();
  }

  // right-hand nexus cell per lane on row 0 (cols 1, 4, 7)
  public void spawnMonsterWaveAtNexus(int maxHeroLevel) {
    int level = Math.max(1, maxHeroLevel);
    int[] laneSpawnCols = { 1, 4, 7 };
    for (int c : laneSpawnCols) {
      Monster m = rollMonsterForLevel(level);
      if (m != null) {
        m.setPosition(0, c);
        worldMonsters.add(m);
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
    for (Monster m : worldMonsters) {
      if (m.getRow() == r && m.getCol() == c) {
        n++;
      }
    }
    return n;
  }

  public List<Monster> getMonstersAt(int r, int c) {
    List<Monster> out = new ArrayList<>();
    for (Monster m : worldMonsters) {
      if (m.getRow() == r && m.getCol() == c) {
        out.add(m);
      }
    }
    return out;
  }

  public void removeMonstersAt(int r, int c) {
    worldMonsters.removeIf(m -> m.getRow() == r && m.getCol() == c);
  }

  // same lane in range
  public List<Monster> getMonstersInHeroAttackRange(int heroRow, int heroCol) {
    int lane = getLaneFromColumn(heroCol);
    List<Monster> out = new ArrayList<>();
    for (Monster m : worldMonsters) {
      if (getLaneFromColumn(m.getCol()) != lane) {
        continue;
      }
      int dr = Math.abs(heroRow - m.getRow());
      int dc = Math.abs(heroCol - m.getCol());
      if (Math.max(dr, dc) <= 1) {
        out.add(m);
      }
    }
    return out;
  }

  public void removeMonsterIfDead(Monster m) {
    if (!m.isAwake()) {
      worldMonsters.remove(m);
    }
  }

  /**
   * returns true if we've moved the current player past any monsters in our lane
   * (should never be able to jump over any)
   * 
   * @return
   */
  public boolean heroMovesPastMonsterInLane(int r, int c) {
    int activeHeroLane = getLaneFromColumn(c);
    for (Monster m : worldMonsters) {
      if (getLaneFromColumn(m.getCol()) == activeHeroLane && r < m.getRow()) {
        return true;
      }
    }
    return false;
  }

  /**
   * returns true if we've moved the current monster past any heroes in our lane
   * (should never be able to jump over any)
   * 
   * @return
   */
  public boolean monsterMovesPastHeroInLane(int r, int c) {
    int currMonsterLane = getLaneFromColumn(c);
    for (Hero h : worldHeroes) {
      if (getLaneFromColumn(h.getCol()) == currMonsterLane && r > h.getRow()) {
        return true;
      }
    }
    return false;
  }

  /**
   * @param c column index
   * @return true if is in bounds and not one of the two inaccessible columns
   */
  private boolean isPlayableColumn(int c) {
    return c >= 0 && c < NUM_BOARD_COLS && c != 2 && c != 5;
  }

  public boolean anyHeroReachedEnemyNexus() {
    for (Hero h : worldHeroes) {
      if (h.getRow() == 0 && isPlayableColumn(h.getCol())) {
        return true;
      }
    }
    return false;
  }

  public boolean anyMonsterReachedHeroesNexus() {
    for (Monster m : worldMonsters) {
      if (m.getRow() == NUM_BOARD_ROWS - 1 && isPlayableColumn(m.getCol())) {
        return true;
      }
    }
    return false;
  }

  private int laneLeftColForMonster(int c) {
    if (c <= 1) {
      return 0;
    }
    if (c <= 4) {
      return 3;
    }
    return 6;
  }

  /**
   * 
   * @param c column on board
   * @return 0-based index lane
   */
  public int getLaneFromColumn(int c) {
    if (c <= 1) {
      return 0;
    }
    if (c <= 4) {
      return 1;
    }
    return 2;
  }

  private boolean canMonsterStepInto(int r, int c) {
    if (!indexIsOnBoard(r) || !indexIsOnBoard(c) || !isPlayableColumn(c) || monsterMovesPastHeroInLane(r, c)) {
      return false;
    }
    BoardSpaceOption t = board[r][c].getSpaceType();
    return t != BoardSpaceOption.INACCESSIBLE && t != BoardSpaceOption.OBSTACLE;
  }

  // attack in range
  private boolean monsterAttacksHeroIfInRange(Monster m) {
    // TODO: check if heros in range and if so call the monster's attack
    // if (hero nearby) {
    // Hero nearbyHero = the one that's nearby (or like a list of them and just
    // choose one at random)
    // m.attack(nearbyHero);
    // return true;
    // }
    return false;
  }

  // priority: attack (TODO), move south, move sideways within lane to dodge
  // obstacles
  public void runMonsterMovementPhase() {
    for (Monster m : new ArrayList<>(worldMonsters)) {
      int r = m.getRow();
      int c = m.getCol();
      if (!isPlayableColumn(c)) {
        continue;
      }
      if (monsterAttacksHeroIfInRange(m)) {
        continue;
      }
      int southR = r + 1;
      if (canMonsterStepInto(southR, c)) {
        m.setPosition(southR, c);
        continue;
      }
      int left = laneLeftColForMonster(c);
      int right = left + 1;
      if (c - 1 >= left && canMonsterStepInto(r, c - 1)) {
        m.setPosition(r, c - 1);
      } else if (c + 1 <= right && canMonsterStepInto(r, c + 1)) {
        m.setPosition(r, c + 1);
      }
    }
  }

  public boolean removeObjectIfPossible(String direction) {
    int r = getActiveHeroRow();
    int c = getActiveHeroCol();
    switch (direction) {
      case "w":
        return removeObjectIfPossible(r - 1, c);
      case "a":
        return removeObjectIfPossible(r, c - 1);
      case "s":
        return removeObjectIfPossible(r + 1, c);
      case "d":
        return removeObjectIfPossible(r, c + 1);
      default:
        return false;
    }
  }

  /**
   * removes object if it is at r, c
   * 
   * @param r
   * @param c
   * @return
   */
  public boolean removeObjectIfPossible(int r, int c) {
    if (board[r][c] instanceof ObstacleSpace) {
      board[r][c] = new PlainSpace(r, c); // replace the space with the correct space type
      return true;
    }
    return false;
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
    for (int h = 0; h < worldHeroes.size(); h++) {
      if (worldHeroes.get(h).getRow() == r && worldHeroes.get(h).getCol() == c) {
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
