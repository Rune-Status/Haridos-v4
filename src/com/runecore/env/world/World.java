package com.runecore.env.world;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.runecore.codec.PacketCodec;
import com.runecore.env.Context;
import com.runecore.env.core.GameEngine;
import com.runecore.env.core.Tick;
import com.runecore.env.core.task.ConcurrentTask;
import com.runecore.env.core.task.GameTask;
import com.runecore.env.core.task.SequentialTask;
import com.runecore.env.model.EntityList;
import com.runecore.env.model.player.Player;
import com.runecore.network.io.Message;

/**
 * World.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 11, 2013
 */
public class World implements GameTask {
    
    /**
     * Logger instance for this class
     */
    private static final Logger logger = Logger.getLogger(World.class.getName());
    
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
	players = new EntityList<Player>(2048);
	Context.get().getGameEngine().register(this, 600, TimeUnit.MILLISECONDS);
    }

    private LinkedList<Tick> ticksToAdd = new LinkedList<Tick>();
    private LinkedList<Tick> ticks = new LinkedList<Tick>();
    
    @Override
    public void execute(GameEngine engine) {
	List<GameTask> preTasks = new LinkedList<GameTask>();
	List<GameTask> updateTasks = new LinkedList<GameTask>();
	List<GameTask> postTasks = new LinkedList<GameTask>();
	preTasks.add(new GameTask() {
	    @Override
	    public void execute(GameEngine engine) {
		handleNetwork();
		processTicks();
	    }
	});
	for(final Player p : getPlayers()) {
	    preTasks.add(new GameTask() {
		@Override
		public void execute(GameEngine engine) {
		    p.tick();   
		    p.processTicks();
		}
	    });
	}
	for(final Player p : getPlayers()) {
	    updateTasks.add(new GameTask() {
		@Override
		public void execute(GameEngine engine) {
		    Context.get().getPlayerUpdateCodec().updatePlayer(p);
		}
	    });
	}
	for(final Player p : getPlayers()) {
	    postTasks.add(new GameTask() {
		@Override
		public void execute(GameEngine engine) {
		    p.getFlagManager().reset();
		}
	    });
	}
	GameTask pre = new SequentialTask(preTasks.toArray(new GameTask[0]));
	GameTask update = new ConcurrentTask(updateTasks.toArray(new GameTask[0]));
	GameTask post = new SequentialTask(postTasks.toArray(new GameTask[0]));
	engine.register(new SequentialTask(pre, update, post));
    }
    
    public void processTicks() {
	if (ticksToAdd.size() > 0) {
	    ticks.addAll(ticksToAdd);
	    ticksToAdd = new LinkedList<Tick>();
	}
	for (Iterator<Tick> it = ticks.iterator(); it.hasNext();) {
	    if (!it.next().run()) {
		it.remove();
	    }
	}
    }
    
    public void register(GameTask task) {
	Context.get().getGameEngine().register(task);
    }
    
    public void register(Tick tick) {
	ticksToAdd.add(tick);
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
		logger.info(player.getDefinition().getName() + " has left the world");
		player.getSession().getQueuedPackets().clear();
		players.remove();
		continue;
	    }
	}
	players = getPlayers().iterator();
	while(players.hasNext()) {
	    Player player = players.next();
	    if(!player.getSession().getQueuedPackets().isEmpty()) {
		Message packet = null;
		while((packet = player.getSession().getQueuedPackets().poll()) != null) {
		    int opcode = packet.getOpcode();
		    PacketCodec codec = Context.get().getPacketCodecs()[opcode];
		    if(codec != null) {
			codec.execute(player, packet);
		    }
		}
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