package com.runecore.env.model.map.pf;

import java.util.List;

import com.runecore.env.model.Entity;
import com.runecore.env.world.Location;

/**
 * PathFinderExecutor.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 20, 2013
 */
public class PathFinderExecutor {
    
    public static boolean walkTo(Entity e, Location destination) {
	DefaultPathFinder finder = new DefaultPathFinder(e);
	Location base = e.getLocation();
	int srcX = base.getLocalX();
	int srcY = base.getLocalY();
	int destX = destination.getLocalX(base);
	int destY = destination.getLocalY(base);
	List<Location> path = finder.findPath(e.getLocation(), srcX, srcY, destX, destY, base.getZ(), 0, e.getWalking().isRunning(), false, true);
	if(path == null)
	    return false;
	if(path.isEmpty())
	    return false;
	e.getWalking().reset();
	for (Location step : path) {
	    e.addPoint(step.getX(), step.getY());
	}
	return true;
	
    }

}
