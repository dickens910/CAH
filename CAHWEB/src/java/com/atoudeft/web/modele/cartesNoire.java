/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atoudeft.web.modele;

/**
 *
 * @author usager
 */
import java.io.Serializable;
public class cartesNoire implements Serializable {
  private String texte;
  
  public cartesNoire(String texte){
      this.texte=texte;
  }
  public cartesNoire(){
      this.texte="";
  }

    /**
     * @return the texte
     */
    public String getTexte() {
        return texte;
    }

    /**
     * @param texte the texte to set
     */
    public void setTexte(String texte) {
        this.texte = texte;
    }
  
 
}
