package Locations;

import java.util.*;
import Fighters.*;
import Fighters.Heros.Hero;
import Fighters.Monsters.Monster;
import Items.Item;
import Items.Potion;
import Items.Spell;
import Items.Weapon;
import Structure.IO;
import Structure.Inventory;
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
    System.out.println("\n\n\n_________________ ENTERING BATTLEFIELD _________________\n\n\n");
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
    for (int i = 0; i < awakeHeroes.size() && !awakeMonsters.isEmpty(); i++) {
      Hero h = awakeHeroes.get(i);
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
    for (int i = 0; i < awakeMonsters.size() && !awakeHeroes.isEmpty(); i++) {
      Monster m = awakeMonsters.get(i);
      if (firstMoveByGroup) {
        System.out.println("_________________ MONSTERS TURN _________________");
        firstMoveByGroup = false;
      }
      monsterPlaysMove(m);
    }
    for (Hero h : awakeHeroes) {
      h.setFighterHp((int) (h.getFighterHp() * 1.1));
      Stats hStats = h.getHeroStats();
      hStats.set(Attribute.MANA, (int) (hStats.get(Attribute.MANA) * 1.1));
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
    int monsterIndex = 0, filteredInventoryIndex;
    Inventory filteredInventory;
    Item filteredInventoryItem;
    Monster target = null;
    while (true) {
      String heroBattleAction = io.getHeroBattleAction(h);
      System.out.println();
      switch (heroBattleAction) {
        case "i": // doesn't take up a turn, allows you to manage your inventory
          h.loopToManageInventory(io);
          break;
        case "a": // attack a monster and take up a turn (need to choose which weapon somehow
          filteredInventory = h.getInventory().filterByItemClass(Weapon.class).filterByEquipped();
          if (filteredInventory.size() >= 1) {
            if (filteredInventory.size() == 1) {
              filteredInventoryItem = filteredInventory.get(0);
            } else {
              filteredInventoryIndex = io.getValidListIndex(filteredInventory, false, "weapon");
              filteredInventoryItem = filteredInventory.get(filteredInventoryIndex);
            }
            monsterIndex = io.getValidListIndex(awakeMonsters, false, "monster to attack");
            target = monsters.get(monsterIndex);
            h.attack(target, filteredInventoryItem);
          } else {
            monsterIndex = io.getValidListIndex(awakeMonsters, false, "monster to attack");
            target = monsters.get(monsterIndex);
            h.attack(monsters.get(monsterIndex), null);
          }
          checkHpAndPrintIfFainted(target);
          return true;
        case "s": // doesn't take up a turn, show stats for all heroes and monsters that are awake
          showstatsAction();
          break;
        case "p": // use a potion from user's inventory
          filteredInventory = h.getInventory().filterByItemClass(Potion.class);
          if (filteredInventory.size() >= 1) {
            if (filteredInventory.size() == 1) {
              filteredInventoryItem = filteredInventory.get(0);
            } else {
              filteredInventoryIndex = io.getValidListIndex(filteredInventory, false, "potion");
              filteredInventoryItem = filteredInventory.get(filteredInventoryIndex);
            }
            if (filteredInventoryItem instanceof Potion p) {
              p.consumeItem(h);
            }
          } else {
            System.out.println("You don't have any potions to use.\n");
            break;
          }
          return true;
        case "m": // cast a magic spell
          filteredInventory = h.getInventory().filterByItemClass(Spell.class);
          if (filteredInventory.size() >= 1) {
            if (filteredInventory.size() == 1) {
              filteredInventoryItem = filteredInventory.get(0);
            } else {
              filteredInventoryIndex = io.getValidListIndex(filteredInventory, false, "spell");
              filteredInventoryItem = filteredInventory.get(filteredInventoryIndex);
            }
          } else {
            System.out.println("You don't have any spells to use.\n");
            break;
          }
          if (filteredInventoryItem instanceof Spell sp) {
            monsterIndex = io.getValidListIndex(awakeMonsters, false, "monster to attack");
            target = awakeMonsters.get(monsterIndex);
            boolean successfulSpellUse = h.useSpell(sp, target);
            if (!successfulSpellUse) {
              System.out.println("Not enough mana to cast this spell.\n");
              break;
            }
            checkHpAndPrintIfFainted(target);
          }
          return true;
        case "q": // quit
          return false;
      }
    }

  }

  private void checkHpAndPrintIfFainted(Fighter f) {
    if (f.getFighterHp() <= 0) {
      System.out.println(f.getName() + " has been knocked out cold!");
      if (f instanceof Hero h) {
        awakeHeroes.remove(h);
      }
      if (f instanceof Monster m) {
        awakeMonsters.remove(m);
      }
      System.out.println("actually removed: " + f.getName());
    } else {
      System.out.println("this kiddo is still kicking: " + f.getName());
    }
  }

  public void monsterPlaysMove(Monster m) {
    int randomAwakeHeroIndex = generator.nextInt(awakeHeroes.size());
    Fighter target = awakeHeroes.get(randomAwakeHeroIndex);
    m.attack(target);
    checkHpAndPrintIfFainted(target);
  }

  /**
   * action executed when hero chooses "s"
   */
  private void showstatsAction() {
    System.out.println("\n---------AWAKE HEROES:\n");
    for (Fighter f : awakeHeroes) {
      System.out.println(f.toString());
    }
    System.out.println("\n--------- AWAKE MONSTERS:\n");
    for (Fighter f : awakeMonsters) {
      System.out.println(f.toString());
    }
    System.out.println();
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
        asleepHeroStats.set(Attribute.MANA, (int) (asleepHeroStats.get(Attribute.MANA) * 0.5));
        h.setFighterHp(h.getLevel() * 100 / 2);
      }
    }
  }
}
