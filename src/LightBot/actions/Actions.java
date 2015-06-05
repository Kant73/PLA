package LightBot.actions;

import LightBot.personnage.Personnage;


public abstract class Actions {

	protected Personnage perso;
	
	Actions(Personnage p){
		this.perso=p;
	}
	
	public void agir(){}
	
}
