package com.runecore.network;

import java.util.logging.Logger;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

/**
 * NetworkChannelHandler.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 8, 2013
 */
public class NetworkChannelHandler extends SimpleChannelHandler {
    
    private static final Logger LOGGER = Logger.getLogger(NetworkChannelHandler.class.getName());
    
    @Override
    public final void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
	
    }
    
    @Override
    public final void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
	
    }
    
}