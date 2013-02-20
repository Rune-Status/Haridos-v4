package com.runecore.env.core;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Phaser;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.runecore.env.Context;
import com.runecore.env.core.task.GameTask;

/**
 * GameEngine.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 20, 2013
 */
public class GameEngine implements Runnable {
    
    private final BlockingQueue<GameTask> tasks = new LinkedBlockingQueue<GameTask>();
    private final ScheduledExecutorService logicService = Executors.newScheduledThreadPool(1);
    private final ExecutorService helperService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    @Override
    public void run() {
	while(true) {
	    try {
		register(tasks.take());
	    } catch(Exception e) {
		e.printStackTrace();
	    }
	}
    }
    
    public void register(final GameTask task, int delay, TimeUnit unit) {
	logicService.scheduleAtFixedRate(new Runnable() {
	    @Override
	    public void run() {
		task.execute(Context.get().getGameEngine());
	    }
	}, delay, delay, unit);
    }
    
    public void register(final GameTask task) {
	logicService.submit(new Runnable() {
	    @Override
	    public void run() {
		task.execute(Context.get().getGameEngine());
	    } 
	});
    }
    
    public void registerConcurrent(final GameTask task, final Phaser phaser) {
	helperService.submit(new Runnable() {
	    @Override
	    public void run() {
		try {
		    task.execute(Context.get().getGameEngine());
		} catch(Exception e) {
		    e.printStackTrace();
		}
		phaser.arriveAndDeregister();
	    }
	});
    }

}