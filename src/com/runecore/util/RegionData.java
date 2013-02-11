package com.runecore.util;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * RegionData.java
 * 
 * @author Harry Andreas<harry@runecore.org> Feb 10, 2013
 */
public class RegionData {
    
    /**
     * Mapdata map
     */
    private final static Map<Short, int[]> mapData = new HashMap<Short, int[]>();

    /**
     * Loads region keys from file
     * @throws Exception
     */
    public static void init() throws Exception {
	final DataInputStream in = new DataInputStream(new FileInputStream("./data/regions.dat"));
	while (in.available() != 0) {
	    final short area = in.readShort();
	    final int[] parts = new int[4];
	    for (int j = 0; j < 4; j++) {
		parts[j] = in.readInt();
	    }
	    mapData.put(area, parts);
	}
	in.close();
    }
    
    public static int[] get(Short s) {
	return mapData.get(s);
    }

}