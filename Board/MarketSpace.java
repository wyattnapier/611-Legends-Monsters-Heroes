package Board;

public class MarketSpace extends Space {
  // TODO: initiate a unique market here with randomized items

  public MarketSpace(int r, int c) {
    super(r, c);
  }

  /**
   * attempt to enter a space
   * 
   * @return -1 if cannot enter, 0 if nothing happens, and 1 if action happens
   */
  @Override
  public int tryToEnterSpace() {
    return 1;
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
