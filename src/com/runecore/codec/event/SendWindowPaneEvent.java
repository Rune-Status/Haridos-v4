package com.runecore.codec.event;

import com.runecore.env.model.player.Player;

/**
 * SendWindowPaneEvent.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 10, 2013
 */
public class SendWindowPaneEvent {
    
    private final Player player;
    private final int pane, subPane;
    
    public SendWindowPaneEvent(Player player, int pane, int subPane) {
	this.player = player;
	this.pane = pane;
	this.subPane = subPane;
    }

    public Player getPlayer() {
	return player;
    }

    public int getPane() {
	return pane;
    }

    public int getSubPane() {
	return subPane;
    }

}
