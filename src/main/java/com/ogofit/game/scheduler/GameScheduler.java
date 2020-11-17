package com.ogofit.game.scheduler;

import com.ogofit.game.task.AbstractTask;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class GameScheduler {
    private final ThreadPoolTaskScheduler instructionGameScheduler;

    private static final Integer FIXED_DELAY = 500;

    public void schedule(AbstractTask task) {
        PeriodicTrigger trigger = new PeriodicTrigger(FIXED_DELAY, TimeUnit.MILLISECONDS);
        instructionGameScheduler.schedule(task, trigger);
    }
}
