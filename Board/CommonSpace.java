package Board;

import java.util.Random;

public class CommonSpace extends Space {
  private Random generator;

  public CommonSpace(int r, int c) {
    super(r, c);
    generator = new Random();
  }

  /**
   * attempt to enter a space
   * 
   * @return -1 if cannot enter, 0 if nothing happens, and 1 if action happens
   */
  @Override
  public int tryToEnterSpace() {
    double battleThreshold = 0.5; // 50% chance of battle
    double battleChance = generator.nextDouble();
    if (battleChance >= battleThreshold) {
      return 1;
    }
    return 0;
  }

  @Override
  public String getSpaceType() {
    return "COMMON";
  }

  public String toString() {
    return " ";
  }
}
