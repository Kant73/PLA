package LightBot;

import java.util.ArrayList;

import Graphique.Afficher_niveau;
import LightBot.actions.Allumer;
import LightBot.actions.Avancer;
import LightBot.actions.PoserBloc;
import LightBot.actions.RetirerBloc;
import LightBot.actions.Sauter;
import LightBot.actions.Swap;
import LightBot.personnage.Personnage;
import LightBot.exceptions.*;

public class Ordonnanceur {

	private ArrayList<Personnage> listPers;
	private Afficher_niveau affichage;
	
	public Ordonnanceur(ArrayList<Personnage> persos, Afficher_niveau affichage){
		this.listPers=persos;
		this.affichage=affichage;
	}
	
	public void run(){
		try{
			System.out.println();
			for(int i=0;i<this.listPers.toArray().length;i++){ //Execute une action pour chaque robot
				majGraphique(this.listPers.get(i),i);				
			}
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
			this.run();
		} catch (CloneException e) {
			if(this.listPers.size()>0)this.listPers=this.listPers.get(0).getNiveau().getPersonnages();
		}			
	}
	
	private void majGraphique(Personnage perso,int index) throws ArrayIndexOutOfBoundsException, BreakException, CloneException{
		this.affichage.animInfos.get(index).setX(this.listPers.get(index).getPositionX());
		this.affichage.animInfos.get(index).setY(this.listPers.get(index).getPositionY());
		
		Object obj=perso.execute();
		
		if(obj instanceof Avancer)
			this.affichage.avancer(index);
		else if(obj instanceof Sauter)
			this.affichage.sauter(index);
		else if(obj instanceof PoserBloc || obj instanceof RetirerBloc || obj instanceof Swap)
			this.affichage.set_position_cases();
		
		if(this.listPers.get(index).isMort()){
			this.affichage.animMort( index );
			this.affichage.supprimer_programme(index);
		}							
		
		this.affichage.animInfos.get(index).setX(this.listPers.get(index).getPositionX());
		this.affichage.animInfos.get(index).setY(this.listPers.get(index).getPositionY());
		this.affichage.set_pos_robot();
		this.affichage.set_textures_cases();
		this.affichage.afficher_carte();
		
		if(obj instanceof Allumer || obj instanceof PoserBloc || obj instanceof RetirerBloc){
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {}
		}
	}
		
	private boolean isListFifoEmpty(){
		Boolean bool=true;
		for(int i=0;i<this.listPers.toArray().length;i++){
			bool&=this.listPers.get(i).isListFifoEmpty();
		}			
		return bool;
	}
}
