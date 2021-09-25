package dao;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;

import bean.Dostavljac;
import bean.Korisnik;
import bean.Kupac;
import bean.Menadzer;


public class DostavljacDAO {

	
	private ArrayList<Dostavljac> dostavljaci = new ArrayList<Dostavljac>();
	private ArrayList<Korisnik> korisnici = new ArrayList<Korisnik>();
	
	public DostavljacDAO() {
		
	}
	
	public DostavljacDAO(String contextPath) throws IOException {
		loadDostavljaci(contextPath);
		loadKorisnike(contextPath);
	}
	
	
	private void loadDostavljaci(String contextPath) throws IOException {
		Gson gson = new GsonBuilder().create();
		FileInputStream fis = new FileInputStream(contextPath + "/Files/dostavljaci.json");
        byte[] buffer = new byte[10];
        StringBuilder sb = new StringBuilder();
        while (fis.read(buffer) != -1) {
        	sb.append(new String(buffer));
        	buffer = new byte[10];
        }
        fis.close();
        String content = sb.toString().trim();
        dostavljaci = gson.fromJson(content, new TypeToken<ArrayList<Dostavljac>>(){}.getType());
	}
	
	private void loadKorisnike(String  contextPath) throws IOException {
		Gson gson = new GsonBuilder().create();
		FileInputStream fis = new FileInputStream(contextPath + "/Files/korisnici.json");
        byte[] buffer = new byte[10];
        StringBuilder sb = new StringBuilder();
        while (fis.read(buffer) != -1) {
        	sb.append(new String(buffer));
        	buffer = new byte[10];
        }
        fis.close();
        String content = sb.toString().trim();
        korisnici = gson.fromJson(content, new TypeToken<ArrayList<Korisnik>>(){}.getType());
	}
	
	public void createDostavljac(Dostavljac novi, String contextPath) throws IOException {
		loadKorisnike(contextPath);
		dostavljaci.add(novi);
		Korisnik k = new Korisnik(novi.getKorisnickoIme(), novi.getLozinka(), novi.getIme(), novi.getPrezime(), novi.getPol(), novi.getDatumRodjenja(), novi.getUloga(), novi.getTipKupca());
		korisnici.add(k);
		System.out.println(novi);
		Writer writer = new FileWriter(contextPath + "/Files/dostavljaci.json");
		Writer writer2 = new FileWriter(contextPath + "/Files/korisnici.json");
		Gson gson = new GsonBuilder().create();

	    try {
			gson.toJson(dostavljaci, writer);
			gson.toJson(korisnici, writer2);
			
		} catch (JsonIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Tu nesto ne valja!!!!");
		}

        writer.flush(); //flush data to file   <---
        writer.close();
        writer2.flush(); //flush data to file   <---
        writer2.close();
	}
	
	public void write(String contextPath) throws IOException {
		Writer writer = new FileWriter(contextPath + "/Files/dostavljaci.json");
		Writer writer2 = new FileWriter(contextPath + "/Files/korisnici.json");
		Gson gson = new GsonBuilder().create();

	    try {
			gson.toJson(dostavljaci, writer);
			gson.toJson(korisnici, writer2);
			
		} catch (JsonIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Tu nesto ne valja!!!!");
		}

        writer.flush(); //flush data to file   <---
        writer.close();
        writer2.flush(); //flush data to file   <---
        writer2.close();
	}
	
	public Dostavljac pronadjiDostavljaca(String username) {
		if (getDostavljaci() != null) {
			for (Dostavljac d : getDostavljaci()) {
				if (d.getKorisnickoIme().equals(username)) {
					return d;
				}
			}
		}
		return null;
	}
	
	public void updateDostavljac(Dostavljac novi) {
		for (int i=0; i<dostavljaci.size(); i++) {
			if(dostavljaci.get(i).getKorisnickoIme().equals(novi.getKorisnickoIme())) {
				dostavljaci.set(i, novi);
			}
		}
		
		for (int i=0; i<korisnici.size(); i++) {
			if(korisnici.get(i).getKorisnickoIme().equals(novi.getKorisnickoIme())) {
				Korisnik k = new Korisnik(novi.getKorisnickoIme(), novi.getLozinka(), novi.getIme(), novi.getPrezime(), novi.getPol(), novi.getDatumRodjenja(), novi.getUloga(), novi.getTipKupca());
				korisnici.set(i, k);
			}
		}
	}
	
	public void updateDostavljaca(Dostavljac novi) {
		for (Dostavljac stari : dostavljaci) {
			if (stari.getKorisnickoIme().equals(novi.getKorisnickoIme())) {
				stari = novi;
			}
		}
	}
	
	
	
	//---------------------GET I SET -----------------------------
	
	public ArrayList<Dostavljac> getDostavljaci() {
		if (!dostavljaci.isEmpty()) {
			return dostavljaci;
		}
		return null;
	}
	
}
