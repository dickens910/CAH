/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atoudeft.web.implementation;
import com.atoudeft.web.DAO.Dao;
import com.atoudeft.web.modele.Client;
import com.atoudeft.web.modele.cartesBlanches;
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
public class WhiteCardsDao extends Dao<Client>{

    public WhiteCardsDao(Connection cnx) {
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
    public List<cartesBlanches> findCards(){
        List<cartesBlanches> listcard = new ArrayList();
               Statement stm = null;
            try {
            stm = cnx.createStatement(); 
            ResultSet rs = stm.executeQuery("SELECT * FROM whitecards");
            
             while (rs.next()) {
                String deck = rs.getString("white");

                //Assuming you have a user object
                cartesBlanches wCards = new cartesBlanches(deck);

                listcard.add(wCards);   
            }
        } 
        catch (SQLException ex) {
            Logger.getLogger(WhiteCardsDao.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return listcard;
    }

    @Override
    public List<Client> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
