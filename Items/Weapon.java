package Items;

import java.util.ArrayList;
import java.util.List;

public class Weapon extends Equippable implements AttackWith {
  private int baseDamage, requiredHands;
  private boolean hasTwoHandedBonus, canUseTwoHands;

  public Weapon(String name, int cost, int level, int damage, int requiredHands) {
    super(name, cost, level);
    this.baseDamage = damage;
    this.requiredHands = requiredHands;
    hasTwoHandedBonus = true;
    canUseTwoHands = true;
  }

  @Override
  public void setIsEquipped(boolean isEquippedNow) {
    super.setIsEquipped(isEquippedNow);
    if (!isEquipped) {
      hasTwoHandedBonus = false;
    } else {
      // TODO: figure if it should have two handed bonus or not (maybe should do in
      // hero)
      hasTwoHandedBonus = requiredHands < 2;
    }
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

  public int getDamage() {
    return baseDamage; // the 1.5 is handled within the hero because it has equip status
  }

  public List<EquipmentSlot> getEquipmentSlotOptions() {
    List<EquipmentSlot> slotOptions = new ArrayList<EquipmentSlot>();
    slotOptions.add(EquipmentSlot.LEFT_HAND);
    slotOptions.add(EquipmentSlot.RIGHT_HAND);
    return slotOptions;
  }

  @Override
  public Weapon copy() {
    return new Weapon(name, cost, requiredLevel, baseDamage, requiredHands);
  }

  public int getLevel() {
    return super.getRequiredLevel();
  }

  public String toString() {
    String isEquippedString = isEquipped ? "[EQUIPPED]" : "";
    return super.toString() + " [DMG: " + baseDamage + "] [HNDS: " + requiredHands + "] " + isEquippedString;
  }
}
