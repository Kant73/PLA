package LightBot.exceptions;

import java.util.ArrayList;

import LightBot.actions.Actions;
import LightBot.personnage.Personnage;

public class CloneException extends Exception {
	
	private ArrayList<Personnage> listPers;
	private Actions commandeEnCours;
	private static final long serialVersionUID = 1L;

	public CloneException(){}
	
	public CloneException(String message){
		super(message);
	}

	public CloneException(ArrayList<Personnage> listPersonnage, Actions action){
		super();
		this.listPers=listPersonnage;
		this.commandeEnCours=action;
	}
	
	public ArrayList<Personnage> getListPesonnage(){
		return this.listPers;
	}
	
	public Actions getCommandeEnCours(){
		return this.commandeEnCours;
	}
}
