package com.ogofit.game.models;

import lombok.Data;

import java.util.UUID;

@Data
public class Client {
    private UUID id;
    private String name;
    private Score score;

    public boolean isEligibleToTerminate(int minScore, int maxScore) {
        long currScore = score.getValue().get();
        return minScore == currScore || maxScore == currScore;
    }
}
