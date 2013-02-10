package com.runecore.env.login;

import com.runecore.env.Context;
import com.runecore.env.model.def.PlayerDefinition;
import com.runecore.env.model.player.Player;
import com.runecore.network.GameSession;
import com.runecore.network.io.MessageBuilder;

/**
 * LoginProcess.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 10, 2013
 */
public class LoginProcess implements Runnable {

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
	int responseCode = 2;
	GameSession session = new GameSession(request.getChannel());
	PlayerDefinition definition = new PlayerDefinition(request.getUser(), 3);
	Player player = new Player(session, definition);
	player.getSession().write(new MessageBuilder().writeByte(responseCode).toMessage());
	if(responseCode == 2) {
	    //do context switching in channelhandler and assign gamepacketdecoder
	    Context c = Context.get();
	    c.getActionSender().sendLoginResponse(player);
	    c.getActionSender().refreshGameInterfaces(player);
	}
    }

}