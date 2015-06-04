
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
	
	Personnage(int x, int y){
		init(x,y);
	}
	
	public void init(int x, int y){}
	
	public void printTerm(){}
	
	public void execute(Programme p, Terrain t){}
}

