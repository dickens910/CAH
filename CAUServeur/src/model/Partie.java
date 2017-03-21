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
/**
 *
 * @author ali
 */

public class Partie {
    private Joueur[] joueurs;
    private Stack pileNoire = new Stack();
    private Stack pileBlanche= new Stack();
    private int limiteCartes= 10;
    
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
    
    
}
