package LightBot.cases;

public class Transparente extends Normal {
	
	private boolean estLampe;

	public Transparente(int pHauteur, boolean estLampe) {
		super(pHauteur);
		this.estLampe = estLampe;
	}

	public boolean getEstLampe() {
		return estLampe;
	}

}
