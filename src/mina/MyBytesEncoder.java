
package mina;

import java.io.NotSerializableException;
import java.io.Serializable;


import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

/**
 * A {@link ProtocolEncoder} which serializes {@link Serializable} Java objects
 * using {@link IoBuffer#putObject(Object)}.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class MyBytesEncoder extends ProtocolEncoderAdapter {
    private int maxObjectSize = Integer.MAX_VALUE; // 2GB

    /**
     * Creates a new instance.
     */
    public MyBytesEncoder() {
        // Do nothing
    }

    /**
     * Returns the allowed maximum size of the encoded object.
     * If the size of the encoded object exceeds this value, this encoder
     * will throw a {@link IllegalArgumentException}.  The default value
     * is {@link Integer#MAX_VALUE}.
     */
    public int getMaxObjectSize() {
        return maxObjectSize;
    }

    /**
     * Sets the allowed maximum size of the encoded object.
     * If the size of the encoded object exceeds this value, this encoder
     * will throw a {@link IllegalArgumentException}.  The default value
     * is {@link Integer#MAX_VALUE}.
     */
    public void setMaxObjectSize(int maxObjectSize) {
        if (maxObjectSize <= 0) {
            throw new IllegalArgumentException("maxObjectSize: " + maxObjectSize);
        }

        this.maxObjectSize = maxObjectSize;
    }

    public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
        if (!(message instanceof Serializable)) {
            throw new NotSerializableException();
        }
/*
        IoBuffer buf = IoBuffer.allocate(256);
        buf.setAutoExpand(true);
        buf.putObject(message);

        int objectSize = buf.position() - 4;
        if (objectSize > maxObjectSize) {
            throw new IllegalArgumentException("The encoded object is too big: " + objectSize + " (> " + maxObjectSize
                    + ')');
        }

        buf.flip();
        out.write(buf);
        */
        byte[] bytes = (byte[]) message;
        IoBuffer buffer = IoBuffer.allocate( bytes.length, false );
       
        buffer.put(bytes);
        buffer.flip();
        out.write(buffer);
        
    }
}
