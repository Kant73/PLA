package LightBot;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import LightBot.personnage.Personnage;
import LightBot.actions.*;

public class Ordonnanceur {

	private ArrayList<Programme> progs;
	private Vector<Iterator<Object>> listItActions;
	
	
	Ordonnanceur(ArrayList<Personnage> persos){
		this.progs=new ArrayList<Programme>();
		this.listItActions=new Vector<Iterator<Object>>();
		for(int i=0;i<persos.toArray().length;i++){
			this.progs.add(persos.get(i).getProgramme());
			this.listItActions.add(persos.get(i).getProgramme().getActions().iterator());
		}
	}
	
	public void run(){
		try{
			for(Iterator<Object> it:this.listItActions){	
				if(it.hasNext()){
					this.execute(it);
				}
			}
			this.run();
		}catch(NullPointerException e){
			e.printStackTrace();
			return;		
		}catch(StackOverflowError e){
			e.printStackTrace();
		}catch(NoClassDefFoundError noDef){}
	}
	
	private void execute(Iterator<Object> itActions){
		try{			
			if(itActions.hasNext()){
				Object obj=itActions.next();
				
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
		}catch(NullPointerException nE){
			return;
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	
}
