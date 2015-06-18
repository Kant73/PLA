package LightBot.actions;

import LightBot.cases.Case;
import LightBot.cases.Couleur;
import LightBot.cases.Pointeur;
import LightBot.cases.Transparente;
import LightBot.personnage.Personnage;

public class CompareFront extends Actions {

	public CompareFront(Personnage p) {
		super(p);
	}
	
	public CompareFront(Personnage p, Couleur c){
		super(p,c);
	}
	
	@Override
	public CompareFront clone() throws CloneNotSupportedException{
		CompareFront copie=(CompareFront)super.clone();
		return copie;
	}
	
	public void agir(){
		if(super.matchColor()){
			Case caseFront = this.perso.getCaseFrontHim();
			Case caseCurrent = this.perso.getCaseCurrent();
			if (caseFront != null){ // On ne doit pas chercher à comparer une case qui n'est pas dans le terrain
				if(caseFront instanceof Pointeur){ // Si on arrive à la case de retour au début on change de couleur pour pouvoir faire les actions de retour
					this.perso.setCouleur(Couleur.Violet);
				}else if(caseFront instanceof Transparente && caseCurrent instanceof Transparente){
					if(caseCurrent.getHauteur() > caseFront.getHauteur()){
						this.perso.setCouleur(Couleur.Rose);
					}
				}
			}
		}
	}

}
