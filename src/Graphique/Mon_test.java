package Graphique;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.PrimitiveType;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Vertex;
import org.jsfml.graphics.VertexArray;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Keyboard.Key;
import org.jsfml.window.Mouse;
import org.jsfml.window.Mouse.Button;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;

import LightBot.Niveau;
import LightBot.actions.*;
import LightBot.cases.Couleur;
import LightBot.cases.Lampe;
import LightBot.parser.Parser;

public class Mon_test {
	 
	
	static Niveau monNiveau;
	
	static int NB_CASE_X ;
	static int NB_CASE_Y ;
	static int NB_CASE_Z ;

	
	public RenderWindow fenetre;
	public Sprite[][][] SpriteCases;
	
	
	public Sprite spriteBoutonAvancer;
	public Sprite spriteBoutonReculer;
	public Sprite spriteBoutonGauche;
	public Sprite spriteBoutonDroite;
	public Sprite spriteBoutonSauter;
	public Sprite spriteBoutonAllumer;
	public Sprite spriteBoutonPlay;
	public Sprite spriteSymboleAvancer;
	public Sprite spriteSymboleReculer;
	public Sprite spriteSymboleGauche;
	public Sprite spriteSymboleDroite;
	public Sprite spriteSymboleTournerGauche;
	public Sprite spriteSymboleTournerDroite;
	public Sprite spriteSymboleSauter;
	public Sprite spriteSymboleAllumer;
	public Sprite spriteRobot;
	
	public LinkedList list_action_possible;
	public float reScale,reScaleRobot;
	int xRobot,yRobot,nextXRobot,nextYRobot;
	
	void set_position_cases()
	{
		float referenceCentreX=fenetre.getSize().x+450;
		float referenceCentreY=fenetre.getSize().y+250;
		float divX = (float) (2.0/reScale);
		float divY = (float) (3.0/reScale);
		
		float Xinit = (referenceCentreX - NB_CASE_X*reScale*this.SpriteCases[0][0][0].getTexture().getSize().x)/2;
		float Yinit = (referenceCentreY/2)-reScale*this.SpriteCases[0][0][0].getTexture().getSize().x/2;

		float ActX;
		float ActY;
		
		
		for(int i= NB_CASE_X-1;i>=0;i--)
		{
			ActX=Xinit + (i-1)*(SpriteCases[0][0][0].getTexture().getSize().x/divX);
			ActY=Yinit-i*(SpriteCases[0][0][0].getTexture().getSize().y/divY);
			for(int j= NB_CASE_Y-1;j>=0;j--)
			{
				ActX=ActX +  SpriteCases[0][0][0].getTexture().getSize().x/divX;
				ActY=ActY +  SpriteCases[0][0][0].getTexture().getSize().y/divY;
				
				SpriteCases[i][j][0].setPosition(ActX ,ActY );

				for(int etageActuel = 1; etageActuel <= monNiveau.getTerrain().getEnsembleDeCase()[i][j].getHauteur();etageActuel ++)
				{
						SpriteCases[i][j][etageActuel].setPosition(SpriteCases[i][j][etageActuel-1].getPosition().x ,SpriteCases[i][j][etageActuel-1].getPosition().y- SpriteCases[i][j][etageActuel-1].getTexture().getSize().y/divY);
				}
			}		
	}	
}
	
	
	void afficher_carte()
	{
		for(int i= NB_CASE_X-1;i>=0;i--)
		{
			for(int j= NB_CASE_Y-1;j>=0;j--)
			{
				for(int etageActuel = 0; etageActuel <=monNiveau.getTerrain().getEnsembleDeCase()[i][j].getHauteur();etageActuel ++)
				{	
					fenetre.draw(SpriteCases[i][j][etageActuel]);
					if((( i==xRobot && j == yRobot)||(i==nextXRobot && j == nextYRobot)) && etageActuel == monNiveau.getTerrain().getEnsembleDeCase()[i][j].getHauteur())
						fenetre.draw(spriteRobot);
				}
			}
		}
		fenetre.display();
	}
	
	void set_pos_robot()
	{
		int i=xRobot,j=yRobot;
			spriteRobot.setPosition(SpriteCases[i][j][monNiveau.getTerrain().getEnsembleDeCase()[i][j].getHauteur()].getPosition().x + reScale*SpriteCases[i][j][monNiveau.getTerrain().getEnsembleDeCase()[i][j].getHauteur()].getTexture().getSize().x/2 - reScaleRobot*spriteRobot.getTexture().getSize().x/2
					,SpriteCases[i][j][monNiveau.getTerrain().getEnsembleDeCase()[i][j].getHauteur()].getPosition().y +reScale*SpriteCases[i][j][monNiveau.getTerrain().getEnsembleDeCase()[i][j].getHauteur()].getTexture().getSize().y/3 - reScaleRobot*spriteRobot.getTexture().getSize().y );
			
	}
	
