/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atoudeft.web.modele;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author usager
 */
public class Joueur {
    private String nom;
    private int numero;
    private int points;
    private String mdp;

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public Joueur(String nom, int numero, int points, String mdp, ArrayList<cartesBlanches> cartes) {
        this.nom = nom;
        this.numero = numero;
        this.points = points;
        this.mdp = mdp;
        this.cartes = cartes;
    }
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
     public Joueur(String name, int num,int pts){
        this.nom =name;
        this.numero =num;
        this.points =pts;
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

