package LightBot.actions;

import LightBot.exceptions.BreakException;
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


	public void agir() throws BreakException{
		if(matchColor())
			throw new BreakException();
	}
}
