package LightBot.actions;

import LightBot.Terrain;
import LightBot.cases.Case;
import LightBot.cases.Couleur;
import LightBot.cases.Memoire;
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
		int reserve = t.getReserveBloc();										//Réserve de blocs par rapport au terrain.
		switch(orientationPerso) {
		case NORTH :
			if ((positionPersoY-1) >= 0) {
				if (t.getEnsembleDeCase()[positionPersoX][positionPersoY-1] instanceof Memoire){
					Memoire devant = (Memoire) t.getEnsembleDeCase()[positionPersoX][positionPersoY-1];
					if(devant.getHauteur()-1 > devant.getHauteurOriginal()){
						devant.setHauteur(devant.getHauteur()-1);
					}else if(devant.getHauteur()-1 == devant.getHauteurOriginal()){
						t.getEnsembleDeCase()[positionPersoX][positionPersoY-1] = new Normal(devant.getHauteurOriginal());
					}
					reserve++;
				}
			}else{
				System.out.println("Hors tableau");
			}	
			break;
		case SOUTH : 
			if ((positionPersoY+1) < t.getLongueur()){
				Case C = t.getEnsembleDeCase()[positionPersoX][positionPersoY+1];
				if (C instanceof Memoire  && ((C.getHauteur()-1) > ((Memoire) C).getHauteurOriginal())) {		
					C.setHauteur((C.getHauteur()-1));															
					reserve = (reserve + 1);
				}	
				else if (C instanceof Memoire && ((C.getHauteur()-1) == ((Memoire) C).getHauteurOriginal())){
					System.out.println("Je suis là)");
					Normal caseNormal = new Normal((((Memoire) C).getHauteurOriginal()));
					t.getEnsembleDeCase()[positionPersoX][positionPersoY+1] = caseNormal;
					reserve = (reserve + 1);
				}
			}	else {System.out.println("Hors tableau");
				}	
			break;
		case EAST :
			if ((positionPersoX+1) < t.getLargeur()) {
				Case C = t.getEnsembleDeCase()[positionPersoX+1][positionPersoY];
				if (C instanceof Memoire  && ((C.getHauteur()-1) > ((Memoire) C).getHauteurOriginal())) {		
					C.setHauteur((C.getHauteur()-1));															
					reserve = (reserve + 1);
				}	
				else if (C instanceof Memoire && ((C.getHauteur()-1) == ((Memoire) C).getHauteurOriginal())){
					System.out.println("Je suis là)");
					Normal caseNormal = new Normal((((Memoire) C).getHauteurOriginal()));
					t.getEnsembleDeCase()[positionPersoX+1][positionPersoY] = caseNormal;
					reserve = (reserve + 1);
				}
			}	else {System.out.println("Hors tableau");
				}	
			break;
		case WEST :
			if ((positionPersoX-1) >= 0) {
				Case C = t.getEnsembleDeCase()[positionPersoX-1][positionPersoY];
				if (C instanceof Memoire  && ((C.getHauteur()-1) > ((Memoire) C).getHauteurOriginal())) {		
					C.setHauteur((C.getHauteur()-1));															
					reserve = (reserve + 1);
				}	
				else if (C instanceof Memoire && ((C.getHauteur()-1) == ((Memoire) C).getHauteurOriginal())){
					System.out.println("Je suis là)");
					Normal caseNormal = new Normal((((Memoire) C).getHauteurOriginal()));
					t.getEnsembleDeCase()[positionPersoX-1][positionPersoY] = caseNormal;
					reserve = (reserve + 1);
				}
			}	else {System.out.println("Hors tableau");
				}	
			break;
		}
	}
}

