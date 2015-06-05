package LightBot.personnage;

import LightBot.Programme;
import LightBot.Terrain;


public class Personnage {
	
	private int currentX;			//Position sur X du personnage.
	private int currentY;			//Position Y sur Y du personnage.
	private Pcardinaux orientation; 	//Orientation du personnage.
	private Programme prog;			//Programme associÃ© au personnage.
	private Terrain terrain;		
	
	/************************************** ACCESSEURS ****************************************/
	
	public int getPositionX() {
		return currentX;
	}
	
	public int getPositionY() {
		return currentY;
	}
	
	public Pcardinaux getOrientation () {
		return orientation;
	}
	
	public Programme getProgramme() {
		return prog;
	}
	
	public Terrain getTerrain() {
		return terrain;
	}
	
	/************************************* MUTATEURS ******************************************/
	
	
	public void setPositionX(int pPositionX) {
		currentX = pPositionX;
	}
	
	public void setPositionY(int pPositionY) {
		currentY = pPositionY;
	}
	
	public void setOrientation( Pcardinaux pOrientation) {
		orientation = pOrientation;
	}
	
	public void setProg(Programme pProg) {
		prog = pProg;
	}
	
	public void setTerrain(Terrain pTerrain) {
		terrain = pTerrain;
	}
	
	Personnage(int x, int y){
		initPosition(x,y);
	}
	
	public void initPosition(int x, int y){			//Initialiser la position initiale du personnage.
		this.currentX = 0;
		this.currentY = 0;
	}
	
	public void printTerm(){}

}

