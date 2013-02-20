package com.runecore.env.core.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.runecore.env.core.GameEngine;

/**
 * SequentialTask.java
 * 
 * @author Harry Andreas<harry@runecore.org> Feb 20, 2013
 */
public class SequentialTask implements GameTask {

    private Collection<GameTask> tasks;
    
    public SequentialTask(GameTask... tasks) {
	List<GameTask> taskList = new ArrayList<GameTask>();
	for (GameTask task : tasks) {
	    taskList.add(task);
	}
	this.tasks = Collections.unmodifiableCollection(taskList);
    }

    @Override
    public void execute(GameEngine engine) {
	for (GameTask task : tasks) {
	    task.execute(engine);
	}
    }

}