package com.netcracker.studPract.clientWeb.ApiUtils;

import com.netcracker.studPract.clientWeb.ChatUserUtils.Agent;
import com.netcracker.studPract.clientWeb.UserApiModel.AgentViewModel;
import org.springframework.stereotype.Component;

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
}
