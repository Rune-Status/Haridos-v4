package com.runecore.env.model.flag;

/**
 * Graphic.java
 * 
 * @author Harry Andreas<harry@runecore.org> Feb 20, 2013
 */
public class Graphic {

    /**
     * Variables for the Graphic
     */
    private final int id, delay, height;

    /**
     * Creates a {@link Graphic}
     * @param args The arguments for the {@link Graphic}
     * @return A produced {@link Graphic}
     */
    public static Graphic create(int... args) {
	switch (args.length) {
	case 1:
	    return new Graphic(args[0], 0, 0);
	case 2:
	    return new Graphic(args[0], args[1], 0);
	case 3:
	    return new Graphic(args[0], args[1], args[2]);
	default:
	    return null;
	}
    }

    /**
     * Construct the {@link Graphic}
     * 
     * @param id
     *            The ID of the graphic
     * @param delay
     *            The Delay of the graphic
     * @param height
     *            The height of the graphic
     */
    private Graphic(int id, int height, int delay) {
	this.id = id;
	this.delay = delay;
	this.height = height;
    }

    public int getId() {
	return id;
    }

    public int getDelay() {
	return delay;
    }

    public int getHeight() {
	return height;
    }

}
