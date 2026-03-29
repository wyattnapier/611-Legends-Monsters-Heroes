package Structure;

import Util.GameData;

public class Main {
  public static void main(String[] args) {
    GameData.loadAll(); // load all text files to factories before game starts
    Game game = new Game();
    game.start();
  }
}
