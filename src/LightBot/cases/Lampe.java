package LightBot.cases;

public class Lampe extends Allumable {

/********************************************* METHODES D'INSTANCE *********************************************/
	
	//Constructeur de l'objet Lampe
	public Lampe(int pHauteur){
		super(Couleur.Bleu,pHauteur);	
	}
	
	@Override
	public void setColor(Couleur c){
		if(c==Couleur.Bleu || c==Couleur.Jaune)this.color=c;
		else System.out.println("(Lampe) Mauvaise couleur");
	}
}
