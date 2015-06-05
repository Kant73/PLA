package LightBot.actions;

import LightBot.personnage.Pcardinaux;
import LightBot.personnage.Personnage;

public class TournerGauche extends Actions {

	TournerGauche(Personnage p) {
		super(p);
	}

	@Override
	public void agir() {
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