	void tourner_droite()
	{
		switch (monNiveau.getPersonnages().get(0).getOrientation())
		{
		case NORTH :
			spriteRobot.setTexture(Textures.TexRobotNE);
			break;
		case SOUTH :
			spriteRobot.setTexture(Textures.TexRobotSW);
			break;
		case WEST :
			spriteRobot.setTexture(Textures.TexRobotNW);
			break;
		case EAST :
			spriteRobot.setTexture(Textures.TexRobotSE);
			break;
		}	
	}
	
	void tourner_gauche()
	{
		switch (monNiveau.getPersonnages().get(0).getOrientation())
		{
		case NORTH :
			spriteRobot.setTexture(Textures.TexRobotSW);
			break;
		case SOUTH :
			spriteRobot.setTexture(Textures.TexRobotNE);
			break;
		case WEST :
			spriteRobot.setTexture(Textures.TexRobotSE);
			break;
		case EAST :
			spriteRobot.setTexture(Textures.TexRobotNW);
			break;
		}	
	}
	
	void avancer()
	{
		int newX,newY;
		newX=xRobot;
		newY=yRobot;
		float deplX=0,deplY = 0,deplSaut=0;
		float coeff = 2;
		
		switch (monNiveau.getPersonnages().get(0).getOrientation())
		{
		case NORTH :
			if(yRobot+1<NB_CASE_Y && monNiveau.getTerrain().getEnsembleDeCase()[xRobot][yRobot+1].getHauteur()>=0)
			{
				newY=yRobot+1;	
				deplX=-1;
				deplY=-0.5f;
			}
			break;
		case SOUTH :
			if(yRobot-1>=0 && monNiveau.getTerrain().getEnsembleDeCase()[xRobot][yRobot-1].getHauteur()>=0)
			{
				newY=yRobot-1;	
				deplX=1;
				deplY=0.5f;
			}
			break;
		case WEST :
			if(xRobot-1>=0 && monNiveau.getTerrain().getEnsembleDeCase()[xRobot-1][yRobot].getHauteur()>=0)
			{
				newX=xRobot-1;	
				deplX=-1;
				deplY=0.5f;
			}
			break;
		case EAST :
			if(xRobot+1<NB_CASE_X && monNiveau.getTerrain().getEnsembleDeCase()[xRobot+1][yRobot].getHauteur()>=0)
			{
				newX=xRobot+1;
				deplX=1;
				deplY=-0.5f;
			}
			break;
		}
	
		deplX=deplX*coeff;
		deplY=deplY*coeff;
		if(yRobot!=newY || xRobot != newX)
		{
			nextXRobot=newX;
			nextYRobot=newY;
			Vector2f posFinale,posInit;
		
			posFinale = new Vector2f(SpriteCases[newX][newY][monNiveau.getTerrain().getEnsembleDeCase()[newX][newY].getHauteur()].getPosition().x + reScale*SpriteCases[newX][newY][monNiveau.getTerrain().getEnsembleDeCase()[newX][newY].getHauteur()].getTexture().getSize().x/2 - reScaleRobot*spriteRobot.getTexture().getSize().x/2, 
				SpriteCases[newX][newY][monNiveau.getTerrain().getEnsembleDeCase()[newX][newY].getHauteur()].getPosition().y +reScale*SpriteCases[newX][newY][monNiveau.getTerrain().getEnsembleDeCase()[newX][newY].getHauteur()].getTexture().getSize().y/3 - reScaleRobot*spriteRobot.getTexture().getSize().y);
			posInit = new Vector2f(spriteRobot.getPosition().x,spriteRobot.getPosition().y);
		
			while( (posInit.x>=posFinale.x && posInit.y>=posFinale.y 
					  && spriteRobot.getPosition().x >= posFinale.x 
					  && spriteRobot.getPosition().y >= posFinale.y)
					  	  || 
				  (posInit.x<=posFinale.x && posInit.y>=posFinale.y 
						  && spriteRobot.getPosition().x <= posFinale.x 
						  && spriteRobot.getPosition().y >= posFinale.y)
						  || 
				  (posInit.x>=posFinale.x && posInit.y<=posFinale.y 
						  && spriteRobot.getPosition().x >= posFinale.x 
						  && spriteRobot.getPosition().y <= posFinale.y)
						  || 
				  (posInit.x<=posFinale.x && posInit.y<=posFinale.y 
						  && spriteRobot.getPosition().x <= posFinale.x 
						  && spriteRobot.getPosition().y <= posFinale.y)						  
				  )
			{
				spriteRobot.setPosition(spriteRobot.getPosition().x+deplX,spriteRobot.getPosition().y+deplY);
				afficher_carte();
			}
		
			initPlaceRobot(newX,newY);
			set_pos_robot();
			afficher_carte();
		}
	}
	
	
	void sauter()
	{
		int newX,newY;
		newX=xRobot;
		newY=yRobot;
		float deplX=0,deplY = 0,deplSaut=0;
		float coeff = 2;
		Vector2f posFinale,posInit;
		
		posFinale = new Vector2f(SpriteCases[newX][newY][monNiveau.getTerrain().getEnsembleDeCase()[newX][newY].getHauteur()].getPosition().x + reScale*SpriteCases[newX][newY][monNiveau.getTerrain().getEnsembleDeCase()[newX][newY].getHauteur()].getTexture().getSize().x/2 - reScaleRobot*spriteRobot.getTexture().getSize().x/2, 
				SpriteCases[newX][newY][monNiveau.getTerrain().getEnsembleDeCase()[newX][newY].getHauteur()].getPosition().y +reScale*SpriteCases[newX][newY][monNiveau.getTerrain().getEnsembleDeCase()[newX][newY].getHauteur()].getTexture().getSize().y/3 - reScaleRobot*spriteRobot.getTexture().getSize().y);
		posInit = new Vector2f(spriteRobot.getPosition().x,spriteRobot.getPosition().y);
		
		
		if(monNiveau.getTerrain().getEnsembleDeCase()[xRobot][yRobot].getHauteur()!=monNiveau.getTerrain().getEnsembleDeCase()[nextXRobot][nextYRobot].getHauteur())
		{	
			deplSaut=-3*coeff;
		
			boolean b =  newX>xRobot || newY>yRobot ;

			while (
					(b && spriteRobot.getPosition().y + reScaleRobot*spriteRobot.getTexture().getSize().y > SpriteCases[nextXRobot][nextYRobot][monNiveau.getTerrain().getEnsembleDeCase()[nextXRobot][nextYRobot].getHauteur()].getPosition().y+reScale*SpriteCases[newX][newY][monNiveau.getTerrain().getEnsembleDeCase()[newX][newY].getHauteur()].getTexture().getSize().y/3*2)
					||(!b  && monNiveau.getTerrain().getEnsembleDeCase()[nextXRobot][nextYRobot].getHauteur()>monNiveau.getTerrain().getEnsembleDeCase()[xRobot][yRobot].getHauteur() && spriteRobot.getPosition().y + reScaleRobot*spriteRobot.getTexture().getSize().y > posInit.y+reScaleRobot*spriteRobot.getTexture().getSize().y -reScale*SpriteCases[newX][newY][monNiveau.getTerrain().getEnsembleDeCase()[newX][newY].getHauteur()].getTexture().getSize().y/3*(monNiveau.getTerrain().getEnsembleDeCase()[newX][newY].getHauteur()-monNiveau.getTerrain().getEnsembleDeCase()[xRobot][yRobot].getHauteur()))
					)
			{
				spriteRobot.setPosition(spriteRobot.getPosition().x,spriteRobot.getPosition().y+deplSaut);
				afficher_carte();	
			}
		}
		this.avancer();
	}
	
	
	void deplacement_robot(int Orientation)
	{
		int newX,newY;
		newX=xRobot;
		newY=yRobot;
		float deplX=0,deplY = 0,deplSaut=0;
		float coeff = 2;
	
		if(Orientation==0)//HAUT
		{
			spriteRobot.setTexture(Textures.TexRobotNW);
			if(yRobot+1<NB_CASE_Y && monNiveau.getTerrain().getEnsembleDeCase()[xRobot][yRobot+1].getHauteur()>=0)
			{
				newY=yRobot+1;	
				deplX=-1;
				deplY=-0.5f;
			}
		}
		if(Orientation==1)//BAS
		{
			spriteRobot.setTexture(Textures.TexRobotSE);
			if(yRobot-1>=0 && monNiveau.getTerrain().getEnsembleDeCase()[xRobot][yRobot-1].getHauteur()>=0)
			{
				newY=yRobot-1;	
				deplX=1;
				deplY=0.5f;
			}
		}
		if(Orientation==2 )// GAUCHE
		{
			spriteRobot.setTexture(Textures.TexRobotSW);
			if(xRobot-1>=0 && monNiveau.getTerrain().getEnsembleDeCase()[xRobot-1][yRobot].getHauteur()>=0)
			{
				newX=xRobot-1;	
				deplX=-1;
				deplY=0.5f;
			}
		}
		if(Orientation==3)// DROITE
		{
			spriteRobot.setTexture(Textures.TexRobotNE);
			if(xRobot+1<NB_CASE_X && monNiveau.getTerrain().getEnsembleDeCase()[xRobot+1][yRobot].getHauteur()>=0)
			{
				newX=xRobot+1;
				deplX=1;
				deplY=-0.5f;
			}
		}
	
		deplX=deplX*coeff;
		deplY=deplY*coeff;
		if(yRobot!=newY || xRobot != newX)
		{
			nextXRobot=newX;
			nextYRobot=newY;
			Vector2f posFinale,posInit;
			
			posFinale = new Vector2f(SpriteCases[newX][newY][monNiveau.getTerrain().getEnsembleDeCase()[newX][newY].getHauteur()].getPosition().x + reScale*SpriteCases[newX][newY][monNiveau.getTerrain().getEnsembleDeCase()[newX][newY].getHauteur()].getTexture().getSize().x/2 - reScaleRobot*spriteRobot.getTexture().getSize().x/2, 
					SpriteCases[newX][newY][monNiveau.getTerrain().getEnsembleDeCase()[newX][newY].getHauteur()].getPosition().y +reScale*SpriteCases[newX][newY][monNiveau.getTerrain().getEnsembleDeCase()[newX][newY].getHauteur()].getTexture().getSize().y/3 - reScaleRobot*spriteRobot.getTexture().getSize().y);
			posInit = new Vector2f(spriteRobot.getPosition().x,spriteRobot.getPosition().y);
			
			
			if(monNiveau.getTerrain().getEnsembleDeCase()[xRobot][yRobot].getHauteur()!=monNiveau.getTerrain().getEnsembleDeCase()[nextXRobot][nextYRobot].getHauteur())
			{	
				deplSaut=-3*coeff;
			
				boolean b =  newX>xRobot || newY>yRobot ;

				while (
						(b && spriteRobot.getPosition().y + reScaleRobot*spriteRobot.getTexture().getSize().y > SpriteCases[nextXRobot][nextYRobot][monNiveau.getTerrain().getEnsembleDeCase()[nextXRobot][nextYRobot].getHauteur()].getPosition().y+reScale*SpriteCases[newX][newY][monNiveau.getTerrain().getEnsembleDeCase()[newX][newY].getHauteur()].getTexture().getSize().y/3*2)
						||(!b  && monNiveau.getTerrain().getEnsembleDeCase()[nextXRobot][nextYRobot].getHauteur()>monNiveau.getTerrain().getEnsembleDeCase()[xRobot][yRobot].getHauteur() && spriteRobot.getPosition().y + reScaleRobot*spriteRobot.getTexture().getSize().y > posInit.y+reScaleRobot*spriteRobot.getTexture().getSize().y -reScale*SpriteCases[newX][newY][monNiveau.getTerrain().getEnsembleDeCase()[newX][newY].getHauteur()].getTexture().getSize().y/3*(monNiveau.getTerrain().getEnsembleDeCase()[newX][newY].getHauteur()-monNiveau.getTerrain().getEnsembleDeCase()[xRobot][yRobot].getHauteur()))
						)
				{
					spriteRobot.setPosition(spriteRobot.getPosition().x,spriteRobot.getPosition().y+deplSaut);
					afficher_carte();	
				}
			}
		
			posFinale = new Vector2f(SpriteCases[newX][newY][monNiveau.getTerrain().getEnsembleDeCase()[newX][newY].getHauteur()].getPosition().x + reScale*SpriteCases[newX][newY][monNiveau.getTerrain().getEnsembleDeCase()[newX][newY].getHauteur()].getTexture().getSize().x/2 - reScaleRobot*spriteRobot.getTexture().getSize().x/2, 
				SpriteCases[newX][newY][monNiveau.getTerrain().getEnsembleDeCase()[newX][newY].getHauteur()].getPosition().y +reScale*SpriteCases[newX][newY][monNiveau.getTerrain().getEnsembleDeCase()[newX][newY].getHauteur()].getTexture().getSize().y/3 - reScaleRobot*spriteRobot.getTexture().getSize().y);
			posInit = new Vector2f(spriteRobot.getPosition().x,spriteRobot.getPosition().y);
		
			while( (posInit.x>=posFinale.x && posInit.y>=posFinale.y 
					  && spriteRobot.getPosition().x >= posFinale.x 
					  && spriteRobot.getPosition().y >= posFinale.y)
					  	  || 
				  (posInit.x<=posFinale.x && posInit.y>=posFinale.y 
						  && spriteRobot.getPosition().x <= posFinale.x 
						  && spriteRobot.getPosition().y >= posFinale.y)
						  || 
				  (posInit.x>=posFinale.x && posInit.y<=posFinale.y 
						  && spriteRobot.getPosition().x >= posFinale.x 
						  && spriteRobot.getPosition().y <= posFinale.y)
						  || 
				  (posInit.x<=posFinale.x && posInit.y<=posFinale.y 
						  && spriteRobot.getPosition().x <= posFinale.x 
						  && spriteRobot.getPosition().y <= posFinale.y)						  
				  )
			{
				spriteRobot.setPosition(spriteRobot.getPosition().x+deplX,spriteRobot.getPosition().y+deplY);
				afficher_carte();
			}
		
			initPlaceRobot(newX,newY);
			set_pos_robot();
			afficher_carte();
		}
	}
	
