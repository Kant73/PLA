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
		
		progs[0].insererQueue(progs[1]);
		progs[0].insererQueue(new TournerGauche());
		progs[0].insererQueue(progs[1]);
		
		progs[1].insererQueue(new Avancer());
		progs[1].insererQueue(new Allumer());
		progs[1].insererQueue(new TournerDroite());
		
		robot.setProgramme(progs[0]);
		
	}
}
