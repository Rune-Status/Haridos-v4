package com.runecore.env.widget;

import java.util.LinkedList;
import java.util.List;

import com.runecore.env.model.player.Player;

/**
 * WidgetAdapterRepository.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 17, 2013
 */
public class WidgetAdapterRepository {
    
    private List<WidgetAdapter> adapters = new LinkedList<WidgetAdapter>();
    
    public void handle(Player player, int[] data) {
	for(WidgetAdapter adapter : adapters) {
	    if(adapter.handle(player, data)) {
		return;
	    }
	}
    }
    
    public void register(WidgetAdapter adapter) {
	adapters.add(adapter);
    }

}
