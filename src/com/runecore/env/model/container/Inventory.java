package com.runecore.env.model.container;

import com.runecore.codec.event.SendItemContainerEvent;
import com.runecore.env.Context;
import com.runecore.env.model.adapt.InventoryAdapter;
import com.runecore.env.model.player.Player;

/**
 * Inventory.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 20, 2013
 */
public class Inventory {
    
    public static void init(Player player) {
	Container container = player.get("inv");
	container.addListener(new InventoryAdapter(player));
	refresh(player);
    }
    
    public static void refresh(Player player) {
	Container container = player.get("inv");
	Context c = Context.get();
	c.getActionSender().sendItemContainer(new SendItemContainerEvent(player, container, 93, false));
    }

}
