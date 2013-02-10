package com.runecore.codec;

import com.runecore.codec.event.SendInterfaceEvent;
import com.runecore.codec.event.SendMessageEvent;
import com.runecore.codec.event.SendWindowPaneEvent;
import com.runecore.env.model.player.Player;

/**
 * ActionSender.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 10, 2013
 */
public interface ActionSender {
    
    public void sendMessage(SendMessageEvent event);
    public void sendInterface(SendInterfaceEvent event);
    public void sendWindowPane(SendWindowPaneEvent event);
    public void sendLoginResponse(Player player);
    public void refreshGameInterfaces(Player player);

}
