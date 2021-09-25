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

import bean.Administrator;
import bean.Korisnik;


public class AdministratorDAO {

	private String putanja;
	private ArrayList<Administrator> administratori = new ArrayList<Administrator>();
	private ArrayList<Korisnik> korisnici = new ArrayList<Korisnik>();
	
	public AdministratorDAO() {
		
	}
	
	public AdministratorDAO(String contextPath) throws IOException {
		loadAdministratori(contextPath);
		loadKorisnike(contextPath);
		if(administratori.isEmpty()) {
			createAdmin(contextPath);
		}
	}
	
	public void write(String contextPath) throws IOException {
		Writer writer = new FileWriter(putanja);
		Writer writer2 = new FileWriter(contextPath + "/Files/korisnici.json");
        Gson gson = new GsonBuilder().create();
 
	    try {
			gson.toJson(administratori, writer);
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
	
	private void loadAdministratori(String contextPath) throws IOException {
		Gson gson = new GsonBuilder().create();
		FileInputStream fis = new FileInputStream(contextPath + "/Files/administratori.json");
		putanja = contextPath + "/Files/administratori.json";
        byte[] buffer = new byte[10];
        StringBuilder sb = new StringBuilder();
        while (fis.read(buffer) != -1) {
        	sb.append(new String(buffer));
        	buffer = new byte[10];
        }
        fis.close();
        String content = sb.toString().trim();
        administratori = gson.fromJson(content, new TypeToken<ArrayList<Administrator>>(){}.getType());
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
	
	public void editAdmin(Administrator novi, String contextPath) throws IOException {
		loadKorisnike(contextPath);
		obrisiAdmina(novi.getKorisnickoIme());
		administratori.add(novi);
		Korisnik k = new Korisnik(novi.getKorisnickoIme(), novi.getLozinka(), novi.getIme(), novi.getPrezime(), novi.getPol(), novi.getDatumRodjenja(), novi.getUloga(), novi.getTipKupca());
		obrisiKorisnika(novi.getKorisnickoIme());
		korisnici.add(k);
		System.out.println(novi);
		Writer writer = new FileWriter(contextPath + "/Files/administratori.json");
		Writer writer2 = new FileWriter(contextPath + "/Files/korisnici.json");
        Gson gson = new GsonBuilder().create();
 
	    try {
			gson.toJson(administratori, writer);
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
	
	public void updateAdministratora(Administrator novi) {
		for (int i=0; i<administratori.size(); i++) {
			if(administratori.get(i).getKorisnickoIme().equals(novi.getKorisnickoIme())) {
				administratori.set(i, novi);
			}
		}
		
		for (int i=0; i<korisnici.size(); i++) {
			if(korisnici.get(i).getKorisnickoIme().equals(novi.getKorisnickoIme())) {
				Korisnik k = new Korisnik(novi.getKorisnickoIme(), novi.getLozinka(), novi.getIme(), novi.getPrezime(), novi.getPol(), novi.getDatumRodjenja(), novi.getUloga(), novi.getTipKupca());
				korisnici.set(i, k);
			}
		}
		
	}
	
	private void createAdmin(String contextPath) throws IOException {
		
//		Administrator markoAdmin = new Administrator("marko123", "123", "Marko", "Markovic", "musko", null,
//				Uloga.Administrator, null);
//		Korisnik k = markoAdmin;
//		
//		korisnici.add(k);
//		administratori.add(markoAdmin);
//		System.out.println(markoAdmin);
		Writer writer = new FileWriter(contextPath + "/Files/administratori.json");
		Writer writer2 = new FileWriter(contextPath + "/Files/korisnici.json");
		Gson gson = new GsonBuilder().create();

	    try {
			gson.toJson(administratori, writer);
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
	
	private void obrisiAdmina(String username) {
		if(administratori != null) {
			for (Administrator a : administratori) {
				if (a.getKorisnickoIme().equals(username))
					administratori.remove(a);
			}
		}
	}
	
	private void obrisiKorisnika(String username) {
		if(korisnici != null) {
			for (Korisnik k : korisnici) {
				if (k.getKorisnickoIme().equals(username))
					korisnici.remove(k);
			}
		}
	}
	
	public Administrator pronadjiAdministratora(String username) {
		if (getAdministratori() != null) {
			for (Administrator a : getAdministratori()) {
				if (a.getKorisnickoIme().equals(username)) {
					return a;
				}
			}
		}
		return null;
	}
	
	
	//---------------------GET I SET -----------------------------
	
	public ArrayList<Administrator> getAdministratori() {
		if (!administratori.isEmpty()) {
			return administratori;
		}
		return null;
	}
	
	
	
}
