package com.runecore.env.model.adapt;

import com.runecore.env.model.container.Container;
import com.runecore.env.model.container.ContainerAdapter;
import com.runecore.env.model.container.Inventory;
import com.runecore.env.model.player.Player;

/**
 * InventoryAdapter.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 20, 2013
 */
public class InventoryAdapter implements ContainerAdapter {
    
    private final Player player;
    
    public InventoryAdapter(Player player) {
	this.player = player;
    }

    /* (non-Javadoc)
     * @see com.runecore.env.model.container.ContainerAdapter#itemChanged(com.runecore.env.model.container.Container, int)
     */
    @Override
    public void itemChanged(Container container, int slot) {
	Inventory.refresh(player);
    }

    /* (non-Javadoc)
     * @see com.runecore.env.model.container.ContainerAdapter#itemsChanged(com.runecore.env.model.container.Container, int[])
     */
    @Override
    public void itemsChanged(Container container, int[] slots) {
	Inventory.refresh(player);
    }

    /* (non-Javadoc)
     * @see com.runecore.env.model.container.ContainerAdapter#itemsChanged(com.runecore.env.model.container.Container)
     */
    @Override
    public void itemsChanged(Container container) {
	Inventory.refresh(player);
    }

}
