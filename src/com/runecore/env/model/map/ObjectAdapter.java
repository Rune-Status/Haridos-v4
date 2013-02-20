package com.runecore.env.model.map;

import com.runecore.env.model.player.Player;

/**
 * ObjectAdapter.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 18, 2013
 */
public interface ObjectAdapter {
    
    public boolean accept(Player player, GameObject object, ObjectOption option);
    public void handle(Player player, GameObject object, ObjectOption option);

}
