package util;

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;

public class PaddedByteArrayInputStream extends ByteArrayInputStream {
    private final ByteBuffer byteBuffer;

    public PaddedByteArrayInputStream(byte[] array) {
        super(array);
        byteBuffer = ByteBuffer.allocate(4);
    }

    @Override
    public synchronized int read(byte[] bPointer, int off, int len) {
        // Zero out the byte buffer before reading from the source
        byteBuffer.putInt(0, 0);
        super.read(byteBuffer.array(), off, len);
        // copy padded result into out pointer
        System.arraycopy(byteBuffer.array(), 0, bPointer, 0, byteBuffer.array().length);
        return len;
    }
}
