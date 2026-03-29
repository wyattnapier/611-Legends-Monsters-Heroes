package Locations;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

import Fighters.Heros.Hero;
import Structure.IO;
import Structure.Inventory;
import Util.GameData;
import Items.Equippable;
import Items.Item;

public class Marketplace {
  private Inventory inventory;
  private Random generator;
  private IO io;

  public Marketplace(IO sharedIO) {
    generator = new Random();
    inventory = new Inventory();
    stockInventory();
    io = sharedIO;
  }

  /**
   * adds items to the inventory of the marketplace
   */
  private void stockInventory() {
    int inventorySize = generator.nextInt(5, 10);
    for (int i = 0; i < inventorySize; i++) {
      inventory.add(GameData.items.random());
    }
  }

  /**
   * handles all high level logic and gameplay for interaction with a marketplace
   * 
   * @param h hero entering the market (only one at a time)
   * @return false to quit
   */
  public boolean enter(Hero h) {
    System.out.println("----------------- MARKET -----------------\n" + h.getName() + ", welcome to the market!");
    int doMoreTurns = 1; // -1 to quit, 0 to go back, 1 to stay at market
    while (doMoreTurns == 1) {
      doMoreTurns = doTurnAtMarket(h);
    }
    System.out.println("Exiting the market...");
    return doMoreTurns == 0 ? true : false;
  }

  public int doTurnAtMarket(Hero h) {
    String action = io.getMarketAction(); // TODO: implement this
    switch (action) {
      // need options to buy item or sell item
      case "b": // hero buys item
        if (inventory.size() == 0) {
          System.out.println("This market has no more items to sell.\n");
        } else {
          transactWithHeroLoop(h, inventory, index -> sellItemToHero(h, index));
        }
        return 1;
      case "s": // hero sells item
        if (h.getInventory().size() == 0) {
          System.out.println("You have no items to sell, try again later.\n");
        } else {
          transactWithHeroLoop(h, h.getInventory(), index -> buyItemFromHero(h, index));
        }
        return 1;
      case "r": // hero wants to repair an item
        if (h.getInventory().size() == 0) {
          System.out.println("You have no items to try to repair, try again later.\n");
        } else {
          transactWithHeroLoop(h, h.getInventory(), index -> repairItemForHero(h, index));
        }
        return 1;
      case "e": // exit market
        return 0;
      case "q": // quit
        return -1;
      default:
        return 1;
    }

  }

  /**
   * loop to buy/sell item from hero until successful transaction or hero quits
   * 
   * @param h           hero h
   * @param transaction lambda function to buy/sell item from hero
   */
  private void transactWithHeroLoop(Hero h, List<? extends Object> goodsToExchange,
      Function<Integer, Boolean> transactWithHero) {
    while (true) {
      int itemIndex = io.getValidListIndex(goodsToExchange, true, "item");
      if (itemIndex == -1) {
        break;
      }
      if (transactWithHero.apply(itemIndex)) {
        break;
      }
    }
  }

  /**
   * handles all logic to sell item to hero
   * 
   * @param h     hero who is buying item
   * @param index index of item (likely ascertained from io.java)
   * @return true if sold successfully and false otherwise
   */
  private boolean sellItemToHero(Hero h, int index) {
    if (index > 0 && index < inventory.size()) {
      Item itemToSell = inventory.get(index);
      if (h.getGoldAmount() >= itemToSell.getCost() && h.getLevel() >= itemToSell.getRequiredLevel()) {
        h.setGoldAmount(h.getGoldAmount() - itemToSell.getCost()); // reduce hero's gold
        int sellingPrice = itemToSell.getCost();
        itemToSell.setCost(sellingPrice / 2); // cut the item's value in half
        // transfer item from marketplace to hero
        inventory.remove(itemToSell);
        h.getInventory().add(itemToSell);
        System.out.println(itemToSell.getName() + " bought by " + h.getName() + " for " + sellingPrice + " gold!\n");
        return true;
      }
    }
    System.out
        .println("Transaction failed." + h.getName() + " didn't meet all of the requirements to buy this item.\n");
    return false; // invalid index or hero doesn't have enough funds
  }

  /**
   * handles logic to buy item from hero including checking if its equipped
   * 
   * @param h     hero
   * @param index index of item in hero's inventory
   * @return true if successful
   */
  private boolean buyItemFromHero(Hero h, int index) {
    Item itemToBuy = h.getInventory().get(index);
    if (itemToBuy instanceof Equippable e && e.getIsEquipped()) {
      System.out.println("Transaction failed. You cannot sell an item that you currently have equipped.");
      return false; // cannot sell a currently equipped item
    }
    h.setGoldAmount(h.getGoldAmount() + itemToBuy.getCost()); // increase hero's gold
    // transfer item from hero to marketplace
    h.getInventory().remove(itemToBuy);
    inventory.add(itemToBuy);
    System.out.println(
        itemToBuy.getName() + " was sold to the market by " + h.getName() + " for " + itemToBuy.getCost() + " gold!\n");
    return true;
  }

  /**
   * handles logic to repair item from hero
   * 
   * @param h     hero
   * @param index index of item in hero's inventory
   * @return true if successful
   */
  private boolean repairItemForHero(Hero h, int index) {
    Item itemToRepair = h.getInventory().get(index);
    if ((itemToRepair instanceof Equippable e)) {
      if (h.getGoldAmount() < e.getCost()) {
        System.out
            .println("Repair failed because it costs half of the item's value and the hero doesn't have enough gold.");
        return false;
      }
      if (e.getDurability() == Equippable.MAX_DURABILITY) {
        System.out.println("Repair failed because item already has max durability. Get out there and start using it!");
        return false;
      }
      h.setGoldAmount(h.getGoldAmount() - (e.getCost() / 2)); // decrease hero's gold
      e.setDurability(Equippable.MAX_DURABILITY);
      System.out.println(e.getName() + " was repaired for " + e.getCost() / 2 + " gold!\n");
      return true;
    } else {
      System.out.println("Repair failed. Market can only repair armor and weapons.");
      return false; // cannot repair consumables
    }
  }
}
