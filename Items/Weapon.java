package Items;

public class Weapon implements Items {
  private String name, owner;
  private int cost, level, damage, requiredHands;

  public Weapon(String owner, String name, int cost, int level, int damage, int requiredHands) {
    this.name = name;
    this.cost = cost;
    this.level = level;
    this.damage = damage;
    this.requiredHands = requiredHands;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public int getCost() {
    return cost;
  }

  @Override
  public int getLevel() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getLevel'");
  }

  @Override
  public String getOwner() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getOwner'");
  }

}
