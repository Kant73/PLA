package LightBot;

import LightBot.actions.*;
import LightBot.personnage.Pcardinaux;
import LightBot.personnage.Personnage;

public class TestProg {

	public static void main(String[] pArgs){
		new TestProg();
	}
	
	public TestProg(){
		Programme[] progs={new Programme("main",5),new Programme ("Proc1",3),null};
		Personnage robot=new Personnage("Robot", 0, 0, Pcardinaux.NORTH);
		
		progs[0].inserer(progs[1], 0);
		progs[0].inserer(new TournerGauche(robot), 1);
		progs[0].inserer(progs[1], 2);
		
		progs[1].inserer(new Avancer(robot), 0);
		progs[1].inserer(new Allumer(robot), 1);
		progs[1].inserer(new TournerDroite(robot), 2);
		
		robot.setProgramme(progs[0]);
		robot.run();
		
	}
}
