package LightBot.actions;

import LightBot.cases.Couleur;
import LightBot.personnage.Pcardinaux;
import LightBot.personnage.Personnage;

public class TournerDroite extends Actions {

	public TournerDroite(Personnage p) {
		super(p);
	}
	
	public TournerDroite(Personnage p, Couleur c){
		super(p,c);
	}
	
	public String toString(){
		if(this.couleurCondition==Couleur.Violet || this.couleurCondition==Couleur.Rose)return "TournerDroite cond.";
		return "TournerDroite";
	}

	@Override
	public void agir() {
		if(matchColor()){
			if (this.perso.getOrientation() == Pcardinaux.EAST){
				this.perso.setOrientation(Pcardinaux.NORTH);
			}
			else if (this.perso.getOrientation() == Pcardinaux.NORTH){
				this.perso.setOrientation(Pcardinaux.WEST);
			}
			else if (this.perso.getOrientation() == Pcardinaux.WEST){
				this.perso.setOrientation(Pcardinaux.SOUTH);
			}
			else {
				this.perso.setOrientation(Pcardinaux.EAST);
			}
		}
	}
}
