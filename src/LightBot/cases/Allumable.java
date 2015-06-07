package LightBot.cases;

public abstract class Allumable extends Case{
	
/********************************************* ATTRIBUTS *********************************************/
	
	private boolean allumee;
	
/********************************************* ACCESSEURS *********************************************/
	
	public boolean getAllumee() {
		return allumee;
	}
	
/********************************************* MUTATEURS *********************************************/
	
	public void setAllumee(boolean pAllumee){
		allumee = pAllumee;
	}
	
/********************************************* METHODES D'INSTANCE *********************************************/

	//Constructeur de l'objet Allumable
	public Allumable(Couleur pColor, int pHauteur, boolean pAllumee){
		super(pColor, pHauteur);
		setAllumee(pAllumee);
	}
	
}
