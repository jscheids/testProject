package edu.wctc.jls.MyEcomApp.websockets.endpoint;

import edu.wctc.jls.MyEcomApp.exeption.InvalidParameterException;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * Custom class which uses Java's websocket features to create a server
 * "endpoint" with the ability to receive/broadcast messages. Currently used in
 * this app as a feature in which admins can broadcast "news updates" to logged
 * in users. Improvements should be made to check the roles as an additional
 * method of server-side validation to ensure that non-admin users aren't
 * creating/sending messages. No variables- cannot do equals, hashcode, to
 * string overrides. 5/14/12017
 *
 * @author Jennifer
 */
//server endpoint annotation. Must match one providied in javascript file. 
@ServerEndpoint(value = "/chat")
public class MessagingEndpoint {

    /*
       Need to track user websocket sessions. Collection here must be made thread-safe.
     */
    private static final Set<Session> userSessions = Collections.synchronizedSet(new HashSet<Session>());

    /**
     * Callback hook for Connection open events. Method invoked when a client
     * requests a WebSocket connection.
     *
     * @param userSession the userSession which is opened.
     * @throws InvalidParameterException
     */
    @OnOpen
    public void onOpen(Session userSession) throws InvalidParameterException {
        if (userSession == null) {
            throw new InvalidParameterException();
        }
        userSessions.add(userSession);
    }

    /**
     * Callback hook for Connection close events. Method invoked when a client
     * closes a WebSocket Connection.
     *
     * @param userSession the userSession which is opened.
     * @throws InvalidParameterException
     */
    @OnClose
    public void onClose(Session userSession) throws InvalidParameterException {
        if (userSession == null) {
            throw new InvalidParameterException();
        }
        userSessions.remove(userSession);
    }

    /**
     * Callback hook for Message Events. This method invoked when a client sends
     * a message to this endpoint
     *
     * @param message The text message. Currently receiving JSON formatted
     * values.
     * @param userSession The session of the client
     * @throws java.io.IOException
     */
    @OnMessage
    public void onMessage(String message, Session userSession) throws IOException, InvalidParameterException {
        if (userSession == null || message.isEmpty() || message == null) {
            throw new InvalidParameterException();
        }
        for (Session session : userSessions) {
            session.getBasicRemote().sendText(message);
        }
    }

}
