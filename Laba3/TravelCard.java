import java.util.UUID;

public abstract class TravelCard {
    private final String id;
    private final PassengerCategory passengerCategory;
    private boolean isBlocked;

    public TravelCard(PassengerCategory passengerCategory) {
        this.id = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.passengerCategory = passengerCategory;
        this.isBlocked = false;
    }

    public String getId() {
        return id;
    }

    public PassengerCategory getPassengerCategory() {
        return passengerCategory;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public abstract boolean canPass(double fareCost);
    public abstract boolean processPass(double fareCost);
    public abstract String getCardDetails();
}