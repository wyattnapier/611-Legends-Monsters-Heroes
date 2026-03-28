import java.util.Scanner;

public class IO {
  public static final String validMoveOptions = "wasdicmqh";
  public static final String nextMoveList = "Pleae input your next move:\n" + "W/A/S/D - move\n"
      + "I/C - manage inventory (view info, equip/use items)\n" + "M - enter market if on market tile\n"
      + "Q - quit game\n" + "H - Help/Information\n" + "Your move --> ";
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
  public String getNextMove() {
    String selection;
    System.out.print(nextMoveList);
    selection = sc.next();
    selection = selection.toLowerCase().trim();
    if (selection.length() == 1 && validMoveOptions.contains(selection)) {
      return selection;
    } else {
      System.out.println("Invalid selection, please try again\n");
      return getNextMove();
    }
  }

  /**
   * gets the users name
   * 
   * @return String of player's name
   */
  public String getPlayerName() {
    String name;
    System.out.print("Enter your name: ");
    name = sc.next();
    return name;
  }

  // -------------------------- outputs

  /**
   * print the game description and intro
   */
  public void printGameIntro() {
    String gameIntro = "Hi player, welcome to my legends, monsters, and heroes game! It is pretty self-explanatory and you're probably a grader if you're playing so you know what to do -- just wander around and kill some monsters to level up your players and their equipment. Have fun!";
    System.out.println(gameIntro);
  }
}
