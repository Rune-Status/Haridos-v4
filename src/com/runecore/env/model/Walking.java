package com.runecore.env.model;

import com.runecore.codec.event.SendSettingEvent;
import com.runecore.codec.event.SendSettingEvent.SettingType;
import com.runecore.env.Context;
import com.runecore.env.model.map.Directions;
import com.runecore.env.model.map.Directions.NormalDirection;
import com.runecore.env.model.player.Player;
import com.runecore.env.world.Location;
import com.runecore.util.Misc;

/**
 * Walking.java
 * 
 * @author Harry Andreas<harry@runecore.org> Feb 12, 2013
 */
public class Walking {

    private Location lastLocation;

    private static class Point {

	private int x;
	private int y;
	private NormalDirection direction;

	private int diffX, diffY;
    }

    public static final int SIZE = 100;

    public int readPosition = 0;
    public int writePosition = 0;
    public Point[] walkingQueue = new Point[SIZE];

    private int runEnergy = 100;
    private int walkDir = -1;
    private int runDir = -1;
    private Entity mob;
    private boolean didTele;

    private boolean isRunning = false, isRunToggled = false;

    private NormalDirection lastWalkedDirection = NormalDirection.NORTH;

    public boolean isRunToggled() {
	return isRunToggled;
    }

    public boolean isMoving() {
	return hasWalkingDirection() || walkDir != -1 || runDir != -1;
    }

    public boolean hasDirection() {
	return walkDir != -1 || runDir != -1;
    }

    public boolean isWalkingMoving() {
	return (hasWalkingDirection() && !isRunning) || walkDir != -1;
    }

    public boolean isRunningMoving() {
	return (hasWalkingDirection() && isRunning) || runDir != -1;
    }

    public boolean isRunningBoth() {
	return isRunning || isRunToggled;
    }

    private boolean hasWalkingDirection() {
	return readPosition != writePosition;
    }

    public void setRunToggled(boolean isRunToggled) {
	this.isRunToggled = isRunToggled;
    }

    public Walking(Entity mob) {
	this.mob = mob;
	this.walkDir = -1;
	this.runDir = -1;
	for (int i = 0; i < SIZE; i++) {
	    walkingQueue[i] = new Point();
	    walkingQueue[i].x = 0;
	    walkingQueue[i].y = 0;
	    walkingQueue[i].direction = null;
	}
    }

    public void setIsRunning(boolean isRunning) {
	this.isRunning = isRunning;
    }

    public void reset() {
	// mob.getMask().setInteractingEntity(null);
	walkingQueue[0].x = mob.getLocation().getLocalX();
	walkingQueue[0].y = mob.getLocation().getLocalY();
	walkingQueue[0].direction = null;
	readPosition = writePosition = 1;
    }

    public void addClippedWalkingQueue(int x, int y) {
	int diffX = x - walkingQueue[writePosition - 1].x, diffY = y
		- walkingQueue[writePosition - 1].y;
	int max = Math.max(Math.abs(diffX), Math.abs(diffY));
	int size = mob.getDefinition().getSize();
	for (int i = 0; i < max; i++) {
	    if (diffX < 0)
		diffX++;
	    else if (diffX > 0)
		diffX--;
	    if (diffY < 0)
		diffY++;
	    else if (diffY > 0)
		diffY--;
	    java.awt.Point step = null;//PrimitivePathFinder.getNextStep(mob.getLocation(), x - diffX, y - diffY, mob.getLocation().getZ(), size, size);
	   // if (step != null) {
		addStepToWalkingQueue(step.x, step.y);
	   // }
	}
    }

    public void addToWalkingQueue(int x, int y) {
	int diffX = x - walkingQueue[writePosition - 1].x, diffY = y
		- walkingQueue[writePosition - 1].y;
	int max = Math.max(Math.abs(diffX), Math.abs(diffY));
	for (int i = 0; i < max; i++) {
	    if (diffX < 0)
		diffX++;
	    else if (diffX > 0)
		diffX--;
	    if (diffY < 0)
		diffY++;
	    else if (diffY > 0)
		diffY--;
	    addStepToWalkingQueue(x - diffX, y - diffY);
	}
    }

    public void addStepToWalkingQueue(int x, int y) {
	int diffX = x - walkingQueue[writePosition - 1].x, diffY = y
		- walkingQueue[writePosition - 1].y;
	NormalDirection direction = Directions.directionFor(diffX, diffY);
	if (direction != null) {
	    if (writePosition >= SIZE) {
		return;
	    }
	    walkingQueue[writePosition].x = x;
	    walkingQueue[writePosition].y = y;
	    walkingQueue[writePosition].diffX = diffX;
	    walkingQueue[writePosition].diffY = diffY;
	    walkingQueue[writePosition++].direction = direction;
	}
    }

