package LightBot;

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

	private Niveau niveau;
	private Afficher_niveau affichage;
	private int numeroRobot;
	
	public Ordonnanceur(Niveau niv, Afficher_niveau affichage){
		this.niveau=niv;
		this.affichage=affichage;
	}
	
	public void run(){
		try{
			for(int i=0;i<this.niveau.getPersonnages().toArray().length;i++){ //Execute une action pour chaque robot
				majGraphique(this.niveau.getPersonnages().get(i),i);				
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
		} catch (CloneException cE) {
			majGraphiqueApresExec(cE.getCommandeEnCours(),this.numeroRobot);
			if(this.niveau.getPersonnages().size()>0){
				this.niveau.setPersonnages(cE.getListPesonnage());
				/*for(Personnage pers:this.niveau.getPersonnages())
					for(Object obj:pers.getProgramme().getActions())
						if(obj instanceof Actions)System.out.println(pers.getNom()+" : "+obj.toString()+((Actions)obj).getPersonnage().getNom());*/
				this.affichage.reinitialiser_anim();
				this.affichage.initialiser_anim();
				this.affichage.set_pos_robot();
			}
			this.run();
		}			
	}
	
	private void majGraphique(Personnage perso,int index) throws ArrayIndexOutOfBoundsException, BreakException, CloneException{
		this.affichage.animInfos.get(index).setX(this.niveau.getPersonnages().get(index).getPositionX());
		this.affichage.animInfos.get(index).setY(this.niveau.getPersonnages().get(index).getPositionY());
		
		Object commande=null;
		try{
			commande=perso.execute();
		}catch(CloneException cE){
			this.numeroRobot=index;
			throw new CloneException(cE.getListPesonnage(),cE.getCommandeEnCours());
		}
		majGraphiqueApresExec(commande,index);		
	}
	
	private void majGraphiqueApresExec(Object commande, int index){
		if(commande instanceof Avancer)
			this.affichage.avancer(index);
		else if(commande instanceof Sauter)
			this.affichage.sauter(index);
		else if(commande instanceof PoserBloc || commande instanceof RetirerBloc || commande instanceof Swap)
			this.affichage.set_position_cases();
		
		if(this.niveau.getPersonnages().get(index).isMort()){
			this.affichage.animMort( index );
			this.affichage.supprimer_programme(index);
		}							
		
		this.affichage.animInfos.get(index).setX(this.niveau.getPersonnages().get(index).getPositionX());
		this.affichage.animInfos.get(index).setY(this.niveau.getPersonnages().get(index).getPositionY());
		this.affichage.set_pos_robot();
		this.affichage.set_textures_cases();
		this.affichage.afficher_carte();
		
		if(commande instanceof Allumer || commande instanceof PoserBloc || commande instanceof RetirerBloc){
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {}
		}
	}
		
	private boolean isListFifoEmpty(){
		Boolean bool=true;
		for(int i=0;i<this.niveau.getPersonnages().toArray().length;i++){
			bool&=this.niveau.getPersonnages().get(i).isListFifoEmpty();
		}			
		return bool;
	}
}
