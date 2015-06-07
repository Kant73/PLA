package LightBot;

import LightBot.cases.Case;
import LightBot.personnage.Pcardinaux;


public class Terrain {
	
/********************************************* ATTRIBUTS *********************************************/

	private int largeur;
	private int longueur;
	private int nbActionsPossible;
	private Case[][] ensembleDeCase;			//Tableau à 2 dimensions de cases représentant le terrain
	private Pcardinaux orientationInitiale;		//Orientation Initiale du Personnage
	
/********************************************* ACCESSEURS *********************************************/
	
	public int getLargeur() {
		return largeur;
	}
	
	public int getLongueur() {
		return longueur;
	}

	public int getNbActionsPossible() {
		return nbActionsPossible;
	}
	
	public Case[][] getEnsembleDeCase() {
		return ensembleDeCase;
	}
	
	public Pcardinaux getOrientationInitiale() {
		return orientationInitiale;
	}
	
/********************************************* MUTATEURS *********************************************/

	public void setLargeur(int pLargeur) {
		this.largeur = pLargeur;
	}

	public void setLongueur(int pLongueur) {
		this.longueur = pLongueur;
	}

	public void setNbActionsPossible(int pNbActionsPossible) {
		this.nbActionsPossible = pNbActionsPossible;
	}

	public void setEnsembleDeCase(Case[][] pEnsembleDeCase) {
		this.ensembleDeCase = pEnsembleDeCase;
	}

	public void setOrientationInitiale(Pcardinaux pOrientationInitiale) {
		this.orientationInitiale = pOrientationInitiale;
	}
	
/********************************************* METHODES D'INSTANCE *********************************************/
	
	//Constructeur de l'objet Terrain
	public Terrain(int pLargeur, int pLongueur, int pScore, int pNbActionsPossible, Case[][] pEnsembleDeCase, Pcardinaux pOrientationInitiale){
		largeur = pLargeur;
		longueur = pLongueur;
		score = pScore;
		nbActionsPossible = pNbActionsPossible;
		ensembleDeCase = pEnsembleDeCase;
		orientationInitiale = pOrientationInitiale;
	}

}
