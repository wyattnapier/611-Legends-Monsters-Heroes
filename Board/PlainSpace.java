package Board;

import Util.ColorString;

public class PlainSpace extends Space {

  public PlainSpace(int r, int c) {
    super(r, c);
  }

  @Override
  public BoardSpaceOption getSpaceType() {
    return BoardSpaceOption.PLAIN;
  }

  @Override
  public String getBackgroundColor() {
    return ColorString.RESET;
  }
}
