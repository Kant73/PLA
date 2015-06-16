package LightBot.actions;

import LightBot.cases.Couleur;
import LightBot.personnage.Personnage;

public class Wash extends Actions {

	public Wash(Personnage p) {
		super(p);
	}
	
	public Wash(Personnage p, Couleur c) {
		super(p, c);
	}
	
	@Override
	public void agir(){
		if(matchColor()){
			if(this.perso.getCouleur()!=Couleur.Blanc)this.perso.setCouleur(Couleur.Blanc);
		}
	}

}
