package LightBot.cases;

import java.util.ArrayList;

public class Clonage extends Allumable {
	
	private ArrayList<Clonage> pops;

	public Clonage(int pHauteur) {
		super(Couleur.Orange, pHauteur);
		pops = new ArrayList<Clonage>();
	}
	
	public void setPops(ArrayList<Clonage> list){
		list.remove(this);
		this.pops = list;
	}
	
	public ArrayList<Clonage> getPops(){
		return this.pops;
	}

}
