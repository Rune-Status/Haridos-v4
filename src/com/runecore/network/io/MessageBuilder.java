package com.runecore.network.io;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

import com.runecore.network.io.Message.PacketType;

/**
 * MessageBuilder.java
 * 
 * @author Harry Andreas<harry@runecore.org> Feb 8, 2013
 */
public class MessageBuilder {

    public static final int[] BIT_MASK_OUT = new int[32];

    static {
	for (int i = 0; i < BIT_MASK_OUT.length; i++) {
	    BIT_MASK_OUT[i] = (1 << i) - 1;
	}
    }

    private final int opcode;

    private final PacketType type;

    private ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();

    private int bitPosition;

    public MessageBuilder() {
	this(-1);
    }

    public MessageBuilder(int opcode) {
	this(opcode, PacketType.STANDARD);
    }

    public MessageBuilder(int opcode, PacketType type) {
	this.opcode = opcode;
	this.type = type;
    }

    public MessageBuilder writeByte(byte b) {
	buffer.writeByte(b);
	return this;
    }

    public MessageBuilder writeBytes(byte[] b) {
	buffer.writeBytes(b);
	return this;
    }

    public void writeBytes(ChannelBuffer other) {
	buffer.writeBytes(other);
    }

    public MessageBuilder writeShort(int s) {
	buffer.writeShort((short) s);
	return this;
    }

    public MessageBuilder writeInt(int i) {
	buffer.writeInt(i);
	return this;
    }

    public MessageBuilder writeLong(long l) {
	buffer.writeLong(l);
	return this;
    }

    public MessageBuilder writeRS2String(String string) {
	buffer.writeBytes(string.getBytes());
	buffer.writeByte((byte) 0);
	return this;
    }

    public MessageBuilder writeShortA(int val) {
	buffer.writeByte((byte) (val >> 8));
	buffer.writeByte((byte) (val + 128));
	return this;
    }

    public MessageBuilder writeByteA(int val) {
	buffer.writeByte((byte) (val + 128));
	return this;
    }

    public MessageBuilder writeLEShortA(int val) {
	buffer.writeByte((byte) (val + 128));
	buffer.writeByte((byte) (val >> 8));
	return this;
    }

    public MessageBuilder startBitAccess() {
	bitPosition = buffer.writerIndex() * 8;
	return this;
    }

    public MessageBuilder finishBitAccess() {
	buffer.writerIndex((bitPosition + 7) / 8);
	return this;
    }

    public MessageBuilder writeBits(int numBits, int value) {
	int bytePos = bitPosition >> 3;
	int bitOffset = 8 - (bitPosition & 7);
	bitPosition += numBits;
	int pos = (bitPosition + 7) / 8;
	buffer.ensureWritableBytes(pos + 1);
	buffer.writerIndex(pos);
	byte b;
	for (; numBits > bitOffset; bitOffset = 8) {
	    b = buffer.getByte(bytePos);
	    buffer.setByte(bytePos, (byte) (b & ~BIT_MASK_OUT[bitOffset]));
	    buffer.setByte(bytePos,
		    (byte) (b | (value >> (numBits - bitOffset))
			    & BIT_MASK_OUT[bitOffset]));
	    bytePos++;
	    numBits -= bitOffset;
	}
	b = buffer.getByte(bytePos);
	if (numBits == bitOffset) {
	    buffer.setByte(bytePos, (byte) (b & ~BIT_MASK_OUT[bitOffset]));
	    buffer.setByte(bytePos,
		    (byte) (b | value & BIT_MASK_OUT[bitOffset]));
	} else {
	    buffer.setByte(
		    bytePos,
		    (byte) (b & ~(BIT_MASK_OUT[numBits] << (bitOffset - numBits))));
	    buffer.setByte(
		    bytePos,
		    (byte) (b | (value & BIT_MASK_OUT[numBits]) << (bitOffset - numBits)));
	}
	return this;
    }

    public MessageBuilder writeByteC(int val) {
	writeByte((byte) (-val));
	return this;
    }

    public MessageBuilder writeLEShort(int val) {
	buffer.writeByte((byte) (val));
	buffer.writeByte((byte) (val >> 8));
	return this;
    }

