package com.ogofit.game.controller;


import com.ogofit.game.models.Client;
import com.ogofit.game.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @MessageMapping("/clients/{clientId}")
    @SendTo("/topic/clients/{clientId}")
    public Client register(@Payload Client client, @DestinationVariable String clientId) {
        return clientService.create(client);
    }
}
