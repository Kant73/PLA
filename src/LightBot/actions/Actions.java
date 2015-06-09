package LightBot.actions;

import LightBot.cases.Couleur;
import LightBot.personnage.Personnage;


public abstract class Actions {

	protected Personnage perso;
	protected Couleur couleurCondition;
	
	public Actions(Personnage p){
		this.perso=p;
		this.couleurCondition=Couleur.Blanc;
	}
	
	public Actions(Personnage p,Couleur c){
		this(p);
		if(c==Couleur.Violet)this.couleurCondition=c;
	}
	
	public void agir(){}
	
	public Personnage getPersonnage(){
		return this.perso;
	}
	
	public Couleur getCouleur(){
		return this.couleurCondition;
	}
	
}
