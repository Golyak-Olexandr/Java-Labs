package main;

import java.util.Locale;

public record SimulationSummary(
        int accountsCount,
        int threadsCount,
        long initialTotalBalance,
        long finalTotalBalance,
        boolean hasNegativeBalance,
        double elapsedMillis
) {
    public boolean isInvariantPreserved() {
        return initialTotalBalance == finalTotalBalance && !hasNegativeBalance;
    }

    public String formatForConsole() {
        return String.format(
                Locale.US,
                "ACCOUNTS: %d%n" +
                "THREADS: %,d%n" +
                "TOTAL BEFORE: %,d%n" +
                "TOTAL AFTER:  %,d%n" +
                "NEGATIVE BALANCES: %b%n" +
                "CONSISTENT: %b%n" +
                "TIME: %.2fms",
                accountsCount,
                threadsCount,
                initialTotalBalance,
                finalTotalBalance,
                hasNegativeBalance,
                isInvariantPreserved(),
                elapsedMillis
        );
    }
}