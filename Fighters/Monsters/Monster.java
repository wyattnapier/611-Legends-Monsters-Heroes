package Fighters.Monsters;

import Fighters.Attribute;
import Fighters.Fighter;
import Fighters.Stats;

public abstract class Monster extends Fighter {

  public Monster(String name, int level, Stats stats) {
    super(name, level, stats);
  }

  /**
   * sees if a target dodged or not based on their agility and other stats
   * 
   * @return true if dodged and false otherwise
   */
  public boolean didDodge() {
    double dodgeThreshold = stats.get(Attribute.AGILITY) * 0.01; // random must be under this value for dodge to be
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

  /**
   * return toString with stats
   */
  public String toLongString() {
    return super.toString() + " [DMG + " + stats.get(Attribute.DAMAGE) + "] [DEF: " + stats.get(Attribute.DAMAGE)
        + "] [AGL: " + stats.get(Attribute.AGILITY) + "]";
  }
}
