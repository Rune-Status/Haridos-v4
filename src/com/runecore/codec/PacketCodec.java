package com.runecore.codec;

import com.runecore.env.model.player.Player;
import com.runecore.network.io.Message;

/**
 * PacketCodec.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 17, 2013
 */
public interface PacketCodec {
    
    public void execute(Player player, Message message);

}
