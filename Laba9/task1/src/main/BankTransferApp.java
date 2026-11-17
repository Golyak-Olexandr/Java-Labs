package main;

public class BankTransferApp {

    public static void main(String[] args) {
        Bank bank = new Bank();
        TransferSimulationService simulationService = new TransferSimulationService(bank);

        SimulationSummary summary = simulationService.runSimulation(
                200,     // кількість рахунків
                10_000,  // максимальний початковий баланс
                3_000,   // кількість одночасних потоків переказів
                2_500    // максимальна сума одного переказу
        );

        System.out.println(summary.formatForConsole());
    }
}