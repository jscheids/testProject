
package edu.wctc.jls.MyEcomApp.websockets.endpoint;

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
 *
 * @author Jennifer
 */
@ServerEndpoint(value="/chat")
public class MessagingEndpoint {
 
/*
       We keep track of a Set (unique values only) of user sessions (note that 
       these are NOT Web Sessions but rather WebSocket sessions). This 
       collection must be made thread-safe (after all we're using a Singleton) 
       so we use the utility method of the Collections class to create one.
    */
    private static final Set<Session> userSessions = 
            Collections.synchronizedSet(new HashSet<Session>());
    
    /**
     * Callback hook for Connection open events.
     *
     * This method will be invoked when a client requests for a
     * WebSocket connection.
     *
     * @param userSession the userSession which is opened.
     */
    @OnOpen
    public void onOpen(Session userSession) {
        System.out.println("New request received. User Id: " + userSession.getId());
        userSessions.add(userSession);
    }
    
    /**
     * Callback hook for Connection close events.
     *
     * This method will be invoked when a client closes a WebSocket
     * connection.
     *
     * @param userSession the userSession which is opened.
     */
    @OnClose
    public void onClose(Session userSession) {
        System.out.println("Connection closed. User Id: " + userSession.getId());
        userSessions.remove(userSession);
    }
    
    /**
     * Callback hook for Message Events.
     *
     * This method will be invoked when a client sends a message to this 
     * endpoint.
     *
     * @param message The text message -- note that we are receiving JSON
     * formatted values here but there is no requirement that WebSocket
     * messages be in this or any other format.
     * @param userSession The session of the client
     */
    @OnMessage
    public void onMessage(String message, Session userSession) throws IOException {
        System.out.println("Message Received, will be brodcast to all users: " + message);
        
        // Here we simply broadcaset the message to all connected users.
        // This is by choice, not by necessity!
        for (Session session : userSessions) {
            System.out.println("Sending to " + session.getId());
            session.getBasicRemote().sendText(message);
        }
    }    
    
    
    
    
}
