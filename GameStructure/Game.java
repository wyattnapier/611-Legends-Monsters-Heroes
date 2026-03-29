package GameStructure;

import java.util.Scanner;

import Board.Board;

public class Game {
  private Board board = new Board();
  private Scanner scan = new Scanner(System.in);
  private IO io = new IO(scan);
  private String playerName;
  private int partySize;

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
    System.out.println(); // newline to break up the text flow
  }

  /**
   * runs the actual game loop
   */
  public void playGame() {
    Boolean continuePlaying = true;
    do {
      System.out.println(board);
      String selectedString = io.getNextMove();
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
        // enter market
        case "m":
          if (board.getCurrentSpaceType() != "MARKET") {
            System.out.println("You cannot enter a market because you aren't on a market space.");
            break;
          }
          // TODO: add some market interaction here
          break;
        case "h":
          // TODO: need to figure out what this is actually supposed to do
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
  }
}
