package com.ogofit.game.service;

import com.ogofit.game.listener.NotificationListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class NotificationService {
    private Map<String, List<NotificationListener>> notificationListeners = new HashMap();

    public void register(String notificationType, NotificationListener listener){
        List<NotificationListener> listeners = getListener(notificationType);
        listeners.add(listener);
        notificationListeners.putIfAbsent(notificationType, listeners);
    }

    private List<NotificationListener> getListener(String notificationType) {
        return notificationListeners.getOrDefault(notificationType, new ArrayList<>());
    }

    public void notify(String notificationType, Object data){
        Optional.ofNullable(notificationListeners.get(notificationType))
                .map(List::stream)
                .ifPresent(listeners -> listeners.forEach(listener -> listener.notify(data)));

    }
}
