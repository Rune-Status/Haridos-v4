package com.runecore.codec;

import org.jboss.netty.channel.ChannelPipeline;

import com.runecore.env.Context;

/**
 * ProtocolCodec.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 8, 2013
 */
public interface ProtocolCodec {
    
    public void init(Context context);
    public void setup(ChannelPipeline pipeline);
    public String[] scriptPaths();

}
