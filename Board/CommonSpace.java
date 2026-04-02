package Board;

import java.util.Random;

import Util.ColorString;

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
  public BoardSpaceOption getSpaceType() {
    return BoardSpaceOption.COMMON;
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
    return monstersDefeated ? ColorString.BLUE + "C" + ColorString.RESET : " "; // set to C for conquered after clearing monsters from that sopt
  }
}
