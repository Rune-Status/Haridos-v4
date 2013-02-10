package com.runecore.codec.event;

import com.runecore.env.model.player.Player;

/**
 * SendMessageEvent.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 10, 2013
 */
public class SendMessageEvent {
    
    private final String message;
    private final int type;
    private final Player player;

    public SendMessageEvent(Player player, String message) {
	this.message = message;
	this.type = 0;
	this.player = player;
    }
    
    public SendMessageEvent(Player player, String message, int type) {
	this.message = message;
	this.type = type;
	this.player = player;
    }
    
    public Player getPlayer() {
	return player;
    }

    public String getMessage() {
	return message;
    }

    public int getType() {
	return type;
    }    

}