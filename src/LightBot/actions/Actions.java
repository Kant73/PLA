package LightBot.actions;

import LightBot.Terrain;
import LightBot.personnage.Personnage;


public interface Actions {
	
	Actions Agir(Personnage P, Terrain T);
	
}
