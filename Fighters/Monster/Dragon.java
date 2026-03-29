package Fighters.Monster;

import java.util.HashSet;
import java.util.Set;

import Fighters.Attribute;
import Fighters.Stats;

public class Dragon extends Monster {
  public Dragon(String name, int level, int baseDamage, int increasedDefense, int agility) {
    super(name, level, createStats(baseDamage, increasedDefense, agility));
  }

  private static Stats createStats(int baseDamage, int increasedDefense, int agility) {
    // add favored attribute
    Set<Attribute> favoredAttributes = new HashSet<Attribute>();
    favoredAttributes.add(Attribute.DAMAGE);
    // create and populate object
    Stats stats = new Stats(favoredAttributes);
    return populateStats(stats, baseDamage, increasedDefense, agility);
  }
}
