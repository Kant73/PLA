package LightBot.actions;

import LightBot.exceptions.BreakException;
import LightBot.cases.Couleur;
import LightBot.personnage.Personnage;

public class Break extends Actions{

	public Break() {
		super();
	}
	
	public Break(Couleur c) {
		super(c);
	}
	
	@Override
	public Break clone() throws CloneNotSupportedException{
		Break copie=(Break)super.clone();
		return copie;
	}


	public void agir(Personnage perso) throws BreakException{
		if(matchColor(perso))
			throw new BreakException();
	}
}
