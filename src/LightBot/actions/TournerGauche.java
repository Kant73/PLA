package LightBot.actions;

import LightBot.cases.Couleur;
import LightBot.personnage.Pcardinaux;
import LightBot.personnage.Personnage;

public class TournerGauche extends Actions {

	public TournerGauche() {
		super();
	}
	
	public TournerGauche(Couleur c){
		super(c);
	}
	
	@Override
	public TournerGauche clone() throws CloneNotSupportedException{
		TournerGauche copie=(TournerGauche)super.clone();
		return copie;
	}

	public String toString(){
		if(this.couleurCondition==Couleur.Violet || this.couleurCondition==Couleur.Rose)return "TournerGauche cond.";
		return "TournerGauche";
	}
	
	@Override
	public void agir(Personnage perso) {
		if(matchColor(perso)){
			if (perso.getOrientation() == Pcardinaux.EAST){
				perso.setOrientation(Pcardinaux.SOUTH);
			}
			else if (perso.getOrientation() == Pcardinaux.NORTH){
				perso.setOrientation(Pcardinaux.EAST);
			}
			else if (perso.getOrientation() == Pcardinaux.WEST){
				perso.setOrientation(Pcardinaux.NORTH);
			}
			else {
				perso.setOrientation(Pcardinaux.WEST);
			}
		}
	}
}
