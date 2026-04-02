package Util;

public final class ColorString {

  private ColorString() {
  } // prevent instantiation

  public static final String RESET = "\u001B[0m";

  public static final String RED = "\u001B[31m";
  public static final String GREEN = "\u001B[32m";
  public static final String YELLOW = "\u001B[33m";
  public static final String BLUE = "\u001B[34m";
  public static final String PURPLE = "\u001B[35m";
  public static final String BACKGROUND_BLACK = "\u001B[40m";
  public static final String BACKGROUND_WHITE = "\u001B[47m";
}
