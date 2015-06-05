package LightBot.actions;

import LightBot.Terrain;
import LightBot.personnage.Pcardinaux;
import LightBot.personnage.Personnage;

public class Avancer implements Actions {

	@Override
	public void Agir(Personnage P) {
		// TODO Auto-generated method stub
		Pcardinaux o = P.getOrientation();
		Terrain T = P.getTerrain();
		int x = P.getPositionX();
		int y = P.getPositionY();
		int h = T.getEnsembleDeCase()[x][y].getHauteur();
		
		if (o == Pcardinaux.EAST){
			if (h == T.getEnsembleDeCase()[x+1][y].getHauteur()){
				P.setPositionX(x+1);
			}
		}
		else if (o == Pcardinaux.SOUTH){
			if (h == T.getEnsembleDeCase()[x][y-1].getHauteur()){
				P.setPositionY(y+1);
			}
		}
		else if (o == Pcardinaux.WEST){
			if (h == T.getEnsembleDeCase()[x-1][y].getHauteur()){
				P.setPositionX(x-1);
			}
		}
		else {
			if (h == T.getEnsembleDeCase()[x][y+1].getHauteur()){
				P.setPositionY(y+1);
			}
		}
	}



}
