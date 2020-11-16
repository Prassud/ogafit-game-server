package com.ogofit.game.models;

import lombok.Data;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Data
public class Score {
    private AtomicLong value;

    public void decrement() {
        this.value = Optional.ofNullable(value).orElse(new AtomicLong(0));
        value.decrementAndGet();
    }

    public void increment() {
        this.value = Optional.ofNullable(value).orElse(new AtomicLong(0));
        value.incrementAndGet();
    }
}
