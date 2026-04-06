package Board;

import Util.ColorString;

// blocked like a wall until removed
public class ObstacleSpace extends Space {

  public ObstacleSpace(int r, int c) {
    super(r, c);
  }

  public BoardSpaceOption getSpaceType() {
    return BoardSpaceOption.OBSTACLE;
  }

  public String getBackgroundColor() {
    return ColorString.BACKGROUND_MAGENTA;
  }

  public String toString() {
    return "O";
  }
}
