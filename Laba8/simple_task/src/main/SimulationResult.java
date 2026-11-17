package main;

import java.util.Locale;

public record SimulationResult(
        double piEstimate,
        int threads,
        long iterations,
        double elapsedMillis
) {
    public String formatForConsole() {
        return String.format(
                Locale.US,
                "PI is %.5f%nTHREADS %d%nITERATIONS %,d%nTIME %.2fms",
                piEstimate,
                threads,
                iterations,
                elapsedMillis
        );
    }
}