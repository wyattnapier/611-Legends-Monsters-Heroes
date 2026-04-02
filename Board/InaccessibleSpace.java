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
  public String toString() {
    return ColorString.RED + "X" + ColorString.RESET;
  }
}
