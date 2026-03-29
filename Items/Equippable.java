package Items;

import java.util.*;

import Fighters.Heros.Hero;

public interface Equippable {
  public void onEquip(Hero h);

  public void onUnequip(Hero h);

  public List<EquipmentSlot> getEquipmentSlotOptions();
}
