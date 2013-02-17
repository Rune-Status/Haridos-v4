package com.runecore.env.model;

import com.runecore.env.model.def.EntityDefinition;
import com.runecore.env.model.flag.FlagManager;
import com.runecore.env.model.player.Player;
import com.runecore.env.world.Location;

/**
 * Entity.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 10, 2013
 */
public abstract class Entity {
    
    private Location location = Location.locate(3200, 3200, 0);
    private final FlagManager flagManager = new FlagManager(this);
    private final Walking walking = new Walking(this);
    private int index;
    
    public abstract EntityDefinition getDefinition();
    public abstract void tick();
    
    public boolean isPlayer() {
	return (this instanceof Player);
    }
    
    public Player player() {
	return (Player)this;
    }
    
    public Location getLocation() {
	return location;
    }
    
    public void setLocation(Location loc) {
	this.location = loc;
    }

    public int getIndex() {
	return index;
    }

    public void setIndex(int index) {
	this.index = index;
    }

    public FlagManager getFlagManager() {
	return flagManager;
    }
    public Walking getWalking() {
	return walking;
    }

}
