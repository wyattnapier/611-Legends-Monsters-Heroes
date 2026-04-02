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
  public String getBackgroundColor() {
    return ColorString.BACKGROUND_WHITE; // works best when in dark mode
  }
}
