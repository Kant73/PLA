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

	public Allumer(Personnage p) {
		super(p);
	}
	
	public Allumer(Personnage p, Couleur c){
		super(p,c);
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
	public void agir() throws CloneException{
		if(super.matchColor()){
			Case C = this.perso.getTerrain().getEnsembleDeCase()[this.perso.getPositionX()][this.perso.getPositionY()];
			int nbLampeAllumee=this.perso.getTerrain().getNbLampeAllumee();
			if (C instanceof Lampe){
				switch(C.getColor()){
				case Bleu:
					C.setColor(Couleur.Jaune);
					this.perso.getTerrain().setNbLampeAllumee(nbLampeAllumee+1);
					break;
				case Jaune:
					C.setColor(Couleur.Bleu);
					this.perso.getTerrain().setNbLampeAllumee(nbLampeAllumee-1);
					break;
				default:break;
				}
			}else if (C instanceof PointeurTri){
				if(((PointeurTri)C).estDepart()){
					if(tableauTrie(C)){
						Lampe arrivee = ((PointeurTri) C).getArrivee();
						this.perso.setPosition(this.perso.getTerrain().getPosCaseX(arrivee), this.perso.getTerrain().getPosCaseY(arrivee));
						victoire();
					}else{
						Case suivante = ((Pointeur) C).getSuivante();
						if(suivante != null){
							this.perso.setPosition(this.perso.getTerrain().getPosCaseX(suivante), this.perso.getTerrain().getPosCaseY(suivante));
						}
					}
				}
			}else if (C instanceof Pointeur){
				Case temp;
				temp = ((Pointeur) C).getSuivante();
				if (temp != null)
					this.perso.setPosition(this.perso.getTerrain().getPosCaseX(temp), this.perso.getTerrain().getPosCaseY(temp));
			}
			else if(C instanceof Condition){
				if(this.perso.getCouleur()!=Couleur.Blanc)this.perso.setCouleur(Couleur.Blanc);
				else this.perso.setCouleur(C.getColor());
			}else if(C instanceof Clonage){
				System.out.println("Eh hop : un clone :)");
				System.out.println("Enfin plutôt : "+((Clonage)C).getPops().size());
				popClone((Clonage)C);
			}
		}
	}
	
	/* Convention : le terrain d'un tri doit avoir le tableau à trier en y = 2 et la lampe en (0,0) */
	private boolean tableauTrie(Case pointeur){
		boolean estTrie = true;
		int xInit = 1;
		int xFin = this.perso.getTerrain().getPosCaseX(pointeur);
		while(estTrie && (xInit < xFin-1)){
			Case courante = this.perso.getTerrain().getEnsembleDeCase()[xInit][2];
			Case prochaine = this.perso.getTerrain().getEnsembleDeCase()[xInit+1][2];
			estTrie &= courante.getHauteur()+1 == prochaine.getHauteur();
			xInit++;
		}
		return estTrie;
	}

	private void victoire() throws CloneException{
		for(int x = 0; x<this.perso.getTerrain().getLargeur(); x++){
			Case courante = this.perso.getTerrain().getEnsembleDeCase()[x][2];
			if(courante instanceof Transparente){
				this.perso.getTerrain().getEnsembleDeCase()[x][2] = ((Transparente)courante).toNormal();
			}
		}
		new Allumer(this.perso).agir();
	}
	
	private void popClone(Clonage cellule) throws CloneException{
		ArrayList<Clonage> pops = cellule.getPops();
		ArrayList<Personnage> clones=new ArrayList<Personnage>();
		clones.add(this.perso);
		for(int i=0 ; i<pops.size() ; i++){
			Clonage courante = pops.get(i);
			int x = this.perso.getTerrain().getPosCaseX(courante);
			int y = this.perso.getTerrain().getPosCaseY(courante);
			Personnage clone=null;
			try {
				clone = (Personnage) this.perso.clone();
				clone.setNom("Clone "+(i+1));
				clone.setPosition(x,y);
				clone.setOrientation(courante.getOrientation());
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
			
			clones.add(clone);
		}
		throw new CloneException(clones);
	}
}