    public void getNextEntityMovement() {
	boolean isPlayer = mob.isPlayer();
	if (isPlayer) {
	    this.didTele = mob.player().getFlagManager().teleportUpdate();
	}
	this.walkDir = -1;
	this.runDir = -1;
	Point walkPoint = nextPoint();
	Point runPoint = null;
	if (walkPoint == null) {
	    return;
	}
	if (walkPoint.direction == null) {
	    walkPoint = nextPoint();
	}
	int walkDir = -1;
	int runDir = -1;
	if (runEnergy == 0 && (isRunning || isRunToggled)) {
	    isRunning = false;
	    isRunToggled = false;
	    Context.get().getActionSender().sendSetting(new SendSettingEvent(mob.player(), 173, isRunToggled ? 1 : 0, SettingType.NORMAL));
	}
	if (isRunning || isRunToggled) {
	    runPoint = nextPoint();
	}
	if (walkPoint != null && walkPoint.direction != null) {
	    walkDir = mob.isPlayer() ? walkPoint.direction.intValue()
		    : walkPoint.direction.npcIntValue();
	}
	if (isPlayer) {
	    if (differentMap(mob.player())) {
		if (walkPoint != null) {
		    readPosition--;
		}
		if (runPoint != null) {
		    readPosition--;
		}
		return;
	    }
	}
	int diffX = 0;
	int diffY = 0;
	if (walkPoint != null) {
	    diffX = walkPoint.diffX;
	    diffY = walkPoint.diffY;
	    if (diffX != 0 || diffY != 0) {
		lastLocation = mob.getLocation();
		mob.setLocation(mob.getLocation().transform(diffX, diffY, 0));
		lastWalkedDirection = direction(lastLocation, mob.getLocation());
		// AreaUpdate.run(mob);
	    }
	}
	if (runPoint != null) {
	    int nextXDiff = runPoint.diffX;
	    int nextYDiff = runPoint.diffY;
	   Directions.RunningDirection direction = Directions
		    .runningDirectionFor(nextXDiff + diffX, nextYDiff + diffY);
	    if (direction != null) {
		runDir = direction.intValue();
	    }
	    if (runDir != -1) {
		walkDir = -1;
		diffX += nextXDiff;
		diffY += nextYDiff;
		if (nextXDiff != 0 || nextYDiff != 0) {
		    lastLocation = mob.getLocation();
		    mob.setLocation(mob.getLocation().transform(nextXDiff,
			    nextYDiff, 0));
		    lastWalkedDirection = direction(lastLocation,
			    mob.getLocation());
		    // AreaUpdate.run(mob);
		}
		if (runEnergy > 0) {
		    Context.get().getActionSender().sendSetting(new SendSettingEvent(mob.player(), 173, isRunToggled ? 1 : 0, SettingType.NORMAL));
		  //  mob.asPlayer().getFrames().sendRunEnergy();
		}
	    } else {
		readPosition--;
	    }
	}
	this.walkDir = walkDir;
	this.runDir = runDir;
    }

    private Point nextPoint() {
	if (readPosition == writePosition) {
	    return null;
	}
	return walkingQueue[readPosition++];
    }

    public boolean isRunning() {
	return isRunning || isRunToggled;
    }

    public int getWalkDir() {
	return walkDir;
    }

    public int getRunDir() {
	return runDir;
    }

    /**
     * Increases the current amount of run energy, and updates it.
     * 
     * @param runEnergy
     *            The amount.
     */
    public void increaseRunEnergy(double runEnergy) {
	if (this.runEnergy > 99) {
	    return;
	}
	this.runEnergy += runEnergy;
	if (this.runEnergy > 99) {
	    this.runEnergy = 100;
	}
	if (mob.isPlayer()) {
	   // mob.player().getFrames().sendRunEnergy();
	}
    }

    /**
     * Decreases the current amount of run energy, and updates it.
     * 
     * @param runEnergy
     *            The amount.
     */
    public void decreaseRunEnergy(double runEnergy) {
	this.runEnergy -= runEnergy;
	if (this.runEnergy < 1) {
	    this.runEnergy = 0;
	    setRunToggled(false);
	}
	if (mob.isPlayer()) {
	    //mob.asPlayer().getFrames().sendRunEnergy();
	}
    }

    public void setRunEnergy(int energy) {
	this.runEnergy = energy;
    }

    public int getRunEnergy() {
	return runEnergy;
    }

    public void setDidTele(boolean didTele) {
	this.didTele = didTele;
    }

    public boolean isDidTele() {
	return didTele;
    }

    public Location getLastLocation() {
	if (lastLocation == null) {
	    lastLocation = mob.getLocation().transform(Misc.random(-1, 1), Misc.random(-1, 1), 0);
	}
	return lastLocation;
    }

    private boolean differentMap(Player player) {
	Location oldLocation = player.getFlagManager().lastKnownRegion();
	int diffX = Math.abs(oldLocation.getRegionX()
		- mob.getLocation().getRegionX()), diffY = Math.abs(oldLocation
		.getRegionY() - mob.getLocation().getRegionY());
	if (diffX >= 4 || diffY >= 4) {
	    player.getFlagManager().setMapRegionChanged(true);
	}
	if (player.getFlagManager().isMapRegionChanged()) {
	    walkDir = -1;
	    runDir = -1;
	    return true;
	}
	return false;
    }

    public void removeLast() {
	if (writePosition > 1) {
	    writePosition--;
	}
    }

    public NormalDirection direction(Location last, Location current) {
	NormalDirection direction = Directions.directionFor(last, current);
	if (direction == null) {
	    return lastWalkedDirection;
	}
	return direction;
    }

    public NormalDirection getLastWalkedDirection() {
	if (lastWalkedDirection == null) {
	    lastWalkedDirection = NormalDirection.NORTH;
	}
	return lastWalkedDirection;
    }

    public void setLastWalkedDirection(NormalDirection direction) {
	this.lastWalkedDirection = direction;
    }

}
