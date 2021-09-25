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

import bean.Artikal;


public class ArtikalDAO {

	private ArrayList<Artikal> artikli = new ArrayList<Artikal>();
	
	public ArtikalDAO() {}
	
	public ArtikalDAO(String contextPath) throws IOException {
		loadArtikli(contextPath);
	}
	
	private void loadArtikli(String contextPath) throws IOException {
		Gson gson = new GsonBuilder().create();
		FileInputStream fis = new FileInputStream(contextPath + "/Files/artikli.json");
        byte[] buffer = new byte[10];
        StringBuilder sb = new StringBuilder();
        while (fis.read(buffer) != -1) {
        	sb.append(new String(buffer));
        	buffer = new byte[10];
        }
        fis.close();
        String content = sb.toString().trim();
        artikli = gson.fromJson(content, new TypeToken<ArrayList<Artikal>>(){}.getType());
	}
	
	public void createArtikal(Artikal novi, String contextPath) throws IOException{
		artikli.add(novi);
		Writer writer = new FileWriter(contextPath + "/Files/artikli.json");
		Gson gson = new GsonBuilder().create();

	    try {
			gson.toJson(artikli, writer);
			
		} catch (JsonIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Tu nesto ne valja!!!!");
		}

        writer.flush(); //flush data to file   <---
        writer.close();
	}
	
	public void write(String contextPath) throws IOException {
		Writer writer = new FileWriter(contextPath + "/Files/artikli.json");
		Gson gson = new GsonBuilder().create();

	    try {
			gson.toJson(artikli, writer);
			
		} catch (JsonIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Tu nesto ne valja!!!!");
		}

        writer.flush(); //flush data to file   <---
        writer.close();
	}
	
	public boolean artikalPostoji(String ime) {
		for(int i=0; i < artikli.size(); i++) {
			if(artikli.get(i).getNaziv().equals(ime)) return true;
		}
		return false;
	}
	
	public Artikal getPoslednjiArtikal() {
		return artikli.get(artikli.size()-1);
	}
	
	
	public ArrayList<Artikal> sviArtikli(){
		return artikli;
	}
}
