package bean;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Kupac extends Korisnik {

	private List<Porudzbina> listaPorudzbina;
	private Korpa korpa;
	private double brojSakupljenihBodova;
	
	private ArrayList<Date> listaDatumaOtkazivanja;
	private boolean blokiran;
	private boolean sumnjiv;
	
	public Kupac() {
		super();
	}
	
	public Kupac(List<Porudzbina> argListaPorudzbina, Korpa argKorpa, double argBrojSakupljenihBodova,String korisnickoIme, String lozinka, String ime, String prezime, String pol, Date datumRodjenja,
			Uloga uloga, TipKupca tipKupca, ArrayList<Date> argListaDatumaOtkazivanja) {
		super(korisnickoIme, lozinka, ime, prezime, pol, datumRodjenja, uloga, tipKupca);
		// TODO Auto-generated constructor stub
		this.listaPorudzbina = argListaPorudzbina;
		this.korpa = argKorpa;
		this.brojSakupljenihBodova = argBrojSakupljenihBodova;
		blokiran = false;
		sumnjiv = false;
		listaDatumaOtkazivanja = argListaDatumaOtkazivanja;
		
	}
	
	

	public boolean isSumnjiv() {
		return sumnjiv;
	}

	public void setSumnjiv(boolean sumnjiv) {
		this.sumnjiv = sumnjiv;
	}

	public ArrayList<Date> getListaDatumaOtkazivanja() {
		return listaDatumaOtkazivanja;
	}

	public void setListaDatumaOtkazivanja(ArrayList<Date> listaDatumaOtkazivanja) {
		this.listaDatumaOtkazivanja = listaDatumaOtkazivanja;
	}

	public boolean isBlokiran() {
		return blokiran;
	}

	public void setBlokiran(boolean blokiran) {
		this.blokiran = blokiran;
	}

	public List<Porudzbina> getListaPorudzbina() {
		return listaPorudzbina;
	}

	public void setListaPorudzbina(List<Porudzbina> listaPorudzbina) {
		this.listaPorudzbina = listaPorudzbina;
	}

	public Korpa getKorpa() {
		return korpa;
	}

	public void setKorpa(Korpa korpa) {
		this.korpa = korpa;
	}

	public double getBrojSakupljenihBodova() {
		return brojSakupljenihBodova;
	}

	public void setBrojSakupljenihBodova(double d) {
		this.brojSakupljenihBodova = d;
	}
	
	

}
