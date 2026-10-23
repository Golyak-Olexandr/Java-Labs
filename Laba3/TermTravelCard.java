import java.time.LocalDate;

public class TermTravelCard extends TravelCard {
    private final LocalDate expirationDate;
    private final TariffType tariffType;

    public TermTravelCard(PassengerCategory category, TariffType tariffType, int durationDays) {
        super(category);
        this.tariffType = tariffType;
        this.expirationDate = LocalDate.now().plusDays(durationDays);
    }

    @Override
    public boolean canPass(double fareCost) {
        return !isBlocked() && !LocalDate.now().isAfter(expirationDate);
    }

    @Override
    public boolean processPass(double fareCost) {
        return canPass(fareCost);
    }

    @Override
    public String getCardDetails() {
        return String.format("Термінова (%s, дійсна до: %s)", tariffType.getDisplayName(), expirationDate);
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }
}