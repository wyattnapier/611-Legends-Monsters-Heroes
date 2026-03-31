package Items;

import Fighters.Heros.Hero;

public interface Consumable {

  /**
   * removes this item from the provided heroe's inventory
   * @param hero who will have item removed
   * @return true if consumed successfully and false otherwise
   */
  public boolean consumeItem(Hero h);
}
