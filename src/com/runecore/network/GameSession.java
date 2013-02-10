package com.runecore.network;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;

import com.runecore.env.model.player.Player;

/**
 * GameSession.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 10, 2013
 */
public class GameSession {
    
    /**
     * Details for the GameSession
     */
    private final Channel channel;
    private Player player;
    private int displayMode;
    private String macAddress, ipAddress;
    
    /**
     * Construct the GameSession
     * @param channel The channel the GameSession is using
     */
    public GameSession(Channel channel) {
	this.channel = channel;
	setIpAddress(channel.getRemoteAddress().toString().replaceAll("/", "").split(":")[0]);
    }
    
    /**
     * Writes a packet to the Channel
     * @param o The packet to write
     * @return The ChannelFuture for closing a channel on finish
     */
    public ChannelFuture write(Object o) {
	return channel.write(o);
    }

    public Channel getChannel() {
	return channel;
    }

    public Player getPlayer() {
	return player;
    }

    public void setPlayer(Player player) {
	this.player = player;
    }

    public int getDisplayMode() {
	return displayMode;
    }

    public void setDisplayMode(int displayMode) {
	this.displayMode = displayMode;
    }

    public String getMacAddress() {
	return macAddress;
    }

    public void setMacAddress(String macAddress) {
	this.macAddress = macAddress;
    }

    public String getIpAddress() {
	return ipAddress;
    }
    
    public void setIpAddress(String ipAddress) {
	this.ipAddress = ipAddress;
    }

}