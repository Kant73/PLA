package LightBot.actions;

import LightBot.Terrain;
import LightBot.cases.Case;
import LightBot.cases.Couleur;
import LightBot.cases.Memoire;
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
	
	@Override
	public PoserBloc clone() throws CloneNotSupportedException{
		PoserBloc copie=(PoserBloc)super.clone();
		return copie;
	}
	
	/**
	 * Poser des blocs sur un terrain de base.
	 * Si la case est de base, elle est transformée en case Memoire.
	 * Sinon, la hauteur de la case Memoire est incrémentée.
	 */
	public void agir() {
		int positionPersoX = this.perso.getPositionX();					// On récupère la position X du personnage.
		int positionPersoY = this.perso.getPositionY();					// On récupère la position Y du personnage.
		Terrain t = this.perso.getTerrain();							// On récupère le terrain.
		switch(this.perso.getOrientation()) {
		case NORTH :
			if ((positionPersoY - 1) >= 0) {
				Case C = this.perso.getTerrain().getEnsembleDeCase()[positionPersoX][positionPersoY-1];					// On récupère la case adjacente en face du personnage.
				if (C instanceof Memoire && (C.getHauteur() < t.getHauteurMax()) && (t.getReserveBloc() > 0)) {			// Si C est un bloc Memoire et que sa hauteur est inférieure à la hauteurMax du terrain et que la réserve n'est pas vide,
					C.setHauteur(C.getHauteur()+1);																		// on augmente la hauteur du bloc Memoire de 1.
					t.decrementReserve();;																				// On diminue la réserve de 1 bloc.
				}else if(C instanceof Normal){																			// Sinon si C est une instance de Normal,
					int pHauteurCase = C.getHauteur();																	// on récupère la hauteur de la case C.
					if ((C.getHauteur() < t.getHauteurMax()) && (C.getHauteur() != 0) && (t.getReserveBloc() > 0)) {	// Si la hauteur de C est inférieure à la hauteurMax du terrain, qu'il y a déjà une case Normal et que la réserve n'est pas vide,
						Memoire caseMemoire = new Memoire((pHauteurCase+1), pHauteurCase);								// création d'un bloc Memoire qui a pour hauteur, la hauteur originale +1. On lui attribut en plus la hauteur original de la case Normal.
						t.getEnsembleDeCase()[positionPersoX][positionPersoY-1] = caseMemoire;																								//On met le bloc Memoire dans C.
						t.decrementReserve();																			// On diminue la réserve de 1 bloc.
					}
				}else {
					System.out.println("LimiteHN  || Case non Normal/Memoire || Pas de case || réserve à 0");
				}
			}else{
				System.out.println("Hors tableau");
			}	
			break;
		case SOUTH :
			if ((positionPersoY + 1) < t.getLongueur()) {
				Case C = this.perso.getTerrain().getEnsembleDeCase()[positionPersoX][positionPersoY+1];
				if (C instanceof Memoire && (C.getHauteur() < t.getHauteurMax()) && (t.getReserveBloc() > 0)) {			// Si C est un bloc Memoire et que sa hauteur est inférieure à la hauteurMax du terrain et que la réserve n'est pas vide,
					C.setHauteur(C.getHauteur()+1);																		// on augmente la hauteur du bloc Memoire de 1.
					t.decrementReserve();																				// On diminue la réserve de 1 bloc.
				}else if (C instanceof Normal) {																		// Sinon si C est une instance de Normal,
					int pHauteurCase = C.getHauteur();																	// on récupère la hauteur de la case C.
					if ((C.getHauteur() < t.getHauteurMax()) && (C.getHauteur() != 0) && (t.getReserveBloc() > 0)) {	// Si la hauteur de C est inférieure à la hauteurMax du terrain, qu'il y a déjà une case Normal et que la réserve n'est pas vide,
						Memoire caseMemoire = new Memoire((pHauteurCase+1), pHauteurCase);								// création d'un bloc Memoire qui a pour hauteur, la hauteur originale +1. On lui attribut en plus la hauteur original de la case Normal.
						t.getEnsembleDeCase()[positionPersoX][positionPersoY+1] = caseMemoire;																								//On met le bloc Memoire dans C.
						t.decrementReserve();																			// On diminue la réserve de 1 bloc.
					}
				}else{
					System.out.println("LimiteHS  || Case non Normal/Memoire || Pas de case || réserve à 0");
				}
			}else{
				System.out.println("Hors tableau");
			}
			break;
		case EAST :
			if ((positionPersoX + 1) < t.getLargeur()) {
				Case C = this.perso.getTerrain().getEnsembleDeCase()[positionPersoX+1][positionPersoY];
				if (C instanceof Memoire && (C.getHauteur() < t.getHauteurMax()) && (t.getReserveBloc() > 0)) {			// Si C est un bloc Memoire et que sa hauteur est inférieure à la hauteurMax du terrain et que la réserve n'est pas vide,
					C.setHauteur(C.getHauteur()+1);																		// on augmente la hauteur du bloc Memoire de 1.
					t.decrementReserve();																				// On diminue la réserve de 1 bloc.
				}else if (C instanceof Normal) {																		// Sinon si C est une instance de Normal,
					int pHauteurCase = C.getHauteur();																	// on récupère la hauteur de la case C.
					if ((C.getHauteur() < t.getHauteurMax()) && (C.getHauteur() != 0) && (t.getReserveBloc() > 0)) {	// Si la hauteur de C est inférieure à la hauteurMax du terrain, qu'il y a déjà une case Normal et que la réserve n'est pas vide,
						Memoire caseMemoire = new Memoire((pHauteurCase+1), pHauteurCase);								// création d'un bloc Memoire qui a pour hauteur, la hauteur originale +1. On lui attribut en plus la hauteur original de la case Normal.
						t.getEnsembleDeCase()[positionPersoX+1][positionPersoY] = caseMemoire;																								//On met le bloc Memoire dans C.
						t.decrementReserve();																			// On diminue la réserve de 1 bloc.
					}
				}else{
					System.out.println("LimiteHE  || Case non Normal/Memoire || Pas de case || réserve à 0");
				}
			}else{
				System.out.println("Hors tableau");
			}
			break;
		case WEST :
			if ((positionPersoX - 1) >= 0) {
				Case C = this.perso.getTerrain().getEnsembleDeCase()[positionPersoX-1][positionPersoY];
				if (C instanceof Memoire && (C.getHauteur() < t.getHauteurMax()) && (t.getReserveBloc() > 0)) {			// Si C est un bloc Memoire et que sa hauteur est inférieure à la hauteurMax du terrain et que la réserve n'est pas vide,
					C.setHauteur(C.getHauteur()+1);																		// on augmente la hauteur du bloc Memoire de 1.
					t.decrementReserve();																				// On diminue la réserve de 1 bloc.
				}else if (C instanceof Normal){																			// Sinon si C est une instance de Normal,
					int pHauteurCase = C.getHauteur();																	// on récupère la hauteur de la case C.
					if ((C.getHauteur() < t.getHauteurMax()) && (C.getHauteur() != 0) && (t.getReserveBloc() > 0)) {	// Si la hauteur de C est inférieure à la hauteurMax du terrain, qu'il y a déjà une case Normal et que la réserve n'est pas vide,
						Memoire caseMemoire = new Memoire((pHauteurCase+1), pHauteurCase);								// création d'un bloc Memoire qui a pour hauteur, la hauteur originale +1. On lui attribut en plus la hauteur original de la case Normal.
						t.getEnsembleDeCase()[positionPersoX-1][positionPersoY] = caseMemoire;																						//On met le bloc Memoire dans C.
						t.decrementReserve();																			// On diminue la réserve de 1 bloc.
					}
				}else{
					System.out.println("LimiteHW  || Case non Normal/Memoire || Pas de case || réserve à 0");
				}
			}else{
				System.out.println("Hors tableau");
			}
			break;
		}

		}
}


