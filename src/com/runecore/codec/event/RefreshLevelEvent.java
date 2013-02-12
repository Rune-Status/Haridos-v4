package com.runecore.codec.event;

import com.runecore.env.model.player.Player;

/**
 * RefreshLevelEvent.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 11, 2013
 */
public class RefreshLevelEvent {
    
    private final Player player;
    private final int index, level;
    private final double xp;
    
    public RefreshLevelEvent(Player player, int index, int level, double xp) {
	this.player = player;
	this.index = index;
	this.level = level;
	this.xp = xp;
    }

    public Player getPlayer() {
	return player;
    }

    public int getIndex() {
	return index;
    }

    public int getLevel() {
	return level;
    }

    public double getXp() {
	return xp;
    }

}
