package LightBot.actions;

import LightBot.exceptions.BreakException;
import LightBot.exceptions.CloneException;
import LightBot.cases.Couleur;
import LightBot.personnage.Personnage;


public abstract class Actions implements Cloneable{

/********************************************* ATTRIBUTS *********************************************/
	
	protected Personnage perso;
	protected Couleur couleurCondition;
	
/********************************************* ACCESSEURS *********************************************/	
	
	public Couleur getCouleur(){
		return this.couleurCondition;
	}
	
/********************************************* MUTATEURS *********************************************/
	
	public void setCouleur(Couleur pColor) {
		this.couleurCondition = pColor;
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
	
	public void agir() throws BreakException,CloneException {}
	
	public Personnage getPersonnage(){
		return this.perso;
	}
	
	public boolean matchColor (){
		boolean colorOK;
		/* Marche bien pour n'avoir que 2 couleurs de conditions */
		colorOK = this.couleurCondition == this.perso.getCouleur(); 
		colorOK |= (this.perso.getCouleur() == Couleur.Violet 	&& this.couleurCondition != Couleur.Rose);
		colorOK |= (this.perso.getCouleur() == Couleur.Rose 	&& this.couleurCondition != Couleur.Violet);
		return colorOK;
	}
	
	@Override
	public Actions clone() throws CloneNotSupportedException{
		Actions copie=(Actions)super.clone();
		return copie;
	}
}
