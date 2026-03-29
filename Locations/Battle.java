package Locations;

import java.util.*;
import Fighters.*;
import Fighters.Heros.Hero;
import Fighters.Monsters.Monster;

public class Battle {
  private List<Hero> heroes;
  private List<Monster> monsters;
  int numHeroesAwake, numMonstersAwake;

  public Battle(List<Hero> input_hero_list) {
    this.heroes = input_hero_list;
    // monster level = max level of heroes in party
    int monsterLevel = 0;
    for (Hero h : heroes) {
      monsterLevel = Math.max(h.getLevel(), monsterLevel);
    }
    for (int i = 0; i < heroes.size(); i++) {
      // TODO: create a set of monsters to fight
      monsters.add(null);
    }
    numHeroesAwake = heroes.size();
    numMonstersAwake = monsters.size();
  }

  public void playBattle() {
    while (numHeroesAwake > 0 && numMonstersAwake > 0) {
      playSingleRound();
    }
    if (numHeroesAwake > 0) {
      System.out.println("All monsters defeated!");
      rewardHeroesForWinningBattle();
    } else {
      System.out.println("All heroes have been knocked out and the game is over.");
    }
  }

  /**
   * plays a single round of battle
   * 
   * @return number of monsters that are still awake
   */
  public void playSingleRound() {
    for (Hero h : heroes) {
      if (h.isAwake()) {
        heroPlaysMove(h);
      }
    }
    for (Monster m : monsters) {
      if (m.isAwake()) {
        monsterPlaysMove(m);
      }
    }
    for (Hero h : heroes) {
      if (h.isAwake()) {
        h.setFighterHp((int) (h.getFighterHp() * 1.1));
        Stats hStats = h.getHeroStats();
        hStats.set(Attribute.MANA, hStats.get(Attribute.MANA) * 1.1);
      }
    }
  }

  // should this live in the hero class?
  public void heroPlaysMove(Hero h) {
    // TODO: get input on what to do/who to attack
    h.attack(monsters.get(0)); // just a dumb example of a turn
  }

  public void monsterPlaysMove(Monster m) {
    // TODO: get a random index from the list of heroes and attack them (make sure
    // they're awake already though)
    int randomHeroIndex = 0;
    m.attack(heroes.get(randomHeroIndex));
  }

  /**
   * reward heroes for winning battles by incrementing their stats
   */
  public void rewardHeroesForWinningBattle() {
    for (Hero h : heroes) {
      // make sure this happens before hp is reset
      if (h.isAwake()) {
        h.setGoldAmount(h.getGoldAmount() * 2);
        h.incrementExperience(monsters.size() * 2);
      } else {
        // asleep heroes get revived with half of their HP and mana but don't gain gold
        // or xp
        Stats asleepHeroStats = h.getHeroStats();
        asleepHeroStats.set(Attribute.MANA, asleepHeroStats.get(Attribute.MANA) * 0.5);
        h.setFighterHp(h.getLevel() * 100 / 2);
      }
    }
  }
}
