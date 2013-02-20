package com.runecore.env.widget;

import com.runecore.env.model.player.Player;

/**
 * WidgetAdapter.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 17, 2013
 */
public interface WidgetAdapter {
    
    public boolean handle(Player player, int[] data);

}
