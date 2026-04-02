package Board;

public abstract class Space {
  private int row, col;

  public Space(int r, int c) {
    row = r;
    col = c;
  }

  /**
   * gets the type of the space you're in
   * 
   * @return type of space
   */
  public abstract BoardSpaceOption getSpaceType();

  public abstract String getBackgroundColor();

  public String toString() {
    return " ";
  }

  public String debugSpace() {
    return getSpaceType() + " space at (" + row + ", " + col + ")";
  }

}
