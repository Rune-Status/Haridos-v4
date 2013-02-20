package com.runecore.env.model.map.pf;

import java.util.ArrayList;
import java.util.List;

import com.runecore.env.model.Entity;
import com.runecore.env.model.map.Region;
import com.runecore.env.world.Location;

/**
 * DefaultPathFinder.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 20, 2013
 */
public class DefaultPathFinder implements PathFinder {
	
	/**
	 * 
	 */
	private Entity entity;
	
	/**
	 * 
	 * @param entity
	 */
	public DefaultPathFinder(Entity entity) {
		this.entity = entity;
	}
	
public static final int SOUTH_FLAG = 0x1, WEST_FLAG = 0x2, NORTH_FLAG = 0x4, EAST_FLAG = 0x8;

public static final int SOUTH_WEST_FLAG = SOUTH_FLAG | WEST_FLAG;
public static final int NORTH_WEST_FLAG = NORTH_FLAG | WEST_FLAG;
public static final int SOUTH_EAST_FLAG = SOUTH_FLAG | EAST_FLAG;
public static final int NORTH_EAST_FLAG = NORTH_FLAG | EAST_FLAG;

public static final int SOLID_FLAG = 0x20000;
public static final int UNKNOWN_FLAG = 0x40000000;

public static final int MAX_ATTEMPTS = 10816;


protected int[] queueX = new int[4096];
protected int[] queueY = new int[4096];
protected int[][] via = new int[104][104];
protected int[][] cost = new int[104][104];

protected int writePosition, readPosition;

public DefaultPathFinder() {
}

public void check(int x, int y, int viaDir, int thisCost) {
    queueX[writePosition] = x;
    queueY[writePosition] = y;
    via[x][y] = viaDir;
    cost[x][y] = thisCost;
    writePosition = writePosition + 1 & 0xfff;
}

