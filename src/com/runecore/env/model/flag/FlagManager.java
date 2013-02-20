package com.runecore.env.model.flag;

import java.util.BitSet;

import com.runecore.env.model.Entity;
import com.runecore.env.world.Location;

/**
 * FlagManager.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 12, 2013
 */
public class FlagManager {
    
    /**
     * Construct the FlagManager
     * @param p
     */
    public FlagManager(Entity p) {
	this.player = p;
    }
    
    /**
     * A BitSet of Flagged UpdateFlags
     */
    private final Entity player;
    private final BitSet bitSet = new BitSet();
    private Animation animation;
    private Graphic graphic;
    private boolean mapRegionChanged = false;
    private Location teleportLocation;
    private Location lastKnownRegion;
    
    /**
     * Is the UpdateFlag flagged
     * @param flag The UpdateFlag to check
     * @return
     */
    public boolean flagged(UpdateFlag flag) {
	return bitSet.get(flag.ordinal());
    }
    
    /**
     * Is an update needed? 
     * @return If an update is needed
     */
    public boolean updateNeeded() {
	return !bitSet.isEmpty() || teleportUpdate() || player.getWalking().getWalkDir() != -1 || player.getWalking().getRunDir() != -1;
    }
    
    /**
     * Flag an update flag
     * @param flag The UpdateFlag to flag
     */
    public void flag(UpdateFlag flag) {
	bitSet.flip(flag.ordinal());
    }
    
    /**
     * Sets the cache
     */
    public void reset() {
	bitSet.clear();
	setAnimation(null);
	setGraphic(null);
	setMapRegionChanged(false);
    }
    
    public Location teleportLocation() {
	return teleportLocation;
    }
    
    public boolean teleportUpdate() {
	return teleportLocation != null;
    }

    public boolean isMapRegionChanged() {
	return mapRegionChanged;
    }

    public void setMapRegionChanged(boolean mapRegionChanged) {
	this.mapRegionChanged = mapRegionChanged;
    }

    public Location lastKnownRegion() {
	return lastKnownRegion;
    }

    public void setLastKnownRegion(Location lastKnownRegion) {
	this.lastKnownRegion = lastKnownRegion;
    }

    public Entity entity() {
	return player;
    }

    public Animation getAnimation() {
	return animation;
    }

    public void setAnimation(Animation animation) {
	this.animation = animation;
    }

    public Graphic getGraphic() {
	return graphic;
    }

    public void setGraphic(Graphic graphic) {
	this.graphic = graphic;
    }

}
