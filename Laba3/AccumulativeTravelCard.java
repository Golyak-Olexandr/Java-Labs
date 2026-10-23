public class AccumulativeTravelCard extends TravelCard {
    private double balance;

    public AccumulativeTravelCard(double initialBalance) {
        super(PassengerCategory.STANDARD);
        this.balance = Math.max(0, initialBalance);
    }

    public void topUp(double amount) {
        if (amount > 0) {
            this.balance += amount;
            System.out.printf("💳 [Реєстр] Картку %s поповнено на %.2f грн. Новий баланс: %.2f грн.%n",
                    getId(), amount, balance);
        }
    }

    @Override
    public boolean canPass(double fareCost) {
        return !isBlocked() && balance >= fareCost;
    }

    @Override
    public boolean processPass(double fareCost) {
        if (canPass(fareCost)) {
            balance -= fareCost;
            return true;
        }
        return false;
    }

    @Override
    public String getCardDetails() {
        return String.format("Накопичувальна (Баланс: %.2f грн)", balance);
    }

    public double getBalance() {
        return balance;
    }
}