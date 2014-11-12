package com.bourse;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;

public class DataManager extends Thread{

	private String url=null;
	private Manager manager;
	public DataManager(Manager m) {
		this.manager=m;
	}
	 
	public void run(){
		System.out.println("Démarrage du data manager");
		while (true){
			this.waiting();
			try {
				DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
				HttpPost httppost = new HttpPost(url);
				
				System.out.println("POST");
				HttpResponse response = httpclient.execute(httppost);
				System.out.println("RETOUR POST");
				HttpEntity entity = response.getEntity();
				analyseListTransaction(entity.getContent());
				manager.setStatus("Transaction terminée");
				manager.notifyToView();
				
			}catch (Exception e){
				manager.setStatus("Problème réseau");
				manager.notifyToView();
				e.printStackTrace();
			}
		}
	}

	
	private void analyseListTransaction(InputStream inputStream){
		try {
			manager.getListTransactions().clear();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(inputStream, "UTF-8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
				if(line.startsWith("					<td  width=\"33%\" align=\"center\">")){
					Transaction tr=new Transaction();
					String date=null;
					String montant=null;
					String nombre=null;

					//date
					int posEnd=line.indexOf("</td>");
					date=line.substring(37, posEnd);
					//montant
					line = reader.readLine();
					posEnd=line.indexOf("</td>");
					montant=line.substring(37, posEnd);
					//nbre
					line = reader.readLine();
					posEnd=line.indexOf("</td>");
					nombre=line.substring(37, posEnd);
					
					SimpleDateFormat formatDate=new SimpleDateFormat("HH:mm:ss");
					Date dte=formatDate.parse(date);
					nombre=nombre.replace(" ", "");
					tr.setHeure(dte);
					tr.setMontant(Double.parseDouble(montant));
					tr.setNombre(Integer.parseInt(nombre));
					manager.getListTransactions().add(tr);
					//System.out.println(tr.toString());
				}
			}
			manager.displayScreen("ListeTransactions");
			
		}catch (Exception e){
			manager.setStatus("Problème lecture data transaction");
			manager.notifyToView();
			e.printStackTrace();
		}
		
		
	}

	
	
	public synchronized void sendHttp(final String url){
				this.url=url;
				notifyAll();
				

	}

	private synchronized void waiting(){
		try{
			System.out.println("DataManager waiting..");
			this.wait();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
