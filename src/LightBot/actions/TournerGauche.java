package LightBot.actions;

import LightBot.cases.Couleur;
import LightBot.personnage.Pcardinaux;
import LightBot.personnage.Personnage;

public class TournerGauche extends Actions {

	public TournerGauche(Personnage p) {
		super(p);
	}
	
	public TournerGauche(Personnage p, Couleur c){
		super(p,c);
	}

	public String toString(){
		if(this.couleurCondition==Couleur.Violet || this.couleurCondition==Couleur.Rose)return "TournerGauche cond.";
		return "TournerGauche";
	}
	
	@Override
	public void agir() {
		if(matchColor()){
			if (this.perso.getOrientation() == Pcardinaux.EAST){
				this.perso.setOrientation(Pcardinaux.SOUTH);
			}
			else if (this.perso.getOrientation() == Pcardinaux.NORTH){
				this.perso.setOrientation(Pcardinaux.EAST);
			}
			else if (this.perso.getOrientation() == Pcardinaux.WEST){
				this.perso.setOrientation(Pcardinaux.NORTH);
			}
			else {
				this.perso.setOrientation(Pcardinaux.WEST);
			}
		}
	}
}
