package LightBot;

import LightBot.cases.Case;
import LightBot.cases.Lampe;
import LightBot.cases.Normal;


public class Terrain {
	
/********************************************* ATTRIBUTS *********************************************/

	private int largeur;
	private int longueur;
	private int nbActionsPossible;
	private int reserveBloc;					//Réserve de blocs que peut poser le robot.
	private int nbLampeAllumee=0;
	private int maxAllumee;
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
	
	/**
	 * Accès à la réserve de blocs du robot.
	 * @return La réserve de blocs reserveBloc.
	 */
	public int getReserveBloc() {
		return reserveBloc;
	}
	
	public Case[][] getEnsembleDeCase() {
		return ensembleDeCase;
	}
	
	public int getNbLampeAllumee(){
		return this.nbLampeAllumee;
	}
	
	public int getMaxLampe(){
		return this.maxAllumee;
	}
	
	public int getHauteurMax(){
		int max = 0;
		for(int i = 0 ; i < this.largeur ; i++){
			for(int j = 0; j < this.longueur ; j++){
				int h = this.ensembleDeCase[i][j].getHauteur();
				if(h > max){
					max = h;
				}
			}
		}
		return max;
	}
	
	public int getPosCaseX(Case C){
		
		for(int i=0; i< largeur; i++){
			for(int j=0; j< longueur; j++){
				if (C == this.ensembleDeCase[i][j]){
					return i;
				}
			}
		}
		System.out.println("Case introuvable !!!");
		return -1;
		
	}
	
	public int getPosCaseY(Case C){
		
		for(int i=0; i< largeur; i++){
			for(int j=0; j< longueur; j++){
				if (C == this.ensembleDeCase[i][j]){
					return j;
				}
			}
		}
		System.out.println("Case introuvable !!!");
		return -1;
		
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
	
	/**
	 * Permet de modifier la réserve de blocs. 
	 * @param Nombre de blocs pour la réserve.
	 */
	public void setReserveBloc(int pReserveBloc) {
		this.reserveBloc = pReserveBloc;
	}

	public void setEnsembleDeCase(Case[][] pEnsembleDeCase) {
		this.ensembleDeCase = pEnsembleDeCase;
		this.setMaxLampe();
	}
	
	public void setMaxAllumee(int i){
		if(this.maxAllumee!=i)this.maxAllumee=i;
	}
	
	public void setMaxLampe(){
		this.maxAllumee=0;
		for(int i=0;i<largeur;i++)
			for(int j=0;j<longueur;j++)
				if(this.ensembleDeCase[i][j] instanceof Lampe)this.maxAllumee++;
	}
	
	public void setNbLampeAllumee(int i){
		this.nbLampeAllumee=i;
	}

/********************************************* METHODES D'INSTANCE *********************************************/
	
	public void affiche(){
		for(int y=0;y<longueur;y++){
			for(int x=0;x<largeur;x++)
				if(this.ensembleDeCase[x][y]==null){
					System.out.print("null    "+" "); // 8 caractères plus 1 espace
				}else if (this.ensembleDeCase[x][y].getHauteur() == 0){
					System.out.print("........"+" ");
				}else{
					String couleur = this.ensembleDeCase[x][y].getColor().toString();
					int hauteur = this.ensembleDeCase[x][y].getHauteur();
					
					for(int k=couleur.length(); k<8; k++){
						couleur += " ";
					}
					if (this.ensembleDeCase[x][y] instanceof Normal) {
						System.out.print(((Normal) this.ensembleDeCase[x][y]).getFondation() + " " );	//Affiche le nombre de blocs posés sur une case.
						
					}
					System.out.print(hauteur + couleur + " ");										//Affiche la hauteur et la couleur d'une case.
				}
				//if(this.ensembleDeCase[x][y]!=null) System.out.print("("+x+","+y+")"+this.ensembleDeCase[x][y].getColor()+" ");
			System.out.println();
		}
	}
	
	public void printTerm(){
		System.out.print("\n");
		String ligne = "";
		for(int i=0; i<(this.largeur*2)+1 ; i++){
			ligne = ligne+"–";
		}
		System.out.println(ligne);
		for(int i=0 ; i<longueur ; i++){
			for(int j=0 ; j<largeur ; j++){
				System.out.print("|");
				ensembleDeCase[j][i].printTerm();
			}
			System.out.print("|\n");
		}
		System.out.println(ligne);
	}
	
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
		initCase();
	}
	
	private void initCase(){
		for(int i=0 ; i<largeur ; i++){
			for(int j=0; j<longueur ; j++){
				this.ensembleDeCase[i][j] = new Normal(0);
			}
		}
	}

}


