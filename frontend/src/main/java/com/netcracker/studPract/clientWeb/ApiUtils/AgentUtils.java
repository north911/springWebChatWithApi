package com.netcracker.studPract.clientWeb.ApiUtils;

import com.netcracker.studPract.clientWeb.ChatUserUtils.Agent;
import com.netcracker.studPract.clientWeb.ChatUserUtils.ChatUserActionListener;
import com.netcracker.studPract.clientWeb.ChatUserUtils.ChatUtils;
import com.netcracker.studPract.clientWeb.ChatUserUtils.Client;
import com.netcracker.studPract.clientWeb.MessageUtils.Message;
import com.netcracker.studPract.clientWeb.UserApiModel.AgentViewModel;
import org.springframework.stereotype.Component;

import javax.websocket.EncodeException;
import java.io.IOException;
import java.util.HashMap;

@Component
public class AgentUtils {

    public HashMap<String, Agent> findFreeAgents(HashMap<String, Agent> agents){
        HashMap<String, Agent> freeAgents = new HashMap<>();
        for (Agent agent : agents.values()) {
            if(agent.getFreeSlots()>0)
                freeAgents.put(agent.getSession().getId(),agent);
        }
        return freeAgents;
    }

    public AgentViewModel findAgentBySessionId(String id, HashMap<String, Agent> agents){
        Agent agent = agents.get(id);
        AgentViewModel agentViewModel = new AgentViewModel();
        agentViewModel.setRole(agent.getRole());
        agentViewModel.setNumberOfSlots(agent.getNumberOfSlots());
        agentViewModel.setName(agent.getName());
        agentViewModel.setIdSession(id);
        agentViewModel.setFreeSlots(agent.getFreeSlots());
        return agentViewModel;
    }

    public int countFreeAgents(HashMap<String, Agent> agents){
        int count = 0;
        for (Agent agent : agents.values()) {
            if(agent.getFreeSlots()>0)
                count++;
        }
        return count;
    }

    public String sendMessageToClient(HashMap<String, Client> clientHashMap, HashMap<String, Agent> agentHashMap, String id, String message, String idClient) throws IOException, EncodeException {

        ChatUserActionListener chatUserActionListener = new ChatUserActionListener(new ChatUtils(agentHashMap, clientHashMap));
        Message messageJ = new Message();
        messageJ.setFrom(agentHashMap.get(id).getName());
        messageJ.setContent(message);
        messageJ.setTo(clientHashMap.get(idClient).getName());
        chatUserActionListener.onMessageHandle(agentHashMap.get(id).getSession(),messageJ,agentHashMap,clientHashMap);
        return "sucsess";

    }
}
