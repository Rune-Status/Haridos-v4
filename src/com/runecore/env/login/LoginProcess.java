package com.runecore.env.login;

import java.util.logging.Logger;

import com.runecore.codec.codec614.net.GamePacketDecoder;
import com.runecore.env.Context;
import com.runecore.env.core.GameEngine;
import com.runecore.env.core.task.GameTask;
import com.runecore.env.model.def.PlayerDefinition;
import com.runecore.env.model.player.Player;
import com.runecore.env.world.World;
import com.runecore.network.GameSession;
import com.runecore.network.io.MessageBuilder;

/**
 * LoginProcess.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 10, 2013
 */
public class LoginProcess implements Runnable {
    
    /**
     * Logger instance
     */
    private static final Logger logger = Logger.getLogger(LoginProcess.class.getName());

    /**
     * The LoginRequest instance
     */
    private LoginRequest request;
    
    /**
     * Construct the login process
     * @param request
     */
    public LoginProcess(LoginRequest request) {
	this.request = request;
    }
    
    @Override
    /**
     * Execute the login process
     */
    public void run() {
	final int responseCode = 2;
	final GameSession session = new GameSession(request.getChannel());
	final PlayerDefinition definition = new PlayerDefinition(request.getUser(), 3);
	final Player player = new Player(session, definition);
	GameTask task = new GameTask() {
	    @Override
	    public void execute(GameEngine engine) {
		int resp = responseCode;
		if(!World.get().register(player)) {
		    resp = 7;
		}
		player.getSession().write(new MessageBuilder().writeByte(responseCode).toMessage());
		if(resp == 2) {
		    Context c = Context.get();
		    c.getActionSender().sendLogin(player);
		    request.getChc().getPipeline().replace("decoder", "decoder", new GamePacketDecoder(session));
		    logger.info(definition.getName()+ " has entered the world");
		}
	    }
	};
	World.get().register(task);
    }

}