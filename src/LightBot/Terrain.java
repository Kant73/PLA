package LightBot;

import LightBot.cases.Case;
import LightBot.personnage.Pcardinaux;


public class Terrain {
	
/********************************************* ATTRIBUTS *********************************************/

	private int largeur;
	private int longueur;
	private int nbActionsPossible;
	private Case[][] ensembleDeCase;			//Tableau à 2 dimensions de cases représentant le terrain
	
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
	
	public void affiche(){
		for(int y=0;y<largeur;y++){
			for(int x=0;x<longueur;x++)
				/*if(this.ensembleDeCase[x][y]==null)System.out.print("null ");
				else System.out.print(this.ensembleDeCase[x][y].getColor()+" ");*/
				if(this.ensembleDeCase[x][y]!=null) System.out.print("("+x+","+y+")"+this.ensembleDeCase[x][y].getColor()+" ");
			System.out.println("");
		}
	}
	
/********************************************* METHODES D'INSTANCE *********************************************/
	
	//Constructeur de l'objet Terrain
	public Terrain(int pLargeur, int pLongueur, int pNbActionsPossible, Case[][] pEnsembleDeCase){
		largeur = pLargeur;
		longueur = pLongueur;
		nbActionsPossible = pNbActionsPossible;
		ensembleDeCase = pEnsembleDeCase;
	}
	
	public Terrain(int largeur, int longueur){
		this.longueur=longueur;
		this.largeur=largeur;
		this.ensembleDeCase=new Case[largeur][longueur];
	}

}
