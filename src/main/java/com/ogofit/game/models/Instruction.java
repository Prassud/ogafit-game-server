package com.ogofit.game.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.UUID;

@Data
@NoArgsConstructor
public class Instruction {
    private String key;
    private UUID id;
    private Client client;
    private boolean isActive;
    private boolean isExpired;
    private ZonedDateTime createdAt;

    @JsonIgnore
    public boolean isExpired(int expirySeconds) {
        return Instant.now()
                .isAfter(createdAt.toInstant()
                        .plus(expirySeconds, ChronoUnit.SECONDS));
    }
}
