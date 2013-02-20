package com.runecore.env;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.runecore.codec.ActionSender;
import com.runecore.codec.PacketCodec;
import com.runecore.codec.PlayerUpdateCodec;
import com.runecore.codec.ProtocolCodec;
import com.runecore.env.core.GameEngine;
import com.runecore.env.groovy.GroovyEngine;
import com.runecore.env.login.LoginProcessor;
import com.runecore.env.model.def.ItemDefinition;
import com.runecore.env.model.map.ObjectAdapter;
import com.runecore.env.widget.WidgetAdapter;
import com.runecore.env.widget.WidgetAdapterRepository;
import com.runecore.util.RegionData;

/**
 * Context.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 8, 2013
 */
public class Context {
    
    /**
     * The instance of the server context
     */
    private static Context context;
    
    /**
     * The ProtocolCodec the context is set to use
     */
    private ProtocolCodec codec;
    
    /**
     * The PlayerUpdateCodec
     */
    private PlayerUpdateCodec playerUpdateCodec;
    
    /**
     * An array of PacketCodecs
     */
    private PacketCodec[] packetCodecs;
    
    /**
     * A map of WidgetAdapterRepositories
     */
    private final Map<Integer, WidgetAdapterRepository> widgetAdapters = new HashMap<Integer, WidgetAdapterRepository>();
    
    /**
     * A List of ObjectAdapters
     */
    private final List<ObjectAdapter> objectAdapters = new LinkedList<ObjectAdapter>();
    
    /**
     * The GameEngine for this Context
     */
    private final GameEngine gameEngine = new GameEngine();
    
    /**
     * The instance of the GroovyEngine
     */
    private GroovyEngine groovyEngine;
    
    /**
     * The instance of the LoginProcessor
     */
    private LoginProcessor loginProcessor;
    
    /**
     * The instance of the ActionSender
     */
    private ActionSender actionSender;
    
    /**
     * Construct the Context instance
     * @param protocol The ProtocolCodec the context is set to use
     */
    public Context(ProtocolCodec protocol) {
	this.setCodec(protocol);
    }
    
    /**
     * Logger instance for this class
     */
    private static final Logger LOGGER = Logger.getLogger(Context.class.getName());
    
    /**
     * Configure the Context
     */
    public void configure() throws Exception {
	LOGGER.info("Configuring context with codec "+getCodec().getClass().getName());
	packetCodecs = new PacketCodec[255];
	RegionData.init();
	ItemDefinition.init();
	setLoginProcessor(new LoginProcessor());
	new Thread(getLoginProcessor()).start();
	setGroovyEngine(new GroovyEngine());
	getCodec().init(this);
	getGroovyEngine().init(this);
    }
    
    public void register(ObjectAdapter adapter) {
	getObjectAdapters().add(adapter);
    }
    
    public void register(int index, PacketCodec codec) {
	packetCodecs[index] = codec;
    }
    
    public void register(int index, WidgetAdapter adapter) {
	WidgetAdapterRepository repo = getWidgetAdapters().get(index);
	if(repo == null) {
	    repo = new WidgetAdapterRepository();
	    getWidgetAdapters().put(index, repo);
	}
	repo.register(adapter);
    }
    
    public static Context get() {
	return context;
    }
    
    public static void set(Context ctx) {
	context = ctx;
    }

    public ProtocolCodec getCodec() {
	return codec;
    }

    public void setCodec(ProtocolCodec codec) {
	this.codec = codec;
    }
    
    public GroovyEngine getGroovyEngine() {
	return groovyEngine;
    }

    public void setGroovyEngine(GroovyEngine groovyEngine) {
	this.groovyEngine = groovyEngine;
    }

    public LoginProcessor getLoginProcessor() {
	return loginProcessor;
    }

    public void setLoginProcessor(LoginProcessor loginProcessor) {
	this.loginProcessor = loginProcessor;
    }

    public ActionSender getActionSender() {
	return actionSender;
    }

    public void setActionSender(ActionSender actionSender) {
	this.actionSender = actionSender;
    }

    public PlayerUpdateCodec getPlayerUpdateCodec() {
	return playerUpdateCodec;
    }

    public void setPlayerUpdateCodec(PlayerUpdateCodec playerUpdateCodec) {
	this.playerUpdateCodec = playerUpdateCodec;
    }

    public PacketCodec[] getPacketCodecs() {
	return packetCodecs;
    }

    public void setPacketCodecs(PacketCodec[] packetCodecs) {
	this.packetCodecs = packetCodecs;
    }

    public List<ObjectAdapter> getObjectAdapters() {
	return objectAdapters;
    }

    public GameEngine getGameEngine() {
	return gameEngine;
    }

    public Map<Integer, WidgetAdapterRepository> getWidgetAdapters() {
	return widgetAdapters;
    }

}