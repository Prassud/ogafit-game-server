package com.ogofit.game.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.Optional.ofNullable;

@Data
@NoArgsConstructor
public class Score {
    private AtomicLong value;

    public void decrement() {
        if (null == this.value) {
            this.value = new AtomicLong(0);
        }
        value.decrementAndGet();
    }

    public void increment() {
        if (null == this.value) {
            this.value = new AtomicLong(0);
        }
        this.value.incrementAndGet();
    }
}
