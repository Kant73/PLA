package LightBot.actions;

import LightBot.Terrain;
import LightBot.cases.Couleur;
import LightBot.personnage.Pcardinaux;
import LightBot.personnage.Personnage;

public class Sauter extends Actions {

	public Sauter() {
		super();
	}
	
	public Sauter(Couleur c){
		super(c);
	}
	
	public String toString(){
		if(this.couleurCondition==Couleur.Violet || this.couleurCondition==Couleur.Rose)return "Sauter cond.";
		return "Sauter";
	}
	
	@Override
	public Sauter clone() throws CloneNotSupportedException{
		Sauter copie=(Sauter)super.clone();
		return copie;
	}

	@Override
	public void agir(Personnage perso) {
		if(matchColor(perso)){
			Pcardinaux o = perso.getOrientation();
			Terrain T = perso.getTerrain();
			int x = perso.getPositionX();
			int y = perso.getPositionY();
			int h = T.getEnsembleDeCase()[x][y].getHauteur();
			
			switch(o){
				case EAST :
					if ((x+1)<T.getLargeur()){
						if(h-1 == T.getEnsembleDeCase()[x+1][y].getHauteur()||h+1 == T.getEnsembleDeCase()[x+1][y].getHauteur()){
							perso.setPositionX(x+1);
						}
					}else {perso.setMort(true);}
					break;
				case SOUTH :
					if ((y+1)<T.getLongueur()){
						if(h-1 == T.getEnsembleDeCase()[x][y+1].getHauteur() || h+1 == T.getEnsembleDeCase()[x][y+1].getHauteur()){
							perso.setPositionY(y+1);
						}
					}else{perso.setMort(true);}
					break;
				case WEST : 
					if ((x-1)>=0){
						if(h-1 == T.getEnsembleDeCase()[x-1][y].getHauteur() || h+1 == T.getEnsembleDeCase()[x-1][y].getHauteur()){
							perso.setPositionX(x-1);
						}
					}else{perso.setMort(true);}
					break;
				case NORTH :
					if ((y-1)>=0 ){
						if(h-1 == T.getEnsembleDeCase()[x][y-1].getHauteur() || h+1 == T.getEnsembleDeCase()[x][y-1].getHauteur()){
							perso.setPositionY(y-1);
						}
					}else{perso.setMort(true);}
					break;
				default:break;
			}

			if(T.getEnsembleDeCase()[perso.getPositionX()][perso.getPositionY()].getHauteur() == 0){
				perso.setMort(true);				
			}
		}
	}

}
