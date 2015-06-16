package LightBot.actions;

import LightBot.Terrain;
import LightBot.cases.Case;
import LightBot.cases.Couleur;
import LightBot.cases.Normal;
import LightBot.personnage.Personnage;
import LightBot.personnage.Pcardinaux;

public class PoserBloc extends Actions{

	public PoserBloc(Personnage pPerso) {
		super(pPerso);
	}
	
	public PoserBloc(Personnage pPerso, Couleur pColor) {
		super(pPerso, pColor);
	}
	
	/**
	 * Poser des blocs sur un terrain de base.
	 * Chaque pose de bloc augmente la hauteur et le compteur de blocs posés de la case de +1.
	 * Le nombre de blocs posés correspond à l'attribut fondation.
	 */
	public void agir() {
		Pcardinaux orientationPerso = this.perso.getOrientation();		//On récupère l'orientation du personnage.
		int positionPersoX = this.perso.getPositionX();					//On récupère la position X du personnage.
		int positionPersoY = this.perso.getPositionY();					//On récupère la position Y du personnage.
		Terrain t = this.perso.getTerrain();							//On récupère le terrain.
		int reserve = t.getReserveBloc();								//Réserve de blocs par rapport au terrain.
		switch(orientationPerso) {
		case NORTH :
			if ((positionPersoY - 1) >= 0) {
				Case C = this.perso.getTerrain().getEnsembleDeCase()[this.perso.getPositionX()][this.perso.getPositionY()-1];		//On récupère la case adjacente en face du personnage.
				if (C instanceof Normal && C.getHauteur() < t.getHauteurMax() && (C.getHauteur() != -1) && (reserve > 0)) {			//On ajoute un bloc que si la case est une instance de Normal, que sa hauteur est inférieure à la hauteur max du terrain et que la réserve n'est pas vide.
					C.setHauteur(C.getHauteur() + 2);									//On augmente la hauteur de la case de +1. {getHauteur() retourne la hauteur-1}
					((Normal) C).setFondation((((Normal) C).getFondation() + 1));		//On informe le nombre de bloc qui a été posé sur la case.
					reserve = (reserve - 1);											//On enlève 1 bloc de la réserve.
					
				}	else System.out.println("LimiteHN  || Case non Normal || Pas de case || réserve à 0");
			}	else {System.out.println("Hors tableau");
				}	
			break;
		case SOUTH :
			if ((positionPersoY + 1) < (t.getLongueur())) {
				Case C = this.perso.getTerrain().getEnsembleDeCase()[this.perso.getPositionX()][this.perso.getPositionY()+1];
				if (C instanceof Normal && C.getHauteur() < t.getHauteurMax() && (C.getHauteur() != -1) && (reserve > 0)) {
					C.setHauteur(C.getHauteur() + 2);
					((Normal) C).setFondation((((Normal) C).getFondation() + 1));
					reserve = (reserve - 1);
				}	else System.out.println("LimiteHS  || Case non Normal || Pas de case || réserve à 0");
			}	else {System.out.println("Hors tableau");
				}
			break;
		case EAST :
			if ((positionPersoX + 1) < (t.getLargeur())) {
				Case C = this.perso.getTerrain().getEnsembleDeCase()[this.perso.getPositionX()+1][this.perso.getPositionY()];
				if (C instanceof Normal && C.getHauteur() < t.getHauteurMax() && (C.getHauteur() != -1) && (reserve > 0)) {
					C.setHauteur(C.getHauteur() + 2);
					((Normal) C).setFondation((((Normal) C).getFondation() + 1));
					reserve = (reserve - 1);
				}	else System.out.println("LimiteHE  || Case non Normal || Pas de case || réserve à 0");
			}	else {System.out.println("Hors tableau");
				}
			break;
		case WEST :
			if ((positionPersoX - 1) >= 0) {
				Case C = this.perso.getTerrain().getEnsembleDeCase()[this.perso.getPositionX()-1][this.perso.getPositionY()];
				if (C instanceof Normal && C.getHauteur() < t.getHauteurMax() && (C.getHauteur() != -1) && (reserve > 0)) {
					C.setHauteur(C.getHauteur() + 2);
					((Normal) C).setFondation((((Normal) C).getFondation() + 1));
					reserve = (reserve - 1);
				}	else System.out.println("LimiteHW  || Case non Normal || Pas de case || réserve à 0");
			}	else {System.out.println("Hors tableau");
				}
			break;
		}

		}
}


