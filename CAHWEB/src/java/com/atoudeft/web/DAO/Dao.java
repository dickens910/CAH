/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atoudeft.web.DAO;

/**
 *
 * @author usager
 */
import java.sql.Connection;
import java.util.List;

public abstract class Dao<T> {
	protected Connection cnx;

	public Dao(Connection cnx) {
		//super();
		this.cnx = cnx;
	}
	public Connection getCnx() {
		return cnx;
	}
	public void setCnx(Connection cnx) {
		this.cnx = cnx;
	}
	public abstract boolean create(T x);    //INSERT
	public abstract T read(int id);         //SELECT
	public abstract T read(String id);      //SELECT
	public abstract boolean update(T x);    //UPDATE
	public abstract boolean delete(T x);    //DELETE
	public abstract List<T> findAll();      //SELECT
}
