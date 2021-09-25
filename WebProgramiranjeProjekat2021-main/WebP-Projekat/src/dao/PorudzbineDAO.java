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
import bean.Porudzbina;

public class PorudzbineDAO {
	
	private ArrayList<Porudzbina> porudzbine = new ArrayList<Porudzbina>();
	
	public PorudzbineDAO() {}
	
	public PorudzbineDAO(String contextPath) throws IOException{
		loadPorudzbine(contextPath);
	}

	private void loadPorudzbine(String contextPath) throws IOException {
		Gson gson = new GsonBuilder().create();
		FileInputStream fis = new FileInputStream(contextPath + "/Files/porudzbine.json");
        byte[] buffer = new byte[10];
        StringBuilder sb = new StringBuilder();
        while (fis.read(buffer) != -1) {
        	sb.append(new String(buffer));
        	buffer = new byte[10];
        }
        fis.close();
        String content = sb.toString().trim();
        porudzbine = gson.fromJson(content, new TypeToken<ArrayList<Porudzbina>>(){}.getType());
	}
	
	public void createPorudzbinu(Porudzbina novi, String contextPath) throws IOException{
		porudzbine.add(novi);
		Writer writer = new FileWriter(contextPath + "/Files/porudzbine.json");
		Gson gson = new GsonBuilder().create();

	    try {
			gson.toJson(porudzbine, writer);
			
		} catch (JsonIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Tu nesto ne valja!!!!");
		}

        writer.flush(); //flush data to file   <---
        writer.close();
	}
	
	public void write(String contextPath) throws IOException{
		Writer writer = new FileWriter(contextPath + "/Files/porudzbine.json");
		Gson gson = new GsonBuilder().create();

	    try {
			gson.toJson(porudzbine, writer);
			
		} catch (JsonIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Tu nesto ne valja!!!!");
		}

        writer.flush(); //flush data to file   <---
        writer.close();
	}
	
	public Porudzbina getPorudzbinu(long ID) {
		Porudzbina ta = new Porudzbina();
		for(Porudzbina a : porudzbine) {
			if(a.getId()==ID)
				ta = a;
		}
		return ta;
	}
	
	
	public ArrayList<Porudzbina> svePorudzbine(){
		return porudzbine;
	}
	
	public ArrayList<Porudzbina> getSvePorudzbine(){
		return porudzbine;
	}
	
}
