package Items;

import Fighters.Attribute;
import Fighters.Heros.Hero;

public class Spell extends Item implements Consumable, AttackWith {
  private int damage, manaCost;

  public Spell(String nameInput, int costInput, int requiredLevelInput, int damageInput, int manaCostInput) {
    super(nameInput, costInput, requiredLevelInput);
    damage = damageInput;
    manaCost = manaCostInput;
  }

  @Override
  public int getDamage() {
    return damage;
  }

  @Override
  public boolean consumeItem(Hero h) {
    if (h.getHeroStats().get(Attribute.MANA) >= manaCost) {
      h.getHeroStats().set(Attribute.MANA, h.getHeroStats().get(Attribute.MANA) - manaCost);
      h.getInventory().remove(this);
      return true;
    }
    return false;
  }

  @Override
  public Spell copy() {
    return new Spell(name, cost, requiredLevel, damage, manaCost);
  }

  public int getLevel() {
    return super.getRequiredLevel();
  }
}
