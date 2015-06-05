package LightBot.cases;


public abstract class Case {
	private Couleur color;
	private int hauteur;
	
	Case(int h, Couleur c){
		this.setColor(c);
		this.setHauteur(h);		
	}
	
	public void printTerm() {
	}

	public Couleur getColor() {
		return color;
	}

	public void setColor(Couleur color) {
		this.color = color;
	}

	public int getHauteur() {
		return hauteur;
	}

	public void setHauteur(int hauteur) {
		this.hauteur = hauteur;
	}
	
	
	
}
