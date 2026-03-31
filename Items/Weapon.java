package Items;

import java.util.ArrayList;
import java.util.List;

public class Weapon extends Equippable implements AttackWith {
  private int baseDamage, requiredHands;
  private boolean isTwoHanded; // tracks if this weapon is currently being wielded with two hands

  public Weapon(String name, int cost, int level, int damage, int requiredHands) {
    super(name, cost, level);
    this.baseDamage = damage;
    this.requiredHands = requiredHands;
    this.isTwoHanded = false;
  }

  @Override
  public void setIsEquipped(boolean isEquippedNow) {
    super.setIsEquipped(isEquippedNow);
    if (!isEquipped) {
      isTwoHanded = false;
    }
  }

  public int getRequiredHands() {
    return requiredHands;
  }

  public boolean canUseTwoHands() {
    return requiredHands == 1; // only 1-handed weapons can be used as 2-handed
  }

  public boolean isTwoHanded() {
    return isTwoHanded;
  }

  /**
   * Set whether this weapon should be wielded with two hands.
   * Only allows two-handed wielding for 1-handed weapons.
   * 
   * @param twoHanded whether to wield with two hands
   */
  public void setTwoHanded(boolean twoHanded) {
    this.isTwoHanded = canUseTwoHands() && twoHanded;
  }

  public int getDamage() {
    return baseDamage;
  }

  public double getDamageMultiplier() {
    return isTwoHanded ? 1.5 : 1.0;
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
    if (isEquipped) {
      if (isTwoHanded) {
        return super.toString() + " [DMG: " + baseDamage + "] [EQUIPPED 2H]";
      } else {
        return super.toString() + " [DMG: " + baseDamage + "] [EQUIPPED]";
      }
    }
    return super.toString() + " [DMG: " + baseDamage + "] [HNDS: " + requiredHands + "]";
  }
}
