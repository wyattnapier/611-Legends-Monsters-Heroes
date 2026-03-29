package Structure;

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

  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < this.size(); i++) {
      sb.append("(" + i + ") " + this.get(i).inventoryToString() + "\n");
    }
    return sb.toString();
  }
}