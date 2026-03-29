package Fighters.Heros;

import java.util.*;

import Fighters.Attribute;
import Fighters.Stats;

public class Warrior extends Hero {
  public Warrior(String name, int mana, int strength, int agility, int dexterity, int startingMoney,
      int startingExperience) {
    super(name, createStats(strength, dexterity, agility, mana), startingMoney, startingExperience);
  }

  private static Stats createStats(int strength, int dexterity, int agility, int mana) {
    // add favored attributes
    Set<Attribute> favoredAttributes = new HashSet<Attribute>();
    favoredAttributes.add(Attribute.STRENGTH);
    favoredAttributes.add(Attribute.AGILITY);
    // create the object
    Stats stats = new Stats(favoredAttributes);
    return populateStats(stats, strength, dexterity, agility, mana);
  }
}
