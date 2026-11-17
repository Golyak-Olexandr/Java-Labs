package test;

import main.Bank;
import main.SimulationSummary;
import main.TransferSimulationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TransferSimulationServiceTest {

    @Test
    @DisplayName("Зберігає загальну суму коштів у банку після 3000 багатопотокових переказів")
    void shouldPreserveTotalMoneyAcrossThousandsOfConcurrentTransfers() {
        Bank bank = new Bank();
        TransferSimulationService service = new TransferSimulationService(bank);

        SimulationSummary summary = service.runSimulation(150, 5_000, 3_000, 1_000);

        assertEquals(summary.initialTotalBalance(), summary.finalTotalBalance());
        assertFalse(summary.hasNegativeBalance());
        assertTrue(summary.isInvariantPreserved());
    }
}