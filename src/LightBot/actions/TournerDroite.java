package LightBot.actions;

import LightBot.cases.Couleur;
import LightBot.personnage.Pcardinaux;
import LightBot.personnage.Personnage;

public class TournerDroite extends Actions {

	public TournerDroite() {
		super();
	}
	
	public TournerDroite(Couleur c){
		super(c);
	}
	
	public String toString(){
		if(this.couleurCondition==Couleur.Violet || this.couleurCondition==Couleur.Rose)return "TournerDroite cond.";
		return "TournerDroite";
	}
	
	@Override
	public TournerDroite clone() throws CloneNotSupportedException{
		TournerDroite copie=(TournerDroite)super.clone();
		return copie;
	}

	@Override
	public void agir(Personnage perso) {
		if(matchColor(perso)){
			if (perso.getOrientation() == Pcardinaux.EAST){
				perso.setOrientation(Pcardinaux.NORTH);
			}
			else if (perso.getOrientation() == Pcardinaux.NORTH){
				perso.setOrientation(Pcardinaux.WEST);
			}
			else if (perso.getOrientation() == Pcardinaux.WEST){
				perso.setOrientation(Pcardinaux.SOUTH);
			}
			else {
				perso.setOrientation(Pcardinaux.EAST);
			}
		}
	}
}
