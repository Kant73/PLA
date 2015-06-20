package LightBot.actions;

import LightBot.Terrain;
import LightBot.cases.Case;
import LightBot.cases.Couleur;
import LightBot.cases.Transparente;
import LightBot.personnage.Personnage;

public class Swap extends Actions {

	public Swap() {
		super();
	}
	
	public Swap(Couleur c){
		super(c);
	}
	
	@Override
	public Swap clone() throws CloneNotSupportedException{
		Swap copie=(Swap)super.clone();
		return copie;
	}

	public void agir(Personnage perso){
		if(matchColor(perso)){
		// Mettre une gestion de la couleur pour ne pouvoir faire un swap que si on est colorisé		
			Terrain t = perso.getTerrain();
			Case actuelle;
			int x = perso.getPositionX();
			int y = perso.getPositionY();
			
			actuelle = t.getEnsembleDeCase()[x][y];
			if(actuelle instanceof Transparente){
				switch(perso.getOrientation()){
					case EAST :
						if ((x+1) < t.getLargeur()){
							Case devant = t.getEnsembleDeCase()[x+1][y];
							if(devant instanceof Transparente){
								t.getEnsembleDeCase()[x][y] = devant;
								t.getEnsembleDeCase()[x+1][y] = actuelle;
							}
						}
						break;
					case SOUTH :
						if ((y+1) < t.getLongueur()){
							Case devant = t.getEnsembleDeCase()[x][y+1];
							if(devant instanceof Transparente){
								t.getEnsembleDeCase()[x][y] = devant;
								t.getEnsembleDeCase()[x][y+1] = actuelle;
							}
						}
						break;
					case WEST : 
						if ((x-1) >= 0){
							Case devant = t.getEnsembleDeCase()[x-1][y];
							if(devant instanceof Transparente){
								t.getEnsembleDeCase()[x][y] = devant;
								t.getEnsembleDeCase()[x-1][y] = actuelle;
							}
						}
						break;
					case NORTH :
						if ((y-1) >= 0){
							Case devant = t.getEnsembleDeCase()[x][y-1];
							if(devant instanceof Transparente){
								t.getEnsembleDeCase()[x][y] = devant;
								t.getEnsembleDeCase()[x][y-1] = actuelle;
							}
						}
						break;
					default:break;
				}
			}
		}
	}
}
