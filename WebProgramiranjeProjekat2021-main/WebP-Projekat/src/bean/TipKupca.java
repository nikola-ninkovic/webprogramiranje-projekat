package bean;

public class TipKupca {

	private ImeTip imeTip;
	private int popust;
	private int brojBodovaZaSledeceUnapredjenje;
	
	public TipKupca(ImeTip imeTip, int popust, int brojBodovaZaSledeceUnapredjenje) {
		super();
		this.imeTip = imeTip;
		this.popust = popust;
		this.brojBodovaZaSledeceUnapredjenje = brojBodovaZaSledeceUnapredjenje;
	}

	public ImeTip getImeTip() {
		return imeTip;
	}

	public void setImeTip(ImeTip imeTip) {
		this.imeTip = imeTip;
	}

	public int getPopust() {
		return popust;
	}

	public void setPopust(int popust) {
		this.popust = popust;
	}

	public int getBrojBodovaZaSledeceUnapredjenje() {
		return brojBodovaZaSledeceUnapredjenje;
	}

	public void setBrojBodovaZaSledeceUnapredjenje(int brojBodovaZaSledeceUnapredjenje) {
		this.brojBodovaZaSledeceUnapredjenje = brojBodovaZaSledeceUnapredjenje;
	}
	
}
