package LightBot.exceptions;

import java.util.ArrayList;

import LightBot.personnage.Personnage;

public class CloneException extends Exception {
	
	private ArrayList<Personnage> listPers;
	private static final long serialVersionUID = 1L;

	public CloneException(){}
	
	public CloneException(String message){
		super(message);
	}

	public CloneException(ArrayList<Personnage> listPersonnage){
		super();
		this.listPers=listPersonnage;
	}
	
	public ArrayList<Personnage> getListPesonnage(){
		return this.listPers;
	}
}
