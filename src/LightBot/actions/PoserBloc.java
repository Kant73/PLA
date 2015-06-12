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

	public void agir() {
		
		Pcardinaux orientationPerso = this.perso.getOrientation();
		int positionPersoX = this.perso.getPositionX();
		int positionPersoY = this.perso.getPositionY();
		Terrain t = this.perso.getTerrain();
		System.out.println(orientationPerso);
		switch(orientationPerso) {
		case NORTH :
			if ((positionPersoY - 1) >= 0) {
				Case C = this.perso.getTerrain().getEnsembleDeCase()[this.perso.getPositionX()][this.perso.getPositionY()-1];
				if (C instanceof Normal && C.getHauteur() < 5) {
					C.setHauteur(C.getHauteur() + 2);
					System.out.println("+1");
				}	else System.out.println("LimiteHN");
			}	else {System.out.println("Hors tableau");
				}	
			break;
		case SOUTH :
			if ((positionPersoY + 1) < (t.getLongueur())) {
				Case C = this.perso.getTerrain().getEnsembleDeCase()[this.perso.getPositionX()][this.perso.getPositionY()+1];
				if (C instanceof Normal && C.getHauteur() < 5) {
					C.setHauteur(C.getHauteur() + 2);
					System.out.println("Poser");
				}	else System.out.println("LimiteHS");
			}	else {System.out.println("Hors tableau");
				}
			break;
		case EAST :
			if ((positionPersoX + 1) < (t.getLargeur())) {
				Case C = this.perso.getTerrain().getEnsembleDeCase()[this.perso.getPositionX()+1][this.perso.getPositionY()];
				if (C instanceof Normal && C.getHauteur() < 5) {
					C.setHauteur(C.getHauteur() + 2);
					
				}	else System.out.println("LimiteHE");
			}	else {System.out.println("Hors tableau");
				}
			break;
		case WEST :
			if ((positionPersoX - 1) >= 0) {
				Case C = this.perso.getTerrain().getEnsembleDeCase()[this.perso.getPositionX()-1][this.perso.getPositionY()];
				if (C instanceof Normal && C.getHauteur() < 5) {
					C.setHauteur(C.getHauteur() + 2);
					
				}	else System.out.println("LimiteHW");
			}	else {System.out.println("Hors tableau");
				}
			break;
		}

		}
}


