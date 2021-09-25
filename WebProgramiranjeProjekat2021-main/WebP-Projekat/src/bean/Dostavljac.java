package bean;

import java.sql.Date;
import java.util.List;

public class Dostavljac extends Korisnik {

	//TODO: Dodatni neophodna polja za Dostavljaca
	private List<Porudzbina> listaPorudzbinaZaDostaviti;
	
	private boolean blokiran;
	
	public Dostavljac() {
		super();
	}
	
	public Dostavljac(List<Porudzbina> argListaPorudzbinaZaDostaviti,String korisnickoIme, String lozinka, String ime, String prezime, String pol, Date datumRodjenja,
			Uloga uloga, TipKupca tipKupca) {
		super(korisnickoIme, lozinka, ime, prezime, pol, datumRodjenja, uloga, tipKupca);
		// TODO Auto-generated constructor stub
		setListaPorudzbinaZaDostaviti(argListaPorudzbinaZaDostaviti);
		blokiran = false;
	}
	
	

	public boolean isBlokiran() {
		return blokiran;
	}

	public void setBlokiran(boolean blokiran) {
		this.blokiran = blokiran;
	}

	public List<Porudzbina> getListaPorudzbinaZaDostaviti() {
		return listaPorudzbinaZaDostaviti;
	}

	public void setListaPorudzbinaZaDostaviti(List<Porudzbina> listaPorudzbinaZaDostaviti) {
		this.listaPorudzbinaZaDostaviti = listaPorudzbinaZaDostaviti;
	}

}
