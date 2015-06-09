package LightBot.actions;

import LightBot.cases.Couleur;
import LightBot.personnage.Personnage;

public class Break extends Actions{

	public Break(Personnage p) {
		super(p);
	}
	
	public Break(Personnage p,Couleur c) {
		super(p,c);
	}
}
