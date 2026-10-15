import java.util.Scanner;

public class Task10 {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Введіть ціле позитивне число: ");
            if (!scanner.hasNextLong()) {
                System.out.println("Помилка: введено не число!");
                return;
            }

            long number = scanner.nextLong();

            if (number <= 0) {
                System.out.println("Помилка: число повинно бути більше 0!");
                return;
            }

            System.out.println("\nРезультати конвертації:");
            System.out.println("Двійкова (radix 2):     " + convertToBase(number, 2));
            System.out.println("Вісімкова (radix 8):    " + convertToBase(number, 8));
            System.out.println("Шістнадцяткова (radix 16): " + convertToBase(number, 16));
        }
    }

    /**
     * Алгоритм ділення на основу (radix) із записом залишків
     */
    public static String convertToBase(long number, int radix) {
        if (number == 0) return "0";

        char[] digits = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder();

        while (number > 0) {
            int remainder = (int) (number % radix);
            sb.append(digits[remainder]);
            number /= radix;
        }

        return sb.reverse().toString();
    }
}