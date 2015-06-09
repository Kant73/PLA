package Graphique;

import java.io.IOException;
import java.nio.file.Paths;

import org.jsfml.graphics.Texture;

public class Textures {

	public static Texture TexCaseBase ;
	public static Texture TexCaseLumEteinte ;
	public static Texture TexCaseLumAllum ;
	
	
	public static Texture TexBoutonAvancer;
	public static Texture TexBoutonReculer;
	public static Texture TexCaseTransp;
	public static Texture TexCaseVerte;
	public static Texture TexBoutonGauche;
	public static Texture TexBoutonDroite;
	public static Texture TexBoutonSauter;
	public static Texture TexBoutonAllumer;
	public static Texture TexBoutonPlay;
	public static Texture TexSymboleAvancer;
	public static Texture TexSymboleReculer;
	public static Texture TexSymboleGauche;
	public static Texture TexSymboleDroite;
	public static Texture TexSymboleSauter;
	public static Texture TexSymboleAllumer;
	public static Texture TexRobotNE;
	public static Texture TexRobotNW;
	public static Texture TexRobotSE;
	public static Texture TexRobotSW;
	
	public static void initTextures()
	{
		TexCaseBase = new Texture();
		TexCaseLumEteinte = new Texture();
		TexCaseLumAllum = new Texture();
		
		TexCaseTransp = new Texture();
		TexCaseVerte = new Texture();
		
		
		TexBoutonAllumer = new Texture();
		TexBoutonAvancer = new Texture();
		TexBoutonDroite = new Texture();
		TexBoutonGauche = new Texture();
		TexBoutonReculer = new Texture();
		TexBoutonSauter = new Texture();
		TexBoutonPlay = new Texture();
		TexSymboleAllumer = new Texture();
		TexSymboleAvancer = new Texture();
		TexSymboleDroite = new Texture();
		TexSymboleGauche = new Texture();
		TexSymboleReculer = new Texture();
		TexSymboleSauter = new Texture();
		TexRobotNE = new Texture();
		TexRobotNW = new Texture();
		TexRobotSE = new Texture();
		TexRobotSW = new Texture();
		
		try {
			TexCaseBase.loadFromFile(Paths.get("src/Img/case0.png"));
			TexCaseLumEteinte.loadFromFile(Paths.get("src/Img/case1.png"));
			TexCaseLumAllum.loadFromFile(Paths.get("src/Img/case2.png"));
			TexCaseVerte.loadFromFile(Paths.get("src/Img/case3.png"));
			TexCaseTransp.loadFromFile(Paths.get("src/Img/case4.png"));
			
			TexBoutonAllumer.loadFromFile(Paths.get("src/Img/allumer.png"));
			TexBoutonAvancer.loadFromFile(Paths.get("src/Img/avancer.png"));
			TexBoutonDroite.loadFromFile(Paths.get("src/Img/droite.png"));
			TexBoutonGauche.loadFromFile(Paths.get("src/Img/gauche.png"));
			TexBoutonReculer.loadFromFile(Paths.get("src/Img/reculer.png"));
			TexBoutonSauter.loadFromFile(Paths.get("src/Img/saut.png"));
			TexBoutonPlay.loadFromFile(Paths.get("src/Img/play.png"));
			
			TexSymboleAllumer.loadFromFile(Paths.get("src/Img/symbole_allumer.png"));
			TexSymboleAvancer.loadFromFile(Paths.get("src/Img/symbole_avancer.png"));
			TexSymboleDroite.loadFromFile(Paths.get("src/Img/symbole_droite.png"));
			TexSymboleGauche.loadFromFile(Paths.get("src/Img/symbole_gauche.png"));
			TexSymboleReculer.loadFromFile(Paths.get("src/Img/symbole_reculer.png"));
			TexSymboleSauter.loadFromFile(Paths.get("src/Img/symbole_saut.png"));
		
			
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
