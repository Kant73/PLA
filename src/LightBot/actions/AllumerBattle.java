package LightBot.actions;

import LightBot.cases.Case;
import LightBot.cases.Couleur;
import LightBot.personnage.Personnage;

public class AllumerBattle extends Actions {

	public AllumerBattle() {
		super();
	}
	
	public AllumerBattle(Couleur c){
		super(c);
	}

	
	@Override
	public void agir(Personnage perso){
		Case C = perso.getTerrain().getEnsembleDeCase()[perso.getPositionX()][perso.getPositionY()];
		Couleur color = perso.getCouleur();
		if (C.getColor() == color){
			C.setColor(Couleur.Bleu);
		}
		else {
			C.setColor(color);
		}
	}
}
