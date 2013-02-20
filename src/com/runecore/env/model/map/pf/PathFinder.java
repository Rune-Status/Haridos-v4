package com.runecore.env.model.map.pf;

import java.util.List;

import com.runecore.env.world.Location;

/**
 * PathFinder.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 20, 2013
 */
public interface PathFinder {
	
    public List<Location> findPath(Location base, int srcX, int srcY, int dstX, int dstY, int z, int radius, boolean running, boolean ignoreLastStep, boolean moveNear);

}

