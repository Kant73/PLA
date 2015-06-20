package LightBot;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Vector;

import LightBot.actions.Actions;
import LightBot.actions.Break;
import LightBot.cases.Couleur;
import LightBot.personnage.Personnage;

public class Programme implements Cloneable{

	private int nbMaxAction;
	private ArrayList<Object> listActions;
	private String nom;
	private Couleur couleurCondition;
	private ListIterator<Object> itActions;
	
	public Programme(String nom, int taille){
		this.nbMaxAction= taille;	
		this.nom=nom;
		this.listActions=new ArrayList<Object>();
		this.couleurCondition=Couleur.Blanc;
		this.itActions=this.listActions.listIterator();
	}
	
	public Programme(String nom, int taille, Couleur couleur){
		this(nom, taille);
		this.couleurCondition=couleur;
	}
	
	public ListIterator<Object> getIterator(){
		this.itActions=this.listActions.listIterator();
		return this.itActions;
	}
	
	public boolean isMatchCouleur(Couleur c){
		boolean colorOK=false;
		/* Marche bien pour n'avoir que 2 couleurs de conditions */
		if(this.listActions!=null){
			colorOK = this.couleurCondition == c; 
			colorOK |= (c == Couleur.Violet && this.couleurCondition != Couleur.Rose);
			colorOK |= (c == Couleur.Rose && this.couleurCondition != Couleur.Violet);
		}
		return colorOK;
	}
	
	public int getNbMaxAction(){
		return nbMaxAction;
	}
	
	public int getNbElements(){
		return this.listActions.size();
	}
	
	public ArrayList<Object> getActions(){
		return this.listActions;
	}
	
	public void supprimer(int index){
		if (index>=0 && index <this.listActions.size()){
			this.listActions.remove(index);
			this.itActions=this.listActions.listIterator();
		}
	}
	
	@Override
	public Programme clone() throws CloneNotSupportedException{
		return cloneRecursif(2);
	}
		
	private Programme cloneRecursif(int n) throws CloneNotSupportedException{
		if(n>=0){			
			Programme prg=(Programme)super.clone();
			prg.couleurCondition=this.couleurCondition;
			prg.listActions=new ArrayList<Object>();
			for(Object obj:this.listActions){
				if(obj instanceof Actions)prg.listActions.add(((Actions) obj).clone());
				else if(obj instanceof Programme){				
					if(((Programme)obj).getNom().equals(prg.getNom()))
						prg.listActions.add(prg);
					else{
						Programme p=((Programme)obj).cloneRecursif(n-1);
						if(p!=null)prg.listActions.add(p);
					}						
				}
			}
			prg.itActions=prg.listActions.listIterator();
			return prg;
		}
		return null;
	}
	
	public void vider(){
		for(int i=this.listActions.size()-1;i>=0;i--)
			this.listActions.remove(i);
		this.itActions=this.listActions.listIterator();
	}
	
	public void insererQueue(Object obj){
		if(this.listActions.size()<this.nbMaxAction){
			if (obj instanceof Programme)
				this.listActions.add((Programme)obj);
			else if (obj instanceof Actions)
				this.listActions.add((Actions)obj);
			this.itActions=this.listActions.listIterator();
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
	
	public void changePersonnage(Personnage pers){
		changePersonnageRecursif(pers,2);
	}
		
	private void changePersonnageRecursif(Personnage pers,int n){
		if(n>=0){
			for(Object obj:this.listActions){
				if(obj instanceof Actions) ((Actions)obj).setPersonnage(pers);
				else if(obj instanceof Programme){				
					if(!((Programme)obj).getNom().equals(this.nom))
						((Programme)obj).changePersonnageRecursif(pers, n-1);					
				}
			}
		}
	}
	
	public String toString(){
		return toStringRecursive(2);
	}
	
	private String toStringRecursive(int n){
		if(n>=0){
			String str="";
			str+=this.nom+" : ";
			for(Object obj:this.listActions){
				if(obj instanceof Actions) str+=((Actions)obj).toString()+" ";
				else if(obj instanceof Programme)
					if(this.nom.equals(((Programme)obj).getNom()))str+=((Programme)obj).getNom()+" ";	
					else str+=((Programme)obj).toStringRecursive(n-1);
			}		
			return str;
		}
		return "";
	}
	
	public String getNom(){
		return this.nom;
	}
	
	public void reset(){
		if(this.listActions.get(0)!=null)
			for(int i=0;i<this.listActions.size();i++){
				if(this.listActions.get(i) instanceof Actions){
					this.supprimer(i);
				}
				else if(this.listActions.get(i) instanceof Programme){
					((Programme)this.listActions.get(i)).reset();	
					this.supprimer(i);
				}
			}
	}
	
	public void setCouleur(Couleur c){
		if(c==Couleur.Rose || c==Couleur.Violet || c==Couleur.Blanc)
			this.couleurCondition=c;
	}
	
}
