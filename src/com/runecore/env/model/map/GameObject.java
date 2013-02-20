package com.runecore.env.model.map;

import com.runecore.env.world.Location;

/**
 * GameObject.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 18, 2013
 */
public class GameObject {
    
    private final int identifier;
    private final Location location;
    
    public GameObject(int id, Location location) {
	this.identifier = id;
	this.location = location;
    }

    public int getIdentifier() {
	return identifier;
    }

    public Location getLocation() {
	return location;
    }
    

}
