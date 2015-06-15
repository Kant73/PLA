package LightBot;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

import Graphique.Afficher_niveau;
import LightBot.actions.Actions;
import LightBot.actions.Allumer;
import LightBot.actions.Avancer;
import LightBot.actions.Break;
import LightBot.actions.Sauter;
import LightBot.actions.TournerDroite;
import LightBot.actions.TournerGauche;
import LightBot.personnage.Personnage;

public class Ordonnanceur {

	private ArrayList<Programme> progs;
	private Vector<Iterator<Object>> listItActions;
	private ArrayList<LinkedList<Iterator<Object>>> listFifo;
	private Personnage pers;
	private Afficher_niveau affichage;
	
	public Ordonnanceur(ArrayList<Personnage> persos, Afficher_niveau affichage){
		this.progs=new ArrayList<Programme>();
		this.listItActions=new Vector<Iterator<Object>>();
		this.listFifo=new ArrayList<LinkedList<Iterator<Object>>>();
		this.affichage=affichage;
		for(int i=0;i<persos.toArray().length;i++){
			this.progs.add(persos.get(i).getProgramme());
			this.pers=persos.get(0);
			this.listItActions.add(persos.get(i).getProgramme().getActions().iterator());
			this.listFifo.add(new LinkedList<Iterator<Object>>());
			this.listFifo.get(i).add(this.listItActions.get(i));
		}
	}
	
	public void run(){
		try{
			for(int i=0;i<this.listItActions.toArray().length;i++) //Execute une action pour chaque robot
				this.execute((Iterator<Object>)this.listItActions.toArray()[i],i);
			if(isListFifoEmpty())return;
			this.run();
		}catch(NullPointerException e){
			e.printStackTrace();
			return;		
		}catch(StackOverflowError e){
			e.printStackTrace();
		}catch(NoClassDefFoundError noDef){	
		}catch (ArrayIndexOutOfBoundsException aE){//Sorti du terrain
		}catch (BreakException e) {
			this.restaureIterator(Integer.parseInt(e.getMessage()));			
			this.run();
		}			
	}
	
	private void execute(Iterator<Object> itActions,int index) throws ArrayIndexOutOfBoundsException,BreakException{
		try{			
			
			if(itActions.hasNext()){
				Object obj=itActions.next();
			    if(obj instanceof Break && ((Break)obj).getCouleur()==((Break)obj).getPersonnage().getCouleur()){
			    	throw new BreakException(Integer.toString(index));
			    }
				else if(obj instanceof Actions && !(obj instanceof Break)){
					int nbLampeAllumee=((Actions)obj).getPersonnage().getTerrain().getNbLampeAllumee();
					if(nbLampeAllumee >= ((Actions)obj).getPersonnage().getTerrain().getMaxLampe() || ((Actions)obj).getPersonnage().isMort()) throw new ArrayIndexOutOfBoundsException();
					else{
						
						((Actions)obj).agir();
						if((Actions)obj instanceof Avancer)
						{
							this.affichage.avancer();
						}
						else if((Actions)obj instanceof Sauter)
						{
							this.affichage.sauter();
						}
						else if((Actions)obj instanceof Allumer)
						{
							// Ajouter une animation
						}
						else if((Actions)obj instanceof TournerDroite)
						{
							this.affichage.tourner_droite();
						}
						else if((Actions)obj instanceof TournerGauche)
						{
							this.affichage.tourner_gauche();
						}
						
						
						
						this.affichage.set_textures_cases();
						this.affichage.afficher_carte();
						
						
						
						/*
						System.out.println(((Actions)obj).toString());
						System.out.println(this.pers.getPositionX()+" "+this.pers.getPositionY());
						System.out.println(this.pers.getOrientation());*/
					}
				}
				else if(obj instanceof Programme){
					this.setNewIterator(((Programme)obj).getActions().iterator(), index);
				}
			}else{
				this.restaureIterator(index);
			}
		}catch(StackOverflowError e){
			e.printStackTrace();
		}catch(NoClassDefFoundError noDef){
		}catch(NullPointerException nE){
			return;
		}
	}
	
	private void setNewIterator(Iterator<Object> iterator,int index){
		this.listFifo.get(index).add(this.listItActions.get(index));
		this.listItActions.setElementAt(iterator, index);
	}
	
	private void restaureIterator(int index){
		if(!this.listFifo.get(index).isEmpty())
			this.listItActions.setElementAt(this.listFifo.get(index).removeLast(),index);
	}
	
	private boolean isListFifoEmpty(){
		Boolean bool=true;
		for(int i=0;i<this.listItActions.toArray().length;i++){
			bool&=!((Iterator<Object>) this.listItActions.toArray()[i]).hasNext();
			bool&=((LinkedList<Iterator<Object>>)this.listFifo.get(i)).size()==0;
		}			
		return bool;
	}
}
