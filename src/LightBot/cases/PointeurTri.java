package LightBot.cases;

public class PointeurTri extends Pointeur {
	
	private Lampe arrivee;

	/* on construit la case */
	public PointeurTri(int pHauteur) {
		super(pHauteur);
		this.suivante = null;
		this.arrivee = null;
	}

	/* On construit la case de départ */
	public PointeurTri(int pHauteur, Case suiv, Lampe arrivee){
		super(pHauteur, suiv);
		this.arrivee = arrivee;
	}
	
	public void setArrivee(Lampe arrivee){
		this.arrivee = arrivee;
	}
	
	public Lampe getArrivee(){
		return arrivee;
	}
	
	public boolean estPointee(){
		return (this.arrivee == null && this.suivante == null);
	}
	
	public boolean estDepart(){
		return (this.arrivee != null && this.suivante != null);
	}
}