    public MessageBuilder writeInt1(int val) {
	buffer.writeByte((byte) (val >> 8));
	buffer.writeByte((byte) val);
	buffer.writeByte((byte) (val >> 24));
	buffer.writeByte((byte) (val >> 16));
	return this;
    }

    public MessageBuilder writeInt2(int val) {
	buffer.writeByte((byte) (val >> 16));
	buffer.writeByte((byte) (val >> 24));
	buffer.writeByte((byte) val);
	buffer.writeByte((byte) (val >> 8));
	return this;
    }

    public MessageBuilder writeLEInt(int val) {
	buffer.writeByte((byte) (val));
	buffer.writeByte((byte) (val >> 8));
	buffer.writeByte((byte) (val >> 16));
	buffer.writeByte((byte) (val >> 24));
	return this;
    }

    public MessageBuilder writeBytes(byte[] data, int offset, int length) {
	buffer.writeBytes(data, offset, length);
	return this;
    }

    public MessageBuilder writeByteA(byte val) {
	buffer.writeByte((byte) (val + 128));
	return this;
    }

    public MessageBuilder writeByteC(byte val) {
	buffer.writeByte((byte) (-val));
	return this;
    }

    public MessageBuilder writeByteS(int val) {
	buffer.writeByte((byte) (128 - val));
	return this;
    }

    public MessageBuilder writeReverse(byte[] is, int offset, int length) {
	for (int i = (offset + length - 1); i >= offset; i--) {
	    buffer.writeByte(is[i]);
	}
	return this;
    }

    public MessageBuilder writeReverseA(byte[] is, int offset, int length) {
	for (int i = (offset + length - 1); i >= offset; i--) {
	    writeByteA(is[i]);
	}
	return this;
    }

    public MessageBuilder writeMedium(int val) {
	buffer.writeByte((byte) (val >> 16));
	buffer.writeByte((byte) (val >> 8));
	buffer.writeByte((byte) val);
	return this;
    }

    public MessageBuilder writeSmart(int val) {
	if (val >= 128) {
	    writeShort((val + 32768));
	} else {
	    writeByte((byte) val);
	}
	return this;
    }

    public MessageBuilder writeByte(int i) {
	writeByte((byte) i);
	return this;
    }

    public MessageBuilder writeMediumInt(int i) {
	buffer.writeByte((byte) (i >> 16));
	buffer.writeByte((byte) (i >> 8));
	buffer.writeByte((byte) i);
	return this;
    }

    public void skip(int skip) {
	for (int i = 0; i < skip; i++) {
	    buffer.writeByte((byte) 0);
	}
    }

    public MessageBuilder putGJString2(String string) {
	byte[] packed = new byte[256];
	int length = packGJString2(0, packed, string);
	writeByte(0).writeBytes(packed, 0, length).writeByte(0);
	return this;
    }
    
    private static int packGJString2(int position, byte[] buffer, String string) {
	int length = string.length();
	int offset = position;
	for (int i_6_ = 0; length > i_6_; i_6_++) {
	    int character = string.charAt(i_6_);
	    if (character > 127) {
		if (character > 2047) {
		    buffer[offset++] = (byte) ((character | 919275) >> 12);
		    buffer[offset++] = (byte) (128 | ((character >> 6) & 63));
		    buffer[offset++] = (byte) (128 | (character & 63));
		} else {
		    buffer[offset++] = (byte) ((character | 12309) >> 6);
		    buffer[offset++] = (byte) (128 | (character & 63));
		}
	    } else
		buffer[offset++] = (byte) character;
	}
	return offset - position;
    }

    public int position() {
	return buffer.writerIndex();
    }

    public Message toMessage() {
	return new Message(opcode, type, ChannelBuffers.copiedBuffer(buffer));
    }

    public ChannelBuffer getBuffer() {
	return buffer;
    }

    public MessageBuilder putGJString(String s) {
	writeByte(0);
	writeBytes(s.getBytes());
	writeByte(0);
	return this;
    }

    public void addBytes128(ChannelBuffer buffer) {
	for (int k = 0; k < buffer.writerIndex(); k++) {
	    writeByte((byte) (buffer.readByte() + 128));
	}
    }

}
