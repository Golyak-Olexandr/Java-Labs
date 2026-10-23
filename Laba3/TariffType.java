public enum TariffType {
    TERM_10_DAYS("На 10 днів"),
    TERM_MONTH("На місяць"),
    RIDE_COUNT_5("На 5 поїздок"),
    RIDE_COUNT_10("На 10 поїздок"),
    ACCUMULATIVE("Накопичувальна");

    private final String displayName;

    TariffType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}