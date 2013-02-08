package com.runecore.env;

import java.net.InetSocketAddress;
import java.util.logging.Logger;

import com.runecore.codec.ProtocolCodec;
import com.runecore.network.NetworkContext;

/**
 * Context.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 8, 2013
 */
public class Context {
    
    /**
     * The instance of the server context
     */
    private static Context context;
    
    /**
     * The ProtocolCodec the context is set to use
     */
    private ProtocolCodec codec;
    
    /**
     * Construct the Context instance
     * @param protocol The ProtocolCodec the context is set to use
     */
    public Context(ProtocolCodec protocol) {
	this.setCodec(protocol);
    }
    
    private static final Logger LOGGER = Logger.getLogger(Context.class.getName());
    
    /**
     * Configure the Context
     */
    public void configure() {
	LOGGER.info("Configuring context with codec "+getCodec().getClass().getName());
	NetworkContext context = new NetworkContext(new InetSocketAddress("0.0.0.0", 43594));
	context.configure(this);
	context.bind();
    }
    
    public static Context get() {
	return context;
    }
    
    public static void set(Context ctx) {
	context = ctx;
    }

    public ProtocolCodec getCodec() {
	return codec;
    }

    public void setCodec(ProtocolCodec codec) {
	this.codec = codec;
    }

}