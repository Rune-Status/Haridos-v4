package com.runecore.env.world;

/**
 * Location.java
 * @author Harry Andreas<harry@runecore.org>
 *  Feb 10, 2013
 */
public class Location {

    public static double distanceFormula(int x, int y, int x2, int y2) {
	return Math.sqrt(Math.pow(x2 - x, 2) + Math.pow(y2 - y, 2));
    }

    public static double distanceFormula(double x, double y, double x2,
	    double y2) {
	return Math.sqrt(Math.pow(x2 - x, 2) + Math.pow(y2 - y, 2));
    }

    public static Location locate(int x, int y, int z) {
	return new Location(x, y, z);
    }

    public boolean inArea(int a, int b, int c, int d) {
	return x >= a && y >= b && x <= c && y <= d;
    }

    private final int x, y, z;

    public Location(int x, int y, int z) {
	this.x = x;
	this.y = y;
	this.z = z;
    }

    public boolean differentMap(Location other) {
	return distanceFormula(getRegionX(), getRegionY(), other.getRegionX(),
		other.getRegionY()) >= 4;
    }

    public double distance(Location other) {
	if (z != other.z) {
	    return Double.MAX_VALUE - 1;
	}
	return distanceFormula(x, y, other.x, other.y);
    }

    public int getWildernessLevel() {
	if (y > 3520 && y < 4000 && x < 3383 && x > 2945) {
	    return (((int) (Math.ceil((y) - 3520D) / 8D) + 1));
	}
	return 0;
    }

    public boolean withinDistance(Location other, int dist) {
	if (other.z != z) {
	    return false;
	}
	return distance(other) <= dist;
    }

    public boolean withinDistance(Location other) {
	if (other.z != z) {
	    return false;
	}
	int deltaX = other.x - x, deltaY = other.y - y;
	return deltaX <= 16 && deltaX >= -15 && deltaY <= 16 && deltaY >= -15;
    }

    @Override
    public boolean equals(Object object) {
	if (object instanceof Location) {
	    Location other = (Location) object;
	    return x == other.x && y == other.y && other.z == z;
	}
	return false;
    }

    public int getLocalX() {
	return getLocalX(this);
    }

    public int getLocalX(Location base) {
	return x - ((base.getRegionX() - 6) << 3);
    }

    public int getLocalY() {
	return getLocalY(this);
    }

    public int getLocalY(Location base) { // enjoy
	return y - ((base.getRegionY() - 6) << 3);
    }

    public int getRegionX() {
	return x >> 3;
    }

    public int getRegionY() {
	return y >> 3;
    }

    public int getX() {
	return x;
    }

    public int getY() {
	return y;
    }

    public int getZ() {
	return z;
    }

    @Override
    public int hashCode() {
	return z << 30 | x << 15 | y;
    }

    @Override
    public String toString() {
	return x + "," + y + "," + z;
    }

    public Location transform(int diffX, int diffY, int diffZ) {
	return Location.locate(x + diffX, y + diffY, z + diffZ);
    }

    public boolean withinRange(Location t) {
	return withinRange(t, 15);
    }

    public boolean withinRange(Location t, int distance) {
	return t.z == z && distance(t) <= distance;
    }

    public int getDistance(Location pos) {
	return (int) distance(pos);
    }

    public int get18BitsHash() {
	int regionId = ((getRegionX() / 8) << 8) + (getRegionY() / 8);
	return (((regionId & 0xff) << 6) >> 6) | (getZ() << 16)
		| ((((regionId >> 8) << 6) >> 6) << 8);
    }

    public int get30BitsHash() {
	return y | z << 28 | x << 14;
    }

    public int getHash() {
	return x << 14 | y & 0x3fff | z << 28;
    }

    public int distance(int x2, int y2) {
	return (int) distanceFormula(x, y, x2, y2);
    }

    public int getRegionId() {
	return (((getRegionX() / 8) << 8) + (getRegionY() / 8));
    }


    public static Location getDelta(Location l, Location o) {
	return new Location(o.x - l.x, o.y - l.y, o.z - l.z);
    }

    public boolean withinDistance2(Location other, int dist) {
	return distance2(other) <= dist;
    }

    public double distance2(Location other) {
	return distanceFormula(x, y, other.x, other.y);
    }

    public static int wildernessLevel(Location l) {
	int y = l.getY();
	if (y > 3520 && y < 4000) {
	    return (((int) (Math.ceil((double) (y) - 3520D) / 8D) + 1));
	}
	return 0;
    }

    public boolean isNextTo(Location other) {
	if (z != other.z) {
	    return false;
	}
	return (x == other.x && y != other.y || x != other.x && y == other.y || x == other.x
		&& y == other.y);
    }

}
