package bean;

import java.sql.Date;

public class Korisnik {

	private String korisnickoIme;
	private String lozinka;
	private String ime;
	private String prezime;
	private String pol;
	private Date datumRodjenja;
	private Uloga uloga;
	private TipKupca tipKupca;
	
	public Korisnik() {
		super();
	}
	
	public Korisnik(String korisnickoIme, String lozinka, String ime, String prezime, String pol, Date datumRodjenja,
			Uloga uloga, TipKupca tipKupca) {
		super();
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.ime = ime;
		this.prezime = prezime;
		this.pol = pol;
		this.datumRodjenja = datumRodjenja;
		this.uloga = uloga;
		this.tipKupca = tipKupca;
	}
	
	
	public Korisnik(String krisnickoIme, String lozinka) {
		this.korisnickoIme = krisnickoIme;
		this.lozinka = lozinka;
		
		this.ime = "";
		this.prezime = "";
		this.pol = "";
		this.datumRodjenja = null;
		this.uloga = null;
		this.tipKupca = null;
	}


	public String getKorisnickoIme() {
		return korisnickoIme;
	}


	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}


	public String getLozinka() {
		return lozinka;
	}


	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}


	public String getIme() {
		return ime;
	}


	public void setIme(String ime) {
		this.ime = ime;
	}


	public String getPrezime() {
		return prezime;
	}


	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}


	public String getPol() {
		return pol;
	}


	public void setPol(String pol) {
		this.pol = pol;
	}


	public Date getDatumRodjenja() {
		return datumRodjenja;
	}


	public void setDatumRodjenja(Date datumRodjenja) {
		this.datumRodjenja = datumRodjenja;
	}


	public Uloga getUloga() {
		return uloga;
	}


	public void setUloga(Uloga uloga) {
		this.uloga = uloga;
	}


	public TipKupca getTipKupca() {
		return tipKupca;
	}


	public void setTipKupca(TipKupca tipKupca) {
		this.tipKupca = tipKupca;
	}
	
	
}
