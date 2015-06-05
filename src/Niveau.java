import java.util.ArrayList;

import personnage.Personnage;


public class Niveau {
	
	private ArrayList<Personnage> Personnages;
	private Terrain Terrain;
	private ArrayList<Programme> Programmes;
	

	public ArrayList<Personnage> getPersonnages() {
		return Personnages;
	}

	public void setPersonnages(ArrayList<Personnage> personnages) {
		Personnages = personnages;
	}

	public Terrain getTerrain() {
		return Terrain;
	}

	public void setTerrain(Terrain terrain) {
		Terrain = terrain;
	}

	public ArrayList<Programme> getProgrammes() {
		return Programmes;
	}

	public void setProgrammes(ArrayList<Programme> programmes) {
		Programmes = programmes;
	}
	
}
