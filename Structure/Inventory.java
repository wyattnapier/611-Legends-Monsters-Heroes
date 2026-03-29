package Structure;

import Items.Item;
import java.util.*;

public class Inventory {
  private List<Item> items;

  public Inventory() {
    items = new ArrayList<>();
  }

  /**
   * add item to inventory
   * 
   * @param item
   */
  public void addItem(Item item) {
    items.add(item);
  }

  /**
   * remove specified item from inventory
   * 
   * @param item
   */
  public void removeItem(Item item) {
    items.remove(item);
  }

  /**
   * get item by name or get null value
   * 
   * @param queriedItemName string of item name (must match 1:1)
   * @return item or null
   */
  public Item getItemByName(String queriedItemName) {
    for (Item i : items) {
      if (i.getName().toLowerCase().equals(queriedItemName.toLowerCase())) {
        return i;
      }
    }
    return null;
  }

  /**
   * get items in inventory
   * 
   * @return
   */
  public List<Item> getItems() {
    return items;
  }

  /**
   * checks if inventory contains item
   * @param questionedItem
   * @return
   */
  public boolean contains(Item questionedItem) {
    return items.contains(questionedItem);
  }
}