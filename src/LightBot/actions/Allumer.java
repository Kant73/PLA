package LightBot.actions;

import java.util.ArrayList;

import LightBot.cases.Case;
import LightBot.cases.Clonage;
import LightBot.cases.Condition;
import LightBot.cases.Couleur;
import LightBot.cases.Lampe;
import LightBot.cases.Pointeur;
import LightBot.cases.PointeurTri;
import LightBot.cases.Transparente;
import LightBot.exceptions.CloneException;
import LightBot.personnage.Personnage;


public class Allumer extends Actions {

	public Allumer() {
		super();
	}
	
	public Allumer(Couleur c){
		super(c);
	}
	
	public String toString(){
		if(this.couleurCondition==Couleur.Violet || this.couleurCondition==Couleur.Rose){
			return "Allumer cond.";
		}else if(this.couleurCondition == Couleur.Vert){
			return "Allumer Pointeur";
		}else{
			return "Allumer";
		}
	}
	
	@Override
	public Allumer clone() throws CloneNotSupportedException{
		Allumer copie=(Allumer)super.clone();
		return copie;
	}

	@Override
	public void agir(Personnage perso) throws CloneException{
		if(super.matchColor(perso)){
			Case C = perso.getTerrain().getEnsembleDeCase()[perso.getPositionX()][perso.getPositionY()];
			int nbLampeAllumee=perso.getTerrain().getNbLampeAllumee();
			if (C instanceof Lampe){
				switch(C.getColor()){
				case Bleu:
					C.setColor(Couleur.Jaune);
					perso.getTerrain().setNbLampeAllumee(nbLampeAllumee+1);
					break;
				case Jaune:
					C.setColor(Couleur.Bleu);
					perso.getTerrain().setNbLampeAllumee(nbLampeAllumee-1);
					break;
				default:break;
				}
			}else if (C instanceof PointeurTri){
				if(((PointeurTri)C).estDepart()){
					if(tableauTrie(perso,C)){
						Lampe arrivee = ((PointeurTri) C).getArrivee();
						perso.setPosition(perso.getTerrain().getPosCaseX(arrivee), perso.getTerrain().getPosCaseY(arrivee));
						victoire(perso);
					}else{
						Case suivante = ((Pointeur) C).getSuivante();
						if(suivante != null){
							perso.setPosition(perso.getTerrain().getPosCaseX(suivante), perso.getTerrain().getPosCaseY(suivante));
						}
					}
				}
			}else if (C instanceof Pointeur){
				Case temp;
				temp = ((Pointeur) C).getSuivante();
				if (temp != null)
					perso.setPosition(perso.getTerrain().getPosCaseX(temp), perso.getTerrain().getPosCaseY(temp));
			}
			else if(C instanceof Condition){
				if(perso.getCouleur()!=Couleur.Blanc)perso.setCouleur(Couleur.Blanc);
				else perso.setCouleur(C.getColor());
			}else if(C instanceof Clonage){
				System.out.println("Eh hop : un clone :)");
				System.out.println("Enfin plutôt : "+((Clonage)C).getPops().size());
				popClone(perso,(Clonage)C);
			}
		}
	}
	
	/* Convention : le terrain d'un tri doit avoir le tableau à trier en y = 2 et la lampe en (0,0) */
	private boolean tableauTrie(Personnage perso, Case pointeur){
		boolean estTrie = true;
		int xInit = 1;
		int xFin = perso.getTerrain().getPosCaseX(pointeur);
		while(estTrie && (xInit < xFin-1)){
			Case courante = perso.getTerrain().getEnsembleDeCase()[xInit][2];
			Case prochaine = perso.getTerrain().getEnsembleDeCase()[xInit+1][2];
			estTrie &= courante.getHauteur()+1 == prochaine.getHauteur();
			xInit++;
		}
		return estTrie;
	}

	private void victoire(Personnage perso) {
		for(int x = 0; x<perso.getTerrain().getLargeur(); x++){
			Case courante = perso.getTerrain().getEnsembleDeCase()[x][2];
			if(courante instanceof Transparente){
				perso.getTerrain().getEnsembleDeCase()[x][2] = ((Transparente)courante).toNormal();
			}
		}
		try {
			new Allumer().agir(perso);
		} catch (CloneException e) {}
	}
	
	private void popClone(Personnage perso,Clonage cellule) throws CloneException{
		ArrayList<Clonage> pops = cellule.getPops();
		ArrayList<Personnage> clones=new ArrayList<Personnage>();
		clones.add(perso);
		for(int i=0 ; i<pops.size() ; i++){
			Clonage courante = pops.get(i);
			int x = perso.getTerrain().getPosCaseX(courante);
			int y = perso.getTerrain().getPosCaseY(courante);
			Personnage clone=null;
			try {
				clone = (Personnage) perso.clone();
				clone.setNom("Clone "+(i+1));
				clone.setPosition(x,y);
				clone.setOrientation(courante.getOrientation());

			} catch (CloneNotSupportedException e){}
			
			clones.add(clone);
		}
		throw new CloneException(clones,this);
	}
}
