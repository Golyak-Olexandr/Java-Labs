package main;

public class MonteCarloDemoRunner {

    public static void main(String[] args) {
        runBenchmark(1, 2, 4, 8, 16);
    }

    private static void runBenchmark(int... threadCounts) {
        System.out.println("\n--- Замір продуктивності ---");
        for (int threads : threadCounts) {
            ParallelMonteCarloPi.main(new String[]{String.valueOf(threads)});
            System.out.println("----------------------------");
        }
    }
}