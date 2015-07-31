
package mina;

import org.apache.mina.core.buffer.BufferDataException;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * A {@link ProtocolCodecFactory} that serializes and deserializes Java objects.
 * This codec is very useful when you have to prototype your application rapidly
 * without any specific codec.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class MyBytesCodecFactory implements ProtocolCodecFactory {
    private final MyBytesEncoder encoder;

    private final MyBytesDecoder decoder;

    /**
     * Creates a new instance with the {@link ClassLoader} of
     * the current thread.
     */
    public MyBytesCodecFactory() {
        this(Thread.currentThread().getContextClassLoader());
    }

    /**
     * Creates a new instance with the specified {@link ClassLoader}.
     */
    public MyBytesCodecFactory(ClassLoader classLoader) {
        encoder = new MyBytesEncoder();
        decoder = new MyBytesDecoder(classLoader);
    }

    public ProtocolEncoder getEncoder(IoSession session) {
        return encoder;
    }

    public ProtocolDecoder getDecoder(IoSession session) {
        return decoder;
    }

    /**
     * Returns the allowed maximum size of the encoded object.
     * If the size of the encoded object exceeds this value, the encoder
     * will throw a {@link IllegalArgumentException}.  The default value
     * is {@link Integer#MAX_VALUE}.
     * <p>
     * This method does the same job with {@link ObjectSerializationEncoder#getMaxObjectSize()}.
     */
    public int getEncoderMaxObjectSize() {
        return encoder.getMaxObjectSize();
    }

    /**
     * Sets the allowed maximum size of the encoded object.
     * If the size of the encoded object exceeds this value, the encoder
     * will throw a {@link IllegalArgumentException}.  The default value
     * is {@link Integer#MAX_VALUE}.
     * <p>
     * This method does the same job with {@link ObjectSerializationEncoder#setMaxObjectSize(int)}.
     */
    public void setEncoderMaxObjectSize(int maxObjectSize) {
        encoder.setMaxObjectSize(maxObjectSize);
    }

    /**
     * Returns the allowed maximum size of the object to be decoded.
     * If the size of the object to be decoded exceeds this value, the
     * decoder will throw a {@link BufferDataException}.  The default
     * value is <tt>1048576</tt> (1MB).
     * <p>
     * This method does the same job with {@link ObjectSerializationDecoder#getMaxObjectSize()}.
     */
    public int getDecoderMaxObjectSize() {
        return decoder.getMaxObjectSize();
    }

    /**
     * Sets the allowed maximum size of the object to be decoded.
     * If the size of the object to be decoded exceeds this value, the
     * decoder will throw a {@link BufferDataException}.  The default
     * value is <tt>1048576</tt> (1MB).
     * <p>
     * This method does the same job with {@link ObjectSerializationDecoder#setMaxObjectSize(int)}.
     */
    public void setDecoderMaxObjectSize(int maxObjectSize) {
        decoder.setMaxObjectSize(maxObjectSize);
    }
}
