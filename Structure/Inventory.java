package Structure;

import Items.Equippable;
import Items.Item;
import java.util.ArrayList;

public class Inventory extends ArrayList<Item> {

  public Item getItemByName(String name) {
    for (Item item : this) {
      if (item.getName().equalsIgnoreCase(name)) {
        return item;
      }
    }
    return null;
  }

  /**
   * filter inventory by input item class
   * 
   * @param type
   * @return filtered inventory (with same objects but different order)
   */
  public Inventory filterByItemClass(Class<? extends Item> type) {
    Inventory filtered = new Inventory();
    for (Item item : this) {
      if (type.isInstance(item)) {
        filtered.add(item);
      }
    }
    return filtered;
  }

  public Inventory filterByEquipped() {
    Inventory filtered = new Inventory();
    for (Item item : this) {
      if (item instanceof Equippable eq && eq.getIsEquipped()) {
        filtered.add(eq);
      }
    }
    return filtered;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < this.size(); i++) {
      sb.append("(" + i + ") " + this.get(i).toString() + "\n");
    }
    return sb.toString();
  }
}