package com.netcracker.studPract.clientWeb.ApiServiceConverters;


import com.netcracker.studPract.clientWeb.ChatUserUtils.Agent;
import com.netcracker.studPract.clientWeb.UserApiModel.AgentViewModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class AgentsConverter {
    public HashMap<String, AgentViewModel> convertAgents(HashMap<String, Agent> agentHashMap) {
        HashMap<String, AgentViewModel> agentViewModelHashMap = new HashMap<>();
        for (Agent agent : agentHashMap.values()) {
            AgentViewModel agentViewModel = new AgentViewModel();
            agentViewModel.setFreeSlots(agent.getFreeSlots());
            agentViewModel.setIdSession(agent.getSession().getId());
            agentViewModel.setName(agent.getName());
            agentViewModel.setNumberOfSlots(agent.getNumberOfSlots());
            agentViewModel.setRole(agent.getRole());
            agentViewModelHashMap.put(agent.getSession().getId(), agentViewModel);
        }
        return agentViewModelHashMap;
    }

    public HashMap<String, AgentViewModel> convertPageAgents(HashMap<String, Agent> agentHashMap, int pageNumber, int size) {
        HashMap<String, AgentViewModel> agentViewModelHashMap = convertAgents(agentHashMap);
        HashMap<String, AgentViewModel> retHashMap = new HashMap<>();
        List<AgentViewModel> agentViewModelList = new ArrayList<>(agentViewModelHashMap.values());
        for (int i = pageNumber * size; i < pageNumber * size + size; i++) {
            retHashMap.put(agentViewModelList.get(i).getIdSession(),agentViewModelList.get(i));
        }
        return retHashMap;
    }
}
