package com.netcracker.studPract.clientWeb.ChatUserUtils;

import com.netcracker.studPract.clientWeb.MessageUtils.Message;

import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;
import java.util.HashMap;

public class ChatUtils {

    private HashMap<String, Agent> agents;
    private HashMap<String, Client> clients;

    public ChatUtils(HashMap<String, Agent> agents, HashMap<String, Client> clients) {
        this.agents = agents;
        this.clients = clients;
    }

    public ChatUser registerChatUser(String name, String role, Session session) {

        ChatUser chatUser;

        switch (TypeUserAnalyzer.typeOfUser(role)) {
            case CLIENT:
                Client client = new Client();
                client.setRole("client");
                chatUser = client;
                break;
            default:
                Agent agent = new Agent();
                agent.setRole("agent");
                agent.setFreeSlots(1);
                agent.setNumberOfSlots(1);
                chatUser = agent;
                break;
        }

        chatUser.setName(name);
        chatUser.setSession(session);

        return chatUser;
    }

    private Agent findAvailableAgent(HashMap<String, Agent> agents) {
        Agent rAgent = null;
        double percents = 1;
        for (Agent agent : agents.values()) {
            if (agent.getFreeSlots()/agent.getNumberOfSlots() <= percents)
                rAgent = agent;
        }
        return rAgent;
    }

    public boolean tryAssignAgent(HashMap<String, Agent> agents, ChatUser chatUser) throws IOException, EncodeException {

        Agent agent = findAvailableAgent(agents);
        if (agent != null) {
            agent.setFreeSlots(agent.getFreeSlots() - 1);
            agent.getInterlocutors().put(chatUser.getName(),chatUser.getSession());
            chatUser.setUserToSession(agent.getSession());
            Message message = new Message();
            message.setFrom(chatUser.getName());
            message.setContent("connected to the chat");
            sendMessage(message, agent);
            message.setFrom(" ");
            message.setContent("connected");
            sendMessage(message, chatUser);
            return true;
        }
        return false;
    }

    public void disconnectUsers(ChatUser chatUser) throws IOException, EncodeException {
        Message message = new Message();
        message.setFrom(chatUser.getName());
        message.setContent("Disconnected!");
        if (chatUser.getUserToSession() != null) {
            if ("agent".equals(chatUser.getRole())) {
                sendMessage(message, clients.get(chatUser.getUserToSession().getId()));
                clients.get(chatUser.getUserToSession().getId()).setUserToSession(null);
            } else {
                sendMessage(message, agents.get(chatUser.getUserToSession().getId()));
                agents.get(chatUser.getUserToSession().getId()).getInterlocutors().remove(chatUser.getName());
                agents.get(chatUser.getUserToSession().getId()).setFreeSlots
                        (agents.get(chatUser.getUserToSession().getId()).getFreeSlots() + 1);
            }
        }
        chatUser.setUserToSession(null);
    }

    public void sendMessage(Message message, ChatUser chatUser) throws IOException, EncodeException {
        chatUser.getSession().getBasicRemote().sendObject(message);
    }
}
