package LightBot.exceptions;

import java.util.ArrayList;

import LightBot.personnage.Personnage;

public class BreakException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public BreakException(){}
	
	public BreakException(String message){
		super(message);
	}
}
