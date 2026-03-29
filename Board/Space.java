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
  public abstract String getSpaceType();

  public String debugSpace() {
    return getSpaceType() + " space at (" + row + ", " + col + ")";
  }

}
