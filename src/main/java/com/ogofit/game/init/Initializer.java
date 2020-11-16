package com.ogofit.game.init;

import com.ogofit.game.listener.ScoreChangeListener;
import com.ogofit.game.scheduler.GameScheduler;
import com.ogofit.game.service.NotificationService;
import com.ogofit.game.task.AbstractTask;
import com.ogofit.game.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Initializer {
    private final GameScheduler scheduler;

    private final ApplicationContext context;

    private final List<AbstractTask> tasks;

    private final NotificationService notificationService;

    @PostConstruct
    public void init() {
        notificationService
                .register(Constants.SCORE_CHANGE_NOTIFICATION_TYPE,
                        context.getBean(ScoreChangeListener.class));
        tasks.forEach(scheduler::schedule);
    }
}
