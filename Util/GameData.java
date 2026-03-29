package Util;

import Util.*;
import Fighters.Heros.*;
import Fighters.Monsters.*;
import Items.*;

import java.util.*;

public class GameData {

  public static RandomFactory<Warrior> warriors;
  public static RandomFactory<Sorcerer> sorcerers;
  public static RandomFactory<Paladin> paladins;

  public static MonsterLevelFactory<Monster> monsters;

  public static RandomFactory<Item> items;

  public static void loadAll() {
    loadHeroes();
    loadMonsters();
    loadItems();
  }

  private static void loadHeroes() {
    try {
      warriors = new RandomFactory<>(
          DataLoader.load("ProvidedData/Warriors.txt",
              t -> new Warrior(
                  t[0],
                  Integer.parseInt(t[1]),
                  Integer.parseInt(t[2]),
                  Integer.parseInt(t[3]),
                  Integer.parseInt(t[4]),
                  Integer.parseInt(t[5]),
                  Integer.parseInt(t[6]))));

      sorcerers = new RandomFactory<>(
          DataLoader.load("ProvidedData/Sorcerers.txt",
              t -> new Sorcerer(t[0],
                  Integer.parseInt(t[1]),
                  Integer.parseInt(t[2]),
                  Integer.parseInt(t[3]),
                  Integer.parseInt(t[4]),
                  Integer.parseInt(t[5]),
                  Integer.parseInt(t[6]))));

      paladins = new RandomFactory<>(
          DataLoader.load("ProvidedData/Paladins.txt",
              t -> new Paladin(t[0],
                  Integer.parseInt(t[1]),
                  Integer.parseInt(t[2]),
                  Integer.parseInt(t[3]),
                  Integer.parseInt(t[4]),
                  Integer.parseInt(t[5]),
                  Integer.parseInt(t[6]))));
    } catch (Exception e) {
      System.out.println("There was an exception in loading heroes in GameData.java: " + e);
    }
  }

  private static void loadMonsters() {
    try {
      List<Monster> monsterTemplates = new ArrayList<>();

      monsterTemplates.addAll(
          DataLoader.load("ProvidedData/Dragons.txt",
              t -> new Dragon(
                  t[0],
                  Integer.parseInt(t[1]),
                  Integer.parseInt(t[2]),
                  Integer.parseInt(t[3]),
                  Integer.parseInt(t[4]))));

      // monsterTemplates.addAll(
      // DataLoader.load("ProvidedData/Spirits.txt",
      // t -> new Spirit(...)
      // )
      // );

      // monsterTemplates.addAll(
      // DataLoader.load("ProvidedData/Exoskeletons.txt",
      // t -> new Exoskeleton(...)
      // )
      // );

      monsters = new MonsterLevelFactory<>(monsterTemplates);
    } catch (Exception e) {
      System.out.println("There was an exception in loading monsters in GameData.java: " + e);
    }
  }

  private static void loadItems() {
    try {
      List<Item> itemTemplates = new ArrayList<>();

      itemTemplates.addAll(
          DataLoader.load("ProvidedData/Weaponry.txt",
              t -> new Weapon(t[0],
                  Integer.parseInt(t[1]),
                  Integer.parseInt(t[2]),
                  Integer.parseInt(t[3]),
                  Integer.parseInt(t[4]))));

      // itemTemplates.addAll(
      // DataLoader.load("ProvidedData/Armory.txt",
      // t -> new Armor(...)
      // )
      // );

      // itemTemplates.addAll(
      // DataLoader.load("ProvidedData/Potions.txt",
      // t -> new Consumable(...)
      // )
      // );

      items = new RandomFactory<>(itemTemplates);
    } catch (Exception e) {
      System.out.println("There was an exception in loading items in GameData.java: " + e);
    }
  }
}
