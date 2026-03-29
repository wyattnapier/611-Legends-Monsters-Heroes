package Items;

import java.util.ArrayList;
import java.util.List;

import Fighters.Heros.Hero;

public class Armor extends Item implements Equippable {
  private int baseDefense;

  public Armor(String name, int cost, int level, int defense) {
    super(name, cost, level);
    this.baseDefense = defense;
  }

  @Override
  public void onEquip(Hero h) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'onEquip'");
  }

  @Override
  public void onUnequip(Hero h) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'onUnequip'");
  }

  @Override
  public List<EquipmentSlot> getEquipmentSlotOptions() {
    List<EquipmentSlot> slotOptions = new ArrayList<EquipmentSlot>();
    slotOptions.add(EquipmentSlot.ARMOR);
    return slotOptions;
  }
}
