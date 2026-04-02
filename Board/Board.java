package Board;

import java.util.Random;

import Util.ColorString;

public class Board {
  private final int NUM_BOARD_ROWS = 8;
  private final int NUM_BOARD_COLS = 8;
  private Space[][] board = new Space[NUM_BOARD_ROWS][NUM_BOARD_COLS];
  private Random generator;
  private int playerRow = 0;
  private int playerCol = 0;

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
          // currSpace = new NexusSpace<Monster>(i, j);
          currSpace = new PlainSpace(i, j); // TODO: implement nexusspace
        } else if (i == 7) {
          // currSpace = new NexusSpace<Hero>(i,j);
          currSpace = new MarketSpace(i, j); // TODO: make marketspace an extension of nexus?
        }
        // now randomly assign tiles
        else if (rand < 0.4) {
          currSpace = new PlainSpace(i, j); // TODO: add obstacles here probably?
        } else if (rand < 0.6) {
          currSpace = new BushSpace(i, j);
        } else if (rand < 0.8) {
          currSpace = new CaveSpace(i, j);
        } else {
          currSpace = new KoulouSpace(i, j);
        }
        board[i][j] = currSpace;
      }
    }
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
    if (board[r][c].getSpaceType() == BoardSpaceOption.INACCESSIBLE) {
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

  public BoardSpaceOption getCurrentSpaceType() {
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
    // TODO: update this to show heroes and monsters --> need to store their
    // locations somewhere
    String backgroundColor = board[r][c].getBackgroundColor();
    return r == playerRow && c == playerCol ? ColorString.YELLOW + " P " + ColorString.RESET
        : backgroundColor + " " + board[r][c].toString() + " " + ColorString.RESET;
  }
}
