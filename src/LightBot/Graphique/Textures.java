package LightBot.Graphique;

import java.io.IOException;
import java.nio.file.Paths;

import org.jsfml.graphics.Texture;

import LightBot.outils.Utils;

public class Textures {

	
	public static Texture []texProcs;
	public static Texture mesAnims[] ;
	public static Texture texPeinture;
	
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
	public static Texture TexSymbolePoser;
	public static Texture TexSymboleSuppr;
	public static Texture TexSymboleSwap;
	public static Texture TexSymboleWash;
	public static Texture TexSymboleComp;
	public static Texture texSuivant;
	
	public static Texture texTabMain;
	public static Texture texTabP1;
	public static Texture texTabP2;
	
	
	public static void initTextures()
	{
		texProcs = new Texture[6];
		
		texSuivant = new Texture();
		texPeinture = new Texture();
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
		TexSymboleSuppr = new Texture();
		TexSymbolePoser = new Texture();
		TexSymboleSwap = new Texture();
		TexSymboleWash = new Texture();
		TexSymboleComp = new Texture();
		texTabMain = new Texture();
		texTabP1 = new Texture();
		texTabP2 = new Texture();
		

		texBoutonReset=new Texture();
		mesAnims = new Texture[4];
		
		for (int i=0;i< mesAnims.length;i++)
			mesAnims[i] = new Texture();
		
		try {
			for (int i=0;i< texProcs.length;i++)
			{
				texProcs[i]=new Texture ();
				if(i<texProcs.length/2)
					texProcs[i].loadFromStream(Utils.getInputStream("LightBot/Img/proc"+i+".png"));	
				else
					texProcs[i].loadFromStream(Utils.getInputStream("LightBot/Img/proc"+(i-texProcs.length/2)+"_select.png"));
			}
		
			mesAnims[0].loadFromStream(Utils.getInputStream("LightBot/Img/BB8_SE.png"));
			mesAnims[1].loadFromStream(Utils.getInputStream("LightBot/Img/BB8_NE.png"));
			mesAnims[2].loadFromStream(Utils.getInputStream("LightBot/Img/BB8_NO.png"));
			mesAnims[3].loadFromStream(Utils.getInputStream("LightBot/Img/BB8_SO.png"));
			
			
			texPeinture.loadFromStream(Utils.getInputStream("LightBot/Img/peinture.png"));
			texBoutonReset.loadFromStream(Utils.getInputStream("LightBot/Img/reset.png"));
			
			texSuivant.loadFromStream(Utils.getInputStream("LightBot/Img/suivant.png"));
			
			TexVerrou.loadFromStream(Utils.getInputStream("LightBot/Img/verrou.png"));
			TexCaseBase.loadFromStream(Utils.getInputStream("LightBot/Img/case0.png"));
			TexCaseLumEteinte.loadFromStream(Utils.getInputStream("LightBot/Img/case1.png"));
			TexCaseLumAllum.loadFromStream(Utils.getInputStream("LightBot/Img/case2.png"));
			TexCasePointeur.loadFromStream(Utils.getInputStream("LightBot/Img/case3.png"));
			TexCaseClonage.loadFromStream(Utils.getInputStream("LightBot/Img/case5.png"));
			TexCaseTransp.loadFromStream(Utils.getInputStream("LightBot/Img/case4.png"));
			TexCaseRose.loadFromStream(Utils.getInputStream("LightBot/Img/case7.png"));
			TexCaseViolet.loadFromStream(Utils.getInputStream("LightBot/Img/case6.png"));
			TexCasePointee.loadFromStream(Utils.getInputStream("LightBot/Img/case8.png"));
			
			TexBoutonAllumer.loadFromStream(Utils.getInputStream("LightBot/Img/allumer.png"));
			TexBoutonAvancer.loadFromStream(Utils.getInputStream("LightBot/Img/avancer.png"));
			TexBoutonDroite.loadFromStream(Utils.getInputStream("LightBot/Img/droite.png"));
			TexBoutonGauche.loadFromStream(Utils.getInputStream("LightBot/Img/gauche.png"));
			TexBoutonReculer.loadFromStream(Utils.getInputStream("LightBot/Img/reculer.png"));
			TexBoutonSauter.loadFromStream(Utils.getInputStream("LightBot/Img/saut.png"));
			TexBoutonPlay.loadFromStream(Utils.getInputStream("LightBot/Img/play.png"));
			
			TexP1.loadFromStream(Utils.getInputStream("LightBot/Img/symbole_P1.png"));
			TexP2.loadFromStream(Utils.getInputStream("LightBot/Img/symbole_P2.png"));
			TexSymboleAllumer.loadFromStream(Utils.getInputStream("LightBot/Img/symbole_allumer.png"));
			TexSymboleAvancer.loadFromStream(Utils.getInputStream("LightBot/Img/symbole_avancer.png"));
			TexSymboleTournerDroite.loadFromStream(Utils.getInputStream("LightBot/Img/symbole_rot_droite.png"));
			TexSymboleTournerGauche.loadFromStream(Utils.getInputStream("LightBot/Img/symbole_rot_gauche.png"));
			TexSymboleSauter.loadFromStream(Utils.getInputStream("LightBot/Img/symbole_saut.png"));
			TexSymboleBreak.loadFromStream(Utils.getInputStream("LightBot/Img/break.png"));
			TexSymbolePoser.loadFromStream(Utils.getInputStream("LightBot/Img/symbole_poser.png"));
			TexSymboleSuppr.loadFromStream(Utils.getInputStream("LightBot/Img/symbole_suppr.png"));
			TexSymboleSwap.loadFromStream(Utils.getInputStream("LightBot/Img/symbole_swap.png"));
			TexSymboleWash.loadFromStream(Utils.getInputStream("LightBot/Img/symbole_shower.png"));
			TexSymboleComp.loadFromStream(Utils.getInputStream("LightBot/Img/symbole_compare.png"));
			
			texTabP1.loadFromStream(Utils.getInputStream("LightBot/Img/tabP1.png"));
			texTabP2.loadFromStream(Utils.getInputStream("LightBot/Img/tabP2.png"));
			texTabMain.loadFromStream(Utils.getInputStream("LightBot/Img/tabMain.png"));
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}


}
