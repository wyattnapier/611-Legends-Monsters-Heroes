package Fighters;

import java.util.Random;

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

  public void takeDamage(int damage) {
    hp -= damage;
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
}
