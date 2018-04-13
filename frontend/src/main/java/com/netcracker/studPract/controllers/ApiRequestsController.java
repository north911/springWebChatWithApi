package com.netcracker.studPract.controllers;

import com.netcracker.devschool.dev4.ConsoleClient.ConsoleClientEndPoint;
import com.netcracker.studPract.clientWeb.ApiServiceConverters.AgentsConverter;
import com.netcracker.studPract.clientWeb.ApiServiceConverters.ClientsConverter;
import com.netcracker.studPract.clientWeb.ApiUtils.AgentUtils;
import com.netcracker.studPract.clientWeb.ApiUtils.ClientUtils;
import com.netcracker.studPract.clientWeb.ApiUtils.OpenedChatUtils;
import com.netcracker.studPract.clientWeb.ChatUserUtils.UserRegistrator;
import com.netcracker.studPract.clientWeb.UserApiModel.AgentViewModel;
import com.netcracker.studPract.clientWeb.UserApiModel.ChatViewModel;
import com.netcracker.studPract.clientWeb.UserApiModel.ClientViewModel;
import org.java_websocket.client.WebSocketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;


@RestController
public class ApiRequestsController {

    @Autowired
    AgentsConverter agentsConverter;

    @Autowired
    ClientsConverter clientsConverter;

    @Autowired
    AgentUtils agentUtils;

    @Autowired
    ClientUtils clientUtils;

    @Autowired
    OpenedChatUtils openedChatUtils;


    private UserRegistrator userRegistrator = UserRegistrator.getInstance();

    @RequestMapping(value = "/getAllAgents", method = RequestMethod.GET)
    @ResponseBody
    public HashMap<String, AgentViewModel> getAgents(@RequestParam(value = "pageNumber") int pageNumber, @RequestParam(value = "size") int size) {
        return agentsConverter.convertPageAgents(userRegistrator.getAgents(), pageNumber, size);
    }

    @RequestMapping(value = "/getAllClients", method = RequestMethod.GET)
    @ResponseBody
    public HashMap<String, ClientViewModel> getClients(@RequestParam(value = "pageNumber") int pageNumber, @RequestParam(value = "size") int size) {
        return clientsConverter.convertPageClients(userRegistrator.getClients(), pageNumber, size);
    }


    @RequestMapping(value = "/getFreeAgents", method = RequestMethod.GET)
    @ResponseBody
    public HashMap<String, AgentViewModel> getFreeAgents() {
        return agentsConverter.convertAgents(agentUtils.findFreeAgents(userRegistrator.getAgents()));
    }

    @RequestMapping(value = "/getAgent/{id}", method = RequestMethod.GET)
    @ResponseBody
    public AgentViewModel getAgent(@PathVariable String id) {
        return agentUtils.findAgentBySessionId(id, userRegistrator.getAgents());
    }

    @RequestMapping(value = "/countFreeAgents", method = RequestMethod.GET)
    @ResponseBody
    public int countFreeAgents() {
        return agentUtils.countFreeAgents(userRegistrator.getAgents());
    }

    @RequestMapping(value = "/getFreeClients", method = RequestMethod.GET)
    @ResponseBody
    public HashMap<String, ClientViewModel> getFreeClients() {

        return clientsConverter.convertClientsToViewModel(clientUtils.findFreeClients(userRegistrator.getClients()));
    }

    @RequestMapping(value = "/clientProfile/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ClientViewModel getClient(@PathVariable String id) {
        return clientUtils.findClientByIdSession(id, userRegistrator.getClients());
    }

    @RequestMapping(value = "/registerAgent", method = RequestMethod.POST)
    @ResponseBody
    public String registerAgent(@RequestParam(value = "name", required = false) String name) throws EncodeException, IOException, URISyntaxException {

        WebSocketClient agent = new ConsoleClientEndPoint(new URI("ws://localhost:8080/" + "webagent" + "/chat/" + name + "/"));
        agent.connect();
        return "success";
    }

    @RequestMapping(value = "/getOpenedChats", method = RequestMethod.GET)
    @ResponseBody
    public List<ChatViewModel> getOpenedChats() {
        return openedChatUtils.getOpenedChats(userRegistrator.getClients(), userRegistrator.getAgents());
    }

    @RequestMapping(value = "/chat/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ChatViewModel getChatInfo(@PathVariable String id) {
        return openedChatUtils.getChatById(userRegistrator.getClients(), userRegistrator.getAgents(), id);
    }

    @RequestMapping(value = "/registerClient", method = RequestMethod.POST)
    @ResponseBody
    public String registerClient(@RequestParam(value = "name", required = false) String name) throws EncodeException, IOException, URISyntaxException {

        WebSocketClient client = new ConsoleClientEndPoint(new URI("ws://localhost:8080/" + "client" + "/chat/" + name + "/"));
        client.connect();
        return "success";
    }

    @RequestMapping(value = "/client/{id}/sendMessage", method = RequestMethod.POST)
    @ResponseBody
    public String sendClientMessage(@PathVariable String id, @RequestParam(value = "message", required = false) String message) throws IOException, EncodeException {
        clientUtils.sendMessageToAgent(userRegistrator.getClients(), userRegistrator.getAgents(), id, message);
        return "success";
    }

    @RequestMapping(value = "/agent/{id}/sendMessage", method = RequestMethod.POST)
    @ResponseBody
    public String sendAgentMessage(@PathVariable String id, @RequestParam(value = "message", required = false) String message,
                                   @RequestParam(value = "idClient", required = false) String idClient) throws IOException, EncodeException {
        agentUtils.sendMessageToClient(userRegistrator.getClients(), userRegistrator.getAgents(), id, message, idClient);
        return "success";
    }

    @RequestMapping(value = "/client/{id}/leave", method = RequestMethod.POST)
    @ResponseBody
    public String clientLeave(@PathVariable String id) throws IOException, EncodeException {

        clientUtils.disconnectClient(id, userRegistrator.getClients(), userRegistrator.getAgents());
        return "success";
    }

}
