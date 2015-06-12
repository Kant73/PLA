package LightBot.cases;

public class Normal extends NonAllumable {
/******************************************** ATTRIBUTS **********************************************/
	
	int fondation;		//Permet de savoir si c'est une case de base dans le terrain.
	
/******************************************** ACCESSEURS *********************************************/
	
	public int getFondation() {
		return this.fondation;
	}
	
/******************************************** MUTATEURS **********************************************/
	
	public void setFondation(int pFondation) {
		this.fondation = pFondation;
	}
	
/********************************************* METHODES D'INSTANCE *********************************************/

	
	//Constructeur de l'objet Normal
	public Normal(int pHauteur){
		super(Couleur.Blanc, pHauteur);
		this.fondation = 0;		//Définition d'une case de base du terrain.
	}

	
}
