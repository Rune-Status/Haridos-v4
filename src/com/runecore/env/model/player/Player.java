package com.runecore.env.model.player;

import com.runecore.env.model.Entity;
import com.runecore.env.model.def.PlayerDefinition;
import com.runecore.network.GameSession;

/**
 * Player.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 10, 2013
 */
public class Player extends Entity {

    /**
     * Variables for the Player
     * No primative types in here!
     */
    private PlayerDefinition definition;
    private final GameSession session;
    
    /**
     * Construct the Player instance
     * @param session The GameSession for the Player
     * @param definition The PlayerDefinition for this Player instance
     */
    public Player(GameSession session, PlayerDefinition definition) {
	this.definition = definition;
	this.session = session;
	this.session.setPlayer(this);
    }
    
    @Override
    public PlayerDefinition getDefinition() {
	return definition;
    }
    
    public GameSession getSession() {
	return session;
    }

}