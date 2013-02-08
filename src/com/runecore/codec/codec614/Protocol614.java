package com.runecore.codec.codec614;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jboss.netty.channel.ChannelPipeline;

import com.runecore.cache.Cache;
import com.runecore.codec.ProtocolCodec;
import com.runecore.codec.codec614.net.EventHandler;
import com.runecore.codec.codec614.net.HandshakeDecoder;
import com.runecore.env.Context;
import com.runecore.network.NetworkEncoder;

/**
 * Protocol614.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 8, 2013
 */
public class Protocol614 implements ProtocolCodec {
    
    private ExecutorService js5Service;
    
    public void init(Context context) {
	Cache.init();
	js5Service = Executors.newFixedThreadPool(5);
    }
    
    /* (non-Javadoc)
     * @see com.runecore.codec.ProtocolCodec#setup(org.jboss.netty.channel.ChannelPipeline)
     */
    @Override
    public void setup(ChannelPipeline pipeline) {
	pipeline.addLast("decoder", new HandshakeDecoder());
	pipeline.addLast("encoder", new NetworkEncoder());
	pipeline.addLast("handler", new EventHandler(js5Service));
    }

}