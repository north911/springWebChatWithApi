package com.netcracker.studPract.clientWeb.Endpoint;

import com.netcracker.studPract.clientWeb.ChatUserUtils.ChatUser;
import com.netcracker.studPract.clientWeb.ChatUserUtils.ChatUserActionListener;
import com.netcracker.studPract.clientWeb.ChatUserUtils.ChatUtils;
import com.netcracker.studPract.clientWeb.MessageUtils.Message;
import com.netcracker.studPract.clientWeb.MessageCoders.MessageDecoder;
import com.netcracker.studPract.clientWeb.MessageCoders.MessageEncoder;
import com.netcracker.studPract.clientWeb.ChatUserUtils.UserRegistrator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.IOException;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;


@ServerEndpoint(value = "/{userrole}/chat/{username}", decoders = MessageDecoder.class, encoders = MessageEncoder.class)
public class ChatEndpoint {

    private Logger logger = Logger.getRootLogger();

    private static UserRegistrator userRegistrator = UserRegistrator.getInstance();
    private ChatUtils chatUtils = new ChatUtils(userRegistrator.getAgents(), userRegistrator.getClients());
    private ChatUserActionListener chatUserActionListener = new ChatUserActionListener(chatUtils);

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username, @PathParam("userrole") String userrole) throws IOException, EncodeException {
        userRegistrator.onOpenUserRegister(session, username, userrole);
        logger.log(Level.INFO, username + " connected to chat");
    }

    @OnMessage
    public void onMessage(Session session, Message message) throws IOException, EncodeException {

        chatUserActionListener.onMessageHandle(session,message,userRegistrator.getAgents(),userRegistrator.getClients());
    }

    @OnClose
    public void onClose(Session session) throws IOException, EncodeException {

        ChatUser chatUser = chatUserActionListener.onCloseHandler(session,userRegistrator.getAgents(),userRegistrator.getClients());
        logger.log(Level.INFO, chatUser.getName() + " disconnected from chat");
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        logger.log(Level.INFO, "error");
    }

}