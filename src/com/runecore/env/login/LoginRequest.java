package com.runecore.env.login;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;

/**
 * LoginRequest.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 9, 2013
 */
public class LoginRequest {
    
    private final String user, pass;
    private final int displayMode;
    private final Channel channel;
    private final ChannelHandlerContext chc;
    
    public LoginRequest(Channel channel, ChannelHandlerContext chc, String user, String pass, int displayMode) {
	this.channel = channel;
	this.chc = chc;
	this.user = user;
	this.pass = pass;
	this.displayMode = displayMode;
    }

    public String getUser() {
	return user;
    }

    public String getPass() {
	return pass;
    }

    public int getDisplayMode() {
	return displayMode;
    }

    public Channel getChannel() {
	return channel;
    }

    public ChannelHandlerContext getChc() {
	return chc;
    }

}