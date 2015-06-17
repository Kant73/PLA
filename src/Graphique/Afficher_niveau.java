package Graphique;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Keyboard.Key;
import org.jsfml.window.Mouse;
import org.jsfml.window.Mouse.Button;
import org.jsfml.window.event.Event;

import LightBot.Mode_Jeu;
import LightBot.Niveau;
import LightBot.Ordonnanceur;
import LightBot.actions.Allumer;
import LightBot.actions.Avancer;
import LightBot.actions.Break;
import LightBot.actions.CompareFront;
import LightBot.actions.PoserBloc;
import LightBot.actions.RetirerBloc;
import LightBot.actions.Sauter;
import LightBot.actions.Swap;
import LightBot.actions.TournerDroite;
import LightBot.actions.TournerGauche;
import LightBot.actions.Wash;
import LightBot.cases.Clonage;
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
	static Color couleurRose;
	static Color couleurViolet;
	static Color couleurUtilisee;

	public Sprite[][][] SpriteCases;
	public Sprite [] spritesProcedures;
	public Sprite [][] spriteAnim;
	
	public Sprite spritePeinture ; 
	public Sprite spriteBoutonPlay;
	public Sprite spriteBoutonReset;
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
	public Sprite spriteSymbolePoser;
	public Sprite spriteSymboleSuppr;
	public int[] ancX,ancY;
	public int[] numAnim;
	public int indexRobot;
	
	public LinkedList list_action_possible;
	public List[] tabProgramme;	//Tableau de liste de sprite (qui représente les actions du main et des proc)
	public float reScale,reScaleRobot;
	int progSelect;
		
	private void initialiser_anim()
	{
		spriteAnim = new Sprite[4][26];
		int y0=0,tailleX=128,tailleY=118;
		
		for(int i=0;i<spriteAnim.length;i++)
		{
			y0=0;
			for(int j = 0; j <spriteAnim[i].length;j++)
			{
				spriteAnim[i][j]=new Sprite();
				spriteAnim[i][j].setTexture(Textures.mesAnims[i]);
				
				spriteAnim[i][j].setTextureRect(new IntRect((j%6)*tailleX, y0*tailleY, tailleX, tailleY));
				if(j%6==0 && j!=0)
					y0++;
				//spriteAnim[i][j].setScale(reScaleRobot,reScaleRobot);
			}
		}	
		
	}
	
	
	/**
	 * Permet de determiner la position des cases
	 */
	public void set_position_cases()
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

				for(int etageActuel = 1; etageActuel <= monNiveau.getTerrain().getEnsembleDeCase()[i][j].getHauteurGraphique();etageActuel ++)
				{
						SpriteCases[i][j][etageActuel].setPosition(SpriteCases[i][j][etageActuel-1].getPosition().x ,SpriteCases[i][j][etageActuel-1].getPosition().y- SpriteCases[i][j][etageActuel-1].getTexture().getSize().y/divY);
				}
			}		
		}	
	}
		
	
	/**
	 * Permet d'afficher les cadres pour les procédures (dont le main)
	 */
	public void afficher_cadre_procedures()
	{
		for (int i=0; i< this.monNiveau.getProgrammes().size(); i++)
			Menu_principal.fenetre.draw(spritesProcedures[i]);
	}
	
	
	/**
	 * Permet d'afficher notre terrain ainsi que le fond dégradé, les boutons d'action dispo pour un niveau et les actions qui sont dans les procédures (dont le main)
	 */
	public void afficher_carte()
	{
		fenetre.draw(Menu_modes.spritefondMode);
		afficher_cadre_procedures();
		this.afficher_boutons();
		this.afficher_procedure();
		this.afficher_verrou();
		for(int i= NB_CASE_X-1;i>=0;i--)
		{
			for(int j= NB_CASE_Y-1;j>=0;j--)
			{
				for(int etageActuel = 0; etageActuel <=monNiveau.getTerrain().getEnsembleDeCase()[i][j].getHauteurGraphique();etageActuel ++)
				{	
					Menu_principal.fenetre.draw(SpriteCases[i][j][etageActuel]);

					for(int l = 0; l<monNiveau.getPersonnages().size();l++)
						if((i==monNiveau.getPersonnages().get(l).getPositionX() && j == monNiveau.getPersonnages().get(l).getPositionY() || i==ancX[l] && j == ancY[l] ) 
								&& etageActuel == monNiveau.getTerrain().getEnsembleDeCase()[i][j].getHauteurGraphique())
						{
							spriteAnim[monNiveau.getPersonnages().get(l).getOrientationInt()][numAnim[l]].setPosition(spriteAnim[monNiveau.getPersonnages().get(l).getOrientationInt()][0].getPosition());
							Menu_principal.fenetre.draw(spriteAnim[monNiveau.getPersonnages().get(l).getOrientationInt()][numAnim[l]]);
						}
				}
			}
		}
		Menu_principal.fenetre.display();
	}
	
	
	/**
	 * Permet de changer la position du robot
	 */
	public void set_pos_robot()
	{
		int i,j;
		for(int l = 0; l<monNiveau.getPersonnages().size();l++)
		{
			 i=monNiveau.getPersonnages().get(l).getPositionX();
			 j=monNiveau.getPersonnages().get(l).getPositionY();
			
			spriteAnim[monNiveau.getPersonnages().get(l).getOrientationInt()][0].setPosition(SpriteCases[i][j][monNiveau.getTerrain().getEnsembleDeCase()[i][j].getHauteurGraphique()].getPosition().x 
						+ reScale*SpriteCases[i][j][monNiveau.getTerrain().getEnsembleDeCase()[i][j].getHauteurGraphique()].getTexture().getSize().x/2
						- reScaleRobot*spriteAnim[monNiveau.getPersonnages().get(l).getOrientationInt()][0].getLocalBounds().width/2
						,SpriteCases[i][j][monNiveau.getTerrain().getEnsembleDeCase()[i][j].getHauteurGraphique()].getPosition().y 
						+reScale*SpriteCases[i][j][monNiveau.getTerrain().getEnsembleDeCase()[i][j].getHauteurGraphique()].getTexture().getSize().y/3 
						- reScaleRobot*spriteAnim[monNiveau.getPersonnages().get(l).getOrientationInt()][0].getLocalBounds().height
						+ 10);	
		}		
	}
	
	
	public void avancer(int index)
	{
		int lastX=ancX[index];
		int lastY=ancY[index];
		int newX=monNiveau.getPersonnages().get(index).getPositionX(),newY=monNiveau.getPersonnages().get(index).getPositionY();
		float deplX=0,deplY = 0;
		float coeff = 2;
		
		switch (monNiveau.getPersonnages().get(index).getOrientation())
		{
		case  SOUTH:
				deplX=-1;
				deplY=-0.5f;
			break;
		case  NORTH:
				deplX=1;
				deplY=0.5f;
			break;
		case WEST :	
				deplX=-1;
				deplY=0.5f;
			break;
		case EAST :
				deplX=1;
				deplY=-0.5f;
			break;
		}
		deplX=deplX*coeff;
		deplY=deplY*coeff;
		if(lastY!=newY || lastX != newX)
		{
			Vector2f posFinale,posInit;
		
			posFinale = new Vector2f(SpriteCases[newX][newY][monNiveau.getTerrain().getEnsembleDeCase()[newX][newY].getHauteurGraphique()].getPosition().x 
					+ reScale*SpriteCases[newX][newY][monNiveau.getTerrain().getEnsembleDeCase()[newX][newY].getHauteurGraphique()].getTexture().getSize().x/2
					- reScaleRobot*spriteAnim[monNiveau.getPersonnages().get(index).getOrientationInt()][0].getLocalBounds().width/2, 
				SpriteCases[newX][newY][monNiveau.getTerrain().getEnsembleDeCase()[newX][newY].getHauteurGraphique()].getPosition().y 
				+reScale*SpriteCases[newX][newY][monNiveau.getTerrain().getEnsembleDeCase()[newX][newY].getHauteurGraphique()].getTexture().getSize().y/3 
				- reScaleRobot*spriteAnim[monNiveau.getPersonnages().get(index).getOrientationInt()][0].getLocalBounds().height);
			
			posInit = new Vector2f(spriteAnim[monNiveau.getPersonnages().get(index).getOrientationInt()][0].getPosition().x
					,spriteAnim[monNiveau.getPersonnages().get(index).getOrientationInt()][0].getPosition().y);
		
			while( (posInit.x>=posFinale.x && posInit.y>=posFinale.y 
					  && spriteAnim[monNiveau.getPersonnages().get(index).getOrientationInt()][0].getPosition().x >= posFinale.x 
					  && spriteAnim[monNiveau.getPersonnages().get(index).getOrientationInt()][0].getPosition().y >= posFinale.y)
					  	  || 
				  (posInit.x<=posFinale.x && posInit.y>=posFinale.y 
						  && spriteAnim[monNiveau.getPersonnages().get(index).getOrientationInt()][0].getPosition().x <= posFinale.x 
						  && spriteAnim[monNiveau.getPersonnages().get(index).getOrientationInt()][0].getPosition().y >= posFinale.y)
						  || 
				  (posInit.x>=posFinale.x && posInit.y<=posFinale.y 
						  && spriteAnim[monNiveau.getPersonnages().get(index).getOrientationInt()][0].getPosition().x >= posFinale.x 
						  && spriteAnim[monNiveau.getPersonnages().get(index).getOrientationInt()][0].getPosition().y <= posFinale.y)
						  || 
				  (posInit.x<=posFinale.x && posInit.y<=posFinale.y 
						  && spriteAnim[monNiveau.getPersonnages().get(index).getOrientationInt()][0].getPosition().x <= posFinale.x 
						  && spriteAnim[monNiveau.getPersonnages().get(index).getOrientationInt()][0].getPosition().y <= posFinale.y)						  
				  )
			{
				numAnim[index]++;
				if(numAnim[index]>=spriteAnim[monNiveau.getPersonnages().get(index).getOrientationInt()].length)
					numAnim[index]=0;
					
				spriteAnim[monNiveau.getPersonnages().get(index).getOrientationInt()][0].setPosition(spriteAnim[monNiveau.getPersonnages().get(index).getOrientationInt()][0].getPosition().x+deplX,spriteAnim[monNiveau.getPersonnages().get(index).getOrientationInt()][0].getPosition().y+deplY);
				afficher_carte();
			}
			set_pos_robot();
			afficher_carte();
		}
	}
	
	
	public void sauter(int index)
	{
		int lastX=ancX[index];
		int lastY=ancY[index];
		int newX=monNiveau.getPersonnages().get(index).getPositionX(),newY=monNiveau.getPersonnages().get(index).getPositionY();
		float deplSaut=0;
		float coeff = 2;
		Vector2f posInit;
		
		posInit = new Vector2f(spriteAnim[monNiveau.getPersonnages().get(index).getOrientationInt()][0].getPosition().x,spriteAnim[monNiveau.getPersonnages().get(index).getOrientationInt()][0].getPosition().y);
		
		if(monNiveau.getTerrain().getEnsembleDeCase()[lastX][lastY].getHauteurGraphique()!=monNiveau.getTerrain().getEnsembleDeCase()[newX][newY].getHauteurGraphique())
		{	
			deplSaut=-3*coeff;
			
			boolean b =  newX>lastX || newY>lastY ;

			while ((b && spriteAnim[monNiveau.getPersonnages().get(index).getOrientationInt()][0].getPosition().y 
							+ reScaleRobot*spriteAnim[monNiveau.getPersonnages().get(index).getOrientationInt()][0].getLocalBounds().height
							> SpriteCases[newX][newY][monNiveau.getTerrain().getEnsembleDeCase()[newX][newY].getHauteurGraphique()].getPosition().y
							+reScale*SpriteCases[newX][newY][monNiveau.getTerrain().getEnsembleDeCase()[newX][newY].getHauteurGraphique()].getTexture().getSize().y/3*2
							+10)
							
				||(!b  && monNiveau.getTerrain().getEnsembleDeCase()[newX][newY].getHauteurGraphique()>monNiveau.getTerrain().getEnsembleDeCase()[lastX][lastY].getHauteurGraphique() 
							&& spriteAnim[monNiveau.getPersonnages().get(index).getOrientationInt()][0].getPosition().y 
							+ reScaleRobot*spriteAnim[monNiveau.getPersonnages().get(index).getOrientationInt()][0].getLocalBounds().height 
							> posInit.y+reScaleRobot*spriteAnim[monNiveau.getPersonnages().get(index).getOrientationInt()][0].getLocalBounds().height 
							-reScale*SpriteCases[newX][newY][monNiveau.getTerrain().getEnsembleDeCase()[newX][newY].getHauteurGraphique()].getTexture().getSize().y/3*(monNiveau.getTerrain().getEnsembleDeCase()[newX][newY].getHauteurGraphique()
							-monNiveau.getTerrain().getEnsembleDeCase()[lastX][lastY].getHauteurGraphique())
							+10)
					)
			{
				spriteAnim[monNiveau.getPersonnages().get(index).getOrientationInt()][0].setPosition(spriteAnim[monNiveau.getPersonnages().get(index).getOrientationInt()][0].getPosition().x,spriteAnim[monNiveau.getPersonnages().get(index).getOrientationInt()][0].getPosition().y+deplSaut);
				afficher_carte();	
			}
		}
		this.avancer(index);
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
		int ecart=70;
		
		spritesProcedures = new Sprite[3];
		for (int i=0;i<spritesProcedures.length;i++)
		{
			spritesProcedures[i]=new Sprite();
			spritesProcedures[i].setTexture(Textures.texProcs[i]);	
		}
		
		spritesProcedures[0].setPosition(25, ecart);
		spritesProcedures[1].setPosition(25, spritesProcedures[0].getPosition().y + spritesProcedures[0].getTexture().getSize().y + ecart );
		spritesProcedures[2].setPosition(25, spritesProcedures[1].getPosition().y + spritesProcedures[1].getTexture().getSize().y + ecart );
		spritesProcedures[0].setTexture(Textures.texProcs[3]);
		
		spritePeinture = new Sprite();
		spritePeinture.setTexture(Textures.texPeinture);
		
		spriteBoutonPlay=new Sprite();
		spriteBoutonPlay.setTexture(Textures.TexBoutonPlay);
		
		spriteBoutonReset = new Sprite();
		spriteBoutonReset.setTexture(Textures.texBoutonReset);
			
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
		
		spriteSymbolePoser=new Sprite();
		spriteSymbolePoser.setTexture(Textures.TexSymbolePoser);
		
		spriteSymboleSuppr=new Sprite();
		spriteSymboleSuppr.setTexture(Textures.TexSymboleSuppr);
		
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
				}
		set_textures_cases();
		
	}
	
	public void set_textures_cases()
	{
		for(int i=0;i<  NB_CASE_X;i++)
			for(int j=0;j<  NB_CASE_Y;j++)
				for(int k=0;k<  NB_CASE_Z;k++)
				{
					
					SpriteCases[i][j][k].setTexture(Textures.TexCaseBase);
					
					if(k==monNiveau.getTerrain().getEnsembleDeCase()[i][j].getHauteurGraphique())
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
						else if(monNiveau.getTerrain().getEnsembleDeCase()[i][j] instanceof Clonage  && monNiveau.getTerrain().getEnsembleDeCase()[i][j].getColor() == Couleur.Orange)
							SpriteCases[i][j][k].setTexture(Textures.TexCaseClonage);
						
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

		this.ancX=new int[monNiveau.getPersonnages().size()];
		this.ancY=new int[monNiveau.getPersonnages().size()];
		this.numAnim=new int [monNiveau.getPersonnages().size()];
		
		for(int l = 0; l<monNiveau.getPersonnages().size();l++)
		{
			ancX[l]=monNiveau.getPersonnages().get(l).getPositionX();
			ancY[l]=monNiveau.getPersonnages().get(l).getPositionY();
			this.numAnim[l]=0;
		}
		
		
		reScale=Scale;
		reScaleRobot=Scale;
		SetSprites();
	}

	
	/**
	 * Permet de ne pas avoir de doublon dans la liste des actions possible pour un niveau
	 * @param compare
	 * @return true si l'action est d�j� pr�sente, false sinon
	 */
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
	
	/**
	 * Initialise la liste des sprites avec uniquement les actions possible pour un niveau donn�
	 */
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
			else if(al.get(i) instanceof PoserBloc) {
				struct = this.new StructStringSprite(this.spriteSymbolePoser, "poser");
			}
			else if(al.get(i) instanceof RetirerBloc) {
				struct = this.new StructStringSprite(this.spriteSymboleSuppr, "suppr");
			}
			else if(al.get(i) instanceof CompareFront) {
				struct = this.new StructStringSprite(this.spriteSymboleSuppr, "comp");
			}
			else if(al.get(i) instanceof Swap) {
				struct = this.new StructStringSprite(this.spriteSymboleSuppr, "swap");
			}
			else if(al.get(i) instanceof Wash) {
				struct = this.new StructStringSprite(this.spriteSymboleSuppr, "wash");
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
		Menu_principal.fenetre.draw(Menu_principal.spriteRetour);
		Menu_principal.fenetre.draw(this.spriteBoutonPlay);
		Menu_principal.fenetre.draw(this.spriteBoutonReset);
		Menu_principal.fenetre.draw(this.spritePeinture);
		
		
	}
	
	
	/**
	 * Permet d'afficher des symboles verrou là afin de limiter le nombre d'action 
	 * possible par l'utilisteur pour chaque procédure
	 */
	public void afficher_verrou(){
		
		int nb_ligne=0;
		switch (this.monNiveau.getProgrammes().size()) {
			case 3:
				nb_ligne=0;
				for (int l = 10; l > this.monNiveau.getProgrammes().get(2).getNbMaxAction(); l--) {
					
					if(l%5==0 && l!=0 && l!=10)
					{
						nb_ligne++;
					}
					Sprite temp3 = new Sprite(Textures.TexVerrou);
					//temp2.setPosition(290-5-(j%5)*65, 400-5-nb_ligne*65);
					temp3.setPosition(spritesProcedures[2].getPosition().x+265-(((20-l)%5)*65), spritesProcedures[2].getPosition().y + 70-(nb_ligne*65));
					Menu_principal.fenetre.draw(temp3);
				}		
				
			case 2:
				nb_ligne=0;
				for (int k = 10; k > this.monNiveau.getProgrammes().get(1).getNbMaxAction(); k--) {
					
					if(k%5==0 && k!=0 && k!=10)
					{
						nb_ligne++;
					}
					Sprite temp4 = new Sprite(Textures.TexVerrou);
					//temp2.setPosition(290-5-(j%5)*65, 400-5-nb_ligne*65);
					temp4.setPosition(spritesProcedures[1].getPosition().x+265-(((20-k)%5)*65), spritesProcedures[1].getPosition().y + 70-(nb_ligne*65));
					Menu_principal.fenetre.draw(temp4);				
				}
	
			default:
				nb_ligne = 0;
				for (int j = 20; j > this.monNiveau.getProgrammes().get(0).getNbMaxAction(); j--) {
					if(j%5==0 && j!=0 && j!=20)
					{
						nb_ligne++;
					}
					Sprite temp2 = new Sprite(Textures.TexVerrou);
					//temp2.setPosition(290-5-(j%5)*65, 400-5-nb_ligne*65);
					temp2.setPosition(spritesProcedures[0].getPosition().x+265-(((20-j)%5)*65), spritesProcedures[0].getPosition().y+200-(nb_ligne*65));
					Menu_principal.fenetre.draw(temp2);
				}
				break;
		}
		
	}
	
	
	/**
	 * Permet d'afficher les proc�dures (le main et les deux autres sous proc�dures)
	 */
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
	
	private Couleur couleur_graphique_vers_couleur_case(Color coul)
	{
		if(couleurUtilisee == Color.WHITE)
		{
			return Couleur.Blanc;
		}
		else if(couleurUtilisee == couleurRose )
		{
			return Couleur.Rose;
		}
		else if(couleurUtilisee == couleurViolet)
		{
			return Couleur.Violet;
		}
		System.out.println("Erreur de couleur");
		return null;
	}
	
	/**
	 * Permet d'inserer une action dans la liste des programmes par rapport à la liste des sprites
	 * @param struct
	 */
	public void inserer_actions(StructStringSprite struct){
		switch (struct.nom) {
		case "avancer":
			this.monNiveau.getProgrammes().get(this.progSelect).insererQueue(new Avancer(this.monNiveau.getPersonnages().get(indexRobot), this.couleur_graphique_vers_couleur_case(struct.sprite.getColor())   ));
			break;
		case "gauche":
			this.monNiveau.getProgrammes().get(this.progSelect).insererQueue(new TournerGauche(this.monNiveau.getPersonnages().get(indexRobot), this.couleur_graphique_vers_couleur_case(struct.sprite.getColor()) ));
			break;
		case "droite":
			this.monNiveau.getProgrammes().get(this.progSelect).insererQueue(new TournerDroite(this.monNiveau.getPersonnages().get(indexRobot), this.couleur_graphique_vers_couleur_case(struct.sprite.getColor()) ));
			break;
		case "sauter":
			this.monNiveau.getProgrammes().get(this.progSelect).insererQueue(new Sauter(this.monNiveau.getPersonnages().get(indexRobot), this.couleur_graphique_vers_couleur_case(struct.sprite.getColor()) ));
			break;
		case "allumer":
			this.monNiveau.getProgrammes().get(this.progSelect).insererQueue(new Allumer(this.monNiveau.getPersonnages().get(indexRobot), this.couleur_graphique_vers_couleur_case(struct.sprite.getColor()) ));
			break;
		case "poser":
			this.monNiveau.getProgrammes().get(this.progSelect).insererQueue(new PoserBloc(this.monNiveau.getPersonnages().get(indexRobot), this.couleur_graphique_vers_couleur_case(struct.sprite.getColor()) ));
			break;
		case "suppr":
			this.monNiveau.getProgrammes().get(this.progSelect).insererQueue(new RetirerBloc(this.monNiveau.getPersonnages().get(indexRobot), this.couleur_graphique_vers_couleur_case(struct.sprite.getColor()) ));
			break;
		case "P1":
			this.monNiveau.getProgrammes().get(this.progSelect).insererQueue(this.monNiveau.getProgrammes().get(1));
			break;
			case "P2":
			this.monNiveau.getProgrammes().get(this.progSelect).insererQueue(this.monNiveau.getProgrammes().get(2));
			break;
		default:
			break;
		}
	}
	
	
	void reset_niveau(Mode_Jeu mj, int selection)
	{
		StructStringSprite temp;
		monNiveau=mj.getNiveau(selection);
		
		int save_select = this.progSelect;
		/*On supprime toute les actions qui existaient au cas où ?*/
		for(int l=0;l<monNiveau.getProgrammes().size();l++)
		{
			for(int k=monNiveau.getProgrammes().get(l).getNbElements();k>0;k--)
			{
				monNiveau.getProgrammes().get(l).supprimer(k);
			}
		}
		/*On réinsère les actions*/
		for(int l=0;l<tabProgramme.length;l++)
		{
			this.progSelect=l;
			if (!tabProgramme[this.progSelect].isEmpty()) {
				for (int k = 0; k < tabProgramme[this.progSelect].size(); k++) {
					temp = (StructStringSprite) tabProgramme[this.progSelect].get(k);
					StructStringSprite struct = new StructStringSprite(temp);
					inserer_actions(struct);	
				}
			}
		}
		this.progSelect = save_select;	
		this.set_textures_cases();
		monNiveau.getPersonnages().get(0).setCouleur(Couleur.Blanc);
		set_pos_robot();
	}
	/**
	 * Méthode principale de la classe qui permet d'afficher tout un niveau avec les actions et procédures associée
	 * @param niveauCharger Le niveau que l'on veut afficher
	 */
	public void afficher_niveau(Niveau niveauCharger,Mode_Jeu mj, int selection)
	{
		this.progSelect=0;
		this.indexRobot=0;
		couleurUtilisee = Color.WHITE;
		couleurRose = new Color(226,102,180);
		couleurViolet = new Color(187,137,193);

		int i=0,j=0;
		boolean unSeulPlay=true;
		//Initialisation des textures
		Textures.initTextures();
		initialiser_anim();
		monNiveau=niveauCharger;
		init_niveau(1.0f);
		set_position_cases();
		set_pos_robot();
		
		
		initActionsPossible();
		StructStringSprite temp= (StructStringSprite)this.list_action_possible.getLast();
		afficher_boutons();
		
		spritePeinture.setPosition(temp.sprite.getPosition().x + spritePeinture.getTexture().getSize().x * 3+ 5,temp.sprite.getPosition().y);
		spriteBoutonPlay.setPosition(spritePeinture.getPosition().x + spriteBoutonPlay.getTexture().getSize().x +5 ,temp.sprite.getPosition().y);
		spriteBoutonReset.setPosition(spriteBoutonPlay.getPosition().x + spriteBoutonReset.getTexture().getSize().x + 5,spriteBoutonPlay.getPosition().y);
		
	
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
						
						if(spriteBoutonPlay.getGlobalBounds().contains(pos.x,pos.y) && unSeulPlay)
						{
							for(int l = 0; l<monNiveau.getPersonnages().size();l++)	
								monNiveau.getPersonnages().get(l).setProgramme(monNiveau.getProgrammes().get(l));
								
							Ordonnanceur monOrdonnanceur = new Ordonnanceur (monNiveau.getPersonnages(),this);
							monOrdonnanceur.run();
	
							if (!tabProgramme[0].isEmpty())
								unSeulPlay=false;
							if(monNiveau.getTerrain().getMaxLampe() == 	monNiveau.getTerrain().getNbLampeAllumee())
								sortie=false;
						}
						else if(this.spritesProcedures[this.progSelect].getGlobalBounds().contains(pos.x,pos.y) )
						{
							//Si clique droit sur un élément du main, on le supprime
							if (!tabProgramme[this.progSelect].isEmpty()) {
								for (int k = 0; k < tabProgramme[this.progSelect].size(); k++) {
									temp = (StructStringSprite) tabProgramme[this.progSelect].get(k);
									if(temp.sprite.getGlobalBounds().contains(pos.x,pos.y))
									{
										temp = (StructStringSprite) tabProgramme[this.progSelect].get(k);	
										temp.sprite.setColor(couleurUtilisee);
									}
								}
							}
							
						}
						else if(this.spritePeinture.getGlobalBounds().contains(pos.x,pos.y))
						{
							if(couleurUtilisee == Color.WHITE)
							{
								couleurUtilisee = couleurRose;
							}
							else if(couleurUtilisee == couleurRose )
							{
								couleurUtilisee = couleurViolet ;
							}
							else if(couleurUtilisee == couleurViolet)
							{
								couleurUtilisee = Color.WHITE;
							}
							spritePeinture.setColor(couleurUtilisee);
						}
						else
							sprite_selectionne(pos);
						
						if(Menu_principal.spriteRetour.getGlobalBounds().contains(pos.x,pos.y))
						{
							sortie=false;
						}
						if(spriteBoutonReset.getGlobalBounds().contains(pos.x,pos.y))
						{
							
							reset_niveau(mj,selection);
							unSeulPlay=true;
						}
						afficher_carte();
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
					}
				}
			}
		if(selection<mj.getNbNiveaux()-1 && monNiveau.getTerrain().getMaxLampe() == 	monNiveau.getTerrain().getNbLampeAllumee())
		{
			Afficher_niveau level = new  Afficher_niveau();
			Niveau copie = mj.getNiveau(selection+1);
			level.afficher_niveau(copie, mj,selection+1);
		}
	}
}