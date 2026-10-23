import java.util.EnumMap;
import java.util.Map;

public class TurnstileStats {
    private int totalPassed = 0;
    private int totalDenied = 0;

    private final Map<PassengerCategory, Integer> passedByCategory = new EnumMap<>(PassengerCategory.class);
    private final Map<PassengerCategory, Integer> deniedByCategory = new EnumMap<>(PassengerCategory.class);

    public TurnstileStats() {
        for (PassengerCategory cat : PassengerCategory.values()) {
            passedByCategory.put(cat, 0);
            deniedByCategory.put(cat, 0);
        }
    }

    public void recordAttempt(PassengerCategory category, boolean isAllowed) {
        if (isAllowed) {
            totalPassed++;
            if (category != null) {
                passedByCategory.put(category, passedByCategory.get(category) + 1);
            }
        } else {
            totalDenied++;
            if (category != null) {
                deniedByCategory.put(category, deniedByCategory.get(category) + 1);
            }
        }
    }

    public void printSummary() {
        System.out.println("\n==========================================");
        System.out.println("📊 СУМАРНА СТАТИСТИКА ТУРНІКЕТА");
        System.out.println("------------------------------------------");
        System.out.println("✅ Дозволено проходів: " + totalPassed);
        System.out.println("❌ Відмовлено у проході: " + totalDenied);
        System.out.println("📈 Всього спроб:        " + (totalPassed + totalDenied));
        System.out.println("==========================================\n");
    }

    public void printDetailedStats() {
        System.out.println("\n==========================================================");
        System.out.println("📊 СТАТИСТИКА ПРОХОДІВ ЗА КАТЕГОРІЯМИ ПРОЇЗНИХ КВИТКІВ");
        System.out.println("----------------------------------------------------------");
        for (PassengerCategory cat : PassengerCategory.values()) {
            int passed = passedByCategory.get(cat);
            int denied = deniedByCategory.get(cat);
            System.out.printf("• Категорія [%-11s] | ✅ Дозволено: %2d | ❌ Відмовлено: %2d | Разом: %2d%n",
                    cat.getDisplayName(), passed, denied, (passed + denied));
        }
        System.out.println("==========================================================\n");
    }
}