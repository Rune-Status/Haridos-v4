package com.runecore.codec;

import com.runecore.codec.event.*;
import com.runecore.env.model.player.Player;

/**
 * ActionSender.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 10, 2013
 */
public interface ActionSender {
    
    public void sendLogin(Player player);
    public void sendSetting(SendSettingEvent event);
    public void sendMessage(SendMessageEvent event);
    public void sendAccessMask(SendAccessMaskEvent event);
    public void refreshLevel(RefreshLevelEvent event);
    public void sendInterface(SendInterfaceEvent event);
    public void sendWindowPane(SendWindowPaneEvent event);
    public void sendLoginResponse(Player player);
    public void sendMapRegion(Player player, boolean login);
    public void refreshGameInterfaces(Player player);
    public void refreshAccessMasks(Player player);
    public void sendPlayerOption(SendPlayerOptionEvent event);

}
