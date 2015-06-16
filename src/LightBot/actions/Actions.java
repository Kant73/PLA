package LightBot.actions;

import LightBot.cases.Couleur;
import LightBot.personnage.Personnage;


public abstract class Actions {

/********************************************* ATTRIBUTS *********************************************/
	
	protected Personnage perso;
	protected Couleur couleurCondition;
	
/********************************************* ACCESSEURS *********************************************/	
	
	public Couleur getCouleur(){
		return this.couleurCondition;
	}
	
/********************************************* MUTATEURS *********************************************/

/********************************************* METHODES D'INSTANCE *********************************************/
	
	public Actions(Personnage p){
		this.perso=p;
		this.couleurCondition=Couleur.Blanc;
	}
	
	public Actions(Personnage p,Couleur c){
		this(p);
		if(c==Couleur.Violet || c==Couleur.Rose){
			this.couleurCondition=c;
		}
	}
	
	public void agir() {}
	
	public Personnage getPersonnage(){
		return this.perso;
	}
}
