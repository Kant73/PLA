package LightBot.actions;

import LightBot.personnage.Pcardinaux;
import LightBot.personnage.Personnage;

public class TournerDroite extends Actions {

	TournerDroite(Personnage p) {
		super(p);
	}

	@Override
	public void agir() {
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