	/* (non-Javadoc)
	 * @see org.dementhium.model.map.PathFinder#findPath(org.runecore.util.Location, int, int, int, int, int, int, boolean, boolean, boolean)
	 */
	@Override
	public List<Location> findPath(Location base, int srcX, int srcY, int dstX,
			int dstY, int z, int radius, boolean running,
			boolean ignoreLastStep, boolean moveNear) {
		List<Location> tiles = new ArrayList<Location>();
		 if (srcX < 0 || srcY < 0 || srcX >= 104 || srcY >= 104 || dstX < 0 || dstY < 0 || dstX >= 104 || dstY >= 104) {
	            return null;
	        }
	        if (srcX == dstX && srcY == dstY) {
	            return null;
	        }
	        int viewportDepth = 104 >> 4;
	        int xLength = entity.getDefinition().getSize();
	        int yLength = entity.getDefinition().getSize();
	        Location location = Region.getLocation(base.getRegionX() - viewportDepth << 3, ((base.getRegionY() - viewportDepth) << 3), base.getZ());
	        boolean foundPath = false;
	        for (int xx = 0; xx < 104; xx++) {
	            for (int yy = 0; yy < 104; yy++) {
	                cost[xx][yy] = 99999999;
	            }
	        }
	        int curX = srcX;
	        int curY = srcY;
	        int attempts = 0;
	        check(curX, curY, 99, 0);
	        while (writePosition != readPosition && ++attempts < MAX_ATTEMPTS) {
	            curX = queueX[readPosition];
	            curY = queueY[readPosition];
	            readPosition = readPosition + 1 & 0xfff;
	            if (curX == dstX && curY == dstY) {
	                foundPath = true;
	                break;
	            }
	            int absX = location.getX() + curX, absY = location.getY() + curY;
	            int thisCost = cost[curX][curY] + 1;
	            if (curY > 0 && via[curX][curY - 1] == 0 && (Region.getClippingMask(absX, absY - 1, z) & 0x40a40000) == 0) {
	                check(curX, curY - 1, SOUTH_FLAG, thisCost);
	            }
	            if (curX > 0 && via[curX - 1][curY] == 0 && (Region.getClippingMask(absX - 1, absY, z) & 0x42240000) == 0) {
	                check(curX - 1, curY, WEST_FLAG, thisCost);
	            }
	            if (curY < 103 && via[curX][curY + 1] == 0 && (Region.getClippingMask(absX, absY + 1, z) & 0x48240000) == 0) {
	                check(curX, curY + 1, NORTH_FLAG, thisCost);
	            }
	            if (curX < 103 && via[curX + 1][curY] == 0 && (Region.getClippingMask(absX + 1, absY, z) & 0x60240000) == 0) {
	                check(curX + 1, curY, EAST_FLAG, thisCost);
	            }
	            if (curX > 0 && curY > 0 && via[curX - 1][curY - 1] == 0 && (Region.getClippingMask(absX - 1, absY - 1, z) & 0x43a40000) == 0 && (Region.getClippingMask(absX - 1, absY, z) & 0x42240000) == 0 && (Region.getClippingMask(absX, absY - 1, z) & 0x40a40000) == 0) {
	                check(curX - 1, curY - 1, SOUTH_WEST_FLAG, thisCost);
	            }
	            if (curX > 0 && curY < 103 && via[curX - 1][curY + 1] == 0 && (Region.getClippingMask(absX - 1, absY + 1, z) & 0x4e240000) == 0 && (Region.getClippingMask(absX - 1, absY, z) & 0x42240000) == 0 && (Region.getClippingMask(absX, absY + 1, z) & 0x48240000) == 0) {
	                check(curX - 1, curY + 1, NORTH_WEST_FLAG, thisCost);
	            }
	            if (curX < 103 && curY > 0 && via[curX + 1][curY - 1] == 0 && (Region.getClippingMask(absX + 1, absY - 1, z) & 0x60e40000) == 0 && (Region.getClippingMask(absX + 1, absY, z) & 0x60240000) == 0 && (Region.getClippingMask(absX, absY - 1, z) & 0x40a40000) == 0) {
	                check(curX + 1, curY - 1, SOUTH_EAST_FLAG, thisCost);
	            }
	            if (curX < 103 && curY < 103 && via[curX + 1][curY + 1] == 0 && (Region.getClippingMask(absX + 1, absY + 1, z) & 0x78240000) == 0 && (Region.getClippingMask(absX + 1, absY, z) & 0x60240000) == 0 && (Region.getClippingMask(absX, absY + 1, z) & 0x48240000) == 0) {
	                check(curX + 1, curY + 1, NORTH_EAST_FLAG, thisCost);
	            }
	        }
	        if (!foundPath) {
	            if (moveNear) {
	                int fullCost = 1000;
	                int thisCost = 100;
	                int depth = 10;
	                for (int x = dstX - depth; x <= dstX + depth; x++) {
	                    for (int y = dstY - depth; y <= dstY + depth; y++) {
	                        if (x >= 0 && y >= 0 && x < 104 && y < 104 && cost[x][y] < 100) {
	                            int diffX = 0;
	                            if (x < dstX)
	                                diffX = dstX - x;
	                            else if (x > dstX + xLength - 1)
	                                diffX = x - (dstX + xLength - 1);
	                            int diffY = 0;
	                            if (y < dstY)
	                                diffY = dstY - y;
	                            else if (y > dstY + yLength - 1)
	                                diffY = y - (dstY + yLength - 1);
	                            int totalCost = diffX * diffX + diffY * diffY;
	                            if (totalCost < fullCost || (totalCost == fullCost && (cost[x][y] < thisCost))) {
	                                fullCost = totalCost;
	                                thisCost = cost[x][y];
	                                curX = x;
	                                curY = y;
	                            }
	                        }
	                    }
	                }
	                int costValue = entity.getAttribute("costValue", 1);
	                if (fullCost > costValue) {
	                    if (costValue > 1) {
	                        entity.removeAttribute("costValue");
	                    }
	                    return null;
	                }
	                if (fullCost == 1000)
	                    return null;
	            }
	        }
	        readPosition = 0;
	        queueX[readPosition] = curX;
	        queueY[readPosition++] = curY;
	        int l5;
	        attempts = 0;
	        for (int j5 = l5 = via[curX][curY]; curX != srcX || curY != srcY; j5 = via[curX][curY]) {
	            if (attempts++ > queueX.length) {
	                return null;
	            }
	            if (j5 != l5) {
	                l5 = j5;
	                queueX[readPosition] = curX;
	                queueY[readPosition++] = curY;
	            }
	            if ((j5 & WEST_FLAG) != 0) {
	                curX++;
	            } else if ((j5 & EAST_FLAG) != 0) {
	                curX--;
	            }
	            if ((j5 & SOUTH_FLAG) != 0) {
	                curY++;
	            } else if ((j5 & NORTH_FLAG) != 0) {
	                curY--;
	            }
	        }
	        int size = readPosition--;
	        int absX = location.getX() + queueX[readPosition];
	        int absY = location.getY() + queueY[readPosition];
	        tiles.add(Location.locate(absX, absY, z));
	        for (int i = 1; i < size; i++) {
	            readPosition--;
	            absX = location.getX() + queueX[readPosition];
	            absY = location.getY() + queueY[readPosition];
	            tiles.add(Location.locate(absX, absY, z));
	        }
	        return tiles;
	}

}
