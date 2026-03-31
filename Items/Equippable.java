package Items;

public abstract class Equippable extends Item {
  public static final int MAX_DURABILITY = 10;
  protected boolean isEquipped;
  protected int durability; // # of uses until broken
  protected boolean isBroken; // tracks if item is broken

  public Equippable(String nameInput, int costInput, int requiredLevelInput) {
    super(nameInput, costInput, requiredLevelInput);
    durability = 10;
    isBroken = false;
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

  /**
   * @return true if the item is broken
   */
  public boolean isBroken() {
    return isBroken;
  }

  /**
   * Reduce durability by 1 and check if item breaks.
   * When durability reaches 0, the item breaks.
   * 
   * @return true if the item just broke, false otherwise
   */
  public boolean reduceDurability() {
    if (isBroken) {
      return false;
    }
    
    durability--;
    if (durability <= 0) {
      durability = 0;
      isBroken = true;
      return true;
    }
    return false;
  }

  public String toString() {
    String brokenStatus = isBroken ? " [BROKEN]" : "";
    return super.toString() + " [DUR: " + durability + "]" + brokenStatus;
  }
}
