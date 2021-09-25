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

import bean.Restoran;

public class RestoranDAO {

	private ArrayList<Restoran> restorani = new ArrayList<Restoran>();
	
	public RestoranDAO() {}
	
	public RestoranDAO(String contextPath) throws IOException {
		loadRestorani(contextPath);
	}
	
	private void loadRestorani(String contextPath) throws IOException {
		Gson gson = new GsonBuilder().create();
		FileInputStream fis = new FileInputStream(contextPath + "/Files/restorani.json");
        byte[] buffer = new byte[10];
        StringBuilder sb = new StringBuilder();
        while (fis.read(buffer) != -1) {
        	sb.append(new String(buffer));
        	buffer = new byte[10];
        }
        fis.close();
        String content = sb.toString().trim();
        restorani = gson.fromJson(content, new TypeToken<ArrayList<Restoran>>(){}.getType());
	}
	
	public void createRestoran(Restoran novi, String contextPath) throws IOException{
		restorani.add(novi);
		Writer writer = new FileWriter(contextPath + "/Files/restorani.json");
		Gson gson = new GsonBuilder().create();

	    try {
			gson.toJson(restorani, writer);
			
		} catch (JsonIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Tu nesto ne valja!!!!");
		}

        writer.flush(); //flush data to file   <---
        writer.close();
	}
	
	public void write(String contextPath) throws IOException{
		Writer writer = new FileWriter(contextPath + "/Files/restorani.json");
		Gson gson = new GsonBuilder().create();

	    try {
			gson.toJson(restorani, writer);
			
		} catch (JsonIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Tu nesto ne valja!!!!");
		}

        writer.flush(); //flush data to file   <---
        writer.close();
	}
	
	public Restoran getRestoran(String pronadji) {
		Restoran pronadjen = new Restoran();
		for(Restoran r : restorani) {
			if(r.getNaziv().equals(pronadji)) {
				pronadjen = r;
				break;
				}
		}
		return pronadjen;
	}

	public Restoran getLastRestaurant() {
		// TODO Auto-generated method stub
		return restorani.get(restorani.size()-1);
	}
	
	public ArrayList<Restoran> sviRestorani(){
		return restorani;
	}
}
