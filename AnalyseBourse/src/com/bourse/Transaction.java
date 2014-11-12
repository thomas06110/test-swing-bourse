package com.bourse;

import java.util.Date;

public class Transaction {
	private Date heure=null;
	private int nombre;
	private double montant;
	

	public Date getHeure() {
		return heure;
	}


	public void setHeure(Date heure) {
		this.heure = heure;
	}


	public int getNombre() {
		return nombre;
	}


	public void setNombre(int nombre) {
		this.nombre = nombre;
	}


	public double getMontant() {
		return montant;
	}


	public void setMontant(double montant) {
		this.montant = montant;
	}

	public String toString(){
		return(heure+"->"+montant+"->"+nombre);
	}

	public Transaction() {
	}

}
