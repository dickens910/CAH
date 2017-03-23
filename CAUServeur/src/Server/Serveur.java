//Laboratoire 1 : CORRECTION - Le serveur
package Server;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import model.Joueur;
import model.Partie;
import model.cartesBlanches;
import model.cartesNoire;
//import org.apache.commons.la;


public class Serveur {

    private ServerSocket serveurSock;
    private int port;
    private final int maxConnexions = 12;
    private Socket[] connexions = new Socket[maxConnexions]; //Maximum de 12 connexions
    private int nbConnexions = -1; //Nombre effectif de connexions
    private PrintWriter[] os = new PrintWriter[maxConnexions]; //Les flux de sortie
    private BufferedInputStream[] is = new BufferedInputStream[maxConnexions]; //Les flux d'entrée
    public static Partie p = new Partie();
    public boolean game  = false; //game is started
    public boolean acc = false;// game is accepted
    public int conexionsAccepted = -1;
    private VerifierServeur vt; //Thread d'inspection du serveur

    public Serveur(int p) {
        port = p;
    }

    public Serveur() {
        this(5555);
    }

    public int getPort() {
        return port;
    }

    public void setPort(int p) {
        port = p;
    }

    //Envoyer un message à une connection spécifique:
    private void envoyer(String msg, int i) {
        if (os[i] == null) {
            return;
        }
        os[i].print(msg);
        os[i].flush();
    }

    //Connecter comme Serveur
    private void connecter() {
        try {
            //Cr�ation du socket
            serveurSock = new ServerSocket(port);

            //D�marrer l'inspecteur
            VerifierConnexion vc = new VerifierConnexion(this);
            vc.start();

            //Message à l'usager
            System.out.println("Serveur " + serveurSock
                    + " a l\'ecoute sur le port #" + serveurSock.getLocalPort());

        } catch (IOException e) {
            System.out.println("\nServeur déjà actif sur ce port...");
        }
    }

    //==================================================
    //Attente d'une connection
    //==================================================
    public void attente() {
        try {
            int num = nbConnexions + 1;
            
            //Attente d'une connection :
            Socket sk = serveurSock.accept();

            //Mémorisation de la connection
            connexions[num] = sk;

            //Initialisation des entrées/sorties :
            os[num] = new PrintWriter(connexions[num].getOutputStream());
            is[num] = new BufferedInputStream(connexions[num].getInputStream());

            //Première connection?
            if (num == 0) {
                //Oui, démarrer le thread inspecteur de texte :
                vt = new VerifierServeur(this);
                vt.start();
            }

            //Envoyer le numéro de la connection au client
            this.envoyer(String.valueOf(num), num);
            nbConnexions++;
            //Message à l'usager
            System.out.println("Connexion " + num
                    + " sur le port #" + sk.getPort());
            
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    public String getPlayerName(String texte){
        String n = texte.substring(0,texte.indexOf(">>") );
        String name = n.substring(texte.indexOf("|")+1);
            return name ;             
    }
    //==================================================
    //Lire le socket
    //==================================================
    public void lire() {
        try {
            //buffer de lecture
            byte buf[] = new byte[500];
            String texte;
            int provenance; //provenance du texte
            
            //Lire toutes les connections
            for (int i = 0; i <= nbConnexions; i++) {
                //La connection est-elle active?
                if (is[i] != null && is[i].available() > 0) {
                    //Oui, lire le socket
                    is[i].read(buf);
                    texte = (new String(buf)).trim();
                    provenance = Integer.parseInt(texte.substring(0, texte.indexOf("|")));
                    //Vérifier si le client a demandé une déconnexion :
                   String msg = texte.substring(texte.indexOf(">>") + 2); 
                   if (msg.equals("Activate"))
                    for (int z = 0; z <= nbConnexions; z++) {
                            this.envoyer(msg, z);
                    }
                   else if (msg.contains("Accepted")){//une parite est accepte 
                       Joueur player = new Joueur(getPlayerName(texte),provenance);
                        player.setCartes(p.distributeWhiteCards());
                        p.add(player);
                        acc = true;
                        conexionsAccepted++;
                        if (conexionsAccepted==nbConnexions){game = true;} //start game when all are conected
                        this.envoyer(player.getCartes().toString().replaceAll(",", "\n"),player.getNumero());
                        if (acc){
                            if (game)
                            {
                               String blacCard = p.pickBlackCard().toString();
                               for (int z = 0; z <= nbConnexions; z++) {
                                   this.envoyer(blacCard,z);
                                }
                            }
                        }
                   }
                   else 
                   { //Déterminer la provenance (voir la méthode envoyer() du client):
                        for (int z = 0; z <= nbConnexions; z++) {
                            //Ne pas renvoyer le message à l'expéditeur
                            if (z != provenance) {
                                this.envoyer(texte.substring(texte.indexOf("|") + 1), z);
                            }
                        }
                   }
                    if (msg.equals("STOP")) {
                        try {
                            is[provenance].close();
                            os[provenance].close();
                            connexions[provenance].close();
                            is[provenance] = null;
                            os[provenance] = null;
                            connexions[provenance] = null;
                            System.out.println("TODO : Deconnecter " + texte);
                        } catch (Exception x) {
                            x.printStackTrace();
                        }
                    }
                    //Effacer le buffer
                    buf = null;
                }
            }
             
        } catch (IOException e) {
        }
    }

    //main() démarre le serveur:
    public static void main(String args[]) {
        Serveur serveur = new Serveur();
        serveur.connecter();
    }
}

// Thread d'inspection d'arrivé de texte:
class VerifierServeur extends Thread {

    Serveur ref;

    public VerifierServeur(Serveur cs) {
        ref = cs;
    }

    public void run() {
        while (true) {
            ref.lire();
            try {
                //Laisser une chance d'exécution aux autres threads
                Thread.sleep(10);
            } catch (Exception x) {
            }
        }
    }
}

// Thread d'inspection des connections
class VerifierConnexion extends Thread {

    Serveur ref;

    public VerifierConnexion(Serveur cs) {
        ref = cs;
    }

    public void run() {
        while (true) {
            ref.attente();
            try {
                //Laisser une chance d'exécution aux autres threads
                Thread.sleep(10);
            } catch (Exception x) {
            }
        }
    }
}
