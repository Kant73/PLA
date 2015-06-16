package LightBot.actions;

import LightBot.Terrain;
import LightBot.cases.Case;
import LightBot.personnage.Personnage;

public class Swap extends Actions {

	public Swap(Personnage p) {
		super(p);
		// TODO Auto-generated constructor stub
	}

	public void agir(){
		Terrain t = this.perso.getTerrain();
		Case[][] tableau = t.getEnsembleDeCase();
		Case temp;
		int x = this.perso.getPositionX();
		int y = this.perso.getPositionY();
		
		temp = tableau[x][y];		
		switch(this.perso.getOrientation()){
			case EAST :
				if ((x+1) < t.getLargeur()){
					tableau[x][y] = tableau[x+1][y];
					tableau[x+1][y] = temp;
				}
				break;
			case SOUTH :
				if ((y+1) < t.getLongueur()){
					tableau[x][y] = tableau[x][y+1];
					tableau[x][y+1] = temp;
				}
				break;
			case WEST : 
				if ((x-1) >= 0){
					tableau[x][y] = tableau[x-1][y];
					tableau[x-1][y] = temp;
				}else
				break;
			case NORTH :
				if ((y-1) >= 0){
					tableau[x][y] = tableau[x][y-1];
					tableau[x][y-1] = temp;
				}
				break;
			default:break;
		}
	}
}
