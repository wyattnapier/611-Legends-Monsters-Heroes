package Board;

import java.util.Random;

public class Board {
  private final int NUM_BOARD_ROWS = 8;
  private final int NUM_BOARD_COLS = 8;
  private Space[][] board = new Space[NUM_BOARD_ROWS][NUM_BOARD_COLS];
  private Random generator;
  private int playerRow = 0;
  private int playerCol = 0;

  /**
   * create 8x8 board with:
   * 20% inaccessible spaces
   * 30% market spaces
   * 50% common spaces
   */
  public Board() {
    generator = new Random();
    for (int i = 0; i < NUM_BOARD_ROWS; i++) {
      for (int j = 0; j < NUM_BOARD_COLS; j++) {
        // pick a random number and based on value set tile type
        double rand = generator.nextDouble();
        Space currSpace;
        if (rand < 0.2) {
          currSpace = new InaccessibleSpace(i, j);
        } else if (rand < 0.5) {
          currSpace = new MarketSpace(i, j);
        } else {
          currSpace = new CommonSpace(i, j);
        }
        board[i][j] = currSpace;
      }
    }
    board[0][0] = new StartingSpace(0, 0);
    // TODO: ensure that from the starting tile you don't get stuck
  }

  /**
   * checks is a move is valid and updates player location if so
   * 
   * @param direction w/a/s/d
   * @return true if can move (and moves player) and false otherwise
   */
  public boolean isValidMove(String direction) {
    switch (direction) {
      case "w":
        return canMoveToSpace(playerRow - 1, playerCol);
      case "a":
        return canMoveToSpace(playerRow, playerCol - 1);
      case "s":
        return canMoveToSpace(playerRow + 1, playerCol);
      case "d":
        return canMoveToSpace(playerRow, playerCol + 1);
      default:
        return false;
    }
  }

  /**
   * check if we can move player to space and actually move them if so
   * 
   * @param r destination row
   * @param c destination column
   * @return true if we can move them
   */
  public boolean canMoveToSpace(int r, int c) {
    if (!(indexIsOnBoard(r) && indexIsOnBoard(c))) {
      return false;
    }
    if (board[r][c].getSpaceType() == "INACCESSIBLE") {
      return false;
    }
    // move player
    setPlayerBoardPosition(r, c);
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

  /**
   * setter method for player position on board
   * 
   * @param r new player row
   * @param c new player col
   */
  public void setPlayerBoardPosition(int r, int c) {
    playerRow = r;
    playerCol = c;
  }

  public Space getCurrentSpace() {
    return board[playerRow][playerCol];
  }

  public String getCurrentSpaceType() {
    return board[playerRow][playerCol].getSpaceType();
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
        sb.append("| " + spaceToString(i, j) + " ");
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
    return r == playerRow && c == playerCol ? Space.YELLOW + "P" + Space.RESET : board[r][c].toString();
  }
}
