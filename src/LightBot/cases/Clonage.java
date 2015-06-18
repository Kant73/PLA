package LightBot.cases;

import java.util.ArrayList;

import LightBot.personnage.Pcardinaux;

public class Clonage extends Allumable {
	
	private ArrayList<Clonage> pops;
	private Pcardinaux o;

	public Clonage(int pHauteur) {
		super(Couleur.Orange, pHauteur);
		pops = new ArrayList<Clonage>();
		this.o = Pcardinaux.NORTH;
	}
	
	public Pcardinaux getOrientation(){
		return this.o;
	}
	
	public void setOrientation(Pcardinaux orientation){
		this.o = orientation;
	}
	
	public void setPops(ArrayList<Clonage> list){
		list.remove(this);
		this.pops = list;
	}
	
	public ArrayList<Clonage> getPops(){
		return this.pops;
	}

}
