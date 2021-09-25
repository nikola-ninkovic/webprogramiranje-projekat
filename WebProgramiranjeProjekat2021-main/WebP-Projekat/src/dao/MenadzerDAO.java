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

import bean.Korisnik;
import bean.Menadzer;

public class MenadzerDAO {

	
	private ArrayList<Menadzer> menadzeri = new ArrayList<Menadzer>();
	private ArrayList<Korisnik> korisnici = new ArrayList<Korisnik>();
	
	public MenadzerDAO() {
		
	}
	
	public MenadzerDAO(String contextPath) throws IOException {
		loadMenadzeri(contextPath);
		loadKorisnike(contextPath);
	}
	
	
	private void loadMenadzeri(String contextPath) throws IOException {
		Gson gson = new GsonBuilder().create();
		FileInputStream fis = new FileInputStream(contextPath + "/Files/menadzeri.json");
        byte[] buffer = new byte[10];
        StringBuilder sb = new StringBuilder();
        while (fis.read(buffer) != -1) {
        	sb.append(new String(buffer));
        	buffer = new byte[10];
        }
        fis.close();
        String content = sb.toString().trim();
        menadzeri = gson.fromJson(content, new TypeToken<ArrayList<Menadzer>>(){}.getType());
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
	
	public void createMenadzer(Menadzer novi, String contextPath) throws IOException {
		loadKorisnike(contextPath);
		menadzeri.add(novi);
		Korisnik k = new Korisnik(novi.getKorisnickoIme(), novi.getLozinka(), novi.getIme(), novi.getPrezime(), novi.getPol(), novi.getDatumRodjenja(), novi.getUloga(), novi.getTipKupca());
		korisnici.add(k);
		System.out.println(novi);
		Writer writer = new FileWriter(contextPath + "/Files/menadzeri.json");
		Writer writer2 = new FileWriter(contextPath + "/Files/korisnici.json");
		Gson gson = new GsonBuilder().create();

	    try {
			gson.toJson(menadzeri, writer);
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
	
	public void updateMenadzer(Menadzer novi) {
		for (int i=0; i<menadzeri.size(); i++) {
			if(menadzeri.get(i).getKorisnickoIme().equals(novi.getKorisnickoIme())) {
				menadzeri.set(i, novi);
			}
		}
		
		for (int i=0; i<korisnici.size(); i++) {
			if(korisnici.get(i).getKorisnickoIme().equals(novi.getKorisnickoIme())) {
				Korisnik k = new Korisnik(novi.getKorisnickoIme(), novi.getLozinka(), novi.getIme(), novi.getPrezime(), novi.getPol(), novi.getDatumRodjenja(), novi.getUloga(), novi.getTipKupca());
				korisnici.set(i, k);
			}
		}
	}
	
	public void write(String contextPath) throws IOException {
		Writer writer = new FileWriter(contextPath + "/Files/menadzeri.json");
		Writer writer2 = new FileWriter(contextPath + "/Files/korisnici.json");
		Gson gson = new GsonBuilder().create();

	    try {
			gson.toJson(menadzeri, writer);
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
	
	
	public Menadzer pronadjiMenadzera(String username) {
		if (getMenadzeri() != null) {
			for (Menadzer m : getMenadzeri()) {
				if (m.getKorisnickoIme().equals(username)) {
					return m;
				}
			}
		}
		return null;
	}
	
	public ArrayList<Menadzer> slobodniMenadzeri() {
		ArrayList<Menadzer> slobodni = new ArrayList<Menadzer>();
		for(Menadzer m : menadzeri) {
			if(m.getRestoran() == null)
				slobodni.add(m);
		}
		if (!slobodni.isEmpty()) {
			return slobodni;
		}
		return null;
	}
	
	//---------------------GET I SET -----------------------------
	
	public ArrayList<Menadzer> getMenadzeri() {
		if (!menadzeri.isEmpty()) {
			return menadzeri;
		}
		return null;
	}

	public Menadzer getMenadzer(String username) {
		Menadzer menadzer = new Menadzer();
		for(int i=0; i<menadzeri.size(); i++) {
			if(menadzeri.get(i).getKorisnickoIme().equals(username)) menadzer = menadzeri.get(i);
		}
		return menadzer;
	}
}
