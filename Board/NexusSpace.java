package Board;

import Locations.Marketplace;
import Util.ColorString;

// heroes' nexus has a shop; monsters' nexus is just a spawn/goal tile
public class NexusSpace extends Space {
  private final boolean heroesNexus;
  private Marketplace marketplace;

  public NexusSpace(int r, int c, boolean heroesNexus) {
    super(r, c);
    this.heroesNexus = heroesNexus;
    if (heroesNexus) {
      marketplace = new Marketplace();
    }
  }

  public BoardSpaceOption getSpaceType() {
    return BoardSpaceOption.NEXUS;
  }

  public String getBackgroundColor() {
    return heroesNexus ? ColorString.BACKGROUND_BLUE : ColorString.BACKGROUND_RED;
  }

  public String toString() {
    return "N";
  }

  public boolean isHeroesNexus() {
    return heroesNexus;
  }

  public Marketplace getMarketplace() {
    return marketplace;
  }
}
