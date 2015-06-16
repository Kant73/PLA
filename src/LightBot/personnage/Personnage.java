package LightBot.personnage;

import LightBot.Programme;
import LightBot.Terrain;
import LightBot.cases.Case;
import LightBot.cases.Couleur;


public class Personnage {
	
	private int[] positionInitial=new int[2];
	private int currentX;			//Position sur X du personnage.
	private int currentY;			//Position sur Y du personnage.
	private Pcardinaux orientation; 	//Orientation du personnage.
	private Programme prog;			//Programme associé au personnage.
	private Terrain terrain;
	private Couleur couleur;
	private String nom;
	private boolean mort=false;
	
	public Personnage(String nom, int x, int y, Pcardinaux sens){
		this.nom=nom;
		this.positionInitial[0]=x;
		this.positionInitial[1]=y;
		this.orientation=sens;
		this.couleur=Couleur.Blanc;
		setCurrentToOriginPosition();
	}
	
	public String getNom(){
		return this.nom;
	}
	
	public Couleur getCouleur(){
		return this.couleur;
	}
	
	public int getPositionX() {
		return this.currentX;
	}
	
	public int getPositionY() {
		return this.currentY;
	}
	
	public Pcardinaux getOrientation () {
		return this.orientation;
	}
	
	public int getOrientationInt () {
		return this.orientation.getCode();
	}
	
	public Programme getProgramme() {
		return this.prog;
	}
	
	public Case getCaseCurrent() {
		return this.terrain.getEnsembleDeCase()[this.currentX][this.currentY];
	}
	
	public Case getCaseFrontHim(){
		Case caseDevant = null;
		try{
			switch(this.orientation){
				case EAST :
					caseDevant = this.terrain.getEnsembleDeCase()[this.currentX+1][this.currentY];
					break;
				case SOUTH :
					caseDevant = this.terrain.getEnsembleDeCase()[this.currentX][this.currentY+1];
					break;
				case WEST : 
					caseDevant = this.terrain.getEnsembleDeCase()[this.currentX-1][this.currentY];
					break;
				case NORTH :
					caseDevant = this.terrain.getEnsembleDeCase()[this.currentX][this.currentY-1];
					break;
				default:break;
			}
		}catch(IndexOutOfBoundsException e){
			return null;
		}
		return caseDevant;
	}
	
	public Terrain getTerrain() {
		return this.terrain;
	}	
	
	public void setCouleur(Couleur c){
		this.couleur=c;
	}
	
	public void setPositionX(int pPositionX) {
		this.currentX = pPositionX;
	}
	
	public void setPositionY(int pPositionY) {
		this.currentY = pPositionY;
	}
	
	public void setPosition(int x, int y){
		setPositionX(x);
		setPositionY(y);
	}
	
	public void setOrientation( Pcardinaux pOrientation) {
		this.orientation = pOrientation;
	}
	
	public void setProgramme(Programme pProg) {
		this.prog = pProg;
	}
	
	public void setTerrain(Terrain pTerrain) {
		this.terrain = pTerrain;
	}
	
	public void setCurrentToOriginPosition(){			//Initialiser la position initiale du personnage.
		this.currentX = positionInitial[0];
		this.currentY = positionInitial[1];
	}
	
	public void run(){
		this.prog.execute();
	}
	
	public void printTerm(){}

	public boolean isMort() {
		return mort;
	}

	public void setMort(boolean mort) {
		this.mort = mort;
	}

}

