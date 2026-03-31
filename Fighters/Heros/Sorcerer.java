package Fighters.Heros;

import java.util.*;

import Fighters.Attribute;
import Fighters.Stats;
import Structure.Copyable;

public class Sorcerer extends Hero implements Copyable<Sorcerer> {
  public Sorcerer(String name, int mana, int strength, int agility, int dexterity, int startingMoney,
      int startingExperience) {
    super(name, createStats(strength, dexterity, agility, mana), startingMoney, startingExperience);
  }

  private static Stats createStats(int strength, int dexterity, int agility, int mana) {
    // add favored attributes
    Set<Attribute> favoredAttributes = new HashSet<Attribute>();
    favoredAttributes.add(Attribute.AGILITY);
    favoredAttributes.add(Attribute.DEXTERITY);
    // create and populate the object
    Stats stats = new Stats(favoredAttributes);
    return populateStats(stats, strength, dexterity, agility, mana);
  }

  public Sorcerer copy() {
    return new Sorcerer(name, stats.get(Attribute.MANA), stats.get(Attribute.STRENGTH), stats.get(Attribute.DEXTERITY),
        stats.get(Attribute.AGILITY), goldAmount, experience);
  }
}