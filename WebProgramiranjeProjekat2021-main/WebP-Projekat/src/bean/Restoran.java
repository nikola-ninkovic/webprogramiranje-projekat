package bean;

import java.util.ArrayList;

public class Restoran {

	private String naziv;
	private TipRestorana tipRestorana;
	private Status status;
	private Lokacija lokacija;
	private String logoRestorana;
	private double prosecnaOcena;
	private ArrayList<Double> ocene;
	
	public Restoran() {
	}
	
	public Restoran(String naziv, TipRestorana tipRestorana, Status status, Lokacija lokacija, String logoRestorana, double prosecnaOcena, ArrayList<Double> ocene) {
		this.naziv = naziv;
		this.tipRestorana = tipRestorana;
		this.status = status;
		this.lokacija = lokacija;
		this.logoRestorana = logoRestorana;
		this.prosecnaOcena = prosecnaOcena;
		this.ocene = ocene;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public TipRestorana getTipRestorana() {
		return tipRestorana;
	}

	public void setTipRestorana(TipRestorana tipRestorana) {
		this.tipRestorana = tipRestorana;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Lokacija getLokacija() {
		return lokacija;
	}

	public void setLokacija(Lokacija lokacija) {
		this.lokacija = lokacija;
	}

	public String getLogoRestorana() {
		return logoRestorana;
	}

	public void setLogoRestorana(String logoRestorana) {
		this.logoRestorana = logoRestorana;
	}

	public double getProsecnaOcena() {
		return prosecnaOcena;
	}

	public void setProsecnaOcena(double prosecnaOcena) {
		this.prosecnaOcena = prosecnaOcena;
	}

	public ArrayList<Double> getOcene() {
		return ocene;
	}

	public void setOcene(ArrayList<Double> ocene) {
		this.ocene = ocene;
	}
}
