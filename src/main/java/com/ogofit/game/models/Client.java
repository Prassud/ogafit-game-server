package com.ogofit.game.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class Client {
    private UUID id;
    private String name;
    private Score score = new Score();

    public boolean isEligibleToTerminate(int minScore, int maxScore) {
        long currScore = score.getValue().get();
        return minScore == currScore || maxScore == currScore;
    }
}
