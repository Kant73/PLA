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
	
	@Override
	public Break clone() throws CloneNotSupportedException{
		Break copie=(Break)super.clone();
		return copie;
	}

	// pas de Agir car actions qui fais juste remonter dans l'appelante
}
