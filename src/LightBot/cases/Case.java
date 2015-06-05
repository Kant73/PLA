package LightBot.cases;


public abstract class Case {
	private Couleur color;
	private int hauteur;
	
	Case(int h, Couleur c){
		this.color=c;
		this.hauteur=h;		
	}
	
	public void printTerm() {
	}
	
	
	
}
