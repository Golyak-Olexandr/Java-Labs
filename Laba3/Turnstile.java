public class Turnstile {
    private final CardRegistry registry;
    private final double fareCost;
    private final TurnstileStats stats;

    public Turnstile(CardRegistry registry, double fareCost) {
        this.registry = registry;
        this.fareCost = fareCost;
        this.stats = new TurnstileStats();
    }

    public boolean validateAndPass(TravelCard card) {
        // 1. Перевірка зчитування даних з картки
        if (card == null) {
            System.out.println("⛔ [Турнікет] Помилка зчитування! Картка недійсне або відсутня.");
            stats.recordAttempt(null, false);
            return false;
        }

        // 2. Перевірка наявності в реєстрі
        var registeredOpt = registry.getCard(card.getId());
        if (registeredOpt.isEmpty()) {
            System.out.printf("⛔ [Турнікет] Картка ID: %s відсутня у базі даних! Прохід заборонено.%n", card.getId());
            stats.recordAttempt(card.getPassengerCategory(), false);
            return false;
        }

        TravelCard registeredCard = registeredOpt.get();

        // 3. Перевірка блокування
        if (registeredCard.isBlocked()) {
            System.out.printf("⛔ [Турнікет] ВІДМОВА! Картка ID: %s [%s] ЗАБЛОКОВАНА.%n",
                    registeredCard.getId(), registeredCard.getPassengerCategory().getDisplayName());
            stats.recordAttempt(registeredCard.getPassengerCategory(), false);
            return false;
        }

        // 4. Перевірка дійсності (термін / залишок поїздок / баланс)
        if (!registeredCard.canPass(fareCost)) {
            System.out.printf("⛔ [Турнікет] ВІДМОВА! Картка ID: %s [%s] — %s (Недостатньо поїздок/балансу або термін вичерпано)%n",
                    registeredCard.getId(), registeredCard.getPassengerCategory().getDisplayName(), registeredCard.getCardDetails());
            stats.recordAttempt(registeredCard.getPassengerCategory(), false);
            return false;
        }

        // 5. Проведення транзакції
        boolean success = registeredCard.processPass(fareCost);
        if (success) {
            System.out.printf("🟢 [Турнікет] ПРОХІД ДОЗВОЛЕНО! Картка ID: %s [%s] (%s)%n",
                    registeredCard.getId(), registeredCard.getPassengerCategory().getDisplayName(), registeredCard.getCardDetails());
            stats.recordAttempt(registeredCard.getPassengerCategory(), true);
            return true;
        } else {
            System.out.printf("⛔ [Турнікет] Помилка обробки транзакції для ID: %s%n", registeredCard.getId());
            stats.recordAttempt(registeredCard.getPassengerCategory(), false);
            return false;
        }
    }

    public void printSummaryStats() {
        stats.printSummary();
    }

    public void printDetailedStats() {
        stats.printDetailedStats();
    }
}