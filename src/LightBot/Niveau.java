package LightBot;

import java.util.ArrayList;

import LightBot.actions.Actions;
import LightBot.personnage.Personnage;


public class Niveau {
	
	private ArrayList<Personnage> personnages;
	private Terrain terrain;
	private ArrayList<Programme> programmes;
	private ArrayList<Actions> actions;
	
	public Niveau(){
		this.personnages=new ArrayList<Personnage>();
		this.programmes=new ArrayList<Programme>();
		this.actions=new ArrayList<Actions>();
	}
	
	public ArrayList<Actions> getActions(){
		return this.actions;
	}
	
	public ArrayList<Personnage> getPersonnages() {
		return this.personnages;
	}

	public void setPersonnages(ArrayList<Personnage> personnages) {
		this.personnages = personnages;
	}
	
	public void setAction(Actions a){
		this.actions.add(a);
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
