package com.runecore.codec;

import org.jboss.netty.channel.ChannelPipeline;

/**
 * ProtocolCodec.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 8, 2013
 */
public interface ProtocolCodec {
    
    public void setup(ChannelPipeline pipeline);

}
