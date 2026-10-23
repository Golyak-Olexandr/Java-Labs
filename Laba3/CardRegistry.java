import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CardRegistry {
    private final Map<String, TravelCard> registeredCards = new HashMap<>();

    public TravelCard issueTermCard(PassengerCategory category, TariffType tariffType) {
        int days = (tariffType == TariffType.TERM_10_DAYS) ? 10 : 30;
        TermTravelCard card = new TermTravelCard(category, tariffType, days);
        registeredCards.put(card.getId(), card);
        System.out.printf("✨ [Реєстр] Випущено термінову картку ID: %s [%s | %s]%n",
                card.getId(), category.getDisplayName(), tariffType.getDisplayName());
        return card;
    }

    public TravelCard issueRideCountCard(PassengerCategory category, TariffType tariffType) {
        int rides = (tariffType == TariffType.RIDE_COUNT_5) ? 5 : 10;
        RideCountTravelCard card = new RideCountTravelCard(category, tariffType, rides);
        registeredCards.put(card.getId(), card);
        System.out.printf("✨ [Реєстр] Випущено картку на поїздки ID: %s [%s | %s]%n",
                card.getId(), category.getDisplayName(), tariffType.getDisplayName());
        return card;
    }

    public TravelCard issueAccumulativeCard(double initialBalance) {
        AccumulativeTravelCard card = new AccumulativeTravelCard(initialBalance);
        registeredCards.put(card.getId(), card);
        System.out.printf("✨ [Реєстр] Випущено накопичувальну картку ID: %s [Баланс: %.2f грн]%n",
                card.getId(), initialBalance);
        return card;
    }

    public boolean topUpCard(String cardId, double amount) {
        TravelCard card = registeredCards.get(cardId);
        if (card instanceof AccumulativeTravelCard accumulativeCard) {
            accumulativeCard.topUp(amount);
            return true;
        }
        System.out.printf("❌ [Реєстр] Картку ID: %s не можна поповнити (не накопичувальна або не існує)%n", cardId);
        return false;
    }

    public boolean blockCard(String cardId) {
        TravelCard card = registeredCards.get(cardId);
        if (card != null) {
            card.setBlocked(true);
            System.out.printf("🔒 [Реєстр] Картку ID: %s успішно заблоковано!%n", cardId);
            return true;
        }
        return false;
    }

    public Optional<TravelCard> getCard(String cardId) {
        return Optional.ofNullable(registeredCards.get(cardId));
    }
}