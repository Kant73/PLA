package LightBot;

import java.util.ArrayList;

import LightBot.personnage.Personnage;


public class Niveau {
	
	private ArrayList<Personnage> personnages;
	private Terrain terrain;
	private ArrayList<Programme> programmes;
	
	public Niveau(){
		this.personnages=new ArrayList<Personnage>();
		this.programmes=new ArrayList<Programme>();
	}
	

	public ArrayList<Personnage> getPersonnages() {
		return this.personnages;
	}

	public void setPersonnages(ArrayList<Personnage> personnages) {
		this.personnages = personnages;
	}

	public Terrain getTerrain() {
		return this.terrain;
	}

	public void setTerrain(Terrain terrain) {
		this.terrain = terrain;
	}

	public ArrayList<Programme> getProgrammes() {
		return this.programmes;
	}

	public void setProgrammes(ArrayList<Programme> programmes) {
		this.programmes = programmes;
	}
	
}
