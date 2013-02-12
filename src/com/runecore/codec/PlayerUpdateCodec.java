package com.runecore.codec;

import com.runecore.env.model.player.Player;

/**
 * PlayerUpdateCodec.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 11, 2013
 */
public interface PlayerUpdateCodec {
    
    public void updatePlayer(Player player);

}
