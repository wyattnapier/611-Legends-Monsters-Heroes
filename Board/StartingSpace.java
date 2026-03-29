package Board;

public class StartingSpace extends Space {

  public StartingSpace(int r, int c) {
    super(r, c);
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
