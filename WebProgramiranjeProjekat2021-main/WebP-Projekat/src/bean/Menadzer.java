package bean;

import java.sql.Date;

public class Menadzer extends Korisnik {

	private Restoran restoran;
	private boolean blokiran;
	
	public Menadzer() {
		super();
	}
	
	public Menadzer(Restoran argRestoran, String korisnickoIme, String lozinka, String ime, String prezime, String pol, Date datumRodjenja,
			Uloga uloga, TipKupca tipKupca) {
		super(korisnickoIme, lozinka, ime, prezime, pol, datumRodjenja, uloga, tipKupca);
		// TODO Auto-generated constructor stub
		restoran = argRestoran;
		blokiran = false;
	}
	
	

	public boolean isBlokiran() {
		return blokiran;
	}

	public void setBlokiran(boolean blokiran) {
		this.blokiran = blokiran;
	}

	public Restoran getRestoran() {
		return restoran;
	}

	public void setRestoran(Restoran restoran) {
		this.restoran = restoran;
	}

}
