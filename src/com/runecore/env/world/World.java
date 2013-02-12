package com.runecore.env.world;

import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.runecore.env.Context;
import com.runecore.env.model.EntityList;
import com.runecore.env.model.player.Player;

/**
 * World.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 11, 2013
 */
public class World implements Runnable {
    
    /**
     * World Instance
     */
    private static final World INSTANCE = new World();
    
    /**
     * EntityList of Players
     */
    private final EntityList<Player> players;
    
    /**
     * Construct the world
     */
    private World() {
	players = new EntityList<Player>(2000);
	Executors.newScheduledThreadPool(1).scheduleAtFixedRate(this, 600, 600, TimeUnit.MILLISECONDS);
    }
    
    @Override
    public void run() {
	handleNetwork();
	for(Player p : getPlayers()) {
	    p.tick();
	}
	for(Player p : getPlayers()) {
	    Context.get().getPlayerUpdateCodec().updatePlayer(p);
	}
	for(Player p : getPlayers()) {
	    p.getFlagManager().reset();
	}
    }
    
    /**
     * Handles networking events, packet execution, disconnections etc
     */
    private void handleNetwork() {
	Iterator<Player> players = getPlayers().iterator();
	while(players.hasNext()) {
	    Player player = players.next();
	    if(player == null) {
		players.remove();
		continue;
	    }
	    if(!player.getSession().getChannel().isConnected()) {
		players.remove();
		continue;
	    }
	}
    }
    
    /**
     * Registers a Player
     * @param player The player to register
     * @return If it was successful
     */
    public boolean register(Player player) {
	return players.add(player);
    }
    
    /**
     * Gets the EntityList of Players
     * @return
     */
    public EntityList<Player> getPlayers() {
	return players;
    }
 
    /**
     * Gets the singleton instance of the World
     * @return
     */
    public static World get() {
	return INSTANCE;
    }

}