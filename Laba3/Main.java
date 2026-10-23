public class Main {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("🚆 МОДЕЛЬ ТУРНІКЕТУ ШВИДКІСНОГО ТРАМВАЮ (Task 3.4)");
        System.out.println("==================================================\n");

        CardRegistry registry = new CardRegistry();
        Turnstile turnstile = new Turnstile(registry, 8.00);

        System.out.println("--- 1. ВИПУСК КАРТОК СИСТЕМОЮ ---");
        TravelCard pupilCard = registry.issueRideCountCard(PassengerCategory.PUPIL, TariffType.RIDE_COUNT_5);
        TravelCard studentCard = registry.issueTermCard(PassengerCategory.STUDENT, TariffType.TERM_10_DAYS);
        TravelCard accumCard = registry.issueAccumulativeCard(15.00);

        System.out.println("\n--- 2. ТЕСТУВАННЯ ПРОХОДІВ ЧЕРЕЗ ТУРНІКЕТ ---");
        
        for (int i = 1; i <= 6; i++) {
            System.out.print("[" + i + "] ");
            turnstile.validateAndPass(pupilCard);
        }

        System.out.print("\nСтудентська (термінова): ");
        turnstile.validateAndPass(studentCard);

        System.out.println("\nНакопичувальна (початковий баланс 15 грн):");
        turnstile.validateAndPass(accumCard);
        turnstile.validateAndPass(accumCard);

        System.out.println("\nПоповнення накопичувальної картки:");
        registry.topUpCard(accumCard.getId(), 20.00);
        turnstile.validateAndPass(accumCard);

        System.out.println("\n--- 3. СЦЕНАРІЇ ВІДМОВ ТА БЛОКУВАННЯ ---");
        registry.blockCard(studentCard.getId());
        turnstile.validateAndPass(studentCard);

        System.out.print("Незчитана картка (null): ");
        turnstile.validateAndPass(null);

        turnstile.printSummaryStats();
        turnstile.printDetailedStats();
    }
}