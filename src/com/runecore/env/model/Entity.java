package com.runecore.env.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.runecore.env.core.Tick;
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
    
    private Location location = Location.locate(2662, 3305, 0);
    private final FlagManager flagManager = new FlagManager(this);
    private final Walking walking = new Walking(this);
    private Map<String, Tick> ticks = new HashMap<String, Tick>();
    private final Map<String, Object> attributes = new HashMap<String, Object>();
    private int index;
    
    public abstract EntityDefinition getDefinition();
    public abstract void tick();
    
    public void addPoint(int x, int y) {
	int firstX = x - (getLocation().getRegionX() - 6) * 8;
	int firstY = y - (getLocation().getRegionY() - 6) * 8;
	getWalking().addToWalkingQueue(firstX, firstY);
    }
    
    public void addAttribute(String var, Object value) {
	attributes.put(var, value);
    }
    
    @SuppressWarnings("unchecked")
    public <T> T getAttribute(String string, T fail) {
	T object = (T) attributes.get(string);
	if (object != null) {
	    return object;
	}
	return fail;
    }

    public void removeAttribute(String string) {
	attributes.remove(string);
    }

    public void register(String identifier, Tick tick, boolean replace) {
	if (ticks.containsKey(identifier) && !replace) {
	    return;
	}
	ticks.put(identifier, tick);
    }

    public void register(String identifier, Tick tick) {
	register(identifier, tick, false);
    }

    public void remove(String identifier) {
	Tick tick = ticks.get(identifier);
	if (tick != null) {
	    tick.stop();
	    ticks.remove(identifier);
	}
    }

    public Tick retrieve(String string) {
	return ticks.get(string);
    }

    public boolean has(String string) {
	return ticks.containsKey(string);
    }

    public void processTicks() {
	if (ticks.isEmpty()) {
	    return;
	}
	Map<String, Tick> ticks = new HashMap<String, Tick>(this.ticks);
	Iterator<Map.Entry<String, Tick>> it = ticks.entrySet().iterator();
	while (it.hasNext()) {
	    Map.Entry<String, Tick> entry = it.next();
	    if (!entry.getValue().run()) {
		this.ticks.remove(entry.getKey());
	    }
	}
    }
    
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
