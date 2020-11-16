package com.ogofit.game.task;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractTask implements Runnable {

    @Override
    public void run() {
        log.info("Executing Task with name as :: {} ", getTaskName());
        execute();
        log.info("Task Execution Done for :: {} ", getTaskName());
    }

    public abstract void execute();

    public abstract String getTaskName();
}
