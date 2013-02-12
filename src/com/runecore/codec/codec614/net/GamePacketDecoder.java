package com.runecore.codec.codec614.net;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

import com.runecore.network.GameSession;
import com.runecore.network.io.Message;
import com.runecore.network.io.Message.PacketType;

/**
 * GamePacketDecoder.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 12, 2013
 */
public class GamePacketDecoder extends FrameDecoder {
    
    private static final int[] PACKET_SIZE = new int[256];
    
    static { 
	for (int i = 0; i < 256; i++) {
		PACKET_SIZE[i] = -3;
	}
	PACKET_SIZE[0] = 8;
	PACKET_SIZE[1] = -1;
	PACKET_SIZE[2] = -1;
	PACKET_SIZE[3] = -1;
	PACKET_SIZE[4] = 1;
	PACKET_SIZE[5] = 4;
	PACKET_SIZE[6] = 0;
	PACKET_SIZE[7] = 6;
	PACKET_SIZE[8] = -1;
	PACKET_SIZE[9] = 7;
	PACKET_SIZE[10] = 16;
	PACKET_SIZE[11] = 3;
	PACKET_SIZE[12] = -1;
	PACKET_SIZE[13] = 8;
	PACKET_SIZE[14] = 8;
	PACKET_SIZE[15] = 3;
	PACKET_SIZE[16] = 7;
	PACKET_SIZE[17] = 4;
	PACKET_SIZE[18] = 2;
	PACKET_SIZE[19] = 7;
	PACKET_SIZE[20] = 8;
	PACKET_SIZE[21] = 3;
	PACKET_SIZE[22] = 2;
	PACKET_SIZE[23] = 15;
	PACKET_SIZE[24] = 8;
	PACKET_SIZE[25] = -1;
	PACKET_SIZE[26] = 3;
	PACKET_SIZE[27] = -1;
	PACKET_SIZE[28] = 11;
	PACKET_SIZE[29] = 7;
	PACKET_SIZE[30] = 0;
	PACKET_SIZE[31] = 2;
	PACKET_SIZE[32] = 2;
	PACKET_SIZE[33] = 7;
	PACKET_SIZE[34] = 6;
	PACKET_SIZE[35] = 4;
	PACKET_SIZE[36] = 3;
	PACKET_SIZE[37] = -1;
	PACKET_SIZE[38] = 15;
	PACKET_SIZE[39] = 0;
	PACKET_SIZE[40] = 8;
	PACKET_SIZE[41] = 4;
	PACKET_SIZE[42] = 3;
	PACKET_SIZE[43] = 7;
	PACKET_SIZE[44] = 4;
	PACKET_SIZE[45] = 2;
	PACKET_SIZE[46] = -1;
	PACKET_SIZE[47] = -1;
	PACKET_SIZE[48] = 8;
	PACKET_SIZE[49] = 4;
	PACKET_SIZE[50] = 3;
	PACKET_SIZE[51] = -1;
	PACKET_SIZE[52] = 8;
	PACKET_SIZE[53] = -1;
	PACKET_SIZE[54] = -1;
	PACKET_SIZE[55] = 8;
	PACKET_SIZE[56] = 7;
	PACKET_SIZE[57] = 11;
	PACKET_SIZE[58] = 12;
	PACKET_SIZE[59] = 3;
	PACKET_SIZE[60] = -1;
	PACKET_SIZE[61] = 4;
	PACKET_SIZE[62] = -1;
	PACKET_SIZE[63] = 3;
	PACKET_SIZE[64] = 7;
	PACKET_SIZE[65] = 3;
	PACKET_SIZE[66] = 3;
	PACKET_SIZE[67] = -1;
	PACKET_SIZE[68] = 2;
	PACKET_SIZE[69] = -1;
	PACKET_SIZE[70] = 3;
	PACKET_SIZE[71] = -1;
	PACKET_SIZE[72] = -1;
	PACKET_SIZE[73] = 6;
	PACKET_SIZE[74] = 3;
	PACKET_SIZE[75] = -1;
	PACKET_SIZE[76] = 3;
	PACKET_SIZE[77] = -1;
	PACKET_SIZE[78] = 7;
	PACKET_SIZE[79] = 8;
	PACKET_SIZE[80] = 7;
	PACKET_SIZE[81] = -1;
	PACKET_SIZE[82] = 16;
	PACKET_SIZE[83] = 1;
    }
    
    /**
     * GameSession for the GamePacketDecoder
     */
    private final GameSession session;

    /**
     * Construct the GamePacketDecoder
     * @param session The GameSession for this GamePacketDecoder
     */
    public GamePacketDecoder(GameSession session) {
	this.session = session;
	session.getChannel().getPipeline().getContext("handler").setAttachment(session);
    }
    
    /**
     * Decode the Game Packets
     */
    @Override
    protected Object decode(ChannelHandlerContext chc, Channel chan, ChannelBuffer buffer) throws Exception {
	if(chc.getAttachment() == null) {
	    chc.setAttachment(session);
	}
	if (buffer.readableBytes() > 1000) {
	    chan.close();
	    return null;
	}
	if (buffer.readable()) {
	    int opcode = buffer.readUnsignedByte();
	    int length = PACKET_SIZE[opcode];
	    if (opcode < 0 || opcode > 255) {
		buffer.discardReadBytes();
		return null;
	    }
	    if (length == -1) {
		if (buffer.readable()) {
		    length = buffer.readUnsignedByte();
		}
	    }
	    if (length <= buffer.readableBytes() && length > 0) {
		byte[] payload = new byte[length];
		buffer.readBytes(payload, 0, length);
		return new Message(opcode, PacketType.STANDARD, ChannelBuffers.wrappedBuffer(payload));
	    }
	}
	return null;
    }

}
