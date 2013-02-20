package com.runecore.env.model.map.pf;

/**
 * Position.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 20, 2013
 */
public class Position {

    public static Position create(int x, int y, int z) {
        return new Position(x, y, z);
    }

    private int x;
    private int y;
    private int z;

    private Position(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
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
    public boolean equals(Object other) {
        if (other instanceof Position) {
            Position p = (Position) other;
            return p.x == x && p.y == y && p.z == z;
        }
        return false;
    }
}
