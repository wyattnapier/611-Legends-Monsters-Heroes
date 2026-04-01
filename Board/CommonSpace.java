package Board;

import java.util.Random;

public class CommonSpace extends Space {
  private Random generator;
  private boolean isBattleHere;
  private boolean monstersDefeated;

  public CommonSpace(int r, int c) {
    super(r, c);
    generator = new Random();
    if (generator.nextDouble() <= 0.4) {
      isBattleHere = true;
      monstersDefeated = false;
    } else {
      isBattleHere = false;
      monstersDefeated = false;
    }
  }

  @Override
  public String getSpaceType() {
    return "COMMON";
  }

  public boolean getIsBattleHere() {
    return isBattleHere;
  }

  public boolean getHaveMonstersBeenDefeated() {
    return monstersDefeated;
  }

  public void setMonstersDefeated(boolean monstersDefeated) {
    this.monstersDefeated = monstersDefeated;
  }

  public String toString() {
    return monstersDefeated ? Space.BLUE + "C" + Space.RESET : " ";
  }
}
