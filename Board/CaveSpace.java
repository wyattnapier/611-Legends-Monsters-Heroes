package Board;

import Fighters.Attribute;
import Util.ColorString;

import java.util.HashMap;
import java.util.Map;

public class CaveSpace extends Space implements HeroBuffAtSpace {
  public CaveSpace(int r, int c) {
    super(r, c);
  }

  @Override
  public BoardSpaceOption getSpaceType() {
    return BoardSpaceOption.CAVE;
  }

  // TOOD: actually buff heroes according to this
  @Override
  public Map<Attribute, Integer> getHeroBuff() {
    Map<Attribute, Integer> map = new HashMap<Attribute, Integer>();
    map.put(Attribute.AGILITY, Integer.valueOf(100));
    return map;
  }

  @Override
  public String getBackgroundColor() {
    return ColorString.BACKGROUND_DARK_GRAY;
  }

  public String toString() {
    return "C";
  }
}
