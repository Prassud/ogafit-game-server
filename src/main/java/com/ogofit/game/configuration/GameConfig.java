package com.ogofit.game.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "game")
@Configuration
@Data
public class GameConfig {
    private Integer instructionTimeLimit;
    private Integer maxContinuousAttempt;
    private Integer minScoreToTerminate;
    private Integer maxScoreToTerminate;

}
