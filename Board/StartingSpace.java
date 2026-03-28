package Board;

public class StartingSpace extends Space {

  public StartingSpace(int r, int c) {
    super(r, c);
  }

  /**
   * attempt to enter a space
   * 
   * @return -1 if cannot enter, 0 if nothing happens, and 1 if action happens
   */
  @Override
  public int tryToEnterSpace() {
    return 0;
  }

  @Override
  public String getSpaceType() {
    return "STARTING";
  }

  @Override
  public String toString() {
    return " ";
  }
}
