package main;

import java.util.SplittableRandom;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;

public class MonteCarloWorkerTask implements Callable<Long> {

    private final long iterations;
    private final Long seed;

    public MonteCarloWorkerTask(long iterations) {
        this(iterations, null);
    }

    public MonteCarloWorkerTask(long iterations, Long seed) {
        if (iterations < 0) {
            throw new IllegalArgumentException("Iterations count must be non-negative");
        }
        this.iterations = iterations;
        this.seed = seed;
    }

    @Override
    public Long call() {
        SplittableRandom random = (seed != null)
                ? new SplittableRandom(seed)
                : new SplittableRandom(ThreadLocalRandom.current().nextLong());

        long hits = 0L;
        for (long i = 0; i < iterations; i++) {
            double x = random.nextDouble();
            double y = random.nextDouble();
            if (x * x + y * y <= 1.0) {
                hits++;
            }
        }
        return hits;
    }
}