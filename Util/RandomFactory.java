package Util;

import java.util.*;

/** one generic factory for everything */
public class RandomFactory<T> {

  private final List<T> templates;
  private final Random rand = new Random();

  public RandomFactory(List<T> templates) {
    this.templates = templates;
  }

  public T random() {
    return templates.get(rand.nextInt(templates.size()));
  }
}
