package LightBot;

import LightBot.cases.Case;
import LightBot.personnage.Pcardinaux;


public class Terrain {

	private int largeur;
	private int longueur;
	private int score;
	private int nbActionsPossible;
	private int xInit, yInit;
	
	private Case ensembleDeCase [] [];
	private Pcardinaux orientationInitiale;
	
	public Terrain init() {
		return null;
	}
	
	public void detruire() {
	}

	public int getLargeur() {
		return largeur;
	}

	public void setLargeur(int largeur) {
		this.largeur = largeur;
	}

	public int getLongueur() {
		return longueur;
	}

	public void setLongueur(int longueur) {
		this.longueur = longueur;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getNbActionsPossible() {
		return nbActionsPossible;
	}

	public void setNbActionsPossible(int nbActionsPossible) {
		this.nbActionsPossible = nbActionsPossible;
	}

	public int getxInit() {
		return xInit;
	}

	public void setxInit(int xInit) {
		this.xInit = xInit;
	}

	public int getyInit() {
		return yInit;
	}

	public void setyInit(int yInit) {
		this.yInit = yInit;
	}

	public Case[][] getEnsembleDeCase() {
		return ensembleDeCase;
	}

	public void setEnsembleDeCase(Case ensembleDeCase[][]) {
		this.ensembleDeCase = ensembleDeCase;
	}

	public Pcardinaux getOrientationInitiale() {
		return orientationInitiale;
	}

	public void setOrientationInitiale(Pcardinaux orientationInitiale) {
		this.orientationInitiale = orientationInitiale;
	}
	
}
