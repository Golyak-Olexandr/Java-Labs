package test;

import main.Account;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AccountTest {

    @Test
    @DisplayName("Присвоює унікальні зростаючі ID та коректно змінює баланс")
    void shouldAssignUniqueIdsAndModifyBalance() {
        Account first = new Account(1000);
        Account second = new Account(500);

        assertTrue(first.getId() < second.getId());

        first.withdraw(300);
        second.deposit(300);

        assertEquals(700, first.getBalance());
        assertEquals(800, second.getBalance());
    }

    @Test
    @DisplayName("Не дозволяє створити рахунок або зняти кошти у мінус")
    void shouldPreventNegativeBalance() {
        assertThrows(IllegalArgumentException.class, () -> new Account(-10));

        Account account = new Account(200);
        assertThrows(IllegalStateException.class, () -> account.withdraw(201));
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(0));
        assertThrows(IllegalArgumentException.class, () -> account.deposit(-5));
    }
}