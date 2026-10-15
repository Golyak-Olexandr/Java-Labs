public class Validator {

    // Українські та латинські букви з урахуванням І, Ї, Є, Ґ
    private static final String LETTERS = "А-Яа-яЁёЇїІіЄєҐґA-Za-z";

    // Слово має починатися з букви. Всередині дозволено один дефіс або апостроф між буквами.
    // Заборонено: "---", "-Тест", "Тест-", "123", спецсимволи
    private static final String NAME_REGEX = "^[" + LETTERS + "]+(['’\\-][" + LETTERS + "]+)?$";

    // Дата: ДД.ММ.РРРР
    private static final String DATE_REGEX = "^(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[012])\\.(19|20)\\d\\d$";

    // Телефон: +380XXXXXXXXX або 0XXXXXXXXX
    private static final String PHONE_REGEX = "^(\\+?38)?0\\d{9}$";

    public static boolean validateName(String name) {
        if (name == null) return false;
        return name.trim().matches(NAME_REGEX);
    }

    public static boolean validateDate(String date) {
        if (date == null) return false;
        return date.trim().matches(DATE_REGEX);
    }

    public static boolean validatePhone(String phone) {
        if (phone == null) return false;
        return phone.trim().matches(PHONE_REGEX);
    }

    public static boolean validateAddress(String address) {
        if (address == null) return false;
        String str = address.trim();
        if (str.length() < 3) return false;

        // В адресі мають бути букви/цифри, а не суцільні знаки "-"
        boolean hasLetterOrDigit = false;
        for (char ch : str.toCharArray()) {
            if (Character.isLetterOrDigit(ch)) {
                hasLetterOrDigit = true;
            }
            // Забороняємо небезпечні спецсимволи
            if ("/*+_!@#$%^&*()=~`[]{}:;\"<>?\\|".indexOf(ch) != -1) {
                return false;
            }
        }
        return hasLetterOrDigit;
    }
}