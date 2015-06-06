package LightBot.cases;

public class Lampe extends Allumable {

	public Lampe(int hauteur,boolean allume){
		super(hauteur,Couleur.Bleu,allume);
		if (allume)setColor(Couleur.Jaune);
	}
}