	public void SetSprites()
	{
		this.spriteRobot=new Sprite();
		switch (monNiveau.getPersonnages().get(0).getOrientation())
		{
		case NORTH :
			spriteRobot.setTexture(Textures.TexRobotNW);
			break;
		case SOUTH :
			spriteRobot.setTexture(Textures.TexRobotSE);
			break;
		case WEST :
			spriteRobot.setTexture(Textures.TexRobotSW);
			break;
		case EAST :
			spriteRobot.setTexture(Textures.TexRobotNE);
			break;
		}	
		this.spriteRobot.setScale(reScaleRobot,reScaleRobot);
		
		spriteBoutonAllumer=new Sprite();
		spriteBoutonAllumer.setTexture(Textures.TexBoutonAllumer);
		
		spriteBoutonAvancer=new Sprite();
		spriteBoutonAvancer.setTexture(Textures.TexBoutonAvancer);
		
		spriteBoutonDroite=new Sprite();
		spriteBoutonDroite.setTexture(Textures.TexBoutonDroite);
		
		spriteBoutonGauche=new Sprite();
		spriteBoutonGauche.setTexture(Textures.TexBoutonGauche);
		
		spriteBoutonReculer=new Sprite();
		spriteBoutonReculer.setTexture(Textures.TexBoutonReculer);
		
		spriteBoutonSauter=new Sprite();
		spriteBoutonSauter.setTexture(Textures.TexBoutonSauter);
		
		spriteBoutonPlay=new Sprite();
		spriteBoutonPlay.setTexture(Textures.TexBoutonPlay);
		
		spriteSymboleAvancer = new Sprite();
		spriteSymboleAvancer.setTexture(Textures.TexSymboleAvancer);
		
		spriteSymboleAllumer = new Sprite();
		spriteSymboleAllumer.setTexture(Textures.TexSymboleAllumer);
		
		spriteSymboleDroite=new Sprite();
		spriteSymboleDroite.setTexture(Textures.TexSymboleDroite);
		
		spriteSymboleGauche=new Sprite();
		spriteSymboleGauche.setTexture(Textures.TexSymboleGauche);
		
		spriteSymboleReculer=new Sprite();
		spriteSymboleReculer.setTexture(Textures.TexSymboleReculer);
		
		spriteSymboleSauter=new Sprite();
		spriteSymboleSauter.setTexture(Textures.TexSymboleSauter);
				
		this.spriteSymboleTournerDroite = new Sprite();
		this.spriteSymboleTournerDroite.setTexture(Textures.TexSymboleTournerDroite);
		
		this.spriteSymboleTournerGauche = new Sprite();
		this.spriteSymboleTournerGauche.setTexture(Textures.TexSymboleTournerGauche);
		
		SpriteCases=new Sprite[NB_CASE_X][NB_CASE_Y][NB_CASE_Z];
		for(int i=0;i<  NB_CASE_X;i++)
			for(int j=0;j<  NB_CASE_Y;j++)
				for(int k=0;k<  NB_CASE_Z;k++)
				{
					SpriteCases[i][j][k]=new Sprite();
					
					SpriteCases[i][j][k].setTexture(Textures.TexCaseBase);
					
					if(k==NB_CASE_Z-1)
					{
						if(monNiveau.getTerrain().getEnsembleDeCase()[i][j] instanceof Lampe  && monNiveau.getTerrain().getEnsembleDeCase()[i][j].getColor() == Couleur.Bleu)
							SpriteCases[i][j][k].setTexture(Textures.TexCaseLumEteinte); 
						else if(monNiveau.getTerrain().getEnsembleDeCase()[i][j] instanceof Lampe  && monNiveau.getTerrain().getEnsembleDeCase()[i][j].getColor() == Couleur.Jaune)
							SpriteCases[i][j][k].setTexture(Textures.TexCaseLumAllum);
									
					}

					

					
					SpriteCases[i][j][k].setScale(reScale,reScale);

				}
	}

	
	public Mon_test(float Scale,String niveauPourParser)
	{
		Parser p = new Parser(niveauPourParser);
		p.lire();
		monNiveau = p.getNiveau();
	
		NB_CASE_X = monNiveau.getTerrain().getLargeur();
		NB_CASE_Y =  monNiveau.getTerrain().getLongueur();
		NB_CASE_Z =  monNiveau.getTerrain().getHauteurMax()+1;
		
		fenetre = new RenderWindow();
		fenetre.create(new VideoMode(1366, 768), "Pokebot");
		reScale=Scale;
		reScaleRobot=Scale/3;
		SetSprites();
		
		initPlaceRobot(monNiveau.getPersonnages().get(0).getPositionX(),monNiveau.getPersonnages().get(0).getPositionY());
	}
	
	
	
