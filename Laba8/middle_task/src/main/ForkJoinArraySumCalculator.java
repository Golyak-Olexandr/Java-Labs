package main;

import java.util.concurrent.ForkJoinPool;

public class ForkJoinArraySumCalculator {

    private final ForkJoinPool forkJoinPool;

    public ForkJoinArraySumCalculator() {
        this(ForkJoinPool.commonPool());
    }

    public ForkJoinArraySumCalculator(ForkJoinPool forkJoinPool) {
        if (forkJoinPool == null) {
            throw new IllegalArgumentException("ForkJoinPool must not be null");
        }
        this.forkJoinPool = forkJoinPool;
    }

    public long calculateSum(int[] array) {
        if (array == null) {
            throw new IllegalArgumentException("Array must not be null");
        }
        if (array.length == 0) {
            return 0L;
        }
        return forkJoinPool.invoke(new ArraySumRecursiveTask(array));
    }

    public long calculateSequentialSum(int[] array) {
        if (array == null) {
            throw new IllegalArgumentException("Array must not be null");
        }
        long sum = 0L;
        for (int value : array) {
            sum += value;
        }
        return sum;
    }
}