public enum PassengerCategory {
    PUPIL("Учнівська"),
    STUDENT("Студентська"),
    STANDARD("Звичайна");

    private final String displayName;

    PassengerCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}