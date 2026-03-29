package Board;

public class InaccessibleSpace extends Space {
  public InaccessibleSpace(int m, int n) {
    super(m, n);
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
