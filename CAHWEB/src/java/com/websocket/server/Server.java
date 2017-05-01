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
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

@ApplicationScoped
@ServerEndpoint("/actions")
public class Server {
     @Inject
    private SessionHandler sessionHandler;
      @OnOpen
        public void open(Session session) {
             sessionHandler.addSession(session);
    }

    @OnClose
        public void close(Session session) {
             sessionHandler.removeSession(session);
    }

    @OnError
        public void onError(Throwable error) {
             Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, error);
    }
             int numberOfPlayers = 0;
    @OnMessage
        public void handleMessage(String message, Session session) {
            try (JsonReader reader = Json.createReader(new StringReader(message))) {
            JsonObject jsonMessage = reader.readObject();
             

            if ("add".equals(jsonMessage.getString("action"))) {
                Client Client = new Client();
                Client.setNom(jsonMessage.getString("name"));
                Client.setStatus("On");
                sessionHandler.addClient(Client); 
            }

            if ("remove".equals(jsonMessage.getString("action"))) {
                int id = (int) jsonMessage.getInt("id");
                sessionHandler.removeClient(id);
            }
            if ("request".equals(jsonMessage.getString("action"))) {
                Client Client = new Client();
                Client.setNom(jsonMessage.getString("name"));
                Client.setStatus("*"+ Client.getNom()+" Has requested a game");
                sessionHandler.addClient(Client);
            }
            if ("join".equals(jsonMessage.getString("action"))) {
                 numberOfPlayers ++;
                Client Client = new Client();
                Client.setNom(jsonMessage.getString("name"));
                Client.setStatus("*"+ Client.getNom()+" Has Join the game.. " +numberOfPlayers+" People Joined");
                sessionHandler.addClient(Client);
            }
            if ("msg".equals(jsonMessage.getString("action"))) {
                Client Client = new Client();
                Client.setNom(jsonMessage.getString("name"));
                Client.setStatus(""+ Client.getNom()+": "+ jsonMessage.getString("message"));
                sessionHandler.sendClientMessage(Client);
            }
            

            if ("toggle".equals(jsonMessage.getString("action"))) {
                int id = (int) jsonMessage.getInt("id");
                sessionHandler.toggleClient(id);
                 System.out.println("qq toggle server ");

            }
        }
    }
}
