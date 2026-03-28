import java.util.Scanner;

import Board.Board;

public class Game {
  Board board = new Board();
  Scanner scan = new Scanner(System.in);
  IO io = new IO(scan);
  String playerName;

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
    io.printGameIntro();
    playerName = io.getPlayerName();
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
        case "w":
        case "a":
        case "s":
        case "d":
          boolean isValidMove = board.isValidMove(selectedString);
          if (!isValidMove) {
            System.out.println("Cannot move to that space. Try again!");
          }
          break;
        case "q":
          continuePlaying = false;
          break;
        default:
          break;
      }
    } while (continuePlaying);
  }
}
