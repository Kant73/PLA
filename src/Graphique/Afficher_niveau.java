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
import org.jsfml.window.event.Event;

import LightBot.Niveau;
import LightBot.actions.Allumer;
import LightBot.actions.Avancer;
import LightBot.actions.Break;
import LightBot.actions.Sauter;
import LightBot.actions.TournerDroite;
import LightBot.actions.TournerGauche;
import LightBot.actions.Wash;
import LightBot.cases.ConditionRose;
import LightBot.cases.ConditionViolet;
import LightBot.cases.Couleur;
import LightBot.cases.Lampe;
import LightBot.cases.Pointeur;

public class Afficher_niveau extends Menu_niveaux{
	 
	
	static Niveau monNiveau;
	
	static int NB_CASE_X ;
	static int NB_CASE_Y ;
	static int NB_CASE_Z ;

	public Sprite[][][] SpriteCases;
	public Sprite [] spritesProcedures;
	
	
	public Sprite spriteBoutonPlay;
	public Sprite spriteP1;
	public Sprite spriteP2;
	public Sprite spriteSymboleAvancer;
	public Sprite spriteSymboleReculer;
	public Sprite spriteSymboleGauche;
	public Sprite spriteSymboleDroite;
	public Sprite spriteSymboleTournerGauche;
	public Sprite spriteSymboleTournerDroite;
	public Sprite spriteSymboleSauter;
	public Sprite spriteSymboleAllumer;
	public Sprite spriteSymboleBreak;
	public Sprite spriteRobot;
	public VertexArray gradient;
	
	public LinkedList list_action_possible;
	public List[] tabProgramme;	//Tableau de liste de sprite (qui représente les actions du main et des proc)
	public float reScale,reScaleRobot;
	int xRobot,yRobot,nextXRobot,nextYRobot;
	int progSelect;
		
	/**
	 * Permet de determiner la position des cases
	 */
	void set_position_cases()
	{
		float referenceCentreX=Menu_principal.fenetre.getSize().x+450;
		float referenceCentreY=Menu_principal.fenetre.getSize().y+250;
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
	
	/**
	 * Permet d'afficher notre terrain ainsi que le fond dégradé, les boutons d'action dispo pour un niveau et les actions qui sont dans les procédures (dont le main)
	 */
	void afficher_carte()
	{
		Menu_principal.fenetre.draw(this.gradient);
		for (int i=0; i< this.monNiveau.getProgrammes().size(); i++)
			Menu_principal.fenetre.draw(spritesProcedures[i]);
		this.afficher_boutons();
		this.afficher_procedure();
		for(int i= NB_CASE_X-1;i>=0;i--)
		{
			for(int j= NB_CASE_Y-1;j>=0;j--)
			{
				for(int etageActuel = 0; etageActuel <=monNiveau.getTerrain().getEnsembleDeCase()[i][j].getHauteur();etageActuel ++)
				{	
					Menu_principal.fenetre.draw(SpriteCases[i][j][etageActuel]);
					if((( i==xRobot && j == yRobot)||(i==nextXRobot && j == nextYRobot)) && etageActuel == monNiveau.getTerrain().getEnsembleDeCase()[i][j].getHauteur())
						Menu_principal.fenetre.draw(spriteRobot);
				}
			}
		}
		Menu_principal.fenetre.display();
	}
	
	/**
	 * Permet de changer la position du robot
	 */
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
	
	
	public void sprite_selectionne(Vector2i pos)
	{	
		int last_select = this.progSelect;
		
		for (int i=0; i< this.monNiveau.getProgrammes().size() ; i++)
		{
			if(spritesProcedures[i].getGlobalBounds().contains(pos.x,pos.y) && i!=this.progSelect)
			{	
				spritesProcedures[i].setTexture(Textures.texProcs[i+this.spritesProcedures.length]);	
				this.progSelect=i;
			}
		}
		if(this.progSelect!=last_select)
			spritesProcedures[last_select].setTexture(Textures.texProcs[last_select]);
	}
	/**
	 * Permet d'initialiser tous les sprites avec leur textures
	 */
	public void SetSprites()
	{
		int ecart=50;
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
		
		
		spritesProcedures = new Sprite[3];
		for (int i=0;i<spritesProcedures.length;i++)
		{
			spritesProcedures[i]=new Sprite();
			spritesProcedures[i].setTexture(Textures.texProcs[i]);	
		}
		
	
		
		spritesProcedures[0].setPosition(25, ecart);
		spritesProcedures[1].setPosition(25,spritesProcedures[0].getPosition().y + spritesProcedures[0].getTexture().getSize().y + ecart );
		spritesProcedures[2].setPosition(25,	spritesProcedures[1].getPosition().y + spritesProcedures[1].getTexture().getSize().y + ecart );
		spritesProcedures[0].setTexture(Textures.texProcs[3]);
		
		spriteBoutonPlay=new Sprite();
		spriteBoutonPlay.setTexture(Textures.TexBoutonPlay);
			
		spriteP1= new Sprite();
		spriteP1.setTexture(Textures.TexP1);
		
		spriteP2= new Sprite();
		spriteP2.setTexture(Textures.TexP2);

		spriteSymboleBreak = new Sprite();
		spriteSymboleBreak.setTexture(Textures.TexSymboleBreak);
		
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
					
					if(k==monNiveau.getTerrain().getEnsembleDeCase()[i][j].getHauteur())
					{
						if(monNiveau.getTerrain().getEnsembleDeCase()[i][j] instanceof Lampe  && monNiveau.getTerrain().getEnsembleDeCase()[i][j].getColor() == Couleur.Bleu)
							SpriteCases[i][j][k].setTexture(Textures.TexCaseLumEteinte); 
						else if(monNiveau.getTerrain().getEnsembleDeCase()[i][j] instanceof Lampe  && monNiveau.getTerrain().getEnsembleDeCase()[i][j].getColor() == Couleur.Jaune)
							SpriteCases[i][j][k].setTexture(Textures.TexCaseLumAllum);
						else if(monNiveau.getTerrain().getEnsembleDeCase()[i][j] instanceof ConditionViolet  && monNiveau.getTerrain().getEnsembleDeCase()[i][j].getColor() == Couleur.Violet)
							SpriteCases[i][j][k].setTexture(Textures.TexCaseViolet);
						else if(monNiveau.getTerrain().getEnsembleDeCase()[i][j] instanceof ConditionRose  && monNiveau.getTerrain().getEnsembleDeCase()[i][j].getColor() == Couleur.Rose)
							SpriteCases[i][j][k].setTexture(Textures.TexCaseRose);
						else if(monNiveau.getTerrain().getEnsembleDeCase()[i][j] instanceof Pointeur  && monNiveau.getTerrain().getEnsembleDeCase()[i][j].getColor() == Couleur.Vert && ((Pointeur) monNiveau.getTerrain().getEnsembleDeCase()[i][j]).estPointee())
							SpriteCases[i][j][k].setTexture(Textures.TexCasePointee);
						else if(monNiveau.getTerrain().getEnsembleDeCase()[i][j] instanceof Pointeur  && monNiveau.getTerrain().getEnsembleDeCase()[i][j].getColor() == Couleur.Vert && !((Pointeur) monNiveau.getTerrain().getEnsembleDeCase()[i][j]).estPointee())
							SpriteCases[i][j][k].setTexture(Textures.TexCasePointeur);
					}

					SpriteCases[i][j][k].setScale(reScale,reScale);

				}
	}

	/**
	 * Constructeur de la classe qui charge les niveaux 
	 * @param Scale
	 * @param niveauPourParser
	 */
	public void init_niveau(float Scale)
	{
		NB_CASE_X = monNiveau.getTerrain().getLargeur();
		NB_CASE_Y =  monNiveau.getTerrain().getLongueur();
		NB_CASE_Z =  monNiveau.getTerrain().getHauteurMax()+1;
		
		
		reScale=Scale;
		reScaleRobot=Scale/3;
		SetSprites();
		this.gradient = this.createGradient(new org.jsfml.graphics.Color(0, 178, 255, 255), Color.BLACK, Menu_principal.fenetre);
		initPlaceRobot(monNiveau.getPersonnages().get(0).getPositionX(),monNiveau.getPersonnages().get(0).getPositionY());
	}
	
	
	/**
	 * Permet d'initialiser l'emplacement du robot
	 * @param x	Les coordonnées du robot en x 
	 * @param y Les coordonnées du robot en y
	 */
	public void initPlaceRobot(int x,int y)
	{
		xRobot=x;
		yRobot=y;
		nextXRobot=x;
		nextYRobot=y;
	}
	
	
	public boolean action_deja_presente(StructStringSprite compare)
	{
		StructStringSprite temp = null;
		for (int i = 0; i < this.list_action_possible.size(); i++) 
		{
			temp=(StructStringSprite) this.list_action_possible.get(i);
			if (temp.nom.equals(compare.nom))
			{
				return true;
			}	
		}
		return false;
	}
	
	public void initActionsPossible()
	{
		this.list_action_possible = new LinkedList();
		ArrayList al = monNiveau.getActions() ;
		
		for (int i = 0; i < al.size(); i++) {
			
			StructStringSprite struct = null;
			if (al.get(i) instanceof Avancer) {
				struct = this.new StructStringSprite(this.spriteSymboleAvancer, "avancer");
			}
			else if(al.get(i) instanceof Allumer) {
				struct = this.new StructStringSprite(this.spriteSymboleAllumer, "allumer");
			}
			else if(al.get(i) instanceof Break) {
				struct = this.new StructStringSprite(this.spriteSymboleBreak, "break");	
			}
			else if(al.get(i) instanceof Sauter) {
				struct = this.new StructStringSprite(this.spriteSymboleSauter, "sauter");
			}
			else if(al.get(i) instanceof TournerDroite) {
				struct = this.new StructStringSprite(this.spriteSymboleTournerDroite, "droite");
			}
			else if(al.get(i) instanceof TournerGauche) {
				struct = this.new StructStringSprite(this.spriteSymboleTournerGauche, "gauche");
			}
			else if(al.get(i) instanceof Wash) {
				System.out.println("Wash");
			}
			
			if(!action_deja_presente(struct))
				this.list_action_possible.add(struct);
		}
		
		if(this.monNiveau.getProgrammes().size()>=2)
		{
			StructStringSprite struct = null;
			struct = this.new StructStringSprite(this.spriteP1, "P1");
			this.list_action_possible.add(struct);
		}
		if(this.monNiveau.getProgrammes().size()>=3)
		{
			StructStringSprite struct = null;
			struct = this.new StructStringSprite(this.spriteP2, "P2");
			this.list_action_possible.add(struct);
		}
		
	}

	/**
	 * Permet d'initialiser toutes les procédures (dont le main)
	 */
	public void initProcedures(){
		this.tabProgramme = new List[this.monNiveau.getProgrammes().size()];
		for (int i = 0; i < this.tabProgramme.length; i++) {
			tabProgramme[i] = new LinkedList();
		}
	}
	
	public void afficher_boutons(){
		if (!this.list_action_possible.isEmpty()) {
			for (int k = 0; k < this.list_action_possible.size(); k++) {
				StructStringSprite temp = (StructStringSprite) this.list_action_possible.get(k);
				temp.sprite.setPosition(450+k*65, 50);
				Menu_principal.fenetre.draw(temp.sprite);
			}
		}
		Menu_principal.fenetre.draw(this.spriteBoutonPlay);
	}
	
	public void afficher_procedure(){
		for(int i=0;i< this.monNiveau.getProgrammes().size(); i++ )
		{
			if (!this.tabProgramme[i].isEmpty()) {
				int cpty=0;
				
				for (int k = 0; k < this.tabProgramme[i].size(); k++) {
					
					if(k%5==0 && k!=0)
					{
						cpty++;
					}
						
					StructStringSprite temp = (StructStringSprite) this.tabProgramme[i].get(k);
					temp.sprite.setPosition(spritesProcedures[i].getPosition().x+5+(k%5)*65
										   ,spritesProcedures[i].getPosition().y+5+cpty*65);
					Menu_principal.fenetre.draw(temp.sprite);
				}
			}
		}
	}
	
	/**
	 * Permet d'executer les actions contenues dans le main 
	 */
	public void jouer_main(){
		if (!this.tabProgramme[0].isEmpty()) {
			for (int k = 0; k < this.monNiveau.getProgrammes().get(this.progSelect).getActions().size(); k++) {
				
				Object temp = this.monNiveau.getProgrammes().get(this.progSelect).getActions().get(k);
				
				if (temp instanceof Avancer) {
					this.avancer();
				}
				else if (temp instanceof TournerDroite) {
					this.tourner_droite();
				}
				else if (temp instanceof TournerGauche) {
					this.tourner_gauche();
				}
				else if (temp instanceof Sauter) {
					this.sauter();
				}
				/*else if (temp instanceof Allumer) {
					this.deplacement_robot(4);
				}*/
			}
		}
	}
	
	/**
	 * Permet de créer un fond dégradé
	 * @param color1	La couleur du haut de la fenêtre 
	 * @param color2	La couleur du bas de la fenêtre
	 * @param window	La fenêtre à laquelle on applique un gradient (pour avoir ses dimensions)
	 * @return	Un VertexArray comprenant le dégradé
	 */
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
	
	/**
	 * Structure comprenant une Sprite et une chaine indiquant de quel type de sprite il s'agit
	 * Utilisé pour les actions
	 * @author quentin
	 *
	 */
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
	
