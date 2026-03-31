package Util;

import java.util.*;

import Fighters.Monsters.Monster;

/**
 * Factory for generating random monsters
 */
public class MonsterLevelFactory<T extends Monster> {
    private Map<Integer, List<T>> byLevel = new HashMap<>();
    private Random rand = new Random();

    public MonsterLevelFactory(List<T> monsters) {
        for (T m : monsters) {
            byLevel.computeIfAbsent(m.getLevel(), k -> new ArrayList<>()).add(m);
        }
    }

    public Monster random(int level) {
        List<T> options = byLevel.get(level);
        if (options == null || options.isEmpty())
            return null;
        T template = options.get(rand.nextInt(options.size()));
        return template.copy();
    }
}
