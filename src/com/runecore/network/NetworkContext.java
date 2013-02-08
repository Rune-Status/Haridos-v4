package com.runecore.network;

import java.net.InetSocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.DefaultChannelPipeline;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import com.runecore.env.Context;

/**
 * NetworkContext.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 8, 2013
 */
public class NetworkContext {
    
    /**
     * The address to bind
     */
    private final InetSocketAddress bindAddress;
    
    /**
     * The ServerBootstrap instance for this context
     */
    private ServerBootstrap bootstrap;
    
    /**
     * Construct the Network context
     * @param address
     */
    public NetworkContext(InetSocketAddress address) {
	this.bindAddress = address;
    }
    
    /**
     * Configure the context before binding
     * @param context The context to configure for
     */
    public void configure(Context context) {
	ChannelPipeline pipeline = new DefaultChannelPipeline();
	pipeline.addLast("handler", new NetworkChannelHandler());
	context.getCodec().setup(pipeline);
	bootstrap = new ServerBootstrap();
	Executor e = Executors.newCachedThreadPool();
	bootstrap.setFactory(new NioServerSocketChannelFactory(e, e));
	bootstrap.setPipelineFactory(new NetworkPipelineFactory(pipeline));
    }
    
    /**
     * Binds the socket address specified in the constructor
     */
    public void bind() {
	bootstrap.bind(bindAddress);
    }

}