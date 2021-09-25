package bean;

import java.sql.Date;

public class Administrator extends Korisnik {

	public Administrator() {
		super();
	}
	
	public Administrator(String korisnickoIme, String lozinka, String ime, String prezime, String pol, Date datumRodjenja,
			Uloga uloga, TipKupca tipKupca) {
		super(korisnickoIme, lozinka, ime, prezime, pol, datumRodjenja, uloga, tipKupca);
	}
	
}
