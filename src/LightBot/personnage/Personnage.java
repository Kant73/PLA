package LightBot.personnage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

import LightBot.exceptions.BreakException;
import LightBot.exceptions.CloneException;
import LightBot.Niveau;
import LightBot.Programme;
import LightBot.Terrain;
import LightBot.actions.Actions;
import LightBot.cases.Case;
import LightBot.cases.Couleur;


public class Personnage implements Cloneable {
	
	private int[] positionInitial=new int[2];
	private int currentX;			//Position sur X du personnage.
	private int currentY;			//Position sur Y du personnage.
	private Pcardinaux orientation; 	//Orientation du personnage.
	private Programme prog;			//Programme associé au personnage.
	private Terrain terrain;
	private Couleur couleur;
	private String nom;
	private boolean mort=false;
	private LinkedList<ListIterator<Object>> fifo;
	private ListIterator<Object> itActions;
	
	
	public Personnage(String nom, int x, int y, Pcardinaux sens){
		this.nom=nom;
		this.positionInitial[0]=x;
		this.positionInitial[1]=y;
		this.orientation=sens;
		this.couleur=Couleur.Blanc;
		this.fifo=new LinkedList<ListIterator<Object>>();
		setCurrentToOriginPosition();
	}
	
	public Object execute() throws ArrayIndexOutOfBoundsException,BreakException, CloneException{
		Object commande = null;
		try{
			if(itActions.hasNext() ){
				commande=itActions.next();
				if(commande instanceof Actions){
					int nbLampeAllumee=this.getTerrain().getNbLampeAllumee();
					if( nbLampeAllumee >= this.getTerrain().getMaxLampe() || this.isMort() ||
						this.getTerrain().getNbActionsRestantes() <= 0 ){
							this.prog.reset();
							throw new ArrayIndexOutOfBoundsException();
						}
					else{
						System.out.println(((Actions)commande).toString());
						this.getTerrain().setNbActionsrestantes(this.getTerrain().getNbActionsRestantes()-1);
						((Actions)commande).agir();												
					}
				}else if(commande instanceof Programme){
					if(((Programme)commande).isMatchCouleur(this.couleur))
						this.setNewIterator(((Programme)commande).getIterator());
				}
			}else{
				this.restaureIterator();
			}
		}catch(StackOverflowError e){
			e.printStackTrace();
		}catch(NoClassDefFoundError noDef){
			noDef.printStackTrace();
		}catch(NullPointerException nE){
			nE.printStackTrace();
			return null;
		}catch(BreakException bE){
			this.restaureIterator();
	    	throw new BreakException();
		}
		return commande;
	}
	
	private void setNewIterator(ListIterator<Object> iterator){
		this.fifo.add(this.itActions);
		this.itActions=iterator;
	}
	
	private void restaureIterator(){
		if(!this.fifo.isEmpty())
			this.itActions=this.fifo.removeLast();
	}
	
	public boolean isListFifoEmpty(){		
		return this.fifo.size()==0 && !((Iterator<Object>) this.itActions).hasNext();
	}
	
	public Personnage(String nom, int x, int y, Pcardinaux sens, Couleur color){
		this(nom, x, y, sens);
		this.setCouleur(color);
	}
	
	public String getNom(){
		return this.nom;
	}
	
	public void setNom(String nom){
		this.nom=nom;
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
		if(Couleur.Rose == c || Couleur.Violet == c || Couleur.Blanc == c){
			this.couleur = c;
		}
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
		this.itActions=this.prog.getIterator();
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
	
	@Override
	public Personnage clone() throws CloneNotSupportedException{
		Personnage copie=(Personnage)super.clone();
		copie.prog=this.prog.clone();
		copie.fifo=new LinkedList<ListIterator<Object>>();
		for(int i=0;i<this.fifo.toArray().length;i++)
			copie.fifo.add((ListIterator<Object>) this.fifo.toArray()[i]);
		
		ArrayList<Object> tmp=new ArrayList<Object>();
		int nextIndexIt=this.itActions.nextIndex();
		while(this.itActions.hasPrevious())this.itActions.previous(); //Remise a zero
		
		while(this.itActions.hasNext())tmp.add(this.itActions.next()); 
		
		copie.itActions=tmp.listIterator(nextIndexIt);
		
		return copie;
	}

}

