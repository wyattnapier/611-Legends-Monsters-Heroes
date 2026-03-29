package Board;

import java.util.Random;

public class CommonSpace extends Space {
  private Random generator;

  public CommonSpace(int r, int c) {
    super(r, c);
    generator = new Random();
  }

  @Override
  public String getSpaceType() {
    return "COMMON";
  }

  public String toString() {
    return " ";
  }
}
