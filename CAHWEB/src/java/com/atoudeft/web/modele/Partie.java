/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atoudeft.web.modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import cardParser.Parser;
/**
 *
 * @author alP
 */

public class Partie {
    private Joueur[] Joueur;
    //private Stack pileNoire = new Stack();
   // private Stack pileBlanche= new Stack();
    private int cartesRestantes = 10;
    public Stack playerList = new Stack(); 
    public static  Parser parser  = new Parser();
    public static ArrayList listeNoires = parser.ParseBlackCards();
    public static ArrayList listeBlancs = parser.ParseWhiteCards();
    private boolean firstRound = true;
    public int cZar;
    public int JoueurCree = 0;
    
    public Partie(){
        this.Joueur= new Joueur[3];
    }
     public Partie(int nbJoueur){
        this.Joueur= new Joueur[nbJoueur];
    }
    public void getAll(){
        for (int i=0;i<JoueurCree;i++){
           // Joueur[i].setCartes(distributeWhiteCards());
        }
    }
    public Joueur getJoueur(int numero){
    for (int i=0;i<Joueur.length;i++){
        if (Joueur[i].getNumero()==numero)
            return Joueur[i];
        }
      return null;
    }
    public void add(Joueur j){;
       if (JoueurCree < Joueur.length){
        if(this.Joueur[JoueurCree]==null){ //case accepts twice
                this.Joueur[JoueurCree] = j;
        JoueurCree++;
        }
       
       }
    }
    public void setJoueur(List Joueur){
    Iterator itr = Joueur.iterator();
    int i=0;
        while(itr.hasNext()){
            //Joueur j = itr.next();
            this.Joueur[i]=(Joueur)Joueur.get(i);
            i++;
        }
    }
   public String pickBlackCard(){
         boolean condition = false; // to only get 1 card 
         String card;  
            if ( listeNoires.size()> 0)
            {
                int nombreAleatoire = 0 + (int)(Math.random() * ((listeNoires.size() ) ));
                System.out.println(listeNoires.get(nombreAleatoire)+"--"+listeNoires.size());
                listeNoires.remove(nombreAleatoire); 
               return card = listeNoires.get(nombreAleatoire).toString() ;      
            }
        return "a";
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
        for (int i = 0; i < Joueur.length ; i++) {
          //  Joueur[i].setCartes(distributeWhiteCards());
        }
    }
    public int findWinner(){
        int winnerIndex = -1 ;//
        int pts = 0;
            for (int i = 0; i < Joueur.length; i++) {
                if (Joueur[i].getPoints()> pts)
                    {  
                        pts = Joueur[i].getPoints();
                        winnerIndex = i; 
                    }
        }
        return winnerIndex;
    }
    public void round(){
        if (firstRound) {
           int cZar = 0 + (int)(Math.random() * ((Joueur.length ) ));
           firstRound = false;
        }
       while (cartesRestantes > 0) {
            for (int i = 0; i < Joueur.length; i++) {
               if (i == cZar) {
                    //Joueur[i];
                    //function select best received white card
                    //numero carte == numero jouer so 
                   //getJoueur(numeroCarte)
                  // Joueur[i].augmenterPoints();
                }
       
            else {
                   
                    //Joueur[i];
                    //function choose white card
                    }
            }
            if (cZar == Joueur.length) { cZar = 0; }
            else { cZar++;}
            cartesRestantes--;
        }
       // int i = findWinner(); 
//        System.out.println("Partie Terminée , le gagnant est "+ Joueur[i].getNom()+ " avec " +Joueur[i].getPoints() + "    points");
    }
    /* public static void main(String args[]) {
           Partie pa = new Partie(3);
            Joueur j = new Joueur("yves",1,10);
            Joueur j2 = new Joueur("ali",2,4);
            Joueur j3 = new Joueur("s",3,9);
            pa.add(j);
            pa.add(j2);
            pa.add(j3);
            pa.distributeWhiteCards();
            pa.round();    
    }*/

}

