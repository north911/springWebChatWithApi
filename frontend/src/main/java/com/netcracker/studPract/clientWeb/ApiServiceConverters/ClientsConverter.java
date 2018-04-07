package com.netcracker.studPract.clientWeb.ApiServiceConverters;

import com.netcracker.studPract.clientWeb.ChatUserUtils.Client;
import com.netcracker.studPract.clientWeb.UserApiModel.ClientViewModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class ClientsConverter {


    public HashMap<String, ClientViewModel> convertClientsToViewModel(HashMap<String, Client> clientHashMap) {

        HashMap<String, ClientViewModel> clientViewModelHashMap = new HashMap<>();

        for (Client client : clientHashMap.values()) {
            ClientViewModel clientViewModel = new ClientViewModel();
            clientViewModel.setName(client.getName());
            clientViewModel.setRole(client.getRole());
            clientViewModel.setSessionId(client.getSession().getId());
            clientViewModelHashMap.put(clientViewModel.getSessionId(), clientViewModel);
        }
        return clientViewModelHashMap;
    }

    public HashMap<String, ClientViewModel> convertPageClients(HashMap<String, Client> clientHashMap, int pageNumber, int size) {
        HashMap<String, ClientViewModel> clientViewModelHashMap = convertClientsToViewModel(clientHashMap);
        HashMap<String, ClientViewModel> retHashMap = new HashMap<>();
        List<ClientViewModel> clientViewModels = new ArrayList<>(clientViewModelHashMap.values());
        for (int i = pageNumber * size; i < pageNumber * size + size; i++) {
            retHashMap.put(clientViewModels.get(i).getSessionId(), clientViewModels.get(i));
        }
        return retHashMap;
    }
}
