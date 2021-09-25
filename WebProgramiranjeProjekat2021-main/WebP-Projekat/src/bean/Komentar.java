package bean;

public class Komentar {

	private int id;
	private Porudzbina porudzbina;
	private Restoran restoran;
	private String tekst;
	private double ocena; // od 1 do 5
	
	public Komentar() {}
	
	public Komentar(int id,Porudzbina porudzbina, Restoran restoran, String tekst, double ocena) {
		this.id = id;
		this.ocena = ocena;
		this.porudzbina = porudzbina;
		this.restoran = restoran;
		this.tekst = tekst;
	}
	
	public Porudzbina getPorudzbina() {
		return porudzbina;
	}
	public void setPorudzbina(Porudzbina porudzbina) {
		this.porudzbina = porudzbina;
	}
	public Restoran getRestoran() {
		return restoran;
	}
	public void setRestoran(Restoran restoran) {
		this.restoran = restoran;
	}
	public double getOcena() {
		return ocena;
	}
	public void setOcena(double ocena) {
		this.ocena = ocena;
	}
	public String getTekst() {
		return tekst;
	}
	public void setTekst(String tekst) {
		this.tekst = tekst;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
