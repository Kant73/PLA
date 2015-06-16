package LightBot.actions;

import LightBot.cases.Couleur;
import LightBot.personnage.Personnage;


public abstract class Actions {

/********************************************* ATTRIBUTS *********************************************/
	
	protected Personnage perso;
	protected Couleur couleurCondition;
	protected int reserveCase;
	
/********************************************* ACCESSEURS *********************************************/	
	
	public Couleur getCouleur(){
		return this.couleurCondition;
	}
	
	public int getReserveCase() {
		return this.reserveCase;
	}
	
/********************************************* MUTATEURS *********************************************/
	
	public void setCouleur(Couleur pColor) {
		this.couleurCondition = pColor;
	}
	
	public void setReserveCase(int pReserveCase) {
		this.reserveCase = pReserveCase;
	}
	
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
	
	public Actions(Personnage pPerso, int pReserveCase){
		this(pPerso);
		this.reserveCase = pReserveCase;
	}
	
	public void agir() {}
	
	public Personnage getPersonnage(){
		return this.perso;
	}
}
