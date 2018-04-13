package com.netcracker.studPract.clientWeb.ApiUtils;

import com.netcracker.studPract.clientWeb.ChatUserUtils.Agent;
import com.netcracker.studPract.clientWeb.ChatUserUtils.ChatUserActionListener;
import com.netcracker.studPract.clientWeb.ChatUserUtils.ChatUtils;
import com.netcracker.studPract.clientWeb.ChatUserUtils.Client;
import com.netcracker.studPract.clientWeb.MessageUtils.Message;
import com.netcracker.studPract.clientWeb.UserApiModel.ClientViewModel;
import org.springframework.stereotype.Component;

import javax.websocket.EncodeException;
import java.io.IOException;
import java.util.HashMap;

@Component
public class ClientUtils {

    public HashMap<String, Client> findFreeClients(HashMap<String, Client> clientHashMap) {
        HashMap<String, Client> clientViewModelHashMap = new HashMap<>();
        for (Client client : clientHashMap.values()) {
            if (client.getUserToSession() == null) {
                clientViewModelHashMap.put(client.getSession().getId(), client);
            }
        }
        return clientViewModelHashMap;
    }

    public ClientViewModel findClientByIdSession(String id, HashMap<String, Client> clientHashMap) {

        ClientViewModel clientViewModel = new ClientViewModel();
        Client client = clientHashMap.get(id);
        clientViewModel.setName(client.getName());
        clientViewModel.setRole(client.getRole());
        clientViewModel.setSessionId(client.getSession().getId());
        return clientViewModel;
    }

    public String sendMessageToAgent(HashMap<String, Client> clientHashMap, HashMap<String, Agent> agentHashMap, String id, String message) throws IOException, EncodeException {

        ChatUserActionListener chatUserActionListener = new ChatUserActionListener(new ChatUtils(agentHashMap, clientHashMap));
        Message messageJ = new Message();
        messageJ.setFrom(clientHashMap.get(id).getName());
        messageJ.setContent(message);
        chatUserActionListener.onMessageHandle(clientHashMap.get(id).getSession(), messageJ, agentHashMap, clientHashMap);
        return "sucsess";

    }

    public String disconnectClient(String id, HashMap<String, Client> clientHashMap, HashMap<String, Agent> agentHashMap) throws IOException, EncodeException {

        ChatUserActionListener chatUserActionListener = new ChatUserActionListener(new ChatUtils(agentHashMap, clientHashMap));
        Message messageJ = new Message();
        messageJ.setFrom(clientHashMap.get(id).getName());
        messageJ.setContent("/leave");
        chatUserActionListener.onMessageHandle(clientHashMap.get(id).getSession(),messageJ,agentHashMap,clientHashMap);
        return "success";
    }
}
