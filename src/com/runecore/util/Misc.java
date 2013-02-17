package com.runecore.util;

import java.util.Random;

/**
 * Misc.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 12, 2013
 */
public class Misc {
    
    private static final Random RANDOM = new Random();
    
    public static int random() {
	return RANDOM.nextInt();
    }
    
    public static int random(int max) {
	return RANDOM.nextInt(max);
    }
    
    public static int random(int min, int max) {
	return min + (max == min ? 0 : RANDOM.nextInt(max - min));
    }
    
    /**
     * Format a player's name for display
     * @param name
     * @return
     */
    public static String capitalize(String name) {
	name = name.replaceAll("_", " ");
	name = name.toLowerCase();
	StringBuilder newName = new StringBuilder();
	boolean wasSpace = true;
	for (int i = 0; i < name.length(); i++) {
	    if (wasSpace) {
		newName.append(("" + name.charAt(i)).toUpperCase());
		wasSpace = false;
	    } else {
		newName.append(name.charAt(i));
	    }
	    if (name.charAt(i) == ' ') {
		wasSpace = true;
	    }
	}
	return newName.toString();
    }
    

}
