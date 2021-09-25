package dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

import com.google.gson.reflect.TypeToken;
import com.google.gson.*;

import bean.Korisnik;
import bean.Kupac;

public class KorisnikDAO {

	private ArrayList<Korisnik> korisnici = new ArrayList<Korisnik>();
	
	public KorisnikDAO() {}
	
	public KorisnikDAO(String contextPath) throws IOException {
		loadKorisnike(contextPath);
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
	
	//---------------------GET I SET -----------------------------
	
	public ArrayList<Korisnik> getKorisnici(String contextPath) throws IOException {
		loadKorisnike(contextPath);
		if (!korisnici.isEmpty()) {
			return korisnici;
		}
		return null;
	}
	
	public ArrayList<Korisnik> getK() {
		if (!korisnici.isEmpty()) {
			return korisnici;
		}
		return null;
	}
	
	public void updateKorisnika(Korisnik novi) {
		for (int i=0; i<korisnici.size(); i++) {
			if(korisnici.get(i).getKorisnickoIme().equals(novi.getKorisnickoIme())) {
				korisnici.set(i, novi);
			}
		}
		
	}
	
	public void write(String contextPath) throws IOException {
		Writer writer2 = new FileWriter(contextPath + "/Files/korisnici.json");
        Gson gson = new GsonBuilder().create();
 
	    try {
			gson.toJson(korisnici, writer2);
 
		} catch (JsonIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Tu nesto ne valja!!!!");
		}
	    
        writer2.flush(); //flush data to file   <---
        writer2.close();
	}
	
	public Korisnik pronadjiKorisnika(String username) {
		if (getK() != null) {
			for (Korisnik k : getK()) {
				if (k.getKorisnickoIme().equals(username)) {
					return k;
				}
			}
		}
		return null;
	}
	
}
