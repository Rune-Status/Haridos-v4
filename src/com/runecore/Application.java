package com.runecore;

import java.util.logging.Logger;

import com.runecore.codec.codec614.Protocol614;
import com.runecore.env.Context;

/**
 * Application.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 8, 2013
 */
public class Application {
    
    private static final Logger LOGGER = Logger.getLogger(Application.class.getName());
    
    /**
     * Invoked on execution of the Application
     * @param args The arguments for the application
     */
    public static void main(String[] args) throws Exception {
	LOGGER.info("Starting RuneCore v4...");
	Context.set(new Context(new Protocol614()));
	Context.get().configure();
	LOGGER.info("Context configuration completed, server is now online");
    }

}
