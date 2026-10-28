package Task3;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

public class EncryptFilterWriter extends FilterWriter {
    private final char keyChar;

    public EncryptFilterWriter(Writer out, char keyChar) {
        super(out);
        this.keyChar = keyChar;
    }

    @Override
    public void write(int c) throws IOException {
        super.write(c + keyChar);
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        for (int i = off; i < off + len; i++) {
            write(cbuf[i]);
        }
    }

    @Override
    public void write(String str, int off, int len) throws IOException {
        for (int i = off; i < off + len; i++) {
            write(str.charAt(i));
        }
    }
}
