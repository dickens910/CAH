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
    private Stack pileNoire = new Stack();
    private Stack pileBlanche= new Stack();
    private int limiteCartes= 10;
    public static  Parser parser  = new Parser();
    public static ArrayList listeNoires = parser.ParseBlackCards();
    public static ArrayList listeBlancs = parser.ParseWhiteCards();
    
    public Partie(){
        this.joueurs= new Joueur[3];
    }
     public Partie(int nbJoueurs){
        this.joueurs= new Joueur[nbJoueurs];
    }
    
    public Joueur getJoueur(int numero){
    for (int i=0;i<joueurs.length;i++){
        if (joueurs[i].getNumero()==numero)
            return joueurs[i];
    }
    return null;
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
    public void distributeWhiteCards(){
        for (int i = 0; i < 10 ;i++) {
            if ( listeBlancs.size()> 0)
            {
                int nombreAleatoire = 0 + (int)(Math.random() * ((listeBlancs.size() ) ));
                System.out.println(listeBlancs.get(nombreAleatoire)+"--"+listeBlancs.size());
                listeBlancs.remove(nombreAleatoire); 
            }     
        }
    } 
    
}

