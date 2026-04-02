package Board;

public class StartingSpace extends Space {

  public StartingSpace(int r, int c) {
    super(r, c);
  }

  @Override
  public BoardSpaceOption getSpaceType() {
    return BoardSpaceOption.STARTING;
  }

  @Override
  public String toString() {
    return " ";
  }
}
