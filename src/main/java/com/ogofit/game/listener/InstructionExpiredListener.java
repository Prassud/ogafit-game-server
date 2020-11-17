package com.ogofit.game.listener;

import com.ogofit.game.configuration.GameConfig;
import com.ogofit.game.models.Client;
import com.ogofit.game.models.Instruction;
import com.ogofit.game.models.Response;
import com.ogofit.game.service.InstructionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class InstructionExpiredListener implements NotificationListener {

    private final GameConfig gameConfig;

    private final InstructionService instructionService;

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void notify(Object data) {
        Instruction instruction = (Instruction) data;
        Client client = instruction.getClient();

        if (instructionService.isReachedMaxContinuousAttempt(client)) {
            log.info("Has Reached Maximum Continuous Attempt");
            String destination = "/topic/clients/" + client.getId() + "/close";
            Response response = Response.builder().client(client).status("CLOSE").build();
            simpMessagingTemplate.convertAndSend(destination, response);
        } else {
            log.info("Instruction is Expired");
            String destination = "/topic/clients/" + client.getId() + "/instructions/expired";
            Response response = Response.builder().client(client).status("EXPIRED").build();
            simpMessagingTemplate.convertAndSend(destination, response);
        }
    }
}
