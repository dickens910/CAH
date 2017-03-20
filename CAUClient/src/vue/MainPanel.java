
Skip to content
This repository

    Pull requests
    Issues
    Gist

    @yves93

1
0

    0

yves93/HumanityCards
Code
Issues 0
Pull requests 0
Projects 0
Wiki
Pulse
Graphs
Settings
HumanityCards/salonChat/SalonChatClient/src/vue/MainPanel.java
f3da1fd 4 days ago
@yves93 yves93 Start game appears on > 2 players
118 lines (97 sloc) 3.15 KB
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import javax.swing.*;
import javax.swing.border.*; //pour les bordures

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import modele.*;

/**
 * @author A. Toudeft
 */
public class MainPanel extends JPanel implements Observer {

//  Modele :
    Client client;
//	Composants pour l'interface :
//	Panneaux:
    JPanel centerPanel;
    JPanel northPanel;

//	Composants:	
    JTextField tSaisie;
    JTextArea taSalon;
    MainPanelActionListener mpal = new MainPanelActionListener();
    MainPanelKeyListener mpkl = new MainPanelKeyListener();
//boutton pour demarrer partie (min 3 users)
    JButton button = new JButton("Demarrer");
    /**
     *
     */
    public MainPanel() {
        super();
//		Panneaux et composants:	 	 
        centerPanel = new JPanel();
        Border bordure1 = BorderFactory.createLineBorder(Color.blue, 1);
        tSaisie = new JTextField(10);
        taSalon = new JTextArea(20, 10);

        tSaisie.addKeyListener(mpkl);

        taSalon.setBorder(bordure1);
        taSalon.setEditable(false);

        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(button,BorderLayout.EAST);
        centerPanel.add(tSaisie, BorderLayout.NORTH);
        centerPanel.add(new JScrollPane(taSalon), BorderLayout.CENTER);
        setLayout(new BorderLayout());
        add(centerPanel);
        button.setVisible(false);
        setVisible(false);

    }

    public void update(Observable o, Object t) {
        if (o instanceof Client) {
            if (client == null) {
                client = (Client) o;
            }

            String s = ((Client) o).getEtat();
            String g = ((Client) o).getGame();

            if (t != null) {
                taSalon.append("\n" + t);
            } else if (s.equals("RECH_SERVEUR")) {
                setVisible(true);
                taSalon.append("\nRecherche d'un serveur...");
            } else if (s.equals("CONNECTION")) {
                taSalon.append("\nConnection établie");
                taSalon.append("\n------------------");
            } else if (s.equals("DECONNECTE")) {
                setVisible(false);
            }
             if (!button.isVisible()){
                 if (g.equals("Activate"))
                 { button.setVisible(true); }
             }
        }
    }
    
    class MainPanelActionListener implements ActionListener {

        public void actionPerformed(ActionEvent evt) {
            Object source = evt.getSource();
        }
    }

    class MainPanelKeyListener implements KeyListener {

        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                String s = tSaisie.getText();
                if (!s.equals("")) {
                    client.envoyer(s);
                    taSalon.append("\n>" + s);
                    tSaisie.setText("");
                }
            }
        }

        public void keyReleased(KeyEvent e) {
        }

        public void keyTyped(KeyEvent e) {
        }
    }
}

    Contact GitHub API Training Shop Blog About 

    © 2017 GitHub, Inc. Terms Privacy Security Status Help 

