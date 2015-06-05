import java.awt.List;


public class Case {
	
	public enum Couleur{
		Bleu, /* A allumer*/
		Jaune, /* Case allumée*/
		Blanc,/*Normal*/
		Vert, /* Téléportation*/
		Orange, /*Case finale*/
		Noir, /*Case qui disparait*/
		Violet, /*Case condition if*/
		Incolore, /*Transparent*/
		Rouge; /*Case memoire*/
		
	}
	private Couleur color;
	private int hauteur;
	private List Cases; 
	
	
	public void printTerm() {
	}
	
	public Case getCase() {
		return null;
	}
	
}
