package Locations;

import java.util.Random;

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
        return 1;
      case "s": // hero sells item
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
   * handles all logic to sell item to hero
   * 
   * @param h     hero who is buying item
   * @param index index of item (likely ascertained from io.java)
   * @return true if sold successfully and false otherwise
   */
  public boolean sellItemToHero(Hero h, int index) {
    if (index > 0 && index < inventory.size()) {
      Item itemToSell = inventory.get(index);
      if (h.getGoldAmount() >= itemToSell.getCost() && h.getLevel() > itemToSell.getRequiredLevel()) {
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

  // todo: handle if equipped or not
  public boolean buyItemFromHero(Hero h, Item itemToBuy) {
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
}
