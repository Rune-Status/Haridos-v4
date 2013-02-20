package com.runecore.env.core.task;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Phaser;

import com.runecore.env.core.GameEngine;

/**
 * ConcurrentTask.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 20, 2013
 */
public class ConcurrentTask implements GameTask {
    
    private final Collection<GameTask> tasks;

    public ConcurrentTask(GameTask... tasks) {
	List<GameTask> task = new LinkedList<GameTask>();
	for(GameTask t : tasks)
	    task.add(t);
	this.tasks = Collections.unmodifiableCollection(task);
    }
    
    @Override
    public void execute(GameEngine engine) {
	Phaser phaser = new Phaser(1);
	phaser.bulkRegister(tasks.size());
	for(GameTask task : tasks) {
	    engine.registerConcurrent(task, phaser);
	}
	phaser.arriveAndAwaitAdvance();
    }

}
