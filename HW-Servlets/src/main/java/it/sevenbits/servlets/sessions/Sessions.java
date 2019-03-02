package it.sevenbits.servlets.sessions;

import it.sevenbits.servlets.sessions.exceptions.SessionsException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Sessions {
    private Map<String, String> sessionsMap;
    private static Sessions sessions;

    private Sessions() {
        sessionsMap = new HashMap<>();
    }

    public static Sessions initialize() {
        if (sessions == null) {
            sessions = new Sessions();
        }
        return sessions;
    }

    public void add(String id, String userName) {
        sessionsMap.put(id, userName);
    }

    public String add(String userName) {
        String id = UUID.randomUUID().toString();
        sessionsMap.put(id, userName);
        return id;
    }

    public String get(String id) throws SessionsException {
        if (!sessionsMap.containsKey(id)) {
            throw new SessionsException("The sessions map does not contain the key: " + id);
        }
        return sessionsMap.get(id);
    }

    public void remove(String id) throws SessionsException {
        if (!sessionsMap.containsKey(id)) {
            throw new SessionsException("The sessions map does not contain the key: " + id);
        }
        sessionsMap.remove(id);
    }
}
