package com.atoudeft.web.modele;

public class Client {

	private String  nom, courriel, mdp;
        private int pts;
        public Client() {
        }
        
	public Client(int pts, String nom, String courriel, String mdp) {
		this.pts = pts;
		this.nom = nom;
		this.courriel = courriel;
		this.mdp = mdp;
	}
        public Client( String nom,int pts) {
		this.pts = pts;
		this.nom = nom;
	}

	public int getPts() {
		return pts;
	}

	public String getCourriel() {
		return courriel;
	}

	public void setPts(int pts) {
		this.pts = pts;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.pts+","+this.nom+","+this.courriel+","+this.mdp;
	}

    public String getNom() {
        return nom;
    }

    public String getVille() {
        return mdp;
    }

    public void setCourriel(String courriel) {
        this.courriel = courriel;
    }

    public void setVille(String mdp) {
        this.mdp = mdp;
    }
	
}
