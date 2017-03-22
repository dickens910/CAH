
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
public class MainPanel extends JPanel implements Observer,ActionListener {

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
        button.addActionListener(this);
        //this.setSize(400, 6000);

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
             if (g.equals("Invited")){
                     button.setText("Accepter");
                 }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == button) {
            if(button.getText() !="Accepter")
            {
                // System.out.println("button cliked");
                client.envoyer(client.getAlias() + "    Vous a inviter");
                client.AccepterButton();
                button.setText("Accepted");
                client.envoyer("Accepted");
                button.setVisible(false);
            }
            else {
                client.envoyer("Accepted");
                button.setVisible(false);
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
