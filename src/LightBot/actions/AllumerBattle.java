package LightBot.actions;

import LightBot.cases.Case;
import LightBot.cases.Couleur;
import LightBot.personnage.Personnage;

public class AllumerBattle extends Actions {

	public AllumerBattle(Personnage p) {
		super(p);
	}
	
	public AllumerBattle(Personnage p, Couleur c){
		super(p,c);
	}

	
	@Override
	public void agir(){
		Case C = this.perso.getTerrain().getEnsembleDeCase()[this.perso.getPositionX()][this.perso.getPositionY()];
		Couleur color = this.perso.getCouleur();
		if (C.getColor() == color){
			C.setColor(Couleur.Bleu);
		}
		else {
			C.setColor(color);
		}
	}
}
