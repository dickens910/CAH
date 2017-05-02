/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atoudeft.web.implementation;
import com.atoudeft.web.DAO.Dao;
import com.atoudeft.web.modele.Client;
import com.atoudeft.web.modele.cartesNoire;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author usager
 */
public class BlackCardsDao extends Dao<Client>{

    public BlackCardsDao(Connection cnx) {
        super(cnx);
    }

    @Override
    public boolean create(Client x) {
       return false;
    }

    @Override
    public Client read(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Client read(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(Client x) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Client x) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public List<cartesNoire> findCards(){
        List<cartesNoire> listcard = new ArrayList();
               Statement stm = null;
            try {
            stm = cnx.createStatement(); 
            ResultSet rs = stm.executeQuery("SELECT * FROM blackcards");
            
             while (rs.next()) {
                String deck = rs.getString("black");

                //Assuming you have a user object
                cartesNoire wCards = new cartesNoire(deck);

                listcard.add(wCards);   
            }
        } 
        catch (SQLException ex) {
            Logger.getLogger(BlackCardsDao.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return listcard;
    }

    @Override
    public List<Client> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
