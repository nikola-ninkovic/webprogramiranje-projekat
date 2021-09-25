package bean;

import java.util.HashMap;
import java.util.Map;


public class Korpa {

	private Map<String, Integer> artikalIkolicina;
	private String vlasnikKorpe;
	private double cena;
	
	public Korpa() {
		
	}
	
	public Korpa(HashMap<String, Integer> artikalIkolicina, String vlasnikKorpe, double cena) {
		super();
		this.artikalIkolicina = artikalIkolicina;
		this.vlasnikKorpe = vlasnikKorpe;
		this.cena = cena;
	}

	public Map<String, Integer> getArtikalIkolicina() {
		return artikalIkolicina;
	}

	public void setArtikalIkolicina(Map<String, Integer> artikalIkolicina) {
		this.artikalIkolicina = artikalIkolicina;
	}

	public String getVlasnikKorpe() {
		return vlasnikKorpe;
	}

	public void setVlasnikKorpe(String vlasnikKorpe) {
		this.vlasnikKorpe = vlasnikKorpe;
	}

	public double getCena() {
		return cena;
	}

	public void setCena(double kosta) {
		this.cena = kosta;
	}
	

}
