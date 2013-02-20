package com.runecore.env.model.item;

import com.runecore.env.model.def.ItemDefinition;

/**
 * Item.java
 * 
 * @author Harry Andreas<harry@runecore.org> Feb 12, 2013
 */
public class Item {

    /**
     * Variables for the server
     */
    private int itemId, itemAmount;

    /**
     * 
     * @param id
     */
    public Item(int id) {
	setId(id);
	setAmount(1);
    }

    /**
     * Constructor
     * 
     * @param itemId
     * @param amount
     */
    public Item(int itemId, int amount) {
	setId(itemId);
	setAmount(amount);
    }

    /**
     * @param itemId
     *            the itemId to set
     */
    public void setId(int itemId) {
	this.itemId = itemId;
    }

    /**
     * @return the itemId
     */
    public int getId() {
	return itemId;
    }

    /**
     * @param itemAmount
     *            the itemAmount to set
     */
    public void setAmount(int itemAmount) {
	this.itemAmount = itemAmount;
    }

    /**
     * @return the itemAmount
     */
    public int getAmount() {
	return itemAmount;
    }
    
    public ItemDefinition getDefinition() {
	return ItemDefinition.forId(getId());
    }

}