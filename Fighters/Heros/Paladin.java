package Fighters.Heros;

import java.util.*;

import Fighters.Attribute;
import Fighters.Stats;
import Structure.Copyable;

public class Paladin extends Hero implements Copyable<Paladin> {
  public Paladin(String name, int mana, int strength, int agility, int dexterity, int startingMoney,
      int startingExperience) {
    super(name, createStats(strength, dexterity, agility, mana), startingMoney, startingExperience);
  }

  private static Stats createStats(int strength, int dexterity, int agility, int mana) {
    // add favored attributes
    Set<Attribute> favoredAttributes = new HashSet<Attribute>();
    favoredAttributes.add(Attribute.STRENGTH);
    favoredAttributes.add(Attribute.DEXTERITY);
    // create the object
    Stats stats = new Stats(favoredAttributes);
    return populateStats(stats, strength, dexterity, agility, mana);
  }

  public Paladin copy() {
    return new Paladin(name, stats.get(Attribute.MANA), stats.get(Attribute.STRENGTH), stats.get(Attribute.DEXTERITY),
        stats.get(Attribute.AGILITY), goldAmount, experience);
  }
}
