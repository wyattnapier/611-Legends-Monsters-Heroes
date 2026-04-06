package Board;

import java.util.HashMap;
import java.util.Map;

import Fighters.Attribute;
import Util.ColorString;

public class BushSpace extends Space implements HeroBuffAtSpace {

  public BushSpace(int r, int c) {
    super(r, c);
  }

  @Override
  public BoardSpaceOption getSpaceType() {
    return BoardSpaceOption.BUSH;
  }

  // TOOD: actually buff heroes according to this
  @Override
  public Map<Attribute, Integer> getHeroBuff() {
    Map<Attribute, Integer> map = new HashMap<Attribute, Integer>();
    map.put(Attribute.DEXTERITY, Integer.valueOf(100));
    return map;
  }

  public String getBackgroundColor() {
    return ColorString.BACKGROUND_GREEN;
  }

  public String toString() {
    return "B";
  }
}
