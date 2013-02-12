package com.runecore.codec.codec614.auth;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.replay.ReplayingDecoder;

import com.runecore.env.login.LoginRequest;

/**
 * AuthenticationDecoder.java
 * @author Harry Andreas<harry@runecore.org>
 *  Feb 8, 2013
 */
public class AuthenticationDecoder extends ReplayingDecoder<AuthenticationStage> {

	public AuthenticationDecoder() {
		checkpoint(AuthenticationStage.VALIDATION);
	}

	/**
	 * Readable bytes
	 * @param buffer
	 * @return
	 */
	public int readableBytes(ChannelBuffer buffer) {
		return buffer.writerIndex() - buffer.readerIndex();
	}

	@Override
	protected Object decode(ChannelHandlerContext chtx, Channel chan,
			ChannelBuffer in, AuthenticationStage state) throws Exception {
		switch (state) {
		case VALIDATION:
			int bytesLeft = readableBytes(in);
			if (bytesLeft >= 3) {
				int type = in.readUnsignedByte();
				int size = in.readUnsignedShort();
				if (size != readableBytes(in)) {
					throw new Exception("Mismatched login packet size.");
				}
				int version = in.readInt();
				if (version != 614) {
					throw new Exception("Incorrect revision read");
				}
				if (type == 16 || type == 18) {
					checkpoint(AuthenticationStage.DETAILS);
				}
			}
			break;
		case DETAILS:
			in.readUnsignedByte();
			int mode = in.readUnsignedByte();
			in.readUnsignedShort();
			in.readUnsignedShort();
			in.readUnsignedByte();
			in.skipBytes(24);
			readRS2String(in); // macaddress!
			in.readInt();
			int size = in.readUnsignedByte();
			in.skipBytes(size);
			in.skipBytes(6 + (33 * 4) + 8 + 2 + 14);
			if (in.readUnsignedByte() != 10) {
				throw new Exception("Invalid RSA header.");
			}
			in.readLong();
			in.readLong();
			long l = in.readLong();
			String name = longToString(l);
			String password = readRS2String(in);
			int left = readableBytes(in);
			in.skipBytes(left);
			return new LoginRequest(chan, chtx, name, password, mode);
		}
		return null;
	}

	public static String longToString(long l) {
		if (l <= 0L || l >= 0x5b5b57f8a98a5dd1L)
			return null;
		if (l % 37L == 0L)
			return null;
		int i = 0;
		char ac[] = new char[12];
		while (l != 0L) {
			long l1 = l;
			l /= 37L;
			ac[11 - i++] = VALID_CHARS[(int) (l1 - l * 37L)];
		}
		return new String(ac, 12 - i, i);
	}

	/**
	 * Valid characters for chat
	 */
	public static final char[] VALID_CHARS = { '_', 'a', 'b', 'c', 'd', 'e',
			'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
			's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4',
			'5', '6', '7', '8', '9', '/' };

	public static String readRS2String(ChannelBuffer buffer) {
		StringBuilder sb = new StringBuilder();
		byte b;
		while (buffer.readable() && (b = buffer.readByte()) != 0) {
			sb.append((char) b);
		}
		return sb.toString();
	}

}