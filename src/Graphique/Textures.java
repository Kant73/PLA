package Graphique;

import java.io.IOException;
import java.nio.file.Paths;

import org.jsfml.graphics.Texture;

public class Textures {

	
	public static Texture []texProcs;
	public static Texture mesAnims[] ;

	
	public static Texture TexP1 ;
	public static Texture TexP2 ;
	public static Texture TexCaseBase ;
	public static Texture TexVerrou ;
	public static Texture TexCaseLumEteinte ;
	public static Texture TexCaseLumAllum ;
	public static Texture TexCasePointeur;		//Case Pointeur (Vert)
	public static Texture TexCaseClonage;		//Case Clonage (Orange)
	
	public static Texture TexCasePointee;
	public static Texture TexBoutonAvancer;
	public static Texture TexBoutonReculer;
	public static Texture TexCaseTransp;
	public static Texture TexCaseRose;			//Case condition (Rose)
	public static Texture TexCaseViolet;		//Case condition (Violet)
	public static Texture TexBoutonGauche;
	public static Texture TexBoutonDroite;
	public static Texture TexBoutonSauter;
	public static Texture TexBoutonAllumer;
	public static Texture TexBoutonPlay;
	public static Texture texBoutonReset ;
	
	public static Texture TexSymboleBreak;
	public static Texture TexSymboleAvancer;
	public static Texture TexSymboleReculer;
	public static Texture TexSymboleGauche;
	public static Texture TexSymboleDroite;
	public static Texture TexSymboleTournerGauche;
	public static Texture TexSymboleTournerDroite;
	public static Texture TexSymboleSauter;
	public static Texture TexSymboleAllumer;
	public static Texture TexRobotNE;
	public static Texture TexRobotNW;
	public static Texture TexRobotSE;
	public static Texture TexRobotSW;
	
	public static void initTextures()
	{
		texProcs = new Texture[6];
		
		TexVerrou = new Texture();
		TexCaseBase = new Texture();
		TexCaseLumEteinte = new Texture();
		TexCaseLumAllum = new Texture();
		TexCasePointeur = new Texture();
		TexP1 = new Texture();
		TexP2 = new Texture();
		
		TexCaseTransp = new Texture();
		TexCaseRose = new Texture();
		TexCaseViolet = new Texture();
		TexCasePointee = new Texture();
		TexCaseClonage = new Texture();
		
		TexBoutonAllumer = new Texture();
		TexBoutonAvancer = new Texture();
		TexBoutonDroite = new Texture();
		TexBoutonGauche = new Texture();
		TexBoutonReculer = new Texture();
		TexBoutonSauter = new Texture();
		TexBoutonPlay = new Texture();
		TexSymboleBreak = new Texture();
		TexSymboleAllumer = new Texture();
		TexSymboleAvancer = new Texture();
		TexSymboleDroite = new Texture();
		TexSymboleTournerGauche = new Texture();
		TexSymboleTournerDroite = new Texture();
		TexSymboleGauche = new Texture();
		TexSymboleReculer = new Texture();
		TexSymboleSauter = new Texture();

		texBoutonReset=new Texture();
		TexRobotNE= new Texture();
		TexRobotNW= new Texture();
		TexRobotSE= new Texture();
		TexRobotSW= new Texture();
		mesAnims = new Texture[4];
		
		for (int i=0;i< mesAnims.length;i++)
			mesAnims[i] = new Texture();
		
		try {
			for (int i=0;i< texProcs.length;i++)
			{
				texProcs[i]=new Texture ();
				if(i<texProcs.length/2)
					texProcs[i].loadFromFile(Paths.get("src/Img/proc"+i+".png"));	
				else
					texProcs[i].loadFromFile(Paths.get("src/Img/proc"+(i-texProcs.length/2)+"_select.png"));
			}
		
			mesAnims[0].loadFromFile(Paths.get("src/Img/BB8_SE.png"));
			mesAnims[1].loadFromFile(Paths.get("src/Img/BB8_NE.png"));
			mesAnims[2].loadFromFile(Paths.get("src/Img/BB8_NO.png"));
			mesAnims[3].loadFromFile(Paths.get("src/Img/BB8_SO.png"));
			
			texBoutonReset.loadFromFile(Paths.get("src/Img/reset.png"));
			
			TexVerrou.loadFromFile(Paths.get("src/Img/verrou.png"));
			TexCaseBase.loadFromFile(Paths.get("src/Img/case0.png"));
			TexCaseLumEteinte.loadFromFile(Paths.get("src/Img/case1.png"));
			TexCaseLumAllum.loadFromFile(Paths.get("src/Img/case2.png"));
			TexCasePointeur.loadFromFile(Paths.get("src/Img/case3.png"));
			TexCaseClonage.loadFromFile(Paths.get("src/Img/case5.png"));
			TexCaseTransp.loadFromFile(Paths.get("src/Img/case4.png"));
			TexCaseRose.loadFromFile(Paths.get("src/Img/case7.png"));
			TexCaseViolet.loadFromFile(Paths.get("src/Img/case6.png"));
			TexCasePointee.loadFromFile(Paths.get("src/Img/case8.png"));
			
			TexBoutonAllumer.loadFromFile(Paths.get("src/Img/allumer.png"));
			TexBoutonAvancer.loadFromFile(Paths.get("src/Img/avancer.png"));
			TexBoutonDroite.loadFromFile(Paths.get("src/Img/droite.png"));
			TexBoutonGauche.loadFromFile(Paths.get("src/Img/gauche.png"));
			TexBoutonReculer.loadFromFile(Paths.get("src/Img/reculer.png"));
			TexBoutonSauter.loadFromFile(Paths.get("src/Img/saut.png"));
			TexBoutonPlay.loadFromFile(Paths.get("src/Img/play.png"));
			
			TexP1.loadFromFile(Paths.get("src/Img/symbole_P1.png"));
			TexP2.loadFromFile(Paths.get("src/Img/symbole_P2.png"));
			TexSymboleAllumer.loadFromFile(Paths.get("src/Img/symbole_allumer.png"));
			TexSymboleAvancer.loadFromFile(Paths.get("src/Img/symbole_avancer.png"));
			TexSymboleTournerDroite.loadFromFile(Paths.get("src/Img/symbole_rot_droite.png"));
			TexSymboleTournerGauche.loadFromFile(Paths.get("src/Img/symbole_rot_gauche.png"));
			TexSymboleSauter.loadFromFile(Paths.get("src/Img/symbole_saut.png"));
			TexSymboleBreak.loadFromFile(Paths.get("src/Img/break.png"));
			
			TexRobotNE.loadFromFile(Paths.get("src/Img/man_ne.png"));
			TexRobotNW.loadFromFile(Paths.get("src/Img/man_nw.png"));
			TexRobotSE.loadFromFile(Paths.get("src/Img/man_se.png"));
			TexRobotSW.loadFromFile(Paths.get("src/Img/man_sw.png"));
			
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}


}
