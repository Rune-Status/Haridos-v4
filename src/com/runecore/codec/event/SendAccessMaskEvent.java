package com.runecore.codec.event;

import com.runecore.env.model.player.Player;

/**
 * SendAccessMaskEvent.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 11, 2013
 */
public class SendAccessMaskEvent {
    
    private final Player player;
    private final int set, set2, inter, inter2, child, child2;
    
    public SendAccessMaskEvent(Player player, int set, int set2, int inter, int child, int inter2, int child2)  {
	this.player = player;
	this.set = set;
	this.set2 = set2;
	this.inter = inter;
	this.child = child;
	this.inter2 = inter2;
	this.child2 = child2;
    }

    public Player getPlayer() {
	return player;
    }

    public int getSet() {
	return set;
    }

    public int getSet2() {
	return set2;
    }

    public int getInter() {
	return inter;
    }

    public int getInter2() {
	return inter2;
    }

    public int getChild() {
	return child;
    }

    public int getChild2() {
	return child2;
    }

}
