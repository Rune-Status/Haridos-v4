package com.runecore.env.model.container;

import com.runecore.codec.event.SendItemContainerEvent;
import com.runecore.env.Context;
import com.runecore.env.model.adapt.EquipmentAdapter;
import com.runecore.env.model.player.Player;

/**
 * Equipment.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 20, 2013
 */
public class Equipment {
    
    public static void init(Player player) {
	Container container = player.get("equip");
	container.addListener(new EquipmentAdapter(player));
	refresh(player);
    }
    
    public static void refresh(Player player) {
	Container container = player.get("equip");
	Context c = Context.get();
	c.getActionSender().sendItemContainer(new SendItemContainerEvent(player, container, 94, false));
    }

}
