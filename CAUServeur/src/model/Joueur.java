/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author usager
 */
public class Joueur {
    private String nom;
    private int numero;
    private int points;
    private ArrayList<cartesBlanches> cartes;
    
    public Joueur(String _name,int num,int pointage,ArrayList<cartesBlanches> _cartes){
    this.nom=_name;
    this.numero=num;
    this.points=pointage;
    this.cartes=_cartes;
    }
    public Joueur(String name,int num,ArrayList<cartesBlanches> bCartes){
    this.nom=name;
    this.numero=num;
    this.points=0;
    this.cartes=bCartes;
    }
    public Joueur(String name, int num){
        this.nom =name;
        this.numero =num;
        this.points =0;
        this.cartes = new ArrayList<cartesBlanches>();
    }

    /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param nom the nom to set
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * @return the numero
     */
    public int getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }

    /**
     * @return the points
     */
    public int getPoints() {
        return points;
    }

    /**
     * @param points the points to set
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * @return the cartes
     */
    public ArrayList<cartesBlanches> getCartes() {
        return cartes;
    }

    /**
     * @param cartes the cartes to set
     */
    public void setCartes(ArrayList<cartesBlanches> cartes) {
        this.cartes = cartes;
    }
    
    public Boolean ajouterCarte(cartesBlanches c){
    return this.cartes.add(c);
    }
    public Boolean RetirerCarte(cartesBlanches c){
    return this.cartes.remove(c);
    }
    public void augmenterPoints(){
        this.points= this.points+1; 
    }
    public String toString(){
        return ""+this.points;
    }
}

