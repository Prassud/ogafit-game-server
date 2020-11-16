package com.ogofit.game.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response {
    private String status;
    private Client client;
}
