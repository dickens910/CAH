/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.net.*;
import java.io.*;
import java.util.*;

public class Client extends Observable {

    private Socket clientSock;	//socket de connexion

    private PrintWriter os; //flux de sortie
    private BufferedInputStream is; //flux d'entrée

    private String etat = ""; //État actuel du client

    private String adr = "127.0.0.1"; //Addresse IP du serveur
    private int port = 5555; //Port du serveur

    private int numConnexion; //Numéro de la connexion obtenue avec le serveur
    private String alias; //Alias de l'usager

    private VerifierClient vc; //Thread d'inpection du texte
    private String texte; //Texte recu du serveur
    private boolean activated = false; //activation du button parite
    public Client(String adr, String nom, int port) {
        this.adr = adr;
        this.port = port;
        alias = nom;
    }

    public Client(String adr, String nom) {
        this(adr, nom, 5555);
    }

    public Client(String nom) {
        this("127.0.0.1", nom, 5555);
    }

    public Client() {
        this("127.0.0.1", "", 5555);
    }

    public boolean estConnecte() {
        return clientSock != null; //ou utiliser etat != "DECONNECTE"
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String e) {
        etat = e;
        setChanged();
        notifyObservers();
    }
    public String getGame() {
        return etat;
    }

    public void setGame(String e) {
        etat = e;
        setChanged();
        notifyObservers();
    }

    public void setAlias(String a) {
        alias = a;
    }

    public String getAlias() {
        return alias;
    }

    public String getAdrServeur() {
        return adr;
    }

    public int getPortServeur() {
        return port;
    }

    public void setPortServeur(int p) {
        port = p;
        setChanged();
        notifyObservers();
    }

    public void setAdrServeur(String a) {
        adr = a;
        setChanged();
        notifyObservers();
    }

    public void envoyer(String message) {

        os.print(String.valueOf(numConnexion) + "|" + alias + ">>" + message);
        os.flush();
    }

    public boolean connecter() {
        if (!estConnecte()) {
            try {
                setEtat("RECH_SERVEUR");

                //Connecter au serveur
                clientSock = new Socket(adr, port); //On suppose que le serveur utilise le port 5555.

                is = new BufferedInputStream(clientSock.getInputStream());
                os = new PrintWriter(clientSock.getOutputStream());

                setEtat("CONNECTION");

                //Démarrer l'inspecteur
                vc = new VerifierClient(this);
                vc.start();
                return true;

		//	this.setTitle("CLIENT : " + alias);
            } catch (IOException e) {
                System.out.println("\nNO SERVER...");
                //System.exit(0);
            }
        }
        return false;
    }

    public boolean deconnecter() {
        if (!estConnecte()) {
            return true;
        }
        try {
            envoyer("STOP");
            vc.interrupt();
            clientSock.close();
            clientSock = null;
            setEtat("DECONNECTE");
            return true;
        } catch (IOException io) {
            return false;
        }
    }

    public void lire() {
        try {
            byte buf[] = new byte[500];	//buffer de lecture
            String msg;				//Texte lu
            is.read(buf);
            msg = (new String(buf)).trim(); 
            System.out.println(msg + "modele.Client.lire()");
            //En cours de connection?
            if (getEtat().equals("CONNECTION")) {
                //oui, obtenir le numéro de la connexion
                numConnexion = Integer.parseInt(msg);
                setEtat("CONNECTÉ");
            } 
            else 
            {
                if (!msg.contains("Activate"))
                {
                    texte = msg;
                    setChanged();
                    notifyObservers(texte);
                }
                else
                {
                    setGame("Activate");
                }
            }

            //Effacer le buffer
            buf = null;
        } catch (IOException e) {
        }
    } 
    public void ActiverButton(){
        System.out.println(activated +"before");
        if (numConnexion > 2)//starts at 0 
            {
                if (!activated)//  pour eviter la repetition de cette action
                {
                    this.envoyer("Activate");
                    activated = true;
                    setGame("Activate");
                }
            }
         System.out.println(activated +"after");
    }
}

class VerifierClient extends Thread {

    Client ref;

    public VerifierClient(Client cli) {
        ref = cli;
    }
   
    public void run() {
        while (true && !interrupted()) {
           ref.ActiverButton();//verifier le nombre de jouer
            //Lire le socket
            ref.lire();
            try {
                //Laisser une chance d'exécution aux autres threads
                Thread.sleep(100);
            } catch (Exception x) {
                System.out.println("Exception dans thread");
            }
        }
    }
}
