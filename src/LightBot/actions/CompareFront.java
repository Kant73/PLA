package LightBot.actions;

import LightBot.cases.Case;
import LightBot.cases.Couleur;
import LightBot.cases.Pointeur;
import LightBot.cases.Transparente;
import LightBot.personnage.Personnage;

public class CompareFront extends Actions {

	public CompareFront() {
		super();
	}
	
	public CompareFront(Couleur c){
		super(c);
	}
	
	@Override
	public CompareFront clone() throws CloneNotSupportedException{
		CompareFront copie=(CompareFront)super.clone();
		return copie;
	}
	
	public void agir(Personnage perso){
		if(super.matchColor(perso)){
			Case caseFront = perso.getCaseFrontHim();
			Case caseCurrent = perso.getCaseCurrent();
			if (caseFront != null){ // On ne doit pas chercher à comparer une case qui n'est pas dans le terrain
				if(caseFront instanceof Pointeur){ // Si on arrive à la case de retour au début on change de couleur pour pouvoir faire les actions de retour
					perso.setCouleur(Couleur.Violet);
				}else if(caseFront instanceof Transparente && caseCurrent instanceof Transparente){
					if(caseCurrent.getHauteur() > caseFront.getHauteur()){
						perso.setCouleur(Couleur.Rose);
					}
				}
			}
		}
	}

}
