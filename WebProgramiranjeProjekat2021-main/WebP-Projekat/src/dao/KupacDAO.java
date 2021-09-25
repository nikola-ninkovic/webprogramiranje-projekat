package dao;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.Date;
import java.util.ArrayList;

import com.google.gson.reflect.TypeToken;
import com.google.gson.*;

import bean.Korisnik;
import bean.Kupac;
import bean.StatusPorudzbine;

public class KupacDAO {

	private ArrayList<Kupac> kupci = new ArrayList<Kupac>();
	private ArrayList<Korisnik> korisnici = new ArrayList<Korisnik>();
	
	public KupacDAO() {
		
	}
	


	public KupacDAO(String contextPath) throws IOException {
		loadKupci(contextPath);
		loadKorisnike(contextPath);
	}
	
//	public Kupac(List<Porudzbina> argListaPorudzbina, Korpa argKorpa, int argBrojSakupljenihBodova,String korisnickoIme, String lozinka, String ime, String prezime, String pol, Date datumRodjenja,
//			Uloga uloga, TipKupca tipKupca)
	
	private void loadKupci(String contextPath) throws IOException {
		Gson gson = new GsonBuilder().create();
		FileInputStream fis = new FileInputStream(contextPath + "/Files/kupci.json");
        byte[] buffer = new byte[10];
        StringBuilder sb = new StringBuilder();
        while (fis.read(buffer) != -1) {
        	sb.append(new String(buffer));
        	buffer = new byte[10];
        }
        fis.close();
        String content = sb.toString().trim();
    	kupci = gson.fromJson(content, new TypeToken<ArrayList<Kupac>>(){}.getType());
    	
    	
//		FileInputStream fis2 = new FileInputStream(contextPath + "/Files/korisnici.json");
//        byte[] buffer2 = new byte[10];
//        StringBuilder sb2 = new StringBuilder();
//        while (fis.read(buffer) != -1) {
//        	sb2.append(new String(buffer2));
//        	buffer2 = new byte[10];
//        }
//        fis2.close();
//        String content2 = sb2.toString().trim();
//        korisnici = gson.fromJson(content2, new TypeToken<ArrayList<Korisnik>>(){}.getType());
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
	
	public void createKupac(Kupac novi, String contextPath) throws IOException {
		loadKorisnike(contextPath);
		
		ArrayList<Date> listaDatumaOtkazivanja = new ArrayList<Date>();
		novi.setListaDatumaOtkazivanja(listaDatumaOtkazivanja);
		
		kupci.add(novi);
		Korisnik k = new Korisnik(novi.getKorisnickoIme(), novi.getLozinka(), novi.getIme(), novi.getPrezime(), novi.getPol(), novi.getDatumRodjenja(), novi.getUloga(), novi.getTipKupca());
		korisnici.add(k);
		System.out.println(novi);
		Writer writer = new FileWriter(contextPath + "/Files/kupci.json");
		Writer writer2 = new FileWriter(contextPath + "/Files/korisnici.json");
        Gson gson = new GsonBuilder().create();
 
	    try {
			gson.toJson(kupci, writer);
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
		Writer writer = new FileWriter(contextPath + "/Files/kupci.json");
		Writer writer2 = new FileWriter(contextPath + "/Files/korisnici.json");
        Gson gson = new GsonBuilder().create();
 
	    try {
			gson.toJson(kupci, writer);
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
	
	public void editKupac(Kupac novi, String contextPath) throws IOException {
		obrisiKupca(novi.getKorisnickoIme());
		kupci.add(novi);
		Korisnik k = new Korisnik(novi.getKorisnickoIme(), novi.getLozinka(), novi.getIme(), novi.getPrezime(), novi.getPol(), novi.getDatumRodjenja(), novi.getUloga(), novi.getTipKupca());
		obrisiKorisnika(novi.getKorisnickoIme());
		korisnici.add(k);
		System.out.println(novi);
		Writer writer = new FileWriter(contextPath + "/Files/kupci.json");
		Writer writer2 = new FileWriter(contextPath + "/Files/korisnici.json");
        Gson gson = new GsonBuilder().create();
 
	    try {
			gson.toJson(kupci, writer);
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
	
	public void updateKupca(Kupac novi) {
		for (int i=0; i<kupci.size(); i++) {
			if(kupci.get(i).getKorisnickoIme().equals(novi.getKorisnickoIme())) {
				kupci.set(i, novi);
			}
		}
		
		for (int i=0; i<korisnici.size(); i++) {
			if(korisnici.get(i).getKorisnickoIme().equals(novi.getKorisnickoIme())) {
				Korisnik k = new Korisnik(novi.getKorisnickoIme(), novi.getLozinka(), novi.getIme(), novi.getPrezime(), novi.getPol(), novi.getDatumRodjenja(), novi.getUloga(), novi.getTipKupca());
				korisnici.set(i, k);
			}
		}
		
	}
	
	private void obrisiKupca(String username) {
		if(getKupci() != null) {
			for (Kupac k : kupci) {
				if (k.getKorisnickoIme().equals(username))
					kupci.remove(k);
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
	
	public Kupac pronadjiKupca(String username) {
		if (getKupci() != null) {
			for (Kupac k : getKupci()) {
				if (k.getKorisnickoIme().equals(username)) {
					return k;
				}
			}
		}
		return null;
	}
	
	public long generisiIdPorudzbine() {
		long idPorudzbine = 1000000000;
		for(int i=0; i< kupci.size(); i++) {
			if(kupci.get(i).getListaPorudzbina() == null) continue;
			for(int j=0; j<kupci.get(i).getListaPorudzbina().size(); j++) {
				idPorudzbine = Math.max(idPorudzbine, kupci.get(i).getListaPorudzbina().get(j).getId());
			}
		}
		return idPorudzbine + 1;
	}
	
	public void otkaziPorudzbinu(String korisnickoIme, long id) {
		for(int i=0; i<kupci.size(); i++) {
			if(kupci.get(i).getKorisnickoIme().equals(korisnickoIme)) {
				for(int j=0; j<kupci.get(i).getListaPorudzbina().size(); j++) {
					if(kupci.get(i).getListaPorudzbina().get(j).getId() == id) {
						kupci.get(i).getListaPorudzbina().get(j).setStatusPorudzbine(StatusPorudzbine.Otkazana);
						break;
					}
				}
			}
		}
	}
	
	
	//---------------------GET I SET -----------------------------
	
	public ArrayList<Kupac> getKupci() {
		if (!kupci.isEmpty()) {
			return kupci;
		}
		return null;
	}
	
	
	
	
}
