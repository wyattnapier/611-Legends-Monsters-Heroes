package Board;

import Fighters.Attribute;
import java.util.Map;

// interface for spaces that are supposed to buff some hero attribute
public interface HeroBuffAtSpace {
  public Map<Attribute, Integer> getHeroBuff();
}
