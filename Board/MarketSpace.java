package Board;

import Locations.Marketplace;
import Util.ColorString;

public class MarketSpace extends Space {
  private Marketplace marketplace;

  public MarketSpace(int r, int c) {
    super(r, c);
    marketplace = new Marketplace();
  }

  @Override
  public BoardSpaceOption getSpaceType() {
    return BoardSpaceOption.MARKET;
  }

  @Override
  public String toString() {
    return ColorString.BLUE + "M" + ColorString.RESET;
  }

  public Marketplace getMarketplace() {
    return marketplace;
  }

}