	public void initPlaceRobot(int x,int y)
	{
		xRobot=x;
		yRobot=y;
		nextXRobot=x;
		nextYRobot=y;
	}
	
	public void initActionsPossible()
	{
		this.list_action_possible = new LinkedList();
		ArrayList al = monNiveau.getActions();
		for (int i = 0; i < al.size(); i++) {
			if (al.get(i) instanceof Avancer) {
				//this.spriteSymboleAvancer.setTexture(Textures.TexSymboleAvancer);
				StructStringSprite struct = this.new StructStringSprite(this.spriteSymboleAvancer, "avancer");
				this.list_action_possible.add(struct);
			}
			else if(al.get(i) instanceof Allumer) {
				//this.spriteSymboleAllumer.setTexture(Textures.TexSymboleAllumer);
				StructStringSprite struct = this.new StructStringSprite(this.spriteSymboleAllumer, "allumer");
				this.list_action_possible.add(struct);
			}
			
			else if(al.get(i) instanceof Break) {
				System.out.println("Break");
			}
			
			else if(al.get(i) instanceof Sauter) {
				//this.spriteSymboleSauter.setTexture(Textures.TexSymboleSauter);
				StructStringSprite struct = this.new StructStringSprite(this.spriteSymboleSauter, "sauter");
				this.list_action_possible.add(struct);
			}
			
			else if(al.get(i) instanceof TournerDroite) {
				//this.spriteSymboleTournerDroite.setTexture(Textures.TexSymboleTournerDroite);
				StructStringSprite struct = this.new StructStringSprite(this.spriteSymboleTournerDroite, "droite");
				this.list_action_possible.add(struct);
			}
			
			else if(al.get(i) instanceof TournerGauche) {
				//this.spriteSymboleTournerGauche.setTexture(Textures.TexSymboleTournerGauche);
				StructStringSprite struct = this.new StructStringSprite(this.spriteSymboleTournerGauche, "gauche");
				this.list_action_possible.add(struct);
			}
			
			else if(al.get(i) instanceof Wash) {
				System.out.println("Wash");
			}
		}
	}

	
	public void afficher_boutons(){
		if (!this.list_action_possible.isEmpty()) {
			for (int k = 0; k < this.list_action_possible.size(); k++) {
				StructStringSprite temp = (StructStringSprite) this.list_action_possible.get(k);
				temp.sprite.setPosition(450+k*65, 50);
				this.fenetre.draw(temp.sprite);
			}
		}
		this.fenetre.draw(this.spriteBoutonPlay);
	}
	
