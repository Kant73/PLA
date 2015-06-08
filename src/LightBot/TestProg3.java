package LightBot;

import LightBot.actions.Allumer;
import LightBot.actions.Avancer;
import LightBot.actions.Sauter;
import LightBot.actions.TournerGauche;
import LightBot.cases.Case;
import LightBot.cases.Condition;
import LightBot.cases.Couleur;
import LightBot.cases.Lampe;
import LightBot.cases.Normal;
import LightBot.personnage.Pcardinaux;
import LightBot.personnage.Personnage;


public class TestProg3 extends Niveau{

	public static void main(String[] pArgs){
		new TestProg3();
	}
	
	public TestProg3(){
		super();
		this.setTerrain(new Terrain(5,5));
		Case[][] cases=this.getTerrain().getEnsembleDeCase();
		
		cases[1][0]=new Normal(2);
		cases[1][1]=new Normal(2);
		cases[1][2]=new Normal(2);
		cases[1][3]=new Condition(2);
		cases[2][3]=new Lampe(2);			
		
		this.getTerrain().setEnsembleDeCase(cases);
		
		Personnage robot=new Personnage(1, 0, Pcardinaux.SOUTH);
		robot.setTerrain(this.getTerrain());
		this.getPersonnages().add(robot);
		
		this.getProgrammes().add(new Programme("main",7));
		this.getProgrammes().add(new Programme("Proc1",4));
		
		Programme progMain=this.getProgrammes().get(0);
		progMain.inserer(new Avancer(robot), 0);
		progMain.inserer(new Avancer(robot), 1);
		progMain.inserer(new Avancer(robot), 2);
		progMain.inserer(new Allumer(robot), 3);
		progMain.inserer(new TournerGauche(robot,Couleur.Violet), 4);
		progMain.inserer(new Avancer(robot), 5);
		progMain.inserer(new Allumer(robot), 6);
		/*Programme proc1=this.getProgrammes().get(1);
		
		proc1.inserer(new Avancer(robot), 0);
		proc1.inserer(new Allumer(robot), 1);
		proc1.inserer(new TournerGauche(robot,Couleur.Violet), 2);
		proc1.inserer(proc1, 3);
		progMain.inserer(proc1, 0);*/

		robot.setProgramme(progMain);
		
		System.out.println("Terrain de base avant exécution");
		System.out.println("Position du robot: (x="+robot.getPositionX()+",y="+robot.getPositionY()+")");
		this.getTerrain().affiche();
		this.getPersonnages().get(0).run();		
		System.out.println("Terrain après exécution");
		System.out.println("Position du robot: (x="+robot.getPositionX()+",y="+robot.getPositionY()+")");
		this.getTerrain().affiche();	
		
	}
	
}

