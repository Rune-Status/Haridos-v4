package com.runecore.env.model.player;

import com.runecore.codec.event.RefreshLevelEvent;
import com.runecore.env.Context;

/**
 * Skills.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 11, 2013
 */
public class Skills {
    
    /**
     * Variables for tlhe class;
     */
    private final Player player;
    public static final int MAX_SKILL_COUNT = 25;
    private final int[] level = new int[MAX_SKILL_COUNT];
    private final double[] xp = new double[MAX_SKILL_COUNT];

    /**
     * Construct the instance
     * @param player
     */
    public Skills(Player player) {
	for(int i = 0; i < MAX_SKILL_COUNT; i++) {
	    getLevel()[i] = 1;
	    getXp()[i] = 0;
	}
	getLevel()[3] = 10;
	getXp()[3] = 1250;
	this.player = player;
    }
   
    public void refresh() {
	for(int i = 0; i < MAX_SKILL_COUNT; i++) {
	    refresh(i);
	}
    }
    
    public void refresh(int i) {
	Context c = Context.get();
	c.getActionSender().refreshLevel(new RefreshLevelEvent(getPlayer(), i, level[i], xp[i]));
    }
    
    public Player getPlayer() {
	return player;
    }

    public int[] getLevel() {
	return level;
    }

    public double[] getXp() {
	return xp;
    }

}