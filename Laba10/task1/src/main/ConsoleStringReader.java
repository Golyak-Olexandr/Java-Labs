package main;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ConsoleStringReader {

    private final Scanner scanner;

    public ConsoleStringReader(InputStream inputStream) {
        this(inputStream, resolveDefaultCharset(inputStream));
    }

    public ConsoleStringReader(InputStream inputStream, Charset charset) {
        if (inputStream == null || charset == null) {
            throw new IllegalArgumentException("InputStream and Charset must not be null");
        }
        this.scanner = new Scanner(inputStream, charset);
    }

    private static Charset resolveDefaultCharset(InputStream inputStream) {
        if (inputStream == System.in) {
            String stdinEncoding = System.getProperty("stdin.encoding");
            if (stdinEncoding != null && !stdinEncoding.isBlank()) {
                return Charset.forName(stdinEncoding);
            }
            String nativeEncoding = System.getProperty("native.encoding");
            if (nativeEncoding != null && !nativeEncoding.isBlank()) {
                return Charset.forName(nativeEncoding);
            }
        }
        return StandardCharsets.UTF_8;
    }

    public String readLine(String prompt) {
        System.out.print(prompt);
        if (!scanner.hasNextLine()) {
            return "";
        }
        return scanner.nextLine();
    }
}