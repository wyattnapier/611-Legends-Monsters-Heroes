package Board;

public class InaccessibleSpace extends Space {
  public InaccessibleSpace(int m, int n) {
    super(m, n);
  }

  /**
   * attempt to enter a space
   * 
   * @return -1 if cannot enter, 0 if nothing happens, and 1 if action happens
   */
  @Override
  public int tryToEnterSpace() {
    return -1;
  }

  @Override
  public String getSpaceType() {
    return "INACCESSIBLE";
  }

  @Override
  public String toString() {
    return "X";
  }
}
