package Board;

import Locations.Marketplace;

public class MarketSpace extends Space {
  private Marketplace marketplace;

  public MarketSpace(int r, int c) {
    super(r, c);
    marketplace = new Marketplace();
  }

  @Override
  public String getSpaceType() {
    return "MARKET";
  }

  @Override
  public String toString() {
    return "M";
  }

  public Marketplace getMarketplace() {
    return marketplace;
  }

}
