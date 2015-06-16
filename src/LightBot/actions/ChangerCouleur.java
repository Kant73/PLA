package LightBot.actions;

import LightBot.cases.Couleur;
import LightBot.personnage.Personnage;

public class ChangerCouleur extends Actions {

	public ChangerCouleur(Personnage p) {
		super(p);
	}
	
	public ChangerCouleur(Personnage p, Couleur pCouleur) {
		super(p, pCouleur);
}
	/**
	 * Permet de changer la couleur de l'action.
	 * Si l'action est :
	 * blanche => violette
	 * violette => rose
	 * rose => blanche
	 */
	public void agir() {
		if (this.couleurCondition == Couleur.Blanc) {
			this.setCouleur(Couleur.Violet);
		}
		if (this.couleurCondition == Couleur.Violet) {
			this.setCouleur(Couleur.Rose);
		}
		if (this.couleurCondition == Couleur.Rose) {
			this.setCouleur(Couleur.Blanc);
		}
		
	}
	
}