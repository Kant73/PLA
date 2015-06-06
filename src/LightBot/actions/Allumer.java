package LightBot.actions;

import LightBot.cases.Case;
import LightBot.cases.Couleur;
import LightBot.cases.Lampe;
import LightBot.personnage.Personnage;


public class Allumer extends Actions {

	public Allumer(Personnage p) {
		super(p);
	}
	
	public String toString(){
		return "Allumer";
	}

	@Override
	public void agir() {
		Case C = this.perso.getTerrain().getEnsembleDeCase()[this.perso.getPositionX()][this.perso.getPositionY()];
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
