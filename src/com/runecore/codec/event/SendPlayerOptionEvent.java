package com.runecore.codec.event;

import com.runecore.env.model.player.Player;

/**
 * SendPlayerOptionEvent.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 17, 2013
 */
public class SendPlayerOptionEvent {
    
    private final Player player;
    private final String option;
    private final int slot;
    private final boolean priority;
    
    public SendPlayerOptionEvent(Player player, String option, int slot, boolean priority) {
	this.player = player;
	this.option = option;
	this.priority = priority;
	this.slot = slot;
    }

    public Player getPlayer() {
	return player;
    }

    public String getOption() {
	return option;
    }

    public boolean isPriority() {
	return priority;
    }

    public int getSlot() {
	return slot;
    }

}
