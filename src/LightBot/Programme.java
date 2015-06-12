package LightBot;

import java.util.Vector;

import LightBot.actions.Actions;
import LightBot.actions.Break;

public class Programme {

	private int nbMaxAction;
	private Vector<Object> listActions;
	private String nom;
	private int currentIndex=0;
	
	public Programme(String nom, int taille){
		this.nbMaxAction= taille;	
		this.nom=nom;
		this.listActions=new Vector<Object>();
		this.listActions.setSize(this.nbMaxAction);
	}
	
	public int getNbMaxAction(){
		return nbMaxAction;
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
				this.listActions.add((Programme)obj);
				this.currentIndex++;
			}
			else if (obj instanceof Actions){
				this.listActions.add((Actions)obj);
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
	
}
