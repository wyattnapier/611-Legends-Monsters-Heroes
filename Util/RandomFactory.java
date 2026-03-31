package Util;

import java.util.*;

import Structure.Copyable;

/** one generic factory for everything */
public class RandomFactory<T extends Copyable<T>> {

  private final List<T> templates;
  private final Random rand = new Random();

  public RandomFactory(List<T> templates) {
    this.templates = templates;
  }

  public T random() {
    T template = templates.get(rand.nextInt(templates.size()));
    return template.copy();
  }
}
