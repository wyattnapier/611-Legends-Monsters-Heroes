package Fighters.Monsters;

import Fighters.Attribute;
import Fighters.Fighter;
import Fighters.Stats;
import Structure.Copyable;

/**
 * superclass of all monster
 * handles most of the monster game logic and state as all of its concrete
 * subclasses are incredibly similar
 */
public abstract class Monster extends Fighter implements Copyable<Monster> {

  public Monster(String name, int level, Stats stats) {
    super(name, level, stats);
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

  @Override
  public void attack(Fighter target) {
    int damage = (int) stats.get(Attribute.DAMAGE);
    int damageActuallyDealt = target.takeDamage(damage);
    if (damageActuallyDealt > 0) {
      System.out.println(name + " attacked " + target.getName() + " for " + damageActuallyDealt);
    } else {
      System.out.println(target.getName() + " dodged the attack by " + name);
    }
  }

  /**
   * populates the stats variable during its initialization before object is
   * created
   * 
   * @param stats
   * @param baseDamage
   * @param increasedDefense
   * @param agility
   * @return a populated stats object for the monster
   */
  public static Stats populateStats(Stats stats, int baseDamage, int increasedDefense, int agility) {
    stats.set(Attribute.DAMAGE, baseDamage);
    stats.set(Attribute.DEFENSE, increasedDefense);
    stats.set(Attribute.AGILITY, agility);
    return stats;
  }

  // gold reward to hero that defeats this monster
  public int getGoldRewardWhenSlain() {
    int dmg = stats.get(Attribute.DAMAGE);
    int def = stats.get(Attribute.DEFENSE);
    return 10 * level + dmg / 4 + def / 8;
  }

  // experience reward to hero that defeats this monster
  public int getExperienceRewardWhenSlain() {
    int dmg = stats.get(Attribute.DAMAGE);
    return Math.max(1, level * 2 + dmg / 50);
  }

  /**
   * return toString with stats
   */
  public String toString() {
    return super.toString() + " [DMG: " + stats.get(Attribute.DAMAGE) + "] [DEF: " + stats.get(Attribute.DEFENSE)
        + "] [AGL: " + stats.get(Attribute.AGILITY) + "]";
  }
}
