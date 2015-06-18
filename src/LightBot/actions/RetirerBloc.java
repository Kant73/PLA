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
	
	@Override
	public RetirerBloc clone() throws CloneNotSupportedException{
		RetirerBloc copie=(RetirerBloc)super.clone();
		return copie;
	}

	/**
	 * Retirer des blocs Memoire du terrain.
	 * Il n'est pas possible de retirer les cases de base du terrain.
	 * Chaque retrait de bloc diminue la hauteur du bloc Memoire. 
	 * Si la hauteur initiale est atteinte dans le retrait de bloc, le bloc devient un bloc Normal.
	 */
	public void agir() {
		Pcardinaux orientationPerso = this.perso.getOrientation();				//On récupère l'orientation du personnage.
		int positionPersoX = this.perso.getPositionX();							//On récupère la position X du personnage.
		int positionPersoY = this.perso.getPositionY();							//On récupère la position Y du personnage.
		Terrain t = this.perso.getTerrain();									//On récupère le terrain.
		switch(orientationPerso) {
		case NORTH :
			if ((positionPersoY-1) >= 0) {
				if (t.getEnsembleDeCase()[positionPersoX][positionPersoY-1] instanceof Memoire){
					Memoire devant = (Memoire) t.getEnsembleDeCase()[positionPersoX][positionPersoY-1];
					if(devant.getHauteur()-1 > devant.getHauteurOriginal()){
						t.getEnsembleDeCase()[positionPersoX][positionPersoY-1].setHauteur(devant.getHauteur()-1);
					}else if(devant.getHauteur()-1 == devant.getHauteurOriginal()){
						t.getEnsembleDeCase()[positionPersoX][positionPersoY-1] = new Normal(devant.getHauteurOriginal());
					}
					t.incrementReserve();
				}
			}else{
				System.out.println("Hors tableau");
			}	
			break;		
		case SOUTH :
			if ((positionPersoY + 1) < t.getLongueur()) {
				if (t.getEnsembleDeCase()[positionPersoX][positionPersoY+1] instanceof Memoire){
					Memoire devant = (Memoire) t.getEnsembleDeCase()[positionPersoX][positionPersoY+1];
					if(devant.getHauteur()-1 > devant.getHauteurOriginal()){
						t.getEnsembleDeCase()[positionPersoX][positionPersoY+1].setHauteur(devant.getHauteur()-1);
					}else if(devant.getHauteur()-1 == devant.getHauteurOriginal()){
						t.getEnsembleDeCase()[positionPersoX][positionPersoY+1] = new Normal(devant.getHauteurOriginal());
					}
					t.incrementReserve();
				}
			}else{
				System.out.println("Hors tableau");
			}	
			break;	
		case EAST :
			if ((positionPersoX + 1) < t.getLargeur()) {
				if (t.getEnsembleDeCase()[positionPersoX+1][positionPersoY] instanceof Memoire){
					Memoire devant = (Memoire) t.getEnsembleDeCase()[positionPersoX+1][positionPersoY];
					if(devant.getHauteur()-1 > devant.getHauteurOriginal()){
						t.getEnsembleDeCase()[positionPersoX+1][positionPersoY].setHauteur(devant.getHauteur()-1);
					}else if(devant.getHauteur()-1 == devant.getHauteurOriginal()){
						t.getEnsembleDeCase()[positionPersoX+1][positionPersoY] = new Normal(devant.getHauteurOriginal());
					}
					t.incrementReserve();
				}
			}else{
				System.out.println("Hors tableau");
			}	
			break;	
		case WEST :
			if ((positionPersoX - 1) >= 0) {
				if (t.getEnsembleDeCase()[positionPersoX-1][positionPersoY] instanceof Memoire){
					Memoire devant = (Memoire) t.getEnsembleDeCase()[positionPersoX-1][positionPersoY];
					if(devant.getHauteur()-1 > devant.getHauteurOriginal()){
						t.getEnsembleDeCase()[positionPersoX-1][positionPersoY].setHauteur(devant.getHauteur()-1);
					}else if(devant.getHauteur()-1 == devant.getHauteurOriginal()){
						t.getEnsembleDeCase()[positionPersoX-1][positionPersoY] = new Normal(devant.getHauteurOriginal());
					}
					t.incrementReserve();
				}
			}else{
				System.out.println("Hors tableau");
			}	
			break;	
		}
	}
}

