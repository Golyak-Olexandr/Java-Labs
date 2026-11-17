package main;

import java.util.Collection;

public class Bank {

    public void transfer(Account from, Account to, int amount) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("Source and destination accounts must not be null");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive: " + amount);
        }
        if (from == to) {
            return;
        }

        Account firstLock = from.getId() < to.getId() ? from : to;
        Account secondLock = from.getId() < to.getId() ? to : from;

        synchronized (firstLock) {
            synchronized (secondLock) {
                if (from.getBalance() >= amount) {
                    from.withdraw(amount);
                    to.deposit(amount);
                }
            }
        }
    }

    public long calculateTotalBalance(Collection<Account> accounts) {
        if (accounts == null) {
            throw new IllegalArgumentException("Accounts collection must not be null");
        }
        long total = 0L;
        for (Account account : accounts) {
            synchronized (account) {
                total += account.getBalance();
            }
        }
        return total;
    }
}