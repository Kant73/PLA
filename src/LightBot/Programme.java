package LightBot;

import java.util.Vector;

import LightBot.actions.Actions;
import LightBot.actions.Break;
import LightBot.cases.Couleur;

public class Programme {

	private int nbMaxAction;
	private Vector<Object> listActions;
	private String nom;
	private int currentIndex=0;
	private Couleur couleurCondition;
	
	public Programme(String nom, int taille){
		this.nbMaxAction= taille;	
		this.nom=nom;
		this.listActions=new Vector<Object>();
		this.listActions.setSize(this.nbMaxAction);
		this.couleurCondition=Couleur.Blanc;
	}
	
	public Programme(String nom, int taille, Couleur couleur){
		this(nom, taille);
		this.couleurCondition=couleur;
	}
	
	public Boolean isMatchCouleur(){
		boolean colorOK=false;
		/* Marche bien pour n'avoir que 2 couleurs de conditions */
		if(this.listActions!=null && this.listActions.size()>=1){
			colorOK = this.couleurCondition == ((Actions)this.listActions.get(0)).getPersonnage().getCouleur(); 
			colorOK |= (((Actions)this.listActions.get(0)).getPersonnage().getCouleur() == Couleur.Violet 	&& this.couleurCondition != Couleur.Rose);
			colorOK |= (((Actions)this.listActions.get(0)).getPersonnage().getCouleur() == Couleur.Rose 	&& this.couleurCondition != Couleur.Violet);
		}
		return colorOK;
	}
	
	public int getNbMaxAction(){
		return nbMaxAction;
	}
	
	public int getNbElements(){
		return this.currentIndex;
	}
	
	public Vector<Object> getActions(){
		return this.listActions;
	}
	
	public void supprimer(int index){
		if (index>=0 && index <this.nbMaxAction){
			this.listActions.set(index, null);
			this.currentIndex--;
		}
	}
	
	public void inserer(Object obj, int index){
		if (index>=0 && index <this.nbMaxAction)
			if (obj instanceof Programme){
				this.listActions.set(index, (Programme)obj);
				this.currentIndex++;
			}
			else if (obj instanceof Actions){
				this.listActions.set(index, (Actions)obj);
				this.currentIndex++;
			}
			
	}
	
	public void insererQueue(Object obj){
		if(this.currentIndex<this.nbMaxAction)
			if (obj instanceof Programme){
				this.listActions.set(this.currentIndex,(Programme)obj);
				this.currentIndex++;
			}
			else if (obj instanceof Actions){
				this.listActions.set(this.currentIndex ,(Actions)obj);
				this.currentIndex++;
			}
	}
	
	public void execute(){
		try{
			for(Object obj:this.listActions){
			    if(obj instanceof Break && ((Break)obj).getCouleur()==((Break)obj).getPersonnage().getCouleur())return;
				else if(obj instanceof Actions){
					int nbLampeAllumee=((Actions)obj).getPersonnage().getTerrain().getNbLampeAllumee();
					if(nbLampeAllumee >= ((Actions)obj).getPersonnage().getTerrain().getMaxLampe() || ((Actions)obj).getPersonnage().isMort())return;
					else((Actions)obj).agir();
				}
				else if(obj instanceof Programme)((Programme)obj).execute();			
			}
		}catch(StackOverflowError e){
			e.printStackTrace();
		}catch(NoClassDefFoundError noDef){
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public String toString(){
		String str="";
		str+=this.nom+" : ";
		for(Object obj:this.listActions){
			if(obj instanceof Actions) str+=((Actions)obj).toString()+" ";
			else if(obj instanceof Programme)str+=((Programme)obj).getNom()+" ";			
		}		
		return str;
	}
	
	public String getNom(){
		return this.nom;
	}
	
	public void reset(){
		for(int i=0;i<this.nbMaxAction;i++){
			if(this.listActions.get(i) instanceof Actions){
				this.supprimer(i);
			}
			else if(this.listActions.get(i) instanceof Programme){
				((Programme)this.listActions.get(i)).reset();	
				this.supprimer(i);
			}
		}
	}
	
}
