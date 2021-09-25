package bean;

import java.sql.Date;
import java.util.ArrayList;


public class Porudzbina {
	public enum kom{da,ne,potvdjen,odbijen};

	private long id; // 10 karaktera mora da bude
	private ArrayList<Artikal> artikli;
	private Restoran restoran;
	private Date datumPorudzbine; // datum i vreme zajedno
	private int cena;
	private String punoImePrezimeKupca;
	private StatusPorudzbine statusPorudzbine;
	private String dostavljac;
	private kom koment;
	private String usernameKupca;
	
	public Porudzbina() {
		
	}

	public Porudzbina(long id, ArrayList<Artikal> artikli, Restoran restoran, Date datumPorudzbine, int cena,
			String punoImePrezimeKupca, StatusPorudzbine statusPorudzbine, String dostavljac, kom koment, String usernameKupca) {
		super();
		this.id = id;
		this.artikli = artikli;
		this.restoran = restoran;
		this.datumPorudzbine = datumPorudzbine;
		this.cena = cena;
		this.punoImePrezimeKupca = punoImePrezimeKupca;
		this.statusPorudzbine = statusPorudzbine;
		this.setDostavljac(dostavljac);
		this.koment = koment;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ArrayList<Artikal> getArtikli() {
		return artikli;
	}

	public void setArtikli(ArrayList<Artikal> artikli) {
		this.artikli = artikli;
	}

	public Restoran getRestoran() {
		return restoran;
	}

	public void setRestoran(Restoran restoran) {
		this.restoran = restoran;
	}

	public Date getDatumPorudzbine() {
		return datumPorudzbine;
	}

	public void setDatumPorudzbine(Date datumPorudzbine) {
		this.datumPorudzbine = datumPorudzbine;
	}

	public int getCena() {
		return cena;
	}

	public void setCena(int cena) {
		this.cena = cena;
	}

	public String getPunoImePrezimeKupca() {
		return punoImePrezimeKupca;
	}

	public void setPunoImePrezimeKupca(String punoImePrezimeKupca) {
		this.punoImePrezimeKupca = punoImePrezimeKupca;
	}

	public StatusPorudzbine getStatusPorudzbine() {
		return statusPorudzbine;
	}

	public void setStatusPorudzbine(StatusPorudzbine statusPorudzbine) {
		this.statusPorudzbine = statusPorudzbine;
	}

	public String getDostavljac() {
		return dostavljac;
	}

	public void setDostavljac(String dostavljac2) {
		this.dostavljac = dostavljac2;
	}

	public kom getKoment() {
		return koment;
	}

	public void setKoment(kom koment) {
		this.koment = koment;
	}

	public String getUsernameKupca() {
		return usernameKupca;
	}

	public void setUsernameKupca(String usernameKupca) {
		this.usernameKupca = usernameKupca;
	}

	
}
