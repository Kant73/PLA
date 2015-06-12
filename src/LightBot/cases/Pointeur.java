package LightBot.cases;

public class Pointeur extends Allumable {

	
	private Case suivante;
/********************************************* METHODES D'INSTANCE *********************************************/	
	public Pointeur(int pHauteur) {			//Constructeur de la case Pointeur
		super(Couleur.Vert, pHauteur);
		this.suivante = null;
	}
	
	public Pointeur(int pHauteur, Case suiv) {			//Constructeur de la case Pointeur
		super(Couleur.Vert, pHauteur);
		this.suivante = suiv;
	}
	
	public Case getSuivante (){
		return suivante;
	}
	
	public void setSuivante (Case c){
		this.suivante = c;
	}
}
