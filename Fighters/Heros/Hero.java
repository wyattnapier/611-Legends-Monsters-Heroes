package Fighters.Heros;

import java.util.*;
import Fighters.Attribute;
import Fighters.Fighter;
import Fighters.Stats;
import Structure.IO;
import Structure.Inventory;
import Items.*;

public abstract class Hero extends Fighter {
  private Inventory inventory;
  protected int goldAmount, experience;
  private Weapon attackWeapon;

  private Map<EquipmentSlot, Equippable> equipment;

  public Hero(String name, Stats stats, int startingMoney, int startingExperience) {
    super(name, 1, stats); // set level to 1
    inventory = new Inventory();
    goldAmount = startingMoney;
    experience = startingExperience;
    equipment = new HashMap<>();
  }

  // --------------------- battle related section
  /**
   * wraps the overridden attack method so we can specify a weapon for attacking
   * 
   * @param target monster to attack
   * @param weapon weapon used to attack target
   */
  public void attack(Fighter target, Item weapon) {
    attackWeapon = (Weapon) weapon;
    attack(target);
    attackWeapon = null;
  }

  @Override
  public void attack(Fighter target) {
    Weapon left = (Weapon) equipment.get(EquipmentSlot.LEFT_HAND);
    Weapon right = (Weapon) equipment.get(EquipmentSlot.RIGHT_HAND);

    double weaponDamage = 0;
    double damageMultiplier = 1.0;
    String weaponName;

    // Check if both hands have the same weapon and it's wielded as two-handed
    if (left != null && right != null && left == right && left.isTwoHanded()) {
      weaponDamage = left.getDamage();
      damageMultiplier = left.getDamageMultiplier();
      weaponName = left.getName();
    } else if (attackWeapon != null) {
      weaponDamage = attackWeapon.getDamage();
      weaponName = attackWeapon.getName();
    } else {
      weaponDamage = 10; // fist damage because no weapon
      weaponName = "hands";
    }

    // apply the actual formula here
    int damage = (int) ((stats.get(Attribute.STRENGTH) + (weaponDamage * damageMultiplier)) * 0.05);
    int damageActuallyDealt = target.takeDamage(damage);
    if (damageActuallyDealt > 0) {
      System.out.println(name + " used a " + weaponName + " to do " +
          damageActuallyDealt + " damage to " + target.getName());
    } else {
      System.out.println(target.getName() + " dodged the attack by " + name);
    }
  }

  public boolean useSpell(Spell sp, Fighter target) {
    boolean canUseSpell = sp.consumeItem(this);
    if (!canUseSpell) {
      return false;
    }
    double damage = sp.getDamage() + (stats.get(Attribute.DEXTERITY) / 10000) * sp.getDamage();
    int damageDone = target.takeDamage((int) damage);
    System.out.println(name + " used a " + sp.getName() + " to do " + damageDone + " damage to " + target.getName());
    return true;
  }

  /**
   * sees if a target dodged or not based on their agility and other stats
   * 
   * @return true if dodged and false otherwise
   */
  public boolean didDodge() {
    double dodgeThreshold = stats.get(Attribute.AGILITY) * 0.002; // random must be under this value for dodge to be
                                                                  // successful
    double dodgeChance = generator.nextDouble();
    return dodgeChance <= dodgeThreshold;
  }

  /**
   * add the input amount of experience to a heroe's total
   * 
   * @param addedExperience
   */
  public void incrementExperience(int addedExperience) {
    experience += addedExperience;
    // continue leveling up multiple times at once iff possible
    while (levelUpIfPossible()) {
    }
  }

  /**
   * level up if possible
   * 
   * @return true if possible and false otherwise
   */
  public boolean levelUpIfPossible() {
    // return false if not enough experience to level up
    if (experience < level * 10) {
      return false;
    }
    level++;
    // all skills increase by 5% and favored skills increase by an extra 5%
    stats.levelUp();
    hp = level * 100;
    return true;
  }

  // --------------------- getters and setters
  /**
   * 
   * @return hero's gold amount
   */
  public int getGoldAmount() {
    return goldAmount;
  }

  /**
   * set new gold amounts
   * 
   * @param newGoldAmount
   */
  public void setGoldAmount(int newGoldAmount) {
    goldAmount = newGoldAmount;
  }

  public Stats getHeroStats() {
    return stats;
  }

  /**
   * populates the stats variable during its initialization before object is
   * created
   * 
   * @param stats
   * @param strength
   * @param dexterity
   * @param agility
   * @param mana
   * @return a populated stats variable
   */
  public static Stats populateStats(Stats stats, int strength, int dexterity, int agility, int mana) {
    stats.set(Attribute.STRENGTH, strength);
    stats.set(Attribute.DEXTERITY, dexterity);
    stats.set(Attribute.AGILITY, agility);
    stats.set(Attribute.MANA, mana);
    return stats;
  }

  /**
   * 
   * @return hero's inventory for updating by marketplace
   */
  public Inventory getInventory() {
    return inventory;
  }

