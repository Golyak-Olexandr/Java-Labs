package test;

import main.Account;
import main.Bank;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BankTest {

    @Test
    @DisplayName("Виконує базовий переказ та не змінює баланси при нестачі коштів")
    void shouldTransferFundsOrSkipWhenInsufficient() {
        Bank bank = new Bank();
        Account from = new Account(500);
        Account to = new Account(200);

        bank.transfer(from, to, 300);
        assertEquals(200, from.getBalance());
        assertEquals(500, to.getBalance());

        // Спроба переказати більше, ніж є на рахунку
        bank.transfer(from, to, 400);
        assertEquals(200, from.getBalance());
        assertEquals(500, to.getBalance());
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    @DisplayName("Не потрапляє в deadlock при інтенсивних зустрічних переказах між двома рахунками")
    void shouldNotDeadlockOnOppositeTransfers() throws InterruptedException {
        Bank bank = new Bank();
        Account accountA = new Account(10_000);
        Account accountB = new Account(10_000);

        int threads = 200;
        CountDownLatch latch = new CountDownLatch(threads);

        for (int i = 0; i < threads; i++) {
            final boolean forward = (i % 2 == 0);
            new Thread(() -> {
                try {
                    for (int j = 0; j < 500; j++) {
                        if (forward) {
                            bank.transfer(accountA, accountB, 10);
                        } else {
                            bank.transfer(accountB, accountA, 10);
                        }
                    }
                } finally {
                    latch.countDown();
                }
            }).start();
        }

        latch.await();
        assertEquals(20_000L, bank.calculateTotalBalance(List.of(accountA, accountB)));
    }

    @Test
    @DisplayName("Валідує вхідні аргументи методу transfer")
    void shouldValidateTransferArguments() {
        Bank bank = new Bank();
        Account acc = new Account(100);

        assertThrows(IllegalArgumentException.class, () -> bank.transfer(null, acc, 10));
        assertThrows(IllegalArgumentException.class, () -> bank.transfer(acc, null, 10));
        assertThrows(IllegalArgumentException.class, () -> bank.transfer(acc, acc, 0));
    }
}