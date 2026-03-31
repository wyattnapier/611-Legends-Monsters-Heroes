package Items;

public class Armor extends Equippable {
  private int baseDefense;

  public Armor(String name, int cost, int level, int defense) {
    super(name, cost, level);
    this.baseDefense = defense;
  }

  public Armor copy() {
    return new Armor(name, cost, requiredLevel, baseDefense);
  }

  public int getLevel() {
    return super.getRequiredLevel();
  }

  public String toString() {
    return super.toString() + " [DEFENSE: " + baseDefense + "]";
  }
}
