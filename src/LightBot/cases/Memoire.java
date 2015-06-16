package LightBot.cases;

public class Memoire extends NonAllumable {

/**************************************** ATTRIBUTS *****************************************/	
	
private int hauteurOriginal;

/**************************************** ACCESSEURS *****************************************/	

public int getHauteurOriginal() {
	return this.hauteurOriginal;
}

/**************************************** MUTATEURS *****************************************/	

public void setHauteurOriginal(int pHauteurOriginal) {
	this.hauteurOriginal = pHauteurOriginal;
}

/**************************************** METHODES D'INSTANCE *****************************************/
	
	public Memoire(int pHauteur, int pHauteurOriginal) {
		super(Couleur.Blanc, pHauteur);		//On laisse la couleur blanche comme pour les cases normales.
		setHauteurOriginal(pHauteurOriginal);
	}
}


