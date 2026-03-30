package Locations;

import java.util.*;
import Fighters.*;
import Fighters.Heros.Hero;
import Fighters.Monsters.Monster;
import Structure.IO;
import Util.GameData;

public class Battle {
  private List<Hero> heroes, awakeHeroes;
  private List<Monster> monsters, awakeMonsters;
  private IO io;
  private Random generator = new Random();

  public Battle(List<Hero> input_hero_list, IO io) {
    this.heroes = input_hero_list;
    this.awakeHeroes = new ArrayList<Hero>(input_hero_list);
    this.io = io;
    this.monsters = new ArrayList<Monster>();
    this.awakeMonsters = new ArrayList<Monster>();
    // monster level = max level of heroes in party
    int monsterLevel = 0;
    for (Hero h : heroes) {
      monsterLevel = Math.max(h.getLevel(), monsterLevel);
    }
    for (int i = 0; i < heroes.size(); i++) {
      Monster newMonster = GameData.monsters.random(monsterLevel);
      if (newMonster != null) {
        monsters.add(newMonster);
        awakeMonsters.add(newMonster);
      } else {
        System.out.println("No monsters found for level " + monsterLevel);
        monsterLevel--;
        i--; // try again with a lower level
      }
    }
  }

  /**
   * runs battle between monsters and heroes until one side wins, then rewards
   * heroes if they win
   * 
   * @return true if heroes win, false if monsters win or player quits the game
   */
  public boolean playBattle() {
    boolean stillPlaying = true;
    while (awakeHeroes.size() > 0 && awakeMonsters.size() > 0) {
      stillPlaying = playSingleRound();
      if (stillPlaying == false) {
        System.out.println("The heroes gave up and the monsters overran them.");
        return false;
      }
    }
    if (awakeHeroes.size() > 0) {
      System.out.println("All monsters defeated!");
      rewardHeroesForWinningBattle();
      return true;
    } else {
      System.out.println("All heroes have been knocked out and the game is over.");
      return false;
    }
  }

  /**
   * plays a single round of battle
   * 
   * @return true if still fighting, false if they quit
   */
  public boolean playSingleRound() {
    boolean stillPlaying = true;
    boolean firstMoveByGroup = true;
    for (Hero h : awakeHeroes) {
      if (firstMoveByGroup) {
        System.out.println("_________________ HEROES TURN _________________");
        firstMoveByGroup = false;
      }
      stillPlaying = heroPlaysMove(h);
      if (stillPlaying == false) {
        return false;
      }
    }
    firstMoveByGroup = true;
    for (Monster m : awakeMonsters) {
      if (firstMoveByGroup) {
        System.out.println("_________________ MONSTERS TURN _________________");
        firstMoveByGroup = false;
      }
      monsterPlaysMove(m);
    }
    for (Hero h : awakeHeroes) {
      h.setFighterHp((int) (h.getFighterHp() * 1.1));
      Stats hStats = h.getHeroStats();
      hStats.set(Attribute.MANA, hStats.get(Attribute.MANA) * 1.1);
    }
    return true;
  }

  /**
   * prompts hero to input their move and plays it
   * 
   * @param h hero who is playing current turn
   * @return true if still playing and false if quit game
   */
  public boolean heroPlaysMove(Hero h) {
    while (true) {
      String heroBattleAction = io.getHeroBattleAction(h);
      switch (heroBattleAction) {
        // doesn't take up a turn, allows you to manage your inventory though
        case "i":
          h.loopToManageInventory(io);
          break;
        // attack a monster and take up a turn (need to choose which weapon somehow
        // though)
        case "a":
          // choose a weapon then choose a target
          h.attack(monsters.get(0)); // just a dumb example of a turn
          return true;
        case "s": // doesn't take up a turn, show stats for all heroes and monsters that are awake
        case "p": // use a potion from user's inventory
          // prompt them to choose a potion and if none exist then give them another turn
        case "m": // cast a magic spell
          // prompt them to pick a spell and pick an opponent (if they fail to choose a
          // spell then turn isn't consumed)
        case "q": // quit
          return false;
      }
    }
  }

  /**
   * setter to remove fighter from the list of awake fighters in a battle
   * 
   * @param f fighter (monster or hero)
   */
  public void setFighterToFainted(Fighter f) {
    if (f instanceof Hero h) {
      awakeHeroes.remove(h);
    }
    if (f instanceof Monster m) {
      awakeMonsters.remove(m);
    }
  }

  public void monsterPlaysMove(Monster m) {
    // TODO: get a random index from the list of heroes and attack them (make sure
    // they're awake already though)
    int randomHeroIndex = generator.nextInt(awakeHeroes.size());
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
