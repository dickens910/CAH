
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.websocket.server;

/**
 *
 * @author usager
 */
import com.atoudeft.web.modele.Client;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import javax.websocket.Session;

@ApplicationScoped
public class SessionHandler {
    //store Clients and active sessions 
    public static int ClientId = 0; // = number of conections + 1
    private final Set<Session> sessions = new HashSet<>();
    private final Set<Client> clients = new HashSet<>();
    
     public void addSession(Session session) {
        sessions.add(session);
          for (Client client : clients) {
            JsonObject addMessage = createAddMessage(client,"add");
            sendToSession(session, addMessage);
        }
    }

    public void removeSession(Session session) {
        sessions.remove(session);
    }
    
    public List<Client> getClients() {
        return new ArrayList<>(clients);
    }

    public void addClient(Client client) {
        client.setId(ClientId);
        //client.add(client);
        ClientId++; 
        JsonObject addMessage = createAddMessage(client,"add");
        sendToAllConnectedSessions(addMessage);
    }
    public void sendClientMessage(Client client) { 
                  System.out.println("qq mess ");

        client.setId(ClientId);
        JsonObject addMessage = createAddMessage(client,"msg");
        sendToAllConnectedSessions(addMessage);
    }

    public void removeClient(int id) {
          Client client = getClientById(id);
        if (client != null) {
          //  client.remove(client);
            System.out.println("qq toggle ");

            JsonProvider provider = JsonProvider.provider();
            JsonObject removeMessage = provider.createObjectBuilder()
                    .add("action", "remove")
                    .add("id", id)
                    .build();
            sendToAllConnectedSessions(removeMessage);
        }
    }

    public void toggleClient(int id) {
                                System.out.println("qq inside toggle shandler ");

         JsonProvider provider = JsonProvider.provider();
        Client client = getClientById(id);
        if (client != null) {
            if ("On".equals(client.getStatus())) {
                client.setStatus("Off");
            } else {
                client.setStatus("On");
            }
               System.out.println("qq toggleClient "+client.getStatus());
            JsonObject updateDevMessage = provider.createObjectBuilder()
                    .add("action", "toggle")
                    .add("id", client.getId())
                    .add("status", client.getStatus())
                    .build();
            sendToAllConnectedSessions(updateDevMessage);
        }
    }

   private Client getClientById(int id) {
        for (Client client : clients) {
            if (client.getId() == id) {
                return client;
            }
        }
        return null;
    }

    private JsonObject createAddMessage(Client client,String action) {
        JsonProvider provider = JsonProvider.provider();
        JsonObject addMessage = provider.createObjectBuilder()
                .add("action", action)
                .add("id", client.getId())
                .add("name", client.getNom())
                .add("status", client.getStatus())
                .build();
        return addMessage;
    }

    private void sendToAllConnectedSessions(JsonObject message) {
        for (Session session : sessions) {
            sendToSession(session, message);
        }
    }

    private void sendToSession(Session session, JsonObject message) {
        try {
            session.getBasicRemote().sendText(message.toString());
        } catch (IOException ex) {
            sessions.remove(session);
            Logger.getLogger(SessionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void RequestClient(Client Client) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
/*
   

*/