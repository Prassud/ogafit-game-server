package com.ogofit.game.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class TaskSchedulerConfig {
    @Bean
    public ThreadPoolTaskScheduler instructionGameScheduler() {
        ThreadPoolTaskScheduler instructionGameTaskScheduler = new ThreadPoolTaskScheduler();
        instructionGameTaskScheduler.setPoolSize(5);
        instructionGameTaskScheduler.setThreadNamePrefix("InstructionGameScheduler");
        return instructionGameTaskScheduler;
    }
}