	public VertexArray createGradient(Color color1, Color color2, RenderWindow window){
		
		//Create the vertex array
        VertexArray gradient = new VertexArray();

        //Our gradient will be a rectangular shape - a quad - so we will use the primitive type QUADS
        gradient.setPrimitiveType(PrimitiveType.QUADS);

        //Create the vertices in counter-clockwise order (standard), beginning with the top left corner of the screen
        //The vertices at the top edge of the screen should be yellow
        //color1 = new org.jsfml.graphics.Color(0, 255, 0, 255);
        gradient.add(new Vertex(new Vector2f(0, 0), color1));

        //Create the bottom left vertex
        //The vertices at the bottom edge of the screen should be red
        gradient.add(new Vertex(new Vector2f(0, window.getSize().y), color2));

        //Create the bottom right vertex (red)
        gradient.add(new Vertex(new Vector2f(window.getSize().x, window.getSize().y), color2));

        //Create the top right vertex (yellow)
        gradient.add(new Vertex(new Vector2f(window.getSize().x, 0),color1));
        
		return gradient;
	}
	
	public class StructStringSprite {
		public Sprite sprite;
		public String nom;
		
		public StructStringSprite(Sprite sprite, String string)
		{
			Sprite temp = new Sprite(sprite.getTexture());
			this.sprite = temp;
			this.nom = string;
		}
		
