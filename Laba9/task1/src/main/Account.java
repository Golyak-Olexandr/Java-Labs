package main;

import java.util.concurrent.atomic.AtomicInteger;

public class Account {

    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1);

    private final int id;
    private int balance;

    public Account(int initialBalance) {
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative: " + initialBalance);
        }
        this.id = ID_GENERATOR.getAndIncrement();
        this.balance = initialBalance;
    }

    public int getId() {
        return id;
    }

    public int getBalance() {
        return balance;
    }

    public void deposit(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive: " + amount);
        }
        balance += amount;
    }

    public void withdraw(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive: " + amount);
        }
        if (balance < amount) {
            throw new IllegalStateException("Insufficient funds on account #" + id);
        }
        balance -= amount;
    }
}