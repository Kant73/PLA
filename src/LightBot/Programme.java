package LightBot;

import java.util.Vector;

import LightBot.actions.Actions;

public class Programme {

	private int nbMaxAction;
	private Vector<Object> listActions;
	
	Programme(int taille){
		this.nbMaxAction= taille;	
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
		for(Object obj:this.listActions){
			if(obj instanceof Actions) ((Actions)obj).agir();
			else if(obj instanceof Programme)((Programme)obj).execute();
		}
	}
	
}
