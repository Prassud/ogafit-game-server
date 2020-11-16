package com.ogofit.game.listener;

import com.ogofit.game.configuration.GameConfig;
import com.ogofit.game.models.Client;
import com.ogofit.game.models.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScoreChangeListener implements NotificationListener {

    private final GameConfig gameConfig;

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void notify(Object data) {
        Map<String, Object> notificationData = (Map) data;
        Client client = (Client) notificationData.get("client");
        Integer minScoreToTerminate = gameConfig.getMinScoreToTerminate();
        Integer maxScoreToTerminate = gameConfig.getMaxScoreToTerminate();
        if (client.isEligibleToTerminate(minScoreToTerminate, maxScoreToTerminate)) {
            log.info("Client has score limit amount to exit {}", client.getScore().getValue().get());
            String destination = "/clients/" + client.getId() + "/close";
            Response response = Response.builder().client(client).status("CLOSE").build();
            simpMessagingTemplate.convertAndSend(destination, response);
        }
    }
}
