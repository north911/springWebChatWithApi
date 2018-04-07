package com.netcracker.studPract.clientWeb.ApiUtils;

import com.netcracker.studPract.clientWeb.ChatUserUtils.Client;
import com.netcracker.studPract.clientWeb.UserApiModel.ClientViewModel;
import org.springframework.stereotype.Component;

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

    public ClientViewModel findClientByIdSession(String id, HashMap<String,Client> clientHashMap){

        ClientViewModel clientViewModel = new ClientViewModel();
        Client client = clientHashMap.get(id);
        clientViewModel.setName(client.getName());
        clientViewModel.setRole(client.getRole());
        clientViewModel.setSessionId(client.getSession().getId());
        return clientViewModel;
    }
}
