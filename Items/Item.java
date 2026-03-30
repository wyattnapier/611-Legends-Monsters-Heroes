package Items;

public abstract class Item {
  protected String name;
  protected int cost;
  protected int requiredLevel;

  public Item(String nameInput, int costInput, int requiredLevelInput) {
    name = nameInput;
    cost = costInput;
    requiredLevel = requiredLevelInput;
  }

  public String getName() {
    return name;
  }

  public int getCost() {
    return cost;
  }

  public void setCost(int newCost) {
    cost = newCost;
  }

  public int getRequiredLevel() {
    return requiredLevel;
  }

  public String getNameAndLevel() {
    return name + " " + requiredLevel;
  }

  public String toString() {
    return name + " [LVL" + requiredLevel
        + "] [COST: " + cost + "]";
  }
}
