package LightBot.cases;

public abstract class Allumable extends Case{
	private boolean allume;
	
	Allumable(int h, Couleur c, boolean allume){
		super(h,c);
		this.allume=allume;
	}
	
	public void setAllume(boolean allume){
		this.allume=allume;
	}
	
	public boolean isAllume(){
		return this.allume;
	}
}
