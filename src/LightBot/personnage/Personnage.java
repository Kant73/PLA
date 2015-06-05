package LightBot.personnage;

import LightBot.Programme;
import LightBot.Terrain;


public class Personnage {
	
	private int[] positionInitial=new int[2];
	private int currentX;			//Position sur X du personnage.
	private int currentY;			//Position Y sur Y du personnage.
	private Pcardinaux orientation; 	//Orientation du personnage.
	private Programme prog;			//Programme associé au personnage.
	private Terrain terrain;		
	
	Personnage(int x, int y, Pcardinaux sens){
		this.positionInitial[0]=x;
		this.positionInitial[1]=y;
		this.orientation=sens;
		setCurrentToOriginPostion();
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
	
	public Programme getProgramme() {
		return this.prog;
	}
	
	public Terrain getTerrain() {
		return this.terrain;
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
	
	public void setCurrentToOriginPostion(){			//Initialiser la position initiale du personnage.
		this.currentX = positionInitial[0];
		this.currentY = positionInitial[1];
	}
	
	public void printTerm(){}

}

