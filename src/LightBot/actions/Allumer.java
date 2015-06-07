package LightBot.actions;

import LightBot.cases.Case;
import LightBot.cases.Condition;
import LightBot.cases.Couleur;
import LightBot.cases.Lampe;
import LightBot.personnage.Personnage;


public class Allumer extends Actions {

	public Allumer(Personnage p) {
		super(p);
	}
	
	public Allumer(Personnage p, Couleur c){
		super(p,c);
	}
	
	public String toString(){
		if(this.couleurCondition==Couleur.Violet)return "Allumer cond.";
		return "Allumer";
	}

	@Override
	public void agir() {
		if(this.couleurCondition==this.perso.getCouleur() || this.perso.getCouleur()==Couleur.Violet){
			Case C = this.perso.getTerrain().getEnsembleDeCase()[this.perso.getPositionX()][this.perso.getPositionY()];
			if (C instanceof Lampe){
				switch(C.getColor()){
				case Bleu:
					C.setColor(Couleur.Jaune);
					((Lampe) C).setAllumee(true);
					break;
				case Jaune:
					C.setColor(Couleur.Bleu);
					((Lampe) C).setAllumee(false);
					break;
				default:break;
				}
			}else if(C instanceof Condition){
				if(((Condition)C).isAllume() && this.perso.getCouleur()!=Couleur.Blanc){
					this.perso.setCouleur(Couleur.Blanc);
					((Condition)C).setAllumee(false);
				}else if(!((Condition)C).isAllume() && this.perso.getCouleur()==Couleur.Blanc){
					this.perso.setCouleur(Couleur.Violet);
					((Condition)C).setAllumee(true);
				}
			}
		}
	}
}
