package com.runecore.env.login;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * LoginProcessor.java
 * @author Harry Andreas<harry@runecore.org> 
 * Feb 9, 2013
 */
public class LoginProcessor implements Runnable {

	/**
	 * Variables for the LoginProcessor
	 */
	private BlockingQueue<LoginRequest> requests = new LinkedBlockingQueue<LoginRequest>();
	private ExecutorService executor = Executors.newSingleThreadExecutor();

	@Override
	/**
	 * Processes the login queue
	 */
	public void run() {
		while (true) {
			try {
				executor.submit(new LoginProcess(requests.take()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Queues a login request
	 * 
	 * @param request
	 *            The LoginRequest to queue
	 */
	public void queue(LoginRequest request) {
		requests.offer(request);
	}

}