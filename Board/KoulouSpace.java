package Board;

import Fighters.Attribute;
import Util.ColorString;

import java.util.HashMap;
import java.util.Map;

public class KoulouSpace extends Space implements HeroBuffAtSpace {
  public KoulouSpace(int r, int c) {
    super(r, c);
  }

  @Override
  public BoardSpaceOption getSpaceType() {
    return BoardSpaceOption.KOULOU;
  }

  // TOOD: actually buff heroes according to this
  @Override
  public Map<Attribute, Integer> getHeroBuff() {
    Map<Attribute, Integer> map = new HashMap<Attribute, Integer>();
    map.put(Attribute.STRENGTH, Integer.valueOf(100));
    return map;
  }

  @Override
  public String getBackgroundColor() {
    return ColorString.BACKGROUND_YELLOW;
  }

  @Override
  public String toString() {
    return "K";
  }
}
