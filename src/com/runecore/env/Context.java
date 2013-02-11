package com.runecore.env;

import java.util.logging.Logger;

import com.runecore.codec.ActionSender;
import com.runecore.codec.ProtocolCodec;
import com.runecore.env.groovy.GroovyEngine;
import com.runecore.env.login.LoginProcessor;
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
	RegionData.init();
	setLoginProcessor(new LoginProcessor());
	new Thread(getLoginProcessor()).start();
	setGroovyEngine(new GroovyEngine());
	getCodec().init(this);
	getGroovyEngine().init(this);
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

}