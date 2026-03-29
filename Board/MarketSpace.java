package Board;

public class MarketSpace extends Space {
  // TODO: initiate a unique market here with randomized items

  public MarketSpace(int r, int c) {
    super(r, c);
  }

  @Override
  public String getSpaceType() {
    return "MARKET";
  }

  @Override
  public String toString() {
    return "M";
  }

}
