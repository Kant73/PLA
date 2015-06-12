package LightBot.actions;

import LightBot.Terrain;
import LightBot.cases.Couleur;
import LightBot.personnage.Pcardinaux;
import LightBot.personnage.Personnage;

public class Sauter extends Actions {

	public Sauter(Personnage p) {
		super(p);
	}
	
	public Sauter(Personnage p, Couleur c){
		super(p,c);
	}
	
	public String toString(){
		if(this.couleurCondition==Couleur.Violet || this.couleurCondition==Couleur.Rose)return "Sauter cond.";
		return "Sauter";
	}

	@Override
	public void agir() {
		if(this.couleurCondition==this.perso.getCouleur() || (this.perso.getCouleur()==Couleur.Violet && this.couleurCondition!=Couleur.Rose) || (this.perso.getCouleur()==Couleur.Rose && this.couleurCondition!=Couleur.Violet)){
			Pcardinaux o = this.perso.getOrientation();
			Terrain T = this.perso.getTerrain();
			int x = this.perso.getPositionX();
			int y = this.perso.getPositionY();
			int h = T.getEnsembleDeCase()[x][y].getHauteur();
			
			switch(o){
				case EAST :
					if ((x+1)<T.getLargeur()){
						if(h == T.getEnsembleDeCase()[x+1][y].getHauteur()-1||h == T.getEnsembleDeCase()[x+1][y].getHauteur()+1)this.perso.setPositionX(x+1);
					}else {this.perso.setMort(true);}
					break;
				case SOUTH :
					if ((y+1)<T.getLongueur()){
						if(h == T.getEnsembleDeCase()[x][y+1].getHauteur() -1 || h == T.getEnsembleDeCase()[x][y+1].getHauteur()+1)this.perso.setPositionY(y+1);
					}else{this.perso.setMort(true);}
					break;
				case WEST : 
					if ((x-1)<0){
						if(h == T.getEnsembleDeCase()[x-1][y].getHauteur()-1 || h == T.getEnsembleDeCase()[x-1][y].getHauteur()+1)this.perso.setPositionX(x-1);
					}else{this.perso.setMort(true);}
					break;
				case NORTH :
					if ((y-1)<0 ){
						if(h == T.getEnsembleDeCase()[x][y-1].getHauteur()-1 || h == T.getEnsembleDeCase()[x][y-1].getHauteur()+1)this.perso.setPositionY(y-1);
					}else{this.perso.setMort(true);}
					break;
				default:break;
			}

			if(T.getEnsembleDeCase()[this.perso.getPositionX()][this.perso.getPositionY()].getHauteur()==-1){
				this.perso.setMort(true);				
			}
		}
	}

}
