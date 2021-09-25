package bean;

public class Lokacija {

	private double duzina;
	private double sirina;
	private String ulica;
	private String grad;
	private int zipCode;
	
	public Lokacija() {}
	
	public Lokacija(double duzina, double sirina, String ulica, String grad, int zipCode) {
		this.duzina = duzina;
		this.sirina = sirina;
		this.ulica = ulica;
		this.setGrad(grad);
		this.setZipCode(zipCode);
	}

	public double getDuzina() {
		return duzina;
	}

	public void setDuzina(double duzina) {
		this.duzina = duzina;
	}

	public double getSirina() {
		return sirina;
	}

	public void setSirina(double sirina) {
		this.sirina = sirina;
	}

	public String getUlica() {
		return ulica;
	}

	public void setUlica(String ulica) {
		this.ulica = ulica;
	}

	public String getGrad() {
		return grad;
	}

	public void setGrad(String grad) {
		this.grad = grad;
	}

	public int getZipCode() {
		return zipCode;
	}

	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}
	
}
