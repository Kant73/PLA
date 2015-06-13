package LightBot.actions;

import LightBot.Terrain;
import LightBot.cases.Case;
import LightBot.cases.Couleur;
import LightBot.cases.Normal;
import LightBot.personnage.Pcardinaux;
import LightBot.personnage.Personnage;

public class RetirerBloc extends Actions {

	public RetirerBloc(Personnage pPerso) {
		super(pPerso);
	}
	
	public RetirerBloc(Personnage pPerso, Couleur pColor) {
		super(pPerso, pColor);
	}

	/**
	 * Retirer des blocs sur une case.
	 * Il n'est pas possible de retirer les cases de base du terrain.
	 * Chaque retrait de bloc diminue la hauteur et le compteur de blocs posés de la case de 1.
	 * Le nombre de blocs posés correspond à l'attribut fondation.
	 */
	public void agir() {
		Pcardinaux orientationPerso = this.perso.getOrientation();				//On récupère l'orientation du personnage.
		int positionPersoX = this.perso.getPositionX();							//On récupère la position X du personnage.
		int positionPersoY = this.perso.getPositionY();							//On récupère la position Y du personnage.
		Terrain t = this.perso.getTerrain();									//On récupère le terrain.
		switch(orientationPerso) {
		case NORTH :
			if ((positionPersoY-1) >= 0) {
				Case C = this.perso.getTerrain().getEnsembleDeCase()[this.perso.getPositionX()][this.perso.getPositionY()-1];		//On récupère la case adjacente en face du personnage.
				if (C instanceof Normal && C.getHauteur() > 0 && (((Normal) C).getFondation() > 0)) {		//On ne retire un bloc que si la case est une instance de Normal, que la hauteur est supérieure à 0 et qu'elle ne soit pas une base du terrain.
					C.setHauteur(C.getHauteur());															//On diminue la hauteur de la case de 1.
					((Normal) C).setFondation((((Normal) C).getFondation() - 1));							//On informe que l'on retire un bloc.
				}	else {System.out.println("LimiteHN || Terrain de base !");}
			}	else {System.out.println("Hors tableau");
				}	
			break;
		case SOUTH : 
			if ((positionPersoY+1) < t.getLongueur()) {
				Case C = this.perso.getTerrain().getEnsembleDeCase()[this.perso.getPositionX()][this.perso.getPositionY()+1];
				if (C instanceof Normal && C.getHauteur() > 0 && (((Normal) C).getFondation() > 0)) {
					C.setHauteur(C.getHauteur());
					((Normal) C).setFondation((((Normal) C).getFondation() - 1));
				}	else {System.out.println("LimiteHS || Terrain de base !");}
			}	else {System.out.println("Hors tableau");
				}	
			break;
		case EAST :
			if ((positionPersoX+1) < t.getLargeur()) {
				Case C = this.perso.getTerrain().getEnsembleDeCase()[this.perso.getPositionX()+1][this.perso.getPositionY()];
				if (C instanceof Normal && C.getHauteur() > 0 && (((Normal) C).getFondation() > 0)) {
					C.setHauteur(C.getHauteur());
					((Normal) C).setFondation((((Normal) C).getFondation() - 1));
				}	else {System.out.println("LimiteHE || Terrain de base !");}
			}	else {System.out.println("Hors tableau");
				}	
			break;
		case WEST :
			if ((positionPersoX-1) >= 0) {
				Case C = this.perso.getTerrain().getEnsembleDeCase()[this.perso.getPositionX()-1][this.perso.getPositionY()];
				if (C instanceof Normal && C.getHauteur() > 0 && (((Normal) C).getFondation() > 0)) {
					C.setHauteur(C.getHauteur());
					((Normal) C).setFondation((((Normal) C).getFondation() - 1));
				}	else {System.out.println("LimiteHW || Terrain de base !");}
			}	else {System.out.println("Hors tableau");
				}	
			break;
		}
	}
}
