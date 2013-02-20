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
    
    /**
     * ExecutorService for the js5 protocol
     */
    private ExecutorService js5Service;
    
    /**
     * Starts inital loading, cache loading etc
     */
    public void init(Context context) {
	Cache.init();
	/*
	String[] scripts = new String[] { "ActionSender", "GPI" };
	for(String s : scripts) {
	    GroovyScript script = context.getGroovyEngine().initScript(s);
	    script.init(context);
	}
	*/
	js5Service = Executors.newFixedThreadPool(5);
    }
    
    /**
     * Sets up the pipeline
     */
    @Override
    public void setup(ChannelPipeline pipeline) {
	pipeline.addLast("decoder", new HandshakeDecoder());
	pipeline.addLast("encoder", new NetworkEncoder());
	pipeline.addLast("handler", new EventHandler(js5Service));
    }

    /* (non-Javadoc)
     * @see com.runecore.codec.ProtocolCodec#scriptPaths()
     */
    @Override
    public String[] scriptPaths() {
	return new String[] {
		"./data/scripts/", "./data/scripts/614/", "./data/scripts/614/packet/", "./data/scripts/614/widget/",
		"./data/scripts/614/objects/"
	};
    }

}