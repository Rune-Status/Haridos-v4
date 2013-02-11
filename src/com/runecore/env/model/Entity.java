package com.runecore.env.model;

import com.runecore.env.model.def.EntityDefinition;
import com.runecore.env.world.Location;

/**
 * Entity.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 10, 2013
 */
public abstract class Entity {
    
    private Location location = Location.locate(3200, 3200, 0);
    
    public abstract EntityDefinition getDefinition();
    
    public Location getLocation() {
	return location;
    }
    
    public void setLocation(Location loc) {
	this.location = loc;
    }

}
