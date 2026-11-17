package main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MonteCarloPiCalculator {

    private final Long baseSeed;

    public MonteCarloPiCalculator() {
        this(null);
    }

    public MonteCarloPiCalculator(Long baseSeed) {
        this.baseSeed = baseSeed;
    }

    public SimulationResult calculate(int threadCount, long totalIterations) {
        if (threadCount <= 0) {
            throw new IllegalArgumentException("Thread count must be greater than 0");
        }
        if (totalIterations <= 0) {
            throw new IllegalArgumentException("Total iterations must be greater than 0");
        }

        long startTime = System.nanoTime();
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        try {
            List<Future<Long>> futures = submitTasks(executor, threadCount, totalIterations);
            long totalHits = collectHits(futures);
            long endTime = System.nanoTime();

            double piEstimate = 4.0 * totalHits / totalIterations;
            double elapsedMillis = (endTime - startTime) / 1_000_000.0;

            return new SimulationResult(piEstimate, threadCount, totalIterations, elapsedMillis);
        } finally {
            executor.shutdownNow();
        }
    }

    private List<Future<Long>> submitTasks(ExecutorService executor, int threadCount, long totalIterations) {
        long baseChunk = totalIterations / threadCount;
        long remainder = totalIterations % threadCount;

        List<Future<Long>> futures = new ArrayList<>(threadCount);
        for (int i = 0; i < threadCount; i++) {
            long chunkIterations = baseChunk + (i < remainder ? 1L : 0L);
            Long workerSeed = (baseSeed != null) ? baseSeed + i : null;
            futures.add(executor.submit(new MonteCarloWorkerTask(chunkIterations, workerSeed)));
        }
        return futures;
    }

    private long collectHits(List<Future<Long>> futures) {
        long totalHits = 0L;
        for (Future<Long> future : futures) {
            try {
                totalHits += future.get();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException("Monte Carlo calculation was interrupted", e);
            } catch (ExecutionException e) {
                throw new IllegalStateException("Worker task failed during calculation", e.getCause());
            }
        }
        return totalHits;
    }
}