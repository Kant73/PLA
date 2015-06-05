package LightBot.actions;

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
			switch(C.getColor()){
			case Bleu:
				C.setColor(Couleur.Jaune);
				((Lampe) C).setAllume(true);
				break;
			case Jaune:
				C.setColor(Couleur.Bleu);
				((Lampe) C).setAllume(false);
				break;
			default:break;
			}
		}
	}

}
