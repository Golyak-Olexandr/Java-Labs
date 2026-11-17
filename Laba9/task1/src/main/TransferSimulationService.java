package main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

public class TransferSimulationService {

    private final Bank bank;

    public TransferSimulationService(Bank bank) {
        if (bank == null) {
            throw new IllegalArgumentException("Bank must not be null");
        }
        this.bank = bank;
    }

    public SimulationSummary runSimulation(int accountsCount, int maxInitialBalance, int threadsCount, int maxTransferAmount) {
        if (accountsCount < 2) {
            throw new IllegalArgumentException("At least 2 accounts are required");
        }
        if (threadsCount <= 0) {
            throw new IllegalArgumentException("Threads count must be positive");
        }

        List<Account> accounts = createAccountsWithRandomBalances(accountsCount, maxInitialBalance);
        long initialTotal = bank.calculateTotalBalance(accounts);

        CountDownLatch startGate = new CountDownLatch(1);
        CountDownLatch finishGate = new CountDownLatch(threadsCount);

        for (int i = 0; i < threadsCount; i++) {
            Thread worker = new Thread(() -> {
                try {
                    startGate.await();
                    ThreadLocalRandom random = ThreadLocalRandom.current();

                    int fromIdx = random.nextInt(accountsCount);
                    int toIdx = random.nextInt(accountsCount);
                    int amount = random.nextInt(1, maxTransferAmount + 1);

                    bank.transfer(accounts.get(fromIdx), accounts.get(toIdx), amount);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    finishGate.countDown();
                }
            }, "transfer-worker-" + i);

            worker.start();
        }

        long startTime = System.nanoTime();
        startGate.countDown();

        try {
            finishGate.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Simulation interrupted while waiting for worker threads", e);
        }

        long endTime = System.nanoTime();
        long finalTotal = bank.calculateTotalBalance(accounts);
        boolean hasNegative = accounts.stream().anyMatch(acc -> acc.getBalance() < 0);
        double elapsedMillis = (endTime - startTime) / 1_000_000.0;

        return new SimulationSummary(
                accountsCount,
                threadsCount,
                initialTotal,
                finalTotal,
                hasNegative,
                elapsedMillis
        );
    }

    private List<Account> createAccountsWithRandomBalances(int count, int maxBalance) {
        List<Account> accounts = new ArrayList<>(count);
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < count; i++) {
            accounts.add(new Account(random.nextInt(100, maxBalance + 1)));
        }
        return accounts;
    }
}