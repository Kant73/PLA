package LightBot.actions;

import LightBot.Terrain;
import LightBot.personnage.Pcardinaux;
import LightBot.personnage.Personnage;

public class TournerDroite implements Actions {

	@Override
	public void Agir(Personnage P) {
		// TODO Auto-generated method stub
		if (P.getOrientation() == Pcardinaux.EAST){
			P.setOrientation(Pcardinaux.SOUTH);
		}
		else if (P.getOrientation() == Pcardinaux.NORTH){
			P.setOrientation(Pcardinaux.EAST);
		}
		else if (P.getOrientation() == Pcardinaux.WEST){
			P.setOrientation(Pcardinaux.NORTH);
		}
		else {
			P.setOrientation(Pcardinaux.WEST);
		}
	}

}
