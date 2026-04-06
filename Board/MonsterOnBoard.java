package Board;

import Fighters.Monsters.Monster;

// one monster instance sitting on a grid cell
public class MonsterOnBoard {
  private final int row;
  private final int col;
  private final Monster monster;

  public MonsterOnBoard(int row, int col, Monster monster) {
    this.row = row;
    this.col = col;
    this.monster = monster;
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  public Monster getMonster() {
    return monster;
  }
}
