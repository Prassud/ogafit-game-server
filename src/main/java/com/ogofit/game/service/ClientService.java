package com.ogofit.game.service;

import com.ogofit.game.models.Client;
import com.ogofit.game.store.IClientRepository;
import com.ogofit.game.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final IClientRepository clientRepository;

    private final NotificationService notificationService;

    public List<Client> getClients() {
        return clientRepository.get();
    }

    public Client create(Client client) {
        clientRepository.add(client);
        return client;
    }

    public void incrementScore(Client client) {
        client.getScore().increment();
        notificationService.notify(Constants.SCORE_CHANGE_NOTIFICATION_TYPE,
                buildScoreChangedData(client));
    }

    public void decrementScore(Client client) {
        client.getScore().decrement();
        notificationService.notify(Constants.SCORE_CHANGE_NOTIFICATION_TYPE,
                buildScoreChangedData(client));
    }

    public Map<String, Object> buildScoreChangedData(Client client) {
        Map<String, Object> data = new HashMap<>();
        data.putIfAbsent("client", client);
        return data;
    }
}
