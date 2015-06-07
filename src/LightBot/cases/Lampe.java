package LightBot.cases;

public class Lampe extends Allumable {

/********************************************* METHODES D'INSTANCE *********************************************/
	
	//Constructeur de l'objet Lampe
	public Lampe(int pHauteur,boolean pAllumee){
		super(Couleur.Bleu,pHauteur,pAllumee);
		if (pAllumee) {
			setColor(Couleur.Jaune);
		}	
	}
}
