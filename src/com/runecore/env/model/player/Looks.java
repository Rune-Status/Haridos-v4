package com.runecore.env.model.player;

/**
 * Looks.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 12, 2013
 */
public class Looks {
    
    private final int[] look = new int[7];
    private final int[] colour = new int[5];
    
    public Looks() {
	getLook()[0] = 3; // Hair
        getLook()[1] = 14; // Beard
        getLook()[2] = 18; // Torso
        getLook()[3] = 26; // Arms
        getLook()[4] = 34; // Bracelets
        getLook()[5] = 38; // Legs
        getLook()[6] = 42; // Shoes
        for (int i = 0; i < 5; i++) {
            getColour()[i] = 6;
        }
    }

    public int[] getLook() {
	return look;
    }

    public int[] getColour() {
	return colour;
    }

}
