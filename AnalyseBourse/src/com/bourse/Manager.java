package com.bourse;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.swing.JPanel;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;

import com.bourse.ui.ChoisirActionForm;
import com.bourse.ui.ListTransactionsTable;

public class Manager extends Observable{
	private String result = null;
	private String status="";
	private DataManager dataManager;
	
	private JPanel mainPanel=null;
	
	public JPanel getCurrentScreen() {
		return currentScreen;
	}


	public void setCurrentScreen(JPanel currentScreen) {
		this.currentScreen = currentScreen;
	}

	private JPanel currentScreen=null;

	// DATA
	private List<Transaction> listTransactions=new ArrayList<Transaction>();
	private String currentAction=null;
	
	public String getCurrentAction() {
		return currentAction;
	}


	public void setCurrentAction(String currentAction) {
		this.currentAction = currentAction;
	}


	public List<Transaction> getListTransactions() {
		return listTransactions;
	}


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Manager(JPanel jp) {
		mainPanel=jp;
		dataManager=new DataManager(this);
		dataManager.start();
	}

	public void displayScreen(String screen){
		status="CONSTRUCTION DE L ECRAN :"+screen;
		notifyToView();
		mainPanel.remove(currentScreen);
		if(screen.equals("ListeTransactions")){
			currentScreen=new ListTransactionsTable(this);
			currentScreen.setVisible(false);
			mainPanel.add(currentScreen);
			currentScreen.setVisible(true);
		}
		if(screen.equals("RechercheAction")){
			currentScreen=new ChoisirActionForm(this);
			currentScreen.setVisible(false);
			mainPanel.add(currentScreen);
			currentScreen.setVisible(true);
		}
		status="FIN AFFICHAGE :"+screen;
		notifyToView();
		
	}
	
	public void notifyToView(){
		setChanged();
		notifyObservers();
	}
	
	public void sendGetActionTransaction(String symbol){
		System.out.println("Transactions :"+symbol);
		currentAction=symbol;
		status="Recherche des transactions";
		setChanged();
		notifyObservers();
		dataManager.sendHttp("http://www.boursorama.com/bourse/cours/includes/last_transactions.phtml?symbole=1rP"+symbol);
	}


	
	
}
