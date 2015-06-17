package LightBot.cases;

public class Pointeur extends Allumable {

	
	protected Case suivante;
/********************************************* METHODES D'INSTANCE *********************************************/	
	public Pointeur(int pHauteur) {			//Constructeur de la case pointée
		super(Couleur.Vert, pHauteur);
		this.suivante = null;
	}
	
	public Pointeur(int pHauteur, Case suiv) {			//Constructeur de la case qui pointe
		super(Couleur.Vert, pHauteur);
		this.suivante = suiv;
	}
	
	public Case getSuivante (){
		return suivante;
	}
	
	public void setSuivante (Case c){
		this.suivante = c;
	}
	
	public boolean estPointee (){
		return this.suivante == null;
	}
}
