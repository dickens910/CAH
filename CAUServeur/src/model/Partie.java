/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import cardParser.Parser;
/**
 *
 * @author ali
 */

public class Partie {
    private Joueur[] joueurs;
    //private Stack pileNoire = new Stack();
   // private Stack pileBlanche= new Stack();
    private int cartesRestantes = 10;
    public Stack playerList = new Stack(); 
    public static  Parser parser  = new Parser();
    public static ArrayList listeNoires = parser.ParseBlackCards();
    public static ArrayList listeBlancs = parser.ParseWhiteCards();
    private boolean firstRound = true;
    public int cZar;
    public int joueursCree = 0;
    
    public Partie(){
        this.joueurs= new Joueur[3];
    }
     public Partie(int nbJoueurs){
        this.joueurs= new Joueur[nbJoueurs];
    }
    public void getAll(){
        for (int i=0;i<joueursCree;i++){
            System.out.println(joueurs[i].getNom()+" ...ALL... "+i);
            //System.out.println(this.distributeWhiteCards()+" ...Cartes... "+i);
            joueurs[i].setCartes(distributeWhiteCards());
            System.out.println(joueurs[i].getCartes()+" ...Cartes... "+i);

        }
    }
    public Joueur getJoueur(int numero){
    for (int i=0;i<joueurs.length;i++){
        if (joueurs[i].getNumero()==numero)
            return joueurs[i];
        }
      return null;
    }
    public void add(Joueur j){;
       if (joueursCree < joueurs.length){
        if(this.joueurs[joueursCree]==null){ //case accepts twice
                this.joueurs[joueursCree] = j;
        joueursCree++;
        }
       
       }
    }
    public void setJoueurs(List joueurs){
    Iterator itr = joueurs.iterator();
    int i=0;
        while(itr.hasNext()){
            //Joueur j = itr.next();
            this.joueurs[i]=(Joueur)joueurs.get(i);
            i++;
        }
    }
   public void pickBlackCard(){
         boolean condition = false; // to only get 1 card 
        while ( !condition) {   
            if ( listeNoires.size()> 0)
            {
                int nombreAleatoire = 0 + (int)(Math.random() * ((listeNoires.size() ) ));
                System.out.println(listeNoires.get(nombreAleatoire)+"--"+listeNoires.size());
                listeNoires.remove(nombreAleatoire); 
                condition = true;
            }     
        }
    }
    public ArrayList distributeWhiteCards(){
      ;ArrayList<String> deck = new ArrayList<String>();
        for (int i = 0; i < 10 ;i++) {
            if ( listeBlancs.size()> 0)
            {
                int nombreAleatoire = 0 + (int)(Math.random() * ((listeBlancs.size() ) ));
                String liste = (listeBlancs.get(nombreAleatoire)+"--"+listeBlancs.size());
               deck.add(liste);
                listeBlancs.remove(nombreAleatoire); 
            }     
        }
        return deck;
    } 
    public void giveCardsPlayers(){
        for (int i = 0; i < joueurs.length ; i++) {
            joueurs[i].setCartes(distributeWhiteCards());
            //System.out.println( joueurs[i].getCartes().get(i)+ " cartes");
           // System.out.println( joueurs[i].getNom()+ "joeurs");
        }
    }
    public int findWinner(){
        int winnerIndex = -1 ;//
        int pts = 0;
            for (int i = 0; i < joueurs.length; i++) {
                if (joueurs[i].getPoints() > pts)
                    {  
                        pts = joueurs[i].getPoints();
                        winnerIndex = i; 
                    }
        }
        return winnerIndex;
    }
    public void round(){
        giveCardsPlayers();
        if (firstRound) {
           int cZar = 0 + (int)(Math.random() * ((joueurs.length ) ));
           firstRound = false;
        }
       while (cartesRestantes > 0) {
            for (int i = 0; i < joueurs.length; i++) {
               if (i == cZar) {
                    //joueurs[i];
                    //function select best received white card
                    //numero carte == numero jouer so 
                   //getJoueur(numeroCarte)
                 //  joueurs[i].augmenterPoints();
                }
       
            else {
                    //joueurs[i];
                    //function choose white card
                    }
            }
            if (cZar == joueurs.length) { cZar = 0; }
            else { cZar++;}
            cartesRestantes--;
        }
        int i = findWinner(); 
//        System.out.println("Partie Terminée , le gagnant est "+ joueurs[i].getNom()+ " avec " +joueurs[i].getPoints() + "    points");
    }
     public static void main(String args[]) {
           Partie pa = new Partie(3);
            Joueur j = new Joueur("yves",1,10);
            Joueur j2 = new Joueur("ali",2,4);
            Joueur j3 = new Joueur("s",3,9);
            pa.add(j);
            pa.add(j2);
            pa.add(j3);
            pa.distributeWhiteCards();
            pa.round();    
    }

}