	public void inserer_actions(StructStringSprite struct){
		switch (struct.nom) {
		case "avancer":
			this.monNiveau.getProgrammes().get(this.progSelect).insererQueue(new Avancer(this.monNiveau.getPersonnageByName("Robot")));
			break;
		case "gauche":
			this.monNiveau.getProgrammes().get(this.progSelect).insererQueue(new TournerGauche(this.monNiveau.getPersonnageByName("Robot")));
			break;
		case "droite":
			this.monNiveau.getProgrammes().get(this.progSelect).insererQueue(new TournerDroite(this.monNiveau.getPersonnageByName("Robot")));
			break;
		case "sauter":
			this.monNiveau.getProgrammes().get(this.progSelect).insererQueue(new Sauter(this.monNiveau.getPersonnageByName("Robot")));
			break;
		case "allumer":
			this.monNiveau.getProgrammes().get(this.progSelect).insererQueue(new Allumer(this.monNiveau.getPersonnageByName("Robot")));
			break;
		default:
			break;
		}
	}
	
	public void afficher_niveau(Niveau niveauCharger)
	{
		this.progSelect=0;
		int i=0,j=0;
		//Initialisation des textures
		Textures.initTextures();
		monNiveau=new Niveau();
		monNiveau=niveauCharger;
		init_niveau(1.0f);
		set_position_cases();
		set_pos_robot();
		
		
		initActionsPossible();
		StructStringSprite temp= (StructStringSprite)this.list_action_possible.getLast();
		afficher_boutons();
		spriteBoutonPlay.setPosition(temp.sprite.getPosition().x + spriteBoutonPlay.getTexture().getSize().x * 3,temp.sprite.getPosition().y);
	
		
	
		initProcedures();
		afficher_carte();
		
		boolean sortie=true;
		while (Menu_principal.fenetre.isOpen() && sortie ) 
		{
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for (Event event : Menu_principal.fenetre.pollEvents()) {					
					if (event.type == Event.Type.MOUSE_BUTTON_PRESSED) { 
						Vector2i pos = Mouse.getPosition(Menu_principal.fenetre); 
						
						//Si clique droit sur un élément du main, on le supprime
						if (!tabProgramme[this.progSelect].isEmpty()) {
							for (int k = 0; k < tabProgramme[this.progSelect].size(); k++) {
								temp = (StructStringSprite) tabProgramme[this.progSelect].get(k);
								if(temp.sprite.getGlobalBounds().contains(pos.x,pos.y) && event.asMouseButtonEvent().button == Button.RIGHT)
								{
									tabProgramme[this.progSelect].remove(k);
									this.monNiveau.getProgrammes().get(this.progSelect).supprimer(k);
								}
							}
						}
						
						//Ajout des éléments qu'on a cliqué dans le main
						if (!list_action_possible.isEmpty()) {
							for (int k = 0; k < list_action_possible.size(); k++) {
								temp = (StructStringSprite) list_action_possible.get(k);
								if(temp.sprite.getGlobalBounds().contains(pos.x,pos.y))
								{
									StructStringSprite struct = new StructStringSprite(temp);
									//monNiveau.getProgrammes().add(struct);
									if(this.tabProgramme[this.progSelect].size() < this.monNiveau.getProgrammes().get(this.progSelect).getNbMaxAction()){
										inserer_actions(struct);
										tabProgramme[this.progSelect].add(struct);
									}
								}
							}
						}
						
						if(spriteBoutonPlay.getGlobalBounds().contains(pos.x,pos.y))
						{
							jouer_main();
						}
						else
							sprite_selectionne(pos);
					}
	
					if (event.type == Event.Type.CLOSED) {
						Menu_principal.fenetre.close();
					}
					 else if (event.type == Event.Type.KEY_PRESSED) {
					 if (Keyboard.isKeyPressed(Key.ESCAPE))
						{
						 	//this.playMusic("Zarnakand.ogg");		//Relancement de la musique de menu.
							sortie=false;
						}	
						if (Keyboard.isKeyPressed(Key.UP)) {
							deplacement_robot(0);
							System.out.println(" UP");
						}
						else if (Keyboard.isKeyPressed(Key.DOWN)) {
							deplacement_robot(1);
							System.out.println(" DOWN");
						}
						else if (Keyboard.isKeyPressed(Key.LEFT)) {
							deplacement_robot(2);
							System.out.println(" LEFT");
						}
						else if (Keyboard.isKeyPressed(Key.RIGHT)) {
							deplacement_robot(3);
							System.out.println(" RIGHT");
						}	
						else if (Keyboard.isKeyPressed(Key.SPACE)) {
							sauter();
							System.out.println("Sauter");
						}
					}
					afficher_carte();
				}
			}	
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
}
