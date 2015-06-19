package LightBot.exceptions;

public class CloneException extends Exception {
	private static final long serialVersionUID = 1L;

	public CloneException(){}
	
	public CloneException(String message){
		super(message);
	}
}
