package LightBot.cases;


public abstract class Case {
	
/********************************************* ATTRIBUTS *********************************************/
	
	protected Couleur color;
	protected int hauteur;
	
/********************************************* ACCESSEURS *********************************************/
	
	public Couleur getColor() {
		return color;
	}
	
	public int getHauteur() {
		return hauteur;
	}
	
/********************************************* MUTATEURS *********************************************/
	
	public void setColor(Couleur color) {
		this.color = color;
	}
	
	public void setHauteur(int hauteur) {
		this.hauteur = hauteur;
	}
	
/********************************************* METHODES D'INSTANCE *********************************************/
	
	//Constructeur de l'objet Case
	public Case(Couleur pColor, int pHauteur){
		this.setColor(pColor);
		this.setHauteur(pHauteur);
	}
	
	public void printTerm() {
	}








	
	
	
}
