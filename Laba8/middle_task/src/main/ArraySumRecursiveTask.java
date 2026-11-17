package main;

import java.util.concurrent.RecursiveTask;

public class ArraySumRecursiveTask extends RecursiveTask<Long> {

    public static final int THRESHOLD = 20;

    private final int[] array;
    private final int start;
    private final int end;

    public ArraySumRecursiveTask(int[] array) {
        this(array, 0, array != null ? array.length : 0);
    }

    public ArraySumRecursiveTask(int[] array, int start, int end) {
        if (array == null) {
            throw new IllegalArgumentException("Array must not be null");
        }
        if (start < 0 || end > array.length || start > end) {
            throw new IllegalArgumentException("Invalid subarray bounds: [" + start + ", " + end + ")");
        }
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        int length = end - start;
        if (length < THRESHOLD) {
            return computeSequentially();
        }

        int mid = start + length / 2;
        ArraySumRecursiveTask leftTask = new ArraySumRecursiveTask(array, start, mid);
        ArraySumRecursiveTask rightTask = new ArraySumRecursiveTask(array, mid, end);

        leftTask.fork();
        long rightResult = rightTask.compute();
        long leftResult = leftTask.join();

        return leftResult + rightResult;
    }

    private long computeSequentially() {
        long sum = 0L;
        for (int i = start; i < end; i++) {
            sum += array[i];
        }
        return sum;
    }
}