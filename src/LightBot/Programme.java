package LightBot;

import java.util.Vector;

import LightBot.actions.Actions;

public class Programme {

	private int nbMaxAction;
	private Vector<Object> listActions;
	private String nom;
	
	public Programme(String nom, int taille){
		this.nbMaxAction= taille;	
		this.nom=nom;
		this.listActions=new Vector<Object>();
		this.listActions.setSize(this.nbMaxAction);
	}
	
	public void supprimer(int index){
		if (index>=0 && index <this.nbMaxAction)this.listActions.set(index, null);
	}
	
	public void inserer(Object obj, int index){
		if (index>=0 && index <this.nbMaxAction)
			if (obj instanceof Programme)this.listActions.set(index, (Programme)obj);
			else if (obj instanceof Actions)this.listActions.set(index, (Actions)obj);		
	}
	
	public void execute(){
		try{
			for(Object obj:this.listActions){
				if(obj instanceof Actions)((Actions)obj).agir();
				else if(obj instanceof Programme)((Programme)obj).execute();			
			}
		}catch(StackOverflowError e){
			e.printStackTrace();
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