		public StructStringSprite(StructStringSprite struct)
		{
			Sprite temp = new Sprite(struct.sprite.getTexture());
			this.sprite = temp;
			this.nom = struct.nom;
		}
		
	}
	
	
	public static void main(String[] args) {
		
		int i=0,j=0;
		List l = new LinkedList();
		Textures.initTextures();
		
		Mon_test Affiche_monde = new Mon_test(1f,"src/LightBot/levels/" + args[0]);
	
		//Affiche_monde.fenetre.clear(Color.GREEN);
		int initPosY=50,initPosX=90;
		
		/*
		Affiche_monde.spriteBoutonAllumer.setPosition(initPosX,initPosY);
		Affiche_monde.spriteBoutonAvancer.setPosition(initPosX,initPosY+=60);
		Affiche_monde.spriteBoutonDroite.setPosition(initPosX,initPosY+=60);
		Affiche_monde.spriteBoutonGauche.setPosition(initPosX,initPosY+=60);
		Affiche_monde.spriteBoutonReculer.setPosition(initPosX,initPosY+=60);
		Affiche_monde.spriteBoutonSauter.setPosition(initPosX,initPosY+=60);
		*/
		Affiche_monde.spriteBoutonPlay.setPosition(1300,0);
		
		
		
		Affiche_monde.set_position_cases();
		Affiche_monde.set_pos_robot();
		Affiche_monde.afficher_carte();
		Affiche_monde.initActionsPossible();
		Affiche_monde.afficher_boutons();
		VertexArray gradient = Affiche_monde.createGradient(new org.jsfml.graphics.Color(0, 178, 255, 255), Color.BLACK, Affiche_monde.fenetre);
	
			while (Affiche_monde.fenetre.isOpen()) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for (Event event : Affiche_monde.fenetre.pollEvents()) {					
					if (event.type == Event.Type.MOUSE_BUTTON_PRESSED) { 
						Vector2i pos = Mouse.getPosition(Affiche_monde.fenetre); 
						
						//Si clique droit sur un élément du main, on le supprime
						if (!l.isEmpty()) {
							for (int k = 0; k < l.size(); k++) {
								StructStringSprite temp = (StructStringSprite) l.get(k);
								if(temp.sprite.getGlobalBounds().contains(pos.x,pos.y) && event.asMouseButtonEvent().button == Button.RIGHT)
								{
									l.remove(k);
								}
							}
						}
						
						//Ajout des éléments qu'on a cliqué dans le main
						if (!Affiche_monde.list_action_possible.isEmpty()) {
							for (int k = 0; k < Affiche_monde.list_action_possible.size(); k++) {
								StructStringSprite temp = (StructStringSprite) Affiche_monde.list_action_possible.get(k);
								if(temp.sprite.getGlobalBounds().contains(pos.x,pos.y))
								{
									StructStringSprite struct = Affiche_monde.new StructStringSprite(temp);
									l.add(struct);
								}
							}
						}
						
						/*
						if(Affiche_monde.spriteBoutonAvancer.getGlobalBounds().contains(pos.x,pos.y))
						{
							
							StructStringSprite struct = Affiche_monde.new StructStringSprite(Affiche_monde.spriteSymboleAvancer, "avancer");
							l.add(struct);
						}
						
						else if(Affiche_monde.spriteBoutonReculer.getGlobalBounds().contains(pos.x,pos.y))
						{
							
							
							StructStringSprite struct = Affiche_monde.new StructStringSprite(Affiche_monde.spriteSymboleReculer, "reculer");
							l.add(struct);
						}
						
						else if(Affiche_monde.spriteBoutonGauche.getGlobalBounds().contains(pos.x,pos.y))
						{
							
							
							StructStringSprite struct = Affiche_monde.new StructStringSprite(Affiche_monde.spriteSymboleGauche, "gauche");
							l.add(struct);
						}
						
						else if(Affiche_monde.spriteBoutonDroite.getGlobalBounds().contains(pos.x,pos.y))
						{
							
							
							StructStringSprite struct = Affiche_monde.new StructStringSprite(Affiche_monde.spriteSymboleDroite, "droite");
							l.add(struct);
						}
						*/
						if(Affiche_monde.spriteBoutonPlay.getGlobalBounds().contains(pos.x,pos.y))
						{
							if (!l.isEmpty()) {
								for (int k = 0; k < l.size(); k++) {
									StructStringSprite temp = (StructStringSprite) l.get(k);
									
									if (temp.nom == "avancer") {
										//System.out.println("spriteBoutonAvancer");
										
										/*j++;
										if(j==NB_CASE_Y)
											j=0;*/
										Affiche_monde.deplacement_robot(0);
										
									}
									else if (temp.nom == "reculer") {
										//System.out.println("spriteBoutonReculer");
										
										/*if(j==0)
											j=NB_CASE_Y-1;
										else
											j--;*/
										Affiche_monde.deplacement_robot(1);
									}
									else if (temp.nom == "droite") {
										//System.out.println("spriteBoutonDroite");
										
										/*i++;
										if(i==NB_CASE_X)
											i=0;*/
										Affiche_monde.deplacement_robot(3);
									}
									else if (temp.nom == "gauche") {
										//System.out.println("spriteBoutonGauche");
										
										/*if(i==0)
											i=NB_CASE_X-1;
										else
											i--;*/
										Affiche_monde.deplacement_robot(2);
									}
									/*
									try {
										Thread.sleep(200);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
									Affiche_monde.fenetre.draw(gradient);
									if (!l.isEmpty()) {
										for (int k1 = 0; k1 < l.size(); k1++) {
											StructStringSprite temp1 = (StructStringSprite) l.get(k1);
											temp1.sprite.setPosition(0, k1*65);
											Affiche_monde.fenetre.draw(temp1.sprite);
										}
									}
									*/
									//Affiche_monde.afficher_boutons();
									//Affiche_monde.initPlaceRobot(i,j);
									//Affiche_monde.set_pos_robot();
									//Affiche_monde.afficher_carte();
								}
							}
						}
	
					}
					if (event.type == Event.Type.CLOSED) {
						Affiche_monde.fenetre.close();
					}
					if (event.type == Event.Type.KEY_PRESSED) {
						if (Keyboard.isKeyPressed(Key.UP)) {
							Affiche_monde.deplacement_robot(0);
							System.out.println(" UP");
						}
						else if (Keyboard.isKeyPressed(Key.DOWN)) {
							Affiche_monde.deplacement_robot(1);
							System.out.println(" DOWN");
						}
						else if (Keyboard.isKeyPressed(Key.LEFT)) {
							Affiche_monde.deplacement_robot(2);
							System.out.println(" LEFT");
						}
						else if (Keyboard.isKeyPressed(Key.RIGHT)) {
							Affiche_monde.deplacement_robot(3);
							System.out.println(" RIGHT");
						}	
					}
					/*
					if (!l.isEmpty()) {
						for (int k = 0; k < l.size(); k++) {
							StructStringSprite temp = (StructStringSprite) l.get(k);
							Vector2i pos2 = Mouse.getPosition(Affiche_monde.fenetre); 
							if(temp.sprite.getGlobalBounds().contains(pos2.x,pos2.y))
							{
								
							}
						}
					}
					*/
					Affiche_monde.fenetre.draw(gradient);
					//Affiche_monde.fenetre.clear(Color.GREEN);
					Affiche_monde.afficher_boutons();
					if (!l.isEmpty()) {
						int cpty=0;
						for (int k = 0; k < l.size(); k++) {
							
							if(k%5==0 && k!=0)
							{
								cpty++;
							}
								
							StructStringSprite temp = (StructStringSprite) l.get(k);
							temp.sprite.setPosition((k%5)*65, cpty*65);
							Affiche_monde.fenetre.draw(temp.sprite);
						}
					}
					//Affiche_monde.initPlaceRobot(i,j);
					//Affiche_monde.set_pos_robot();
					Affiche_monde.afficher_carte();
	
				}
	
			}
	}
}
