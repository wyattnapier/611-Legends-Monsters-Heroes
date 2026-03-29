package Fighters;

import java.util.*;

public class Stats {
  private EnumMap<Attribute, Double> attributes;
  private Set<Attribute> favoredAttributes;

  public Stats(Set<Attribute> favoredAttributes) {
    attributes = new EnumMap<>(Attribute.class);
    this.favoredAttributes = favoredAttributes;
  }

  /**
   * set an attribute to a new value
   * 
   * @param attr  selected attribute
   * @param value new value for it
   */
  public void set(Attribute attr, double value) {
    attributes.put(attr, value);
  }

  /**
   * get value of an attribute or 0.0 if it doesn't exists
   * 
   * @param attr selected attribute used as key
   * @return its value or 0.0
   */
  public double get(Attribute attr) {
    return attributes.getOrDefault(attr, 0.0);
  }

  /**
   * level up all attributes
   * 
   * @param baseIncrease 0.05 for heroes
   */
  public void levelUp() {
    double baseIncrease = 0.05;
    for (Attribute attr : attributes.keySet()) {
      double value = attributes.get(attr);
      value *= attr == Attribute.MANA ? 1.1 : (1 + baseIncrease); // mana points scale by 1.1, everything else by 1.05
      if (favoredAttributes.contains(attr)) {
        value *= (1 + baseIncrease);
      }
      attributes.put(attr, value);
    }
  }
}