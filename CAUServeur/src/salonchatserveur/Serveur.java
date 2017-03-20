//Laboratoire 1 : CORRECTION - Le serveur
package salonchatserveur;

import java.net.*;
import java.io.*;

public class Serveur {

    private ServerSocket serveurSock;
    private int port;

    private final int maxConnexions = 100;
    private Socket[] connexions = new Socket[maxConnexions]; //Maximum de 100 connexions
    private int nbConnexions = -1; //Nombre effectif de connexions

    private PrintWriter[] os = new PrintWriter[maxConnexions]; //Les flux de sortie
    private BufferedInputStream[] is = new BufferedInputStream[maxConnexions]; //Les flux d'entr�e

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

    //Envoyer un message � une connection sp�cifique:
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

            //Message � l'usager
            System.out.println("Serveur " + serveurSock
                    + " a l\'ecoute sur le port #" + serveurSock.getLocalPort());

        } catch (IOException e) {
            System.out.println("\nServeur d�j� actif sur ce port...");
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

            //M�morisation de la connection
            connexions[num] = sk;

            //Initialisation des entr�es/sorties :
            os[num] = new PrintWriter(connexions[num].getOutputStream());
            is[num] = new BufferedInputStream(connexions[num].getInputStream());

            //Premi�re connection?
            if (num == 0) {
                //Oui, d�marrer le thread inspecteur de texte :
                vt = new VerifierServeur(this);
                vt.start();
            }

            //Envoyer le num�ro de la connection au client
            this.envoyer(String.valueOf(num), num);

            nbConnexions++;
            
            //Message � l'usager
            System.out.println("Connexion " + num
                    + " sur le port #" + sk.getPort());
            
        } catch (IOException e) {
            System.out.println(e);
        }
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

                    //D�terminer la provenance (voir la m�thode envoyer() du client):
                    provenance = Integer.parseInt(texte.substring(0, texte.indexOf("|")));

                    for (int z = 0; z <= nbConnexions; z++) {
                        //Ne pas renvoyer le message � l'exp�diteur
                        if (z != provenance) {
                            this.envoyer(texte.substring(texte.indexOf("|") + 1), z);
                        }
                    }

                    //V�rifier si le client a demand� une d�connexion :
                    String msg = texte.substring(texte.indexOf(">>") + 2);
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

    //main() d�marre le serveur:
    public static void main(String args[]) {
        Serveur serveur = new Serveur();
        serveur.connecter();
    }
}

// Thread d'inspection d'arriv� de texte:
class VerifierServeur extends Thread {

    Serveur ref;

    public VerifierServeur(Serveur cs) {
        ref = cs;
    }

    public void run() {
        while (true) {
            ref.lire();
            try {
                //Laisser une chance d'ex�cution aux autres threads
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
                //Laisser une chance d'ex�cution aux autres threads
                Thread.sleep(10);
            } catch (Exception x) {
            }
        }
    }
}
