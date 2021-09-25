package bean;

public class Artikal {

	private String naziv;
	private int cena;
	private Tip tip;
	private String imeRestorana;
	private int kolicina;
	private String opis;
	private String slika;
	
	public Artikal() {
	}
	
	public Artikal(String naziv, int cena, Tip tip, String imeRestorana, int kolicina, String opis, String slika) {
		super();
		this.naziv = naziv;
		this.cena = cena;
		this.tip = tip;
		this.imeRestorana = imeRestorana;
		this.kolicina = kolicina;
		this.opis = opis;
		this.slika = slika;
	}
	
	public String getNaziv() {
		return naziv;
	}


	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}


	public int getCena() {
		return cena;
	}


	public void setCena(int cena) {
		this.cena = cena;
	}


	public Tip getTip() {
		return tip;
	}


	public void setTip(Tip tip) {
		this.tip = tip;
	}


	public String getImeRestorana() {
		return imeRestorana;
	}


	public void setImeRestorana(String imeRestorana) {
		this.imeRestorana = imeRestorana;
	}


	public int getKolicina() {
		return kolicina;
	}


	public void setKolicina(int kolicina) {
		this.kolicina = kolicina;
	}


	public String getOpis() {
		return opis;
	}


	public void setOpis(String opis) {
		this.opis = opis;
	}


	public String getSlika() {
		return slika;
	}


	public void setSlika(String slika) {
		this.slika = slika;
	}
}
