package Board;

public abstract class Space {
  private int row, col;

  public Space(int r, int c) {
    row = r;
    col = c;
  }

  /**
   * attempt to enter a space
   * 
   * @return -1 if cannot enter, 0 if nothing happens, and 1 if action happens
   */
  public abstract int tryToEnterSpace();

  /**
   * gets the type of the space you're in
   * TODO: figure out correct return type or use enum
   * 
   * @return type of space
   */
  public abstract String getSpaceType();

  public String debugSpace() {
    return getSpaceType() + " space at (" + row + ", " + col + ")";
  }

}
