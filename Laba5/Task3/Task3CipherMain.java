package Task3;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Task3CipherMain {
    public static void encryptFile(String inputFile, String outputFile, char keyChar) throws IOException {
        try (FileReader fr = new FileReader(inputFile);
             FileWriter fw = new FileWriter(outputFile);
             EncryptFilterWriter ew = new EncryptFilterWriter(fw, keyChar)) {
            int c;
            while ((c = fr.read()) != -1) {
                ew.write(c);
            }
        }
    }

    public static void decryptFile(String inputFile, String outputFile, char keyChar) throws IOException {
        try (FileReader fr = new FileReader(inputFile);
             DecryptFilterReader dr = new DecryptFilterReader(fr, keyChar);
             FileWriter fw = new FileWriter(outputFile)) {
            int c;
            while ((c = dr.read()) != -1) {
                fw.write(c);
            }
        }
    }

    public static void main(String[] args) {
        String originalFile = "source.txt";
        String encryptedFile = "encrypted.txt";
        String decryptedFile = "decrypted.txt";
        char key = 'K';

        try {
            try (FileWriter fw = new FileWriter(originalFile)) {
                fw.write("Hello World! Привіт Світ!");
            }

            encryptFile(originalFile, encryptedFile, key);
            System.out.println("Зашифровано у файл: " + encryptedFile);

            decryptFile(encryptedFile, decryptedFile, key);
            System.out.println("Розшифровано у файл: " + decryptedFile);

        } catch (IOException e) {
            System.out.println("Помилка роботи з файлами: " + e.getMessage());
        }
    }
}