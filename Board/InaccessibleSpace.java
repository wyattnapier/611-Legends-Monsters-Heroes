package Board;

import Util.ColorString;

public class InaccessibleSpace extends Space {
  public InaccessibleSpace(int m, int n) {
    super(m, n);
  }

  @Override
  public BoardSpaceOption getSpaceType() {
    return BoardSpaceOption.INACCESSIBLE;
  }

  @Override
  // NOTE: right now this isn't used because it is overriden in the board itself
  public String toString() {
    return ColorString.RED + "X" + ColorString.RESET;
  }
}
