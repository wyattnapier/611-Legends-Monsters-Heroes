package Items;

public abstract class Equippable extends Item {
  public static final int MAX_DURABILITY = 10;
  protected boolean isEquipped;
  protected int durability; // # of uses until broken

  public Equippable(String nameInput, int costInput, int requiredLevelInput) {
    super(nameInput, costInput, requiredLevelInput);
    durability = 10;
  }

  public boolean getIsEquipped() {
    return isEquipped;
  };

  public void setIsEquipped(boolean isEquippedNow) {
    isEquipped = isEquippedNow;
  }

  /**
   * @return durability value of an equippable
   */
  public int getDurability() {
    return durability;
  }

  /**
   * either decrement durability or make it MAX_DURABILITY
   * 
   * @param newDurability
   */
  public void setDurability(int newDurability) {
    durability = newDurability;
  }

  /**
   * 
   * @return true if durability is positive and non-zero
   */
  public boolean isDurable() {
    return durability > 0;
  }

  public String toString() {
    return super.toString() + " [DUR: " + durability + "]";
  }
}
