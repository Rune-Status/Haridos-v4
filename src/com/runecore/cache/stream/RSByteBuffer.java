package com.runecore.cache.stream;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;


public class RSByteBuffer extends DataInputStream {

    public RSByteBuffer(InputStream in) {
        super(in);
    }

    public String readRS2String() throws IOException {
        StringBuilder sb = new StringBuilder();
        byte b;
        while ((b = readByte()) != 0) {
            sb.append((char) b);
        }
        return sb.toString();
    }

    public byte readByteUnsigned() throws IOException {
    	return (byte) readUnsignedByte();
    }

    public int method2225(int i) throws IOException {
		if (getData()[position()] < i) {
			return 0x7fffffff & readInt();
		}
		return readShort();
    }

    public int readShortA() throws IOException {
        return (((readByte() & 0xff) << 8) + (readByte() - 128 & 0xff));
    }

    public int readShortLEA() throws IOException {
        byte bl = readByte();
        byte bh = readByte();
        return ((bl - 128 & 0xff)) + ((bh & 0xff) << 8);
    }

    public byte readSByteA() throws IOException {
        return (byte) (read() - 128);
    }

    public byte readSByteC() throws IOException {
        return (byte) (-read());
    }

    public byte readSByteS() throws IOException {
        return (byte) (128 - read());
    }

    public int readByteA() throws IOException {
        return (readUnsignedByte() - 128 & 0xff);
    }

    public int readLEInt() throws IOException {
        return readUnsignedByte() + (readUnsignedByte() << 8) + (readUnsignedByte() << 16) + (readUnsignedByte() << 24);
    }

    public int readByteC() throws IOException {
        return -(readUnsignedByte() & 0xff);
    }

    public int readByteS() throws IOException {
        return (128 - readUnsignedByte() & 0xff);
    }

    public int read24BitInt() throws IOException {
        return ((read() & 0xff) << 16) + ((read() & 0xff) << 8) + (read() & 0xff);
    }

    public int readSShort() throws IOException {
        int i_54_ = readShort();
        if (i_54_ > 32767)
            i_54_ -= 65536;
        return i_54_;
    }

    public void seek(int i_18_) {
        ((RSByteArrayInputStream) in).seek(i_18_);
    }

    public int readUnsignedSmart() throws IOException {
        int i = readUnsignedByte();
        seek(((RSByteArrayInputStream) in).position() - 1);
        if (i >= 128)
            return -32768 + readUnsignedShort();
        return readUnsignedByte();
    }

    public byte[] getData() {
    	return ((RSByteArrayInputStream) in).getBytes();
    }

	public int position() {
		return ((RSByteArrayInputStream) in).position();
	}

	/**
	 * @return
	 * @throws IOException 
	 */
	public int readBigSmart() throws IOException {
		if (getData()[position()] < 0) {
			return 0x7fffffff & readInt();
		}
		return readShort();
	}
}