package LightBot.actions;

import LightBot.Terrain;
import LightBot.cases.Couleur;
import LightBot.personnage.Pcardinaux;
import LightBot.personnage.Personnage;

public class Avancer extends Actions {

	public Avancer(Personnage p) {
		super(p);
	}
	
	public Avancer(Personnage p, Couleur c){
		super(p,c);
	}
	
	public String toString(){
		if(this.couleurCondition==Couleur.Violet)return "Avancer cond.";
		return "Avancer";
	}

	@Override
	public void agir() {
		if(this.couleurCondition==this.perso.getCouleur() || this.perso.getCouleur()==Couleur.Violet){
			Pcardinaux o = this.perso.getOrientation();
			Terrain T = this.perso.getTerrain();
			int x = this.perso.getPositionX();
			int y = this.perso.getPositionY();
			int h = T.getEnsembleDeCase()[x][y].getHauteur();
			
			if (o == Pcardinaux.EAST){
				if (h == T.getEnsembleDeCase()[x+1][y].getHauteur()){
					this.perso.setPositionX(x+1);
				}
			}
			else if (o == Pcardinaux.SOUTH){
				if (h == T.getEnsembleDeCase()[x][y+1].getHauteur()){
					this.perso.setPositionY(y+1);
				}
			}
			else if (o == Pcardinaux.WEST){
				if (h == T.getEnsembleDeCase()[x-1][y].getHauteur()){
					this.perso.setPositionX(x-1);
				}
			}
			else {
				if (h == T.getEnsembleDeCase()[x][y-1].getHauteur()){
					this.perso.setPositionY(y-1);
				}
			}
		}
	}
}
