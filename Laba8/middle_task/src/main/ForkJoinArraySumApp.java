package main;

import java.util.Locale;
import java.util.concurrent.ForkJoinPool;

public class ForkJoinArraySumApp {

    public static final int ARRAY_SIZE = 1_000_000;
    public static final int MIN_VALUE = 0;
    public static final int MAX_VALUE = 100;

    public static void main(String[] args) {
        RandomArrayGenerator generator = new RandomArrayGenerator();
        int[] numbers = generator.generate(ARRAY_SIZE, MIN_VALUE, MAX_VALUE);

        ForkJoinArraySumCalculator calculator = new ForkJoinArraySumCalculator();

        long fjStart = System.nanoTime();
        long forkJoinSum = calculator.calculateSum(numbers);
        long fjEnd = System.nanoTime();

        long seqStart = System.nanoTime();
        long sequentialSum = calculator.calculateSequentialSum(numbers);
        long seqEnd = System.nanoTime();

        double fjMillis = (fjEnd - fjStart) / 1_000_000.0;
        double seqMillis = (seqEnd - seqStart) / 1_000_000.0;

        System.out.printf(Locale.US, "ARRAY SIZE %,d%n", ARRAY_SIZE);
        System.out.printf("THRESHOLD < %d%n", ArraySumRecursiveTask.THRESHOLD);
        System.out.printf("PARALLELISM %d%n", ForkJoinPool.commonPool().getParallelism());
        System.out.printf(Locale.US, "FORKJOIN SUM %,d (TIME %.2fms)%n", forkJoinSum, fjMillis);
        System.out.printf(Locale.US, "SEQUENTIAL SUM %,d (TIME %.2fms)%n", sequentialSum, seqMillis);
        System.out.printf("RESULTS MATCH %b%n", forkJoinSum == sequentialSum);
    }
}