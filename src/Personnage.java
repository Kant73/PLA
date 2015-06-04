
public abstract class Personnage {

	private enum  Pcardinaux{
		NORTH,
		SOUTH,
		EAST,
		WEST;
	};
	
	private int currentX;
	private int currentY;
	private Pcardinaux orientation; 
	private Programme prog;	
	
}

