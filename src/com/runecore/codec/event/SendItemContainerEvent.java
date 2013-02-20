package com.runecore.codec.event;

import com.runecore.env.model.container.Container;
import com.runecore.env.model.player.Player;

/**
 * SendItemContainerEvent.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 20, 2013
 */
public class SendItemContainerEvent {
    
    private final Player player;
    private final Container container;
    private final int type;
    private final boolean split;
    
    public SendItemContainerEvent(Player player, Container container, int type, boolean split) {
	this.player = player;
	this.container = container;
	this.type = type;
	this.split = split;
    }

    public Player getPlayer() {
	return player;
    }

    public Container getContainer() {
	return container;
    }

    public int getType() {
	return type;
    }

    public boolean isSplit() {
	return split;
    }

}
