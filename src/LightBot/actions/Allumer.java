package LightBot.actions;

import LightBot.Terrain;
import LightBot.cases.Case;
import LightBot.cases.Couleur;
import LightBot.cases.Lampe;
import LightBot.personnage.Personnage;


public class Allumer implements Actions {

	@Override
	public void Agir(Personnage P) {
		// TODO Auto-generated method stub
		Case C = P.getTerrain().getEnsembleDeCase()[P.getPositionX()][P.getPositionY()];
		if (C instanceof Lampe){
			if (C.getColor() == Couleur.Bleu){
				C.setColor(Couleur.Jaune);
			}
		}
	}

}
