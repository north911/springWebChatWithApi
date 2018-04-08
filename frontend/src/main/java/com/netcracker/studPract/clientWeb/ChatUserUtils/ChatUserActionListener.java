package com.netcracker.studPract.clientWeb.ChatUserUtils;

import com.netcracker.studPract.clientWeb.MessageUtils.Message;
import com.netcracker.studPract.clientWeb.MessageUtils.MessageTypeAnalyzer;

import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;
import java.util.HashMap;

public class ChatUserActionListener {

    private ChatUtils chatUtils;

    public ChatUserActionListener(ChatUtils chatUtils) {
        this.chatUtils = chatUtils;

    }




    public void onMessageHandle(Session session, Message message, HashMap<String, Agent> agents, HashMap<String, Client> clients) throws IOException, EncodeException {
        switch (MessageTypeAnalyzer.typeOfMessage(message.getContent(), session, clients)) {
            case CLIENT_MESSAGE:
                message.setFrom(clients.get(session.getId()).getName());
                chatUtils.sendMessage(message, clients.get(session.getId()));
                if (clients.get(session.getId()).getUserToSession() == null) {
                    chatUtils.tryAssignAgent(agents, clients.get(session.getId()));
                }
                if (clients.get(session.getId()).getUserToSession() != null)
                    chatUtils.sendMessage(message, agents.get(clients.get(session.getId()).getUserToSession().getId()));
                else {
                    message.setFrom("");
                    message.setContent("НЕТ СОБЕСЕДНИКА");
                    chatUtils.sendMessage(message, clients.get(session.getId()));
                }
                break;

            case AGENT_MESSAGE:
                message.setFrom(agents.get(session.getId()).getName());
                if (agents.get(session.getId()).getFreeSlots() != agents.get(session.getId()).getNumberOfSlots()) {
                    String id = agents.get(session.getId()).getInterlocutors().get(message.getTo()).getId();
                    chatUtils.sendMessage(message, clients.get(id));
                } else {
                    message.setFrom("");
                    message.setContent("НЕТ СОБЕСЕДНИКА");
                    chatUtils.sendMessage(message, agents.get(session.getId()));
                }
                break;


            case LEAVE_MESSAGE:
                message.setFrom("");
                if (clients.get(session.getId()).getUserToSession() != null) {
                    message.setContent("Disconnected!");
                    chatUtils.sendMessage(message, clients.get(session.getId()));
                    chatUtils.disconnectUsers(clients.get(session.getId()));
                }

                break;

            case AGENT_SLOTS_MESSAGE:
                String arr[] = message.getContent().split(" ", 2);
                agents.get(session.getId()).setNumberOfSlots(Integer.parseInt(arr[1]));
                agents.get(session.getId()).setFreeSlots(Integer.parseInt(arr[1]));
                break;
        }

    }

    public ChatUser onCloseHandler(Session session, HashMap<String, Agent> agents, HashMap<String, Client> clients) throws IOException, EncodeException {

        ChatUser user;
        if (clients.containsKey(session.getId())) {
            user = clients.get(session.getId());
            chatUtils.disconnectUsers(user);
            clients.remove(session.getId());
        } else {
            user = agents.get(session.getId());
            chatUtils.disconnectUsers(user);
            agents.remove(session.getId());

        }
        return user;
    }
}
