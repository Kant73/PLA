package LightBot;

import LightBot.actions.Allumer;
import LightBot.actions.Avancer;
import LightBot.actions.TournerGauche;
import LightBot.cases.Case;
import LightBot.cases.Lampe;
import LightBot.cases.Normal;
import LightBot.personnage.Pcardinaux;
import LightBot.personnage.Personnage;


public class TestProg2 {

	public static void main(String[] pArgs){
		new TestProg2();
	}
	
	public TestProg2(){
		Case[][] cases=new Case[5][5];
		
		cases[1][0]=new Normal(2);
		cases[1][1]=new Normal(2);
		cases[1][2]=new Normal(2);
		cases[2][2]=new Lampe(2,false);		
		
		Terrain t=new Terrain();
		t.setEnsembleDeCase(cases);
		Programme[] progs={new Programme(10),null,null};
		Personnage robot=new Personnage(1, 0, Pcardinaux.SOUTH);
		robot.setTerrain(t);
		
		progs[0].inserer(new Avancer(robot), 0);
		progs[0].inserer(new Avancer(robot), 1);
		progs[0].inserer(new TournerGauche(robot), 2);
		progs[0].inserer(new Avancer(robot), 3);
		progs[0].inserer(new Allumer(robot), 4);

		robot.setProgramme(progs[0]);
		
		System.out.println("Terrain de base avant exécution");
		System.out.println("Position du robot: (x="+robot.getPositionX()+",y="+robot.getPositionY()+")");
		printTerrain(t.getEnsembleDeCase());
		robot.run();		
		System.out.println("Terrain après exécution");
		System.out.println("Position du robot: (x="+robot.getPositionX()+",y="+robot.getPositionY()+")");
		printTerrain(t.getEnsembleDeCase());

		
		
	}
	
	public void printTerrain(Case[][] cases){
		for(int y=0;y<cases.length;y++){
			for(int x=0;x<cases[y].length;x++)
				/*if(cases[x][y]==null)System.out.print("null ");
				else System.out.print(cases[x][y].getColor()+" ");*/
				if(cases[x][y]!=null) System.out.print("("+x+","+y+")"+cases[x][y].getColor()+" ");
			System.out.println("");
		}
	}
}
