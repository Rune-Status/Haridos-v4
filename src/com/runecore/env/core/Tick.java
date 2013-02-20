package com.runecore.env.core;

/**
 * Tick.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 20, 2013
 */
public abstract class Tick {

    private int cycles;

    private int attempts;
    private boolean running = true;

    public Tick(int cycles) {
        this.cycles = cycles;
        this.attempts = 0;
    }

    public boolean run() {
        if (!running) {
            return false;
        }
        attempts++;
        if (attempts >= cycles) {
            attempts = 0;
            try {
                execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return running;
    }

    public boolean isRunning() {
        return running;
    }

    public void setTime(int cycles) {
        this.cycles = cycles;
    }

    public int getTime() {
        return cycles;
    }

    public void stop() {
        running = false;
    }

    public void start() {
        running = true;
    }

    public abstract void execute();

}