package LightBot.cases;

public class Transparente extends Normal {
	
	public Transparente(int pHauteur) {
		super(pHauteur);
	}

	public Normal toNormal(){
		return new Normal(this.hauteur);
	}
}
