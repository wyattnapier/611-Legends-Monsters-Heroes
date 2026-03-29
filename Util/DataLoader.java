package Util;

import java.io.*;
import java.util.*;
import java.util.function.Function;

/**
 * loads data from txt files
 */
public class DataLoader {
  public static <T> List<T> load(String path, Function<String[], T> builder) throws IOException {
    List<T> result = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
      reader.readLine(); // skip header

      String line;
      while ((line = reader.readLine()) != null) {
        if (line.isBlank())
          continue;

        String[] tokens = line.trim().split("\\s+");
        result.add(builder.apply(tokens));
      }
    }

    return result;
  }
}