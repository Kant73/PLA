package LightBot.actions;

import LightBot.cases.Couleur;
import LightBot.personnage.Personnage;

public class Wash extends Actions {

	public Wash() {
		super();
	}
	
	public Wash(Couleur c) {
		super(c);
	}
	
	@Override
	public void agir(Personnage perso){
		if(matchColor(perso)){
			if(perso.getCouleur()!=Couleur.Blanc)perso.setCouleur(Couleur.Blanc);
		}
	}
	
	@Override
	public Wash clone() throws CloneNotSupportedException{
		Wash copie=(Wash)super.clone();
		return copie;
	}

}
