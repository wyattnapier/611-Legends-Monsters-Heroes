package Items;

import java.util.ArrayList;
import java.util.List;

import Fighters.Attribute;
import Fighters.Stats;
import Fighters.Heros.Hero;

public class Potion extends Item implements Consumable {
  private int attributeIncrease;
  private List<Object> attributesAffected;
  private String attributesAffectedString, attributesAffectedStringInput;

  public Potion(String nameInput, int costInput, int requiredLevelInput, int attributeIncreaseInput,
      String attributesAffectedStringInput) {
    super(nameInput, costInput, requiredLevelInput);
    attributeIncrease = attributeIncreaseInput;
    attributesAffected = new ArrayList<Object>();
    // convert input attributeseffect string
    String[] stringParts = attributesAffectedStringInput.trim().split("\\s+");
    this.attributesAffectedStringInput = attributesAffectedStringInput;
    attributesAffectedString = stringParts[0];
    String importantSectionWithSlashes = stringParts[stringParts.length - 1];
    if (importantSectionWithSlashes.contains("Health")) {
      attributesAffected.add("HEALTH");
    }
    if (importantSectionWithSlashes.contains("Strength")) {
      attributesAffected.add(Attribute.STRENGTH);
    }
    if (importantSectionWithSlashes.contains("Mana")) {
      attributesAffected.add(Attribute.MANA);
    }
    if (importantSectionWithSlashes.contains("Agility")) {
      attributesAffected.add(Attribute.AGILITY);
    }
    if (importantSectionWithSlashes.contains("Dexterity")) {
      attributesAffected.add(Attribute.DEXTERITY);
    }
    if (importantSectionWithSlashes.contains("Defense")) {
      attributesAffected.add(Attribute.DEFENSE);
    }
  }

  @Override
  public boolean consumeItem(Hero h) {
    for (Object o : attributesAffected) {
      if (o instanceof Attribute att) {
        Stats heroStats = h.getHeroStats();
        int currentValue = heroStats.get(att);
        heroStats.set(att, currentValue + attributeIncrease);
      } else {
        // if it isn't an instance of attribute enum then it has to be "health"
        h.setFighterHp(h.getFighterHp() + attributeIncrease);
      }
    }
    h.getInventory().remove(this);
    System.out.println(
        h.getName() + " used " + name + " to increase " + attributesAffectedString + " stats by " + attributeIncrease);
    return true;
  }

  @Override
  public Potion copy() {
    return new Potion(name, cost, requiredLevel, attributeIncrease, attributesAffectedStringInput);
  }

  public int getLevel() {
    return super.getRequiredLevel();
  }
}
