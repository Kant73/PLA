package LightBot;

import java.util.Iterator;

import LightBot.actions.Actions;
import LightBot.actions.Allumer;
import LightBot.actions.Avancer;
import LightBot.actions.Sauter;
import LightBot.actions.TournerGauche;
import LightBot.cases.Case;
import LightBot.cases.Clonage;
import LightBot.cases.Lampe;
import LightBot.cases.Normal;
import LightBot.personnage.Pcardinaux;
import LightBot.personnage.Personnage;


public class TestProg6{

	public static void main(String[] pArgs){
		new TestProg6();
	}
	
	public TestProg6(){
		
		Mode_Jeu mj=new Mode_Jeu(NomMode.Fork);
		Niveau n=mj.getNiveau(0);
		
		Personnage robot=new Personnage("Robot",1, 0, Pcardinaux.SOUTH);
		robot.setTerrain(n.getTerrain());
		n.getPersonnages().add(robot);
		
		Programme progMain=new Programme("main",10);
		progMain.insererQueue(new Allumer(robot));
		progMain.insererQueue(new Sauter(robot));
		progMain.insererQueue(new Sauter(robot));
		progMain.insererQueue(new Allumer(robot));
		n.getProgrammes().add(progMain);

		robot.setProgramme(progMain);
		
		
		System.out.println("Terrain de base avant exécution");
		System.out.println("Position du robot: (x="+robot.getPositionX()+",y="+robot.getPositionY()+")");
		n.getTerrain().affiche();
		Ordonnanceur o=new Ordonnanceur(n, null);
		o.run();		
		System.out.println("Terrain après exécution");
		System.out.println("Position du robot: (x="+robot.getPositionX()+",y="+robot.getPositionY()+")");
		n.getTerrain().affiche();	
		
	}
	
}
