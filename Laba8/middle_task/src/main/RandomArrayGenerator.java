package main;

import java.util.SplittableRandom;
import java.util.concurrent.ThreadLocalRandom;

public class RandomArrayGenerator {

    private final Long seed;

    public RandomArrayGenerator() {
        this(null);
    }

    public RandomArrayGenerator(Long seed) {
        this.seed = seed;
    }

    public int[] generate(int size, int minInclusive, int maxInclusive) {
        if (size < 0) {
            throw new IllegalArgumentException("Array size must be non-negative");
        }
        if (minInclusive > maxInclusive) {
            throw new IllegalArgumentException("minInclusive cannot be greater than maxInclusive");
        }

        SplittableRandom random = (seed != null)
                ? new SplittableRandom(seed)
                : new SplittableRandom(ThreadLocalRandom.current().nextLong());

        int[] array = new int[size];
        int bound = maxInclusive + 1;
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(minInclusive, bound);
        }
        return array;
    }
}