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

import bean.Komentar;
import bean.Porudzbina;
public class KomentarDAO {
	
	private ArrayList<Komentar> komentari = new ArrayList<Komentar>();

	public KomentarDAO() {}
	
	public KomentarDAO(String contextPath) throws IOException{
		loadKomentare(contextPath);
	}
	
	private void loadKomentare(String contextPath) throws IOException {
		Gson gson = new GsonBuilder().create();
		FileInputStream fis = new FileInputStream(contextPath + "/Files/komentari.json");
        byte[] buffer = new byte[10];
        StringBuilder sb = new StringBuilder();
        while (fis.read(buffer) != -1) {
        	sb.append(new String(buffer));
        	buffer = new byte[10];
        }
        fis.close();
        String content = sb.toString().trim();
        komentari = gson.fromJson(content, new TypeToken<ArrayList<Komentar>>(){}.getType());
	}
	
	public int generisiIdKomentara() {
		int idPorudzbine = 13;
		for(int i=0; i< komentari.size(); i++) {
			if(komentari.get(i) == null) continue;
			idPorudzbine = Math.max(idPorudzbine, komentari.get(i).getId());
		}
		return idPorudzbine + 1;
	}
	
	public void createKomentar(Komentar novi, String contextPath) throws IOException{
		novi.setId(generisiIdKomentara());
		komentari.add(novi);
		Writer writer = new FileWriter(contextPath + "/Files/komentari.json");
		Gson gson = new GsonBuilder().create();

	    try {
			gson.toJson(komentari, writer);
			
		} catch (JsonIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Tu nesto ne valja!!!!");
		}

        writer.flush(); //flush data to file   <---
        writer.close();
	}
	
	public void write(String contextPath) throws IOException{
		Writer writer = new FileWriter(contextPath + "/Files/komentari.json");
		Gson gson = new GsonBuilder().create();

	    try {
			gson.toJson(komentari, writer);
			
		} catch (JsonIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Tu nesto ne valja!!!!");
		}

        writer.flush(); //flush data to file   <---
        writer.close();
	}
	
	public Komentar getKomentar(int ID) {
		Komentar ta = new Komentar();
		for(Komentar a : komentari) {
			if(a.getId()==ID)
				ta = a;
		}
		return ta;
	}
	
	public ArrayList<Komentar> getKomentare(){
		return komentari;
	}
	
}
