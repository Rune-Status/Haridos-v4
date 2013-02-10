package com.runecore.codec.event;

import com.runecore.env.model.player.Player;

/**
 * SendInterfaceEvent.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 10, 2013
 */
public class SendInterfaceEvent {
    
    private final Player player;
    private final int showId, windowId, interfaceId, childId;
    
    public SendInterfaceEvent(Player player, int showId, int windowId, int interfaceId, int childId) {
	this.player = player;
	this.showId = showId;
	this.windowId = windowId;
	this.interfaceId = interfaceId;
	this.childId = childId;
    }

    public Player getPlayer() {
	return player;
    }

    public int getShowId() {
	return showId;
    }

    public int getWindowId() {
	return windowId;
    }

    public int getInterfaceId() {
	return interfaceId;
    }

    public int getChildId() {
	return childId;
    }

}