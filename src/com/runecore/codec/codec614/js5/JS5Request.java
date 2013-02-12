package com.runecore.codec.codec614.js5;

import org.jboss.netty.channel.Channel;

import com.runecore.cache.CacheManager;
import com.runecore.network.io.Message;

/**
 * JS5Request.java
 * @author Harry Andreas<harry@runecore.org>
 *  Feb 8, 2013
 */
public class JS5Request implements Runnable {

	/**
	 * Attributes for the request
	 */
	private final int file, priority, container;

	/**
	 * The channel to write to
	 */
	private Channel channel;

	/**
	 * Construct the request
	 * 
	 * @param channel
	 *            The channel requesting
	 * @param priority
	 *            The priority
	 * @param container
	 *            The container
	 * @param file
	 *            The file in the container
	 */
	public JS5Request(Channel channel, int priority, int container, int file) {
		this.file = file;
		this.container = container;
		this.priority = priority;
		this.channel = channel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		Message response = CacheManager.generateFile(container, file, priority);
		if (response != null) {
			channel.write(response);
		}
	}

}