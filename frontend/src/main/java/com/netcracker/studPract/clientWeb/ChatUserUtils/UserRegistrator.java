package com.netcracker.studPract.clientWeb.ChatUserUtils;

import com.netcracker.studPract.clientWeb.MessageUtils.Message;


import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;
import java.util.HashMap;


public final class UserRegistrator {

    private static UserRegistrator userRegistrator = null;

    private UserRegistrator() {
        chatUtils = new ChatUtils(agents, clients);

    }

    public static synchronized UserRegistrator getInstance() {
        if (userRegistrator == null)
            userRegistrator = new UserRegistrator();
        return userRegistrator;
    }

    private ChatUtils chatUtils;
    private HashMap<String, Agent> agents = new HashMap<>();
    private HashMap<String, Client> clients = new HashMap<>();


    public ChatUser onOpenUserRegister(Session session, String username, String userrole) throws EncodeException, IOException {
        ChatUser chatUser;
        switch (TypeUserAnalyzer.typeOfUser(userrole)) {
            case CLIENT:
                chatUser = chatUtils.registerChatUser(username, userrole, session);
                clients.put(session.getId(), (Client) chatUser);
                break;
            default:
                chatUser = chatUtils.registerChatUser(username, userrole, session);
                agents.put(session.getId(), (Agent) chatUser);
                break;
        }
        Message message = new Message();
        message.setFrom(username);
        message.setContent("Connected!");
        chatUtils.sendMessage(message, chatUser);
        return chatUser;
    }

    public HashMap<String, Agent> getAgents() {
        return agents;
    }

    public void setAgents(HashMap<String, Agent> agents) {
        this.agents = agents;
    }

    public HashMap<String, Client> getClients() {
        return clients;
    }

    public void setClients(HashMap<String, Client> clients) {
        this.clients = clients;
    }
}
