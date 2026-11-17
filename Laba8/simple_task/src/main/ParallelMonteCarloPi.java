package main;

public class ParallelMonteCarloPi {

    public static final long DEFAULT_ITERATIONS = 1_000_000_000L;

    public static void main(String[] args) {
        int threadCount = parseThreadCount(args);

        MonteCarloPiCalculator calculator = new MonteCarloPiCalculator();
        SimulationResult result = calculator.calculate(threadCount, DEFAULT_ITERATIONS);

        System.out.println(result.formatForConsole());
    }

    public static int parseThreadCount(String[] args) {
        if (args == null || args.length != 1) {
            throw new IllegalArgumentException("Expected exactly 1 argument: <number_of_threads>");
        }
        try {
            int threads = Integer.parseInt(args[0]);
            if (threads <= 0) {
                throw new IllegalArgumentException("Number of threads must be positive: " + threads);
            }
            return threads;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid thread count format: " + args[0], e);
        }
    }
}