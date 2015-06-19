package LightBot.cases;

public class LampeBattle extends Lampe{

	public LampeBattle(int pHauteur) {
		super(pHauteur);
	}

	@Override
	public void setColor(Couleur c){
		this.color=c;
	}
}
