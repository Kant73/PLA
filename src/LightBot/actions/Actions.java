package LightBot.actions;

import LightBot.exceptions.BreakException;
import LightBot.exceptions.CloneException;
import LightBot.cases.Couleur;
import LightBot.personnage.Personnage;


public abstract class Actions implements Cloneable{

/********************************************* ATTRIBUTS *********************************************/
	
	protected Couleur couleurCondition;
	
/********************************************* ACCESSEURS *********************************************/	
	/**
	 * Retourne la couleur de condition de l'action
	 * @return couleur de condition
	 */
	public Couleur getCouleur(){
		return this.couleurCondition;
	}
	
/********************************************* MUTATEURS *********************************************/
	
	/**
	 * Defini la couleur de condition de l'action
	 * @param pColor
	 */
	public void setCouleur(Couleur pColor) {
		this.couleurCondition = pColor;
	}
	
/********************************************* METHODES D'INSTANCE *********************************************/
	/**
	 * Construit une action et l'associe à un personnage
	 * @param personnage
	 */
	public Actions(){
		this.couleurCondition=Couleur.Blanc;
	}
	
	/**
	 * Construit une Action à l'aide d'un personnage et d'une couleur de condition
	 * @param personnage
	 * @param couleur
	 */
	public Actions(Couleur couleur){
		this();
		if(couleur==Couleur.Violet || couleur==Couleur.Rose){
			this.couleurCondition=couleur;
		}
	}
	
	/**
	 * Méthode agir à redefinir pour les classes filles de Action.
	 * @throws BreakException
	 * @throws CloneException
	 */
	public void agir(Personnage perso) throws BreakException,CloneException {}
	
	
	/**
	 * Retourne true si la couleur du personnage correspond aux couleurs de conditions
	 * @return boolean
	 */
	public boolean matchColor(Personnage perso){
		boolean colorOK;
		/* Marche bien pour n'avoir que 2 couleurs de conditions */
		colorOK = this.couleurCondition == perso.getCouleur(); 
		colorOK |= (perso.getCouleur() == Couleur.Violet 	&& this.couleurCondition != Couleur.Rose);
		colorOK |= (perso.getCouleur() == Couleur.Rose 	&& this.couleurCondition != Couleur.Violet);
		return colorOK;
	}
	
	/**
	 * Retourne une copie de l'action
	 */
	@Override
	public Actions clone() throws CloneNotSupportedException{
		Actions copie=(Actions)super.clone();
		return copie;
	}
}
