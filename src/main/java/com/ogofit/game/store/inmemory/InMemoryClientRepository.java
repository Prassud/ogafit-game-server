package com.ogofit.game.store.inmemory;

import com.ogofit.game.cache.Cache;
import com.ogofit.game.cache.CacheManager;
import com.ogofit.game.models.Client;
import com.ogofit.game.store.IClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.util.Optional.ofNullable;

@Component
@RequiredArgsConstructor
public class InMemoryClientRepository implements IClientRepository {

    private static final String CLIENT_INFO_CACHE = "CLIENT_CACHE";
    private static final String CLIENT_INFO = "CLIENTS";

    private final CacheManager cacheManager;

    @Override
    public void add(Client client) {
        Cache cache = cacheManager.getCache(CLIENT_INFO_CACHE);
        List<Client> clients = ofNullable((List<Client>) cache.get(CLIENT_INFO))
                .orElse(new CopyOnWriteArrayList<>());
        clients.add(client);
        cache.put(CLIENT_INFO, clients);
    }

    @Override
    public List<Client> get() {
        Cache cache = cacheManager.getCache(CLIENT_INFO_CACHE);
        return ofNullable((List<Client>) cache.get(CLIENT_INFO))
                .orElse(new ArrayList<>());
    }

    @Override
    public void delete(Client client) {
        Cache cache = cacheManager.getCache(CLIENT_INFO_CACHE);
        List<Client> clients = ofNullable((List<Client>) cache.get(CLIENT_INFO))
                .orElse(new CopyOnWriteArrayList<>());
        clients.remove(client);
    }
}
