package Board;

import Fighters.Monsters.Monster;

// one monster instance sitting on a grid cell
public class MonsterOnBoard {
  private int row;
  private int col;
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

  public void setPosition(int r, int c) {
    row = r;
    col = c;
  }

  public Monster getMonster() {
    return monster;
  }
}
