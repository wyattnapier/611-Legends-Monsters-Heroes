package Fighters;

import java.util.Random;

/**
 * highest level of superclass above the actual monsters and heroes
 * holds some very basic state and methods that is shared across all fighters
 */
public abstract class Fighter {
  protected Random generator;
  protected String name;
  protected int level;
  protected int hp;
  protected Stats stats;

  public Fighter(String name, int level, Stats stats) {
    generator = new Random();
    this.name = name;
    this.level = level;
    this.hp = level * 100;
    this.stats = stats;
  }

  public abstract void attack(Fighter target);

  /**
   * Takes damage and applies defense reduction.
   * Defense reduces damage by 0.5% per point of defense.
   * 
   * @param damage the incoming damage
   * @return actual damage taken (after dodge and defense reduction)
   */
  public int takeDamage(int damage) {
    if (didDodge()) {
      return 0;
    }

    // Defense reduces damage: each point of defense reduces damage by 0.05%
    double defenseReduction = 1.0 - (stats.get(Attribute.DEFENSE) * 0.0005);
    defenseReduction = Math.max(0.2, defenseReduction); // minimum 20% damage taken

    int actualDamage = (int) (damage * defenseReduction);
    hp -= actualDamage;
    return actualDamage;
  }

  /**
   * 
   * @return fighter's hp
   */
  public int getFighterHp() {
    return hp;
  }

  /**
   * sets new hp for fighter
   * 
   * @param newHp int for fighter's new hp
   */
  public void setFighterHp(int newHp) {
    hp = newHp;
  }

  /**
   * sees if a target dodged or not based on their agility and other stats
   * 
   * @return true if dodged and false otherwise
   */
  public abstract boolean didDodge();

  /**
   * checks if fighter hasn't fainted yet (is awake)
   * 
   * @return true if they are awake and false if they've fainted
   */
  public boolean isAwake() {
    return hp > 0;
  }

  /**
   * 
   * @return name of fighter
   */
  public String getName() {
    return name;
  }

  /**
   *
   * @return fighter's level
   */
  public int getLevel() {
    return level;
  }

  public String toString() {
    return name + " [LVL " + level + "] [HP: " + hp + "]";
  }
}
