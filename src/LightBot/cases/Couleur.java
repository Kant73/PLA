package LightBot.cases;

public enum Couleur{
	Rose("\033[1;35m"),		/* Condition */
	Bleu("\033[34m"), 		/* A allumer */
	Jaune("\033[33m"), 		/* Case allumee */
	Blanc("\033[0m"),		/* Normal */
	Vert("\033[32m"), 		/* Téléportation */
	Orange("\033[36m"), 	/* Clonage */
	Noir("\033[30m"), 		/* Case qui disparait */
	Violet("\033[35m"), 	/* Case condition if */
	Incolore("\033[5m"),	/* Transparent */
	Rouge("\033[31m"); 		/* Case memoire */
	
	private String codeCouleur;
	
	Couleur(String s){
		this.codeCouleur = s;
	}
	
	public String getCode(){
		return this.codeCouleur;
	}
	
}