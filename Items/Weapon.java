package Items;

import java.util.ArrayList;
import java.util.List;

import Fighters.Heros.Hero;

public class Weapon extends Item implements Equippable {
  private int baseDamage, requiredHands;
  private boolean hasTwoHandedBonus, canUseTwoHands;

  public Weapon(String name, int cost, int level, int damage, int requiredHands) {
    super(name, cost, level);
    this.baseDamage = damage;
    this.requiredHands = requiredHands;
    canUseTwoHands = true;
  }

  @Override
  public void onEquip(Hero h) {
    // todo; implement
  }

  @Override
  public void onUnequip(Hero h) {
    hasTwoHandedBonus = false;
  }

  public int getRequiredHands() {
    return requiredHands;
  }

  public boolean getCanUseTwoHands() {
    return canUseTwoHands;
  }

  public boolean getHasTwoHandedBonus() {
    return hasTwoHandedBonus;
  }

  /**
   * set if weapon has two handed bonus or not as long as its not a 2 handed
   * weapon
   * 
   * @param hasTwoHandedBonus
   */
  public void setHasTwoHandedBonus(boolean hasTwoHandedBonus) {
    this.hasTwoHandedBonus = requiredHands == 2 ? false : hasTwoHandedBonus;
  }

  public double getWeaponDamage() {
    return hasTwoHandedBonus ? baseDamage * 1.5 : baseDamage;
  }

  public List<EquipmentSlot> getEquipmentSlotOptions() {
    List<EquipmentSlot> slotOptions = new ArrayList<EquipmentSlot>();
    slotOptions.add(EquipmentSlot.LEFT_HAND);
    slotOptions.add(EquipmentSlot.RIGHT_HAND);
    return slotOptions;
  }
}
