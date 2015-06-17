package LightBot.actions;

import LightBot.Terrain;
import LightBot.cases.Case;
import LightBot.cases.Couleur;
import LightBot.cases.Transparente;
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
		if(this.couleurCondition==Couleur.Violet || this.couleurCondition==Couleur.Rose)return "Avancer cond.";
		return "Avancer";
	}

	@Override
	public void agir() {
		if(super.matchColor()){
			Terrain T = this.perso.getTerrain();
			int x = this.perso.getPositionX();
			int y = this.perso.getPositionY();
			int h = T.getEnsembleDeCase()[x][y].getHauteur();
			
			Case devant;
			switch(this.perso.getOrientation()){
			case EAST:
				if((x+1) < T.getLargeur()){
					devant = T.getEnsembleDeCase()[x+1][y];
					if(devant instanceof Transparente || (h == devant.getHauteur())){
						this.perso.setPositionX(x+1);
					}else if(devant.getHauteur() == 0){
						this.perso.setMort(true);
					}
				}else{
					this.perso.setMort(true);
				}
				break;
			case SOUTH:
				if((y+1) < T.getLongueur()){
					devant = T.getEnsembleDeCase()[x][y+1];
					if(devant instanceof Transparente || (h == devant.getHauteur())){
						this.perso.setPositionY(y+1);
					}else if(devant.getHauteur() == 0){
						this.perso.setMort(true);
					}
				}else{
					this.perso.setMort(true);
				}
				break;
			case WEST:
				if ((x-1) >= 0){
					devant = T.getEnsembleDeCase()[x-1][y];
					if(devant instanceof Transparente || (h == devant.getHauteur())){
						this.perso.setPositionX(x-1);
					}else if(devant.getHauteur() == 0){
						this.perso.setMort(true);
					}
				}else{
					this.perso.setMort(true);
				}
				break;
			case NORTH:
				if ((y-1) >= 0){
					devant = T.getEnsembleDeCase()[x][y-1];
					if(devant instanceof Transparente || (h == devant.getHauteur())){
						this.perso.setPositionY(y-1);
					}else if(devant.getHauteur() == 0){
						this.perso.setMort(true);
					}
				}else{
					this.perso.setMort(true);
				}
				break;	
			default:break;
			}
		}
	}
}
