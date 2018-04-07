package com.netcracker.studPract.clientWeb.ApiUtils;

import com.netcracker.studPract.clientWeb.ChatUserUtils.Agent;
import com.netcracker.studPract.clientWeb.ChatUserUtils.Client;
import com.netcracker.studPract.clientWeb.UserApiModel.ChatViewModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class OpenedChatUtils {

    public List<ChatViewModel> getOpenedChats(HashMap<String, Client> clientHashMap, HashMap<String, Agent> agents) {
        List<ChatViewModel> chatViewModelList = new ArrayList<>();
        for (Client client : clientHashMap.values()) {
            if (client.getUserToSession() != null) {
                ChatViewModel chatViewModel = new ChatViewModel();
                chatViewModel.setAgentName(agents.get(client.getUserToSession().getId()).getName());
                chatViewModel.setClientName(client.getName());
                chatViewModelList.add(chatViewModel);
            }
        }
        return chatViewModelList;
    }


    public ChatViewModel getChatById(HashMap<String, Client> clientHashMap,HashMap<String, Agent> agents, String id){
        Client client = clientHashMap.get(id);
        ChatViewModel chatViewModel = new ChatViewModel();
        chatViewModel.setAgentName(agents.get(client.getUserToSession().getId()).getName());
        chatViewModel.setClientName(client.getName());
        return chatViewModel;
    }
}
