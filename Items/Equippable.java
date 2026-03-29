package Items;

public abstract class Equippable extends Item {
  protected boolean isEquipped;

  public Equippable(String nameInput, int costInput, int requiredLevelInput) {
    super(nameInput, costInput, requiredLevelInput);
  }

  public boolean getIsEquipped() {
    return isEquipped;
  };

  public void setIsEquipped(boolean isEquippedNow) {
    isEquipped = isEquippedNow;
  }
}
