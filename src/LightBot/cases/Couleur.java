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
	
	public Couleur fromString(String c){
		switch(c){
			case "Rose" : 
				return Rose;
			case "Bleu" : 
				return Bleu;
			case "Jaune" : 
				return Jaune;
			case "Blanc" : 
				return Blanc;
			case "Vert" : 
				return Vert;
			case "Orange" : 
				return Orange;
			case "Noir" : 
				return Noir;
			case "Violet" : 
				return Violet;
			case "Incolore" : 
				return Incolore;
			case "Rouge" : 
				return Rouge;
			default : 
				return Blanc;
		}
	}
}