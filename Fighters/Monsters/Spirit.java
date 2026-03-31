package Fighters.Monsters;

import java.util.HashSet;
import java.util.Set;

import Fighters.Attribute;
import Fighters.Stats;

public class Spirit extends Monster {
  public Spirit(String name, int level, int baseDamage, int increasedDefense, int agility) {
    super(name, level, createStats(baseDamage, increasedDefense, agility));
  }

  private static Stats createStats(int baseDamage, int increasedDefense, int agility) {
    // add favored attribute
    Set<Attribute> favoredAttributes = new HashSet<Attribute>();
    favoredAttributes.add(Attribute.AGILITY);
    // create and populate object
    Stats stats = new Stats(favoredAttributes);
    return populateStats(stats, baseDamage, increasedDefense, agility);
  }

  public Spirit copy() {
    return new Spirit(name, level, stats.get(Attribute.DAMAGE), stats.get(Attribute.DEFENSE),
        stats.get(Attribute.AGILITY));
  }
}
