/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atoudeft.web.implementation;
import com.atoudeft.web.DAO.Dao;
import com.atoudeft.web.modele.Client;
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
public class ClientDao extends Dao<Client>{

    public ClientDao(Connection cnx) {
        super(cnx);
    }

    @Override
    public boolean create(Client x) {
       // TODO Auto-generated method stub
	
		String req = "INSERT INTO usager ( `pts`,`usager` ,  `courriel`) "+
			     "VALUES ('"+x.getPts()+"','"+x.getNom()+"','"+x.getCourriel()+"')";

		//System.out.println("REQUETE "+req);

		Statement stm = null;
		try 
		{
			stm = cnx.createStatement(); 
			int n= stm.executeUpdate(req);
			if (n>0)
			{
				stm.close();
				return true;
			}
		}
		catch (SQLException exp)
		{
		}
		finally
		{
			if (stm!=null)
			try {
				stm.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}

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

    @Override
    public List<Client> findAll() {
           List<Client> listeClient = new ArrayList();
               Statement stm = null;
			
        try {
            stm = cnx.createStatement(); 
            ResultSet rs = stm.executeQuery("SELECT usager, pts FROM usager order by pts DESC");
            
             while (rs.next()) {
                int pts = rs.getInt("pts");
                String usager = rs.getString("usager");

                //Assuming you have a user object
                Client user = new Client( usager , pts);

                listeClient.add(user);   
            }
        } 
        catch (SQLException ex) {
            Logger.getLogger(ClientDao.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return listeClient;
    }
}
