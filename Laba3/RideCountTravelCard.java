public class RideCountTravelCard extends TravelCard {
    private int remainingRides;
    private final TariffType tariffType;

    public RideCountTravelCard(PassengerCategory category, TariffType tariffType, int initialRides) {
        super(category);
        this.tariffType = tariffType;
        this.remainingRides = initialRides;
    }

    @Override
    public boolean canPass(double fareCost) {
        return !isBlocked() && remainingRides > 0;
    }

    @Override
    public boolean processPass(double fareCost) {
        if (canPass(fareCost)) {
            remainingRides--;
            return true;
        }
        return false;
    }

    @Override
    public String getCardDetails() {
        return String.format("За кількістю поїздок (%s, залишилося: %d)", tariffType.getDisplayName(), remainingRides);
    }

    public int getRemainingRides() {
        return remainingRides;
    }
}