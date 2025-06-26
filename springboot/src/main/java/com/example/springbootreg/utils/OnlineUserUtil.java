package com.example.springbootreg.utils;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;



public class OnlineUserUtil {
    private static final Map<String, String> onlineUsers = new ConcurrentHashMap<>();

    public static void addUser(String login, String sessionId) {
        onlineUsers.put(login,sessionId);
    }

    public static void removeUser(String login) {
        onlineUsers.remove(login);
    }

    public static Map<String, String> getOnlineUsers() {
        return onlineUsers;
    }
}