  /**
   * @return true if equips successfully
   */
  public boolean equipItem(Equippable item, boolean useTwoHands) {
    if (item instanceof Weapon weapon) {
      // 2 handed weapon
      if (weapon.getRequiredHands() == 2) {
        if (equipment.get(EquipmentSlot.LEFT_HAND) != null ||
            equipment.get(EquipmentSlot.RIGHT_HAND) != null) {
          System.out.println("Cannot equip item because it requires 2 hands and both aren't free.\n");
          return false;
        }
        equipment.put(EquipmentSlot.LEFT_HAND, weapon);
        equipment.put(EquipmentSlot.RIGHT_HAND, weapon);
      }
      // 1 handed weapon choosing to use as 2 handed
      else if (useTwoHands && weapon.canUseTwoHands()) {
        if (equipment.get(EquipmentSlot.LEFT_HAND) != null ||
            equipment.get(EquipmentSlot.RIGHT_HAND) != null) {
          System.out.println("Cannot equip item in 2 hands because they aren't both free.\n");
          return false;
        }
        equipment.put(EquipmentSlot.LEFT_HAND, weapon);
        equipment.put(EquipmentSlot.RIGHT_HAND, weapon);
        weapon.setTwoHanded(true);
      }
      // 1 handed weapon in one hand
      else {
        if (equipment.get(EquipmentSlot.LEFT_HAND) == null) {
          equipment.put(EquipmentSlot.LEFT_HAND, weapon);
        } else if (equipment.get(EquipmentSlot.RIGHT_HAND) == null) {
          equipment.put(EquipmentSlot.RIGHT_HAND, weapon);
        } else {
          System.out.println("Cannot equip item because both hands are full.\n");
          return false; // both hands full
        }
      }
      // armor
    } else if (item instanceof Armor armor) {
      if (equipment.get(EquipmentSlot.ARMOR) == null) {
        equipment.put(EquipmentSlot.ARMOR, armor);
      } else {
        System.out.println("Cannot equip armor because armor is already equipped.\n");
        return false;
      }
    }
    item.setIsEquipped(true);
    return true;
  }

  /**
   * 
   * @param item
   * @return true if unequips successfully
   */
  public boolean unequipItem(Equippable item) {
    boolean found = false;
    for (EquipmentSlot slot : EquipmentSlot.values()) {
      Equippable equipped = equipment.get(slot);
      if (equipped != null && equipped.equals(item)) {
        equipment.remove(slot);
        found = true;
      }
    }
    if (found) {
      item.setIsEquipped(false);
    }
    return found;
  }

  /**
   * @param io
   * @return true to keep playing game and false to quit
   */
  public boolean loopToManageInventory(IO io) {
    while (true) {
      int res = manageInventory(io);
      System.out.println();
      if (res == -1) {
        return false;
      } else if (res == 0) {
        return true;
      }
    }
  }

  /**
   * 
   * @param io input/output
   * @return -1 for quit, 0 for back, 1 for continue
   */
  public int manageInventory(IO io) {
    Object[] inputOption = io.getListIndexorEquippingOptionForManagingInventory(this);
    if (inputOption.length != 2) {
      System.out.println("Your input broke the game! Try again.");
      return 0; // something is wrong
    }
    String command = inputOption[0].toString();
    int index = ((Number) inputOption[1]).intValue();
    switch (inputOption[0].toString()) {
      case "q":
        return -1;
      case "b":
        return 0;
      case "r":
        inventory.remove(index);
        break;
      case "u":
        if (inventory.get(index) instanceof Equippable eItem) {
          unequipItem(eItem);
        }
        break;
      case "e":
      case "e2":
        Boolean equipTwoHanded = "e2".equals(command);
        if (inventory.get(index) instanceof Equippable eItem) {
          equipItem(eItem, equipTwoHanded);
        }
        break;
      default:
        break;
    }
    return 1;
  }

  public String toString() {
    return name + " [LVL: " + level + "] [GLD: " + goldAmount + "] [HP: " + hp + "] [MP: "
        + stats.get(Attribute.MANA) + "] [STR: " + stats.get(Attribute.STRENGTH) + "] [DEF: "
        + stats.get(Attribute.DEFENSE) + "] [DEX: " + stats.get(Attribute.DEXTERITY) + "] [AGL: "
        + stats.get(Attribute.AGILITY) + "]";
  }

  public String toLongString() {
    StringBuilder sb = new StringBuilder(
        name + "\n - [LVL: " + level + "]\n - [GLD: " + goldAmount + "]\n - [HP: " + hp + "]\n - [MP: "
            + stats.get(Attribute.MANA) + "]\n - Equipped items:\n");
    // add the equipped items
    boolean hasEquippedItems = false;
    if (equipment != null) {
      Equippable left = equipment.get(EquipmentSlot.LEFT_HAND);
      Equippable right = equipment.get(EquipmentSlot.RIGHT_HAND);
      Equippable armor = equipment.get(EquipmentSlot.ARMOR);

      if (right != null && left != null && right.equals(left)) {
        sb.append("   - [2H: ").append(right.getNameAndLevel()).append("]\n");
        hasEquippedItems = true;
      } else {
        if (left != null && right != left) {
          sb.append("   - [HL: ").append(left.getNameAndLevel()).append("]\n");
          hasEquippedItems = true;
        }
        if (right != null && right != left) {
          sb.append("   - [HR: ").append(right.getNameAndLevel()).append("]\n");
          hasEquippedItems = true;
        }
      }
      if (armor != null) {
        sb.append("   - [ARMOR: ").append(armor.getNameAndLevel()).append("]\n");
        hasEquippedItems = true;
      }
    }
    if (!hasEquippedItems) {
      sb.append("   - NONE\n");
    }
    return sb.toString();
  }

  public String toLongStringWithInventory() {
    StringBuilder sb = new StringBuilder();
    sb.append(toLongString());
    if (inventory.size() == 0) {
      sb.append("Empty inventory");
    } else {
      sb.append("Inventory:\n");
      sb.append(inventoryToList());
    }
    return sb.toString();
  }

  public String inventoryToList() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < inventory.size(); i++) {
      sb.append("(" + i + ") - " + inventory.get(i) + "\n");
    }
    return sb.toString();
  }
}
