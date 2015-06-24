package LightBot.Graphique;


import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Text;
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
import LightBot.actions.AllumerBattle;
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
import LightBot.cases.Couleur;
import LightBot.cases.Lampe;
import LightBot.cases.Pointeur;
import LightBot.cases.Transparente;
import LightBot.outils.Utils;

public class Afficher_niveau extends Menu_niveaux{
	 
	static Niveau monNiveau;
	
	private Font police;
	private Text texteNbCase;
	private Text texteWin;
	
	static int NB_CASE_X ;
	static int NB_CASE_Y ;
	static int NB_CASE_Z ;
	static Color couleurRose;
	static Color couleurViolet;
	static Color couleurUtilisee;
	

	public Sprite[][][] SpriteCases;
	public Sprite [] spritesProcedures;
	public Sprite[][] spriteAnim;
	public LinkedList<Sprite[][]> robots;
	
	public Sprite spriteJoueurSuivant ; 
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
	public Sprite spriteSymboleWash;
	public Sprite spriteSymboleSwap;
	public Sprite spriteSymboleComp;
	public Sprite spriteSymbolePoser;
	public Sprite spriteSymboleSuppr;
	
	public Sprite spriteTabMain;
	public Sprite spriteTabP1;
	public Sprite spriteTabP2;
	
	public List<Animation> animInfos;
	public int nbRobotsInit;
	public int indexRobot;
	public boolean conditionExiste;
	public boolean afficherWin;
	
	
	public LinkedList list_action_possible;
	public ArrayList <List[]> tabProgramme;	//Tableau de liste de sprite (qui représente les actions du main et des proc)

	public float reScale,reScaleRobot;
	int progSelect;
	
	public void reinitialiser_anim(){
			this.animInfos.clear();
			this.robots.clear();	
	}
	
	
	public void initialiser_anim(){
		int y0=0,tailleX=128,tailleY=118;
		robots=new LinkedList<Sprite[][]>();
		
		this.animInfos = new ArrayList<Animation>();
		
		for(int n=0;n<monNiveau.getPersonnages().size();n++){
			this.animInfos.add(new Animation(monNiveau.getPersonnages().get(n).getPositionX(),monNiveau.getPersonnages().get(n).getPositionY(),0) ) ;
			this.spriteAnim = new Sprite[4][26];
			for(int i=0;i<this.spriteAnim.length;i++){
				y0=0;
				for(int j = 0; j <this.spriteAnim[i].length;j++){
					this.spriteAnim[i][j]=new Sprite();
					this.spriteAnim[i][j].setTexture(Textures.mesAnims[i]);
					
					this.spriteAnim[i][j].setTextureRect(new IntRect((j%6)*tailleX, y0*tailleY, tailleX, tailleY));
					if(j%6==0 && j!=0)
						y0++;
				}
			}			
			robots.add(this.spriteAnim);
		}
	}
	
	
	/**
	 * Permet de determiner la position des cases
	 */
	public void set_position_cases(){
		float referenceCentreX=Menu_principal.fenetre.getSize().x+450;
		float referenceCentreY=Menu_principal.fenetre.getSize().y+250;
		float divX = (float) (2.0/this.reScale);
		float divY = (float) (3.0/this.reScale);
		
		float Xinit = (referenceCentreX - NB_CASE_X*this.reScale*this.SpriteCases[0][0][0].getTexture().getSize().x)/2;
		float Yinit = (referenceCentreY/2)-this.reScale*this.SpriteCases[0][0][0].getTexture().getSize().x/2;

		float ActX;
		float ActY;
		
		
		for(int i= NB_CASE_X-1;i>=0;i--){
			ActX=Xinit + (i-1)*(this.SpriteCases[0][0][0].getTexture().getSize().x/divX);
			ActY=Yinit-i*(this.SpriteCases[0][0][0].getTexture().getSize().y/divY);
			for(int j= NB_CASE_Y-1;j>=0;j--){
				ActX=ActX +  this.SpriteCases[0][0][0].getTexture().getSize().x/divX;
				ActY=ActY +  this.SpriteCases[0][0][0].getTexture().getSize().y/divY;
				
				this.SpriteCases[i][j][0].setPosition(ActX ,ActY );

				for(int etageActuel = 1; etageActuel <= monNiveau.getTerrain().getEnsembleDeCase()[i][j].getHauteurGraphique();etageActuel ++)
					this.SpriteCases[i][j][etageActuel].setPosition(this.SpriteCases[i][j][etageActuel-1].getPosition().x ,this.SpriteCases[i][j][etageActuel-1].getPosition().y- this.SpriteCases[i][j][etageActuel-1].getTexture().getSize().y/divY);
			}		
		}	
	}
		
	
	/**
	 * Permet d'afficher les cadres pour les procédures (dont le main)
	 */
	public void afficher_cadre_procedures(){
		for (int i=0; i< monNiveau.getProgrammes().size(); i++)
			Menu_principal.fenetre.draw(this.spritesProcedures[i]);
	}
	
	
	/**
	 * Permet d'afficher notre terrain ainsi que le fond dégradé, les boutons d'action dispo pour un niveau et les actions qui sont dans les procédures (dont le main)
	 */
	public void afficher_carte(){
		int orientation;
		fenetre.draw(Menu_modes.spritefondMode);
		this.afficher_tab_procedures();
		afficher_cadre_procedures();
		this.afficher_boutons();
		this.afficher_procedure();
		this.afficher_verrou();
		
		for(int i= NB_CASE_X-1;i>=0;i--){
			for(int j= NB_CASE_Y-1;j>=0;j--){
				for(int etageActuel = 0; etageActuel <=monNiveau.getTerrain().getEnsembleDeCase()[i][j].getHauteurGraphique();etageActuel ++){	
					Menu_principal.fenetre.draw(this.SpriteCases[i][j][etageActuel]);

					for(int l = 0; l<monNiveau.getPersonnages().size();l++)
						if((i==monNiveau.getPersonnages().get(l).getPositionX() && j == monNiveau.getPersonnages().get(l).getPositionY() || i==this.animInfos.get(l).getX() && j == this.animInfos.get(l).getY() ) 
								&& etageActuel == monNiveau.getTerrain().getEnsembleDeCase()[i][j].getHauteurGraphique()){
							 	orientation = monNiveau.getPersonnages().get(l).getOrientationInt();
							 	
							 	robots.get(l)[orientation][this.animInfos.get(l).getNum()].setPosition(robots.get(l)[orientation][0].getPosition());
							 	if(!monNiveau.getPersonnages().get(l).isMort())
							 		robots.get(l)[orientation][this.animInfos.get(l).getNum()].setColor(this.couleur_case_vers_couleur_Graphique(monNiveau.getPersonnages().get(l).getCouleur()));
								
							 	Menu_principal.fenetre.draw(robots.get(l)[orientation][this.animInfos.get(l).getNum()]);
						}
				}
			}
		}
		if(this.afficherWin){
			this.texteWin.setColor(Color.YELLOW);
			Menu_principal.fenetre.draw(this.texteWin);	
		}
		Menu_principal.fenetre.display();
	}
	
	
	public void animMort(int index){
		for(int i=255;i>0;i-=10){
			robots.get(index)[monNiveau.getPersonnages().get(index).getOrientationInt()][this.animInfos.get(index).getNum()]
					.setColor(new Color (robots.get(index)[monNiveau.getPersonnages().get(index).getOrientationInt()][this.animInfos.get(index).getNum()].getColor(),i));
			
			afficher_carte();
			pause(1);	
		}
		robots.get(index)[monNiveau.getPersonnages().get(index).getOrientationInt()][this.animInfos.get(index).getNum()].setColor(Color.TRANSPARENT);
	}
	
	/**
	 * Permet de changer la position du robot
	 */
	public void set_pos_robot(){
		int i,j,h, orientation;
		for(int l = 0; l<monNiveau.getPersonnages().size();l++){
			 orientation = monNiveau.getPersonnages().get(l).getOrientationInt();

			 i=monNiveau.getPersonnages().get(l).getPositionX();
			 j=monNiveau.getPersonnages().get(l).getPositionY();
			 h=monNiveau.getTerrain().getEnsembleDeCase()[i][j].getHauteurGraphique();
			
			if(monNiveau.getTerrain().getEnsembleDeCase()[i][j] instanceof Transparente)
				{h=0;}

			robots.get(l)[orientation][0].setPosition(this.SpriteCases[i][j][h].getPosition().x 
					+ this.reScale*this.SpriteCases[i][j][h].getTexture().getSize().x/2
					- this.reScaleRobot*robots.get(l)[orientation][0].getLocalBounds().width/2
					,this.SpriteCases[i][j][h].getPosition().y 
					+this.reScale*this.SpriteCases[i][j][h].getTexture().getSize().y/3 
					- this.reScaleRobot*robots.get(l)[orientation][0].getLocalBounds().height
					+ 10);
				 
		}				
	}
	
	
	public void avancer(int index){
		int lastX=this.animInfos.get(index).getX() ;
		int lastY=this.animInfos.get(index).getY() ;
		int newX=monNiveau.getPersonnages().get(index).getPositionX(),newY=monNiveau.getPersonnages().get(index).getPositionY();
		float deplX=0,deplY = 0;
		float coeff = 2;
		
		switch (monNiveau.getPersonnages().get(index).getOrientation()){
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
		if(lastY!=newY || lastX != newX){
			Vector2f posFinale,posInit,posActuelle;
			
			int orientation = monNiveau.getPersonnages().get(index).getOrientationInt();
			int hauteur=monNiveau.getTerrain().getEnsembleDeCase()[newX][newY].getHauteurGraphique();
			
			posFinale = new Vector2f(this.SpriteCases[newX][newY][hauteur].getPosition().x 
					+ this.reScale*this.SpriteCases[newX][newY][hauteur].getTexture().getSize().x/2
					- this.reScaleRobot*robots.get(index)[orientation][0].getLocalBounds().width/2, 
					this.SpriteCases[newX][newY][hauteur].getPosition().y 
					+this.reScale*this.SpriteCases[newX][newY][hauteur].getTexture().getSize().y/3 
					- this.reScaleRobot*robots.get(index)[orientation][0].getLocalBounds().height);
			
			posInit = new Vector2f(robots.get(index)[orientation][0].getPosition().x
					,robots.get(index)[orientation][0].getPosition().y);
		
			posActuelle=robots.get(index)[orientation][0].getPosition();
			
			while( (posInit.x>=posFinale.x && posInit.y>=posFinale.y 
					  && posActuelle.x >= posFinale.x 
					  && posActuelle.y >= posFinale.y)
					  	  || 
				  (posInit.x<=posFinale.x && posInit.y>=posFinale.y 
						  && posActuelle.x <= posFinale.x 
						  && posActuelle.y >= posFinale.y)
						  || 
				  (posInit.x>=posFinale.x && posInit.y<=posFinale.y 
						  && posActuelle.x >= posFinale.x 
						  && posActuelle.y <= posFinale.y)
						  || 
				  (posInit.x<=posFinale.x && posInit.y<=posFinale.y 
						  && posActuelle.x <= posFinale.x 
						  && posActuelle.y <= posFinale.y)						  
				  )
			{
			
				this.animInfos.get(index).setNum(this.animInfos.get(index).getNum()+1);
				
				if(this.animInfos.get(index).getNum()>=robots.get(index)[orientation].length)
					this.animInfos.get(index).setNum(0);
					
				robots.get(index)[orientation][0].setPosition(robots.get(index)[orientation][0].getPosition().x+deplX,robots.get(index)[orientation][0].getPosition().y+deplY);
				posActuelle=robots.get(index)[orientation][0].getPosition();
				afficher_carte();
			}
			set_pos_robot();
			afficher_carte();
		}
	}
	
	
	public void sauter(int index){
		int lastX=this.animInfos.get(index).getX() ;
		int lastY=this.animInfos.get(index).getY() ;
		int newX=monNiveau.getPersonnages().get(index).getPositionX(),newY=monNiveau.getPersonnages().get(index).getPositionY();
		int hAct,hNew;
		int orientation = monNiveau.getPersonnages().get(index).getOrientationInt();
		float deplSaut=0;
		float coeff = 2;
		Vector2f posInit,posActuelle;
		
		posInit = new Vector2f(robots.get(index)[orientation][0].getPosition().x,robots.get(index)[orientation][0].getPosition().y);
		posActuelle=robots.get(index)[orientation][0].getPosition();
		hAct=monNiveau.getTerrain().getEnsembleDeCase()[newX][newY].getHauteurGraphique();
		hNew=monNiveau.getTerrain().getEnsembleDeCase()[lastX][lastY].getHauteurGraphique();
		if(hNew!=hAct){	
			deplSaut=-3*coeff;
			
			boolean b =  newX>lastX || newY>lastY ;
			while ((b && posActuelle.y 
							+ this.reScaleRobot*robots.get(index)[orientation][0].getLocalBounds().height
							> this.SpriteCases[newX][newY][hAct].getPosition().y
							+this.reScale*this.SpriteCases[newX][newY][hAct].getTexture().getSize().y/3*2
							+10)
							
				||(!b  && hAct>hNew 
							&& posActuelle.y 
							+ this.reScaleRobot*robots.get(index)[orientation][0].getLocalBounds().height 
							> posInit.y+this.reScaleRobot*robots.get(index)[orientation][0].getLocalBounds().height 
							-this.reScale*this.SpriteCases[newX][newY][hAct].getTexture().getSize().y/3*(hAct
							-hNew)
							+10)
					)
			{
				robots.get(index)[orientation][0].setPosition(posActuelle.x,posActuelle.y+deplSaut);
				posActuelle=robots.get(index)[orientation][0].getPosition();
				afficher_carte();	
			}
		}
		this.avancer(index);
	}
	
	public void reset_sprite_selectionne(){
		this.progSelect=0;
		for (int i=1; i< monNiveau.getProgrammes().size() ; i++)
			this.spritesProcedures[i].setTexture(Textures.texProcs[i]);
		this.spritesProcedures[0].setTexture(Textures.texProcs[this.spritesProcedures.length]);
	}
	
	public void sprite_selectionne(Vector2i pos){	
		int last_select = this.progSelect;
		
		for (int i=0; i< monNiveau.getProgrammes().size() ; i++){
			if(this.spritesProcedures[i].getGlobalBounds().contains(pos.x,pos.y) && i!=this.progSelect){	
				this.spritesProcedures[i].setTexture(Textures.texProcs[i+this.spritesProcedures.length]);	
				this.progSelect=i;
			}
		}
		if(this.progSelect!=last_select)
			this.spritesProcedures[last_select].setTexture(Textures.texProcs[last_select]);
	}
	
	
	
	/**
	 * Permet d'initialiser tous les sprites avec leur textures
	 */
	public void SetSprites(){
		int ecart=70;
		
		this.spritesProcedures = new Sprite[3];
		for (int i=0;i<this.spritesProcedures.length;i++){
			this.spritesProcedures[i]=new Sprite();
			this.spritesProcedures[i].setTexture(Textures.texProcs[i]);	
		}
		
		this.spritesProcedures[0].setPosition(25, ecart);
		this.spritesProcedures[1].setPosition(25, this.spritesProcedures[0].getPosition().y + this.spritesProcedures[0].getTexture().getSize().y + ecart );
		this.spritesProcedures[2].setPosition(25, this.spritesProcedures[1].getPosition().y + this.spritesProcedures[1].getTexture().getSize().y + ecart );
		this.spritesProcedures[0].setTexture(Textures.texProcs[3]);
		
		this.spriteJoueurSuivant  = new Sprite();
		this.spriteJoueurSuivant.setTexture(Textures.texSuivant);

		
		this.texteNbCase = new Text();
		this.texteWin = new Text();
		
		this.spritePeinture = new Sprite();
		this.spritePeinture.setTexture(Textures.texPeinture);
		
		this.spriteBoutonPlay=new Sprite();
		this.spriteBoutonPlay.setTexture(Textures.TexBoutonPlay);
		
		this.spriteBoutonReset = new Sprite();
		this.spriteBoutonReset.setTexture(Textures.texBoutonReset);
			
		this.spriteP1= new Sprite();
		this.spriteP1.setTexture(Textures.TexP1);
		
		this.spriteP2= new Sprite();
		this.spriteP2.setTexture(Textures.TexP2);

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
		
		this.spriteSymboleBreak = new Sprite();
		this.spriteSymboleBreak.setTexture(Textures.TexSymboleBreak);
		
		this.spriteSymboleWash = new Sprite();
		this.spriteSymboleWash.setTexture(Textures.TexSymboleWash);
		
		this.spriteSymboleSwap = new Sprite();
		this.spriteSymboleSwap.setTexture(Textures.TexSymboleSwap);
		
		this.spriteSymboleComp = new Sprite();
		this.spriteSymboleComp.setTexture(Textures.TexSymboleComp);
		
		this.spriteTabMain = new Sprite();
		this.spriteTabMain.setTexture(Textures.texTabMain);
		
		this.spriteTabP1 = new Sprite();
		this.spriteTabP1.setTexture(Textures.texTabP1);
		
		this.spriteTabP2 = new Sprite();
		this.spriteTabP2.setTexture(Textures.texTabP2);
		
		this.SpriteCases=new Sprite[NB_CASE_X][NB_CASE_Y][NB_CASE_Z];
		for(int i=0;i<  NB_CASE_X;i++)
			for(int j=0;j<  NB_CASE_Y;j++)
				for(int k=0;k<  NB_CASE_Z;k++)
					this.SpriteCases[i][j][k]=new Sprite();
		set_textures_cases();
		
	}
	
	public void set_textures_cases(){
		for(int i=0;i<  NB_CASE_X;i++)
			for(int j=0;j<  NB_CASE_Y;j++)
				for(int k=0;k<  NB_CASE_Z;k++){
					
					this.SpriteCases[i][j][k].setTexture(Textures.TexCaseBase);
					
					if(k==monNiveau.getTerrain().getEnsembleDeCase()[i][j].getHauteurGraphique()){
						if(monNiveau.getTerrain().getEnsembleDeCase()[i][j] instanceof Lampe  && monNiveau.getTerrain().getEnsembleDeCase()[i][j].getColor() == Couleur.Bleu)
							this.SpriteCases[i][j][k].setTexture(Textures.TexCaseLumEteinte); 
						else if(monNiveau.getTerrain().getEnsembleDeCase()[i][j] instanceof Lampe  && monNiveau.getTerrain().getEnsembleDeCase()[i][j].getColor() == Couleur.Jaune)
							this.SpriteCases[i][j][k].setTexture(Textures.TexCaseLumAllum);
						else if(monNiveau.getTerrain().getEnsembleDeCase()[i][j].getColor() == Couleur.Violet)
							this.SpriteCases[i][j][k].setTexture(Textures.TexCaseViolet);
						else if( monNiveau.getTerrain().getEnsembleDeCase()[i][j].getColor() == Couleur.Rose)
							this.SpriteCases[i][j][k].setTexture(Textures.TexCaseRose);
						else if(monNiveau.getTerrain().getEnsembleDeCase()[i][j] instanceof Pointeur  && monNiveau.getTerrain().getEnsembleDeCase()[i][j].getColor() == Couleur.Vert && ((Pointeur) monNiveau.getTerrain().getEnsembleDeCase()[i][j]).estPointee())
							this.SpriteCases[i][j][k].setTexture(Textures.TexCasePointee);
						else if(monNiveau.getTerrain().getEnsembleDeCase()[i][j] instanceof Pointeur  && monNiveau.getTerrain().getEnsembleDeCase()[i][j].getColor() == Couleur.Vert && !((Pointeur) monNiveau.getTerrain().getEnsembleDeCase()[i][j]).estPointee())
							this.SpriteCases[i][j][k].setTexture(Textures.TexCasePointeur);
						else if(monNiveau.getTerrain().getEnsembleDeCase()[i][j] instanceof Clonage  && monNiveau.getTerrain().getEnsembleDeCase()[i][j].getColor() == Couleur.Orange)
							this.SpriteCases[i][j][k].setTexture(Textures.TexCaseClonage);
					}
					if(monNiveau.getTerrain().getEnsembleDeCase()[i][j] instanceof Transparente && k!=0)
						this.SpriteCases[i][j][k].setTexture(Textures.TexCaseTransp);
					this.SpriteCases[i][j][k].setScale(this.reScale,this.reScale);
				}
	}

	/**
	 * Constructeur de la classe qui charge les niveaux 
	 * @param Scale
	 * @param niveauPourParser
	 */
	public void init_niveau(float Scale){
		police = new Font();
		
		try {
			police.loadFromStream(Utils.getInputStream("LightBot/Fonts/Starjedi.ttf"));
		} catch(IOException ex) {
		    //Failed to load font
		    ex.printStackTrace();
		}
		this.afficherWin=false;
		NB_CASE_X = monNiveau.getTerrain().getLargeur();
		NB_CASE_Y =  monNiveau.getTerrain().getLongueur();
		NB_CASE_Z=  monNiveau.getTerrain().getHauteurMax()+1;

		this.progSelect=0;
		this.indexRobot=0;
		couleurUtilisee = Color.WHITE;
		couleurRose = new Color(250, 0, 124);
		couleurViolet = new Color(106, 0, 250);
		this.conditionExiste=monNiveau.getTerrain().containConditionCase();
		
		this.reScale=Scale;
		this.reScaleRobot=Scale;
		SetSprites();
	}

	
	/**
	 * Permet de ne pas avoir de doublon dans la liste des actions possible pour un niveau
	 * @param compare
	 * @return true si l'action est d�j� pr�sente, false sinon
	 */
	public boolean action_deja_presente(StructStringSprite compare){
		StructStringSprite temp = null;
		for (int i = 0; i < this.list_action_possible.size(); i++){
			temp=(StructStringSprite) this.list_action_possible.get(i);
			if (temp.nom.equals(compare.nom)) return true;
		}
		return false;
	}
	
	/**
	 * Initialise la liste des sprites avec uniquement les actions possible pour un niveau donn�
	 */
	public void initActionsPossible(){
		this.list_action_possible = new LinkedList();
		ArrayList al = monNiveau.getActions() ;
		
		for (int i = 0; i < al.size(); i++) {
			
			StructStringSprite struct = null;
			if (al.get(i) instanceof Avancer) {
				struct = this.new StructStringSprite(this.spriteSymboleAvancer, "avancer");
			}else if(al.get(i) instanceof Allumer) {
				struct = this.new StructStringSprite(this.spriteSymboleAllumer, "allumer");
			}else if(al.get(i) instanceof AllumerBattle) {
				struct = this.new StructStringSprite(this.spriteSymboleAllumer, "allumerBattle");
			}else if(al.get(i) instanceof Break) {
				struct = this.new StructStringSprite(this.spriteSymboleBreak, "break");	
			}else if(al.get(i) instanceof Sauter) {
				struct = this.new StructStringSprite(this.spriteSymboleSauter, "sauter");
			}else if(al.get(i) instanceof TournerDroite) {
				struct = this.new StructStringSprite(this.spriteSymboleTournerDroite, "droite");
			}else if(al.get(i) instanceof TournerGauche) {
				struct = this.new StructStringSprite(this.spriteSymboleTournerGauche, "gauche");
			}else if(al.get(i) instanceof PoserBloc) {
				struct = this.new StructStringSprite(this.spriteSymbolePoser, "poser");
			}else if(al.get(i) instanceof RetirerBloc) {
				struct = this.new StructStringSprite(this.spriteSymboleSuppr, "suppr");
			}else if(al.get(i) instanceof CompareFront) {
				struct = this.new StructStringSprite(this.spriteSymboleComp, "comp");
			}else if(al.get(i) instanceof Swap) {
				struct = this.new StructStringSprite(this.spriteSymboleSwap, "swap");
			}else if(al.get(i) instanceof Wash) {
				struct = this.new StructStringSprite(this.spriteSymboleWash, "wash");
			}		
			
			if(!action_deja_presente(struct))
				this.list_action_possible.add(struct);
		}
		
		if(monNiveau.getProgrammes().size()>=2){
			StructStringSprite struct = null;
			struct = this.new StructStringSprite(this.spriteP1, "P1");
			this.list_action_possible.add(struct);
		}
		if(monNiveau.getProgrammes().size()>=3){
			StructStringSprite struct = null;
			struct = this.new StructStringSprite(this.spriteP2, "P2");
			this.list_action_possible.add(struct);
		}
	}

	/**
	 * Permet d'initialiser toutes les procédures (dont le main)
	 */
	public void initProcedures(){
		this.tabProgramme = new ArrayList <List[]>();
		
		for(int k = 0; k< monNiveau.getPersonnages().size();k++){
			tabProgramme.add(new List[monNiveau.getProgrammes().size()]);			
			for (int i = 0; i < this.tabProgramme.get(k).length; i++) 
				this.tabProgramme.get(k)[i] = new LinkedList();
		}
	}
	
	public void afficher_boutons(){
		
		this.texteNbCase = new Text(""+monNiveau.getTerrain().getReserveBloc(), police, 40);
		this.texteNbCase.setColor(Color.YELLOW);
		
		if (!this.list_action_possible.isEmpty()) {
			for (int k = 0; k < this.list_action_possible.size(); k++) {
				StructStringSprite temp = (StructStringSprite) this.list_action_possible.get(k);
				temp.sprite.setPosition(450+k*65, 50);
				Menu_principal.fenetre.draw(temp.sprite);
				if(temp.nom=="poser"){
					this.texteNbCase.setPosition(temp.sprite.getPosition());
					this.texteNbCase.move(temp.sprite.getTexture().getSize().x/2-this.texteNbCase.getCharacterSize()/4,temp.sprite.getTexture().getSize().y-5);
					Menu_principal.fenetre.draw(texteNbCase);
				}
			}
		}
		
		StructStringSprite temp= (StructStringSprite)this.list_action_possible.getLast();
		if(this.conditionExiste){
			spritePeinture.setPosition(temp.sprite.getPosition().x + spritePeinture.getTexture().getSize().x * 3+ 5,temp.sprite.getPosition().y);
			this.spriteBoutonPlay.setPosition(spritePeinture.getPosition().x + this.spriteBoutonPlay.getTexture().getSize().x +5 ,temp.sprite.getPosition().y);
			this.spriteBoutonReset.setPosition(this.spriteBoutonPlay.getPosition().x + this.spriteBoutonReset.getTexture().getSize().x + 5,this.spriteBoutonPlay.getPosition().y);
			Menu_principal.fenetre.draw(this.spritePeinture);	
		}
		else{
			this.spriteBoutonPlay.setPosition(temp.sprite.getPosition().x + spritePeinture.getTexture().getSize().x * 3+5 ,temp.sprite.getPosition().y);
			this.spriteBoutonReset.setPosition(this.spriteBoutonPlay.getPosition().x + this.spriteBoutonReset.getTexture().getSize().x + 5,this.spriteBoutonPlay.getPosition().y);		
		}
		if ( action_deja_presente(new StructStringSprite(this.spriteSymboleAllumer, "allumerBattle"))){
			temp = (StructStringSprite)this.list_action_possible.getFirst();
			spriteJoueurSuivant.setPosition(temp.sprite.getPosition().x, temp.sprite.getPosition().y+temp.sprite.getTexture().getSize().y+5);
			spriteJoueurSuivant.setColor(this.couleur_case_vers_couleur_Graphique(monNiveau.getPersonnages().get(this.indexRobot).getCouleur()));
			Menu_principal.fenetre.draw(this.spriteJoueurSuivant);
		}
		
		Menu_principal.fenetre.draw(Menu_principal.spriteRetour);
		Menu_principal.fenetre.draw(this.spriteBoutonPlay);
		Menu_principal.fenetre.draw(this.spriteBoutonReset);
		Menu_principal.fenetre.draw(spriteSon[sonOn]);
	}
	
	
	/**
	 * Permet d'afficher des symboles verrou là afin de limiter le nombre d'action 
	 * possible par l'utilisteur pour chaque procédure
	 */
	public void afficher_verrou(){
		
		int nb_ligne=0;
		switch (monNiveau.getProgrammes().size()) {
			case 3:
				nb_ligne=0;
				for (int l = 10; l > monNiveau.getProgrammes().get(2).getNbMaxAction(); l--) {
					if(l%5==0 && l!=0 && l!=10)	nb_ligne++;
					Sprite temp3 = new Sprite(Textures.TexVerrou);
					//temp2.setPosition(290-5-(j%5)*65, 400-5-nb_ligne*65);
					temp3.setPosition(this.spritesProcedures[2].getPosition().x+265-(((20-l)%5)*65), this.spritesProcedures[2].getPosition().y + 70-(nb_ligne*65));
					Menu_principal.fenetre.draw(temp3);
				}		
				
			case 2:
				nb_ligne=0;
				for (int k = 10; k > monNiveau.getProgrammes().get(1).getNbMaxAction(); k--) {
					if(k%5==0 && k!=0 && k!=10)	nb_ligne++;
					Sprite temp4 = new Sprite(Textures.TexVerrou);
					//temp2.setPosition(290-5-(j%5)*65, 400-5-nb_ligne*65);
					temp4.setPosition(this.spritesProcedures[1].getPosition().x+265-(((20-k)%5)*65), this.spritesProcedures[1].getPosition().y + 70-(nb_ligne*65));
					Menu_principal.fenetre.draw(temp4);				
				}
	
			default:
				nb_ligne = 0;
				for (int j = 20; j > monNiveau.getProgrammes().get(0).getNbMaxAction(); j--) {
					if(j%5==0 && j!=0 && j!=20)	nb_ligne++;
					Sprite temp2 = new Sprite(Textures.TexVerrou);
					//temp2.setPosition(290-5-(j%5)*65, 400-5-nb_ligne*65);
					temp2.setPosition(this.spritesProcedures[0].getPosition().x+265-(((20-j)%5)*65), this.spritesProcedures[0].getPosition().y+200-(nb_ligne*65));
					Menu_principal.fenetre.draw(temp2);
				}
				break;
		}
		
	}
	
	
	/**
	 * Permet d'afficher les proc�dures (le main et les deux autres sous proc�dures)
	 */
	public void afficher_procedure(){
		for(int i=0;i< monNiveau.getProgrammes().size(); i++ ){
			if (!this.tabProgramme.get(indexRobot)[i].isEmpty()) {
				int cpty=0;
				
				for (int k = 0; k < this.tabProgramme.get(indexRobot)[i].size(); k++) {
					
					if(k%5==0 && k!=0) cpty++;
						
					StructStringSprite temp = (StructStringSprite) this.tabProgramme.get(indexRobot)[i].get(k);
					temp.sprite.setPosition(this.spritesProcedures[i].getPosition().x+5+(k%5)*65
										   ,this.spritesProcedures[i].getPosition().y+5+cpty*65);
					Menu_principal.fenetre.draw(temp.sprite);
				}
			}
		}
	}
	
	/**
	 * Permet d'afficher les petits symboles de Main, P1 et P2 
	 */
	public void afficher_tab_procedures(){
		float fScale = 0.4f;
		
		switch (monNiveau.getProgrammes().size()) {
		case 3:
			this.spriteTabP2.setPosition(this.spritesProcedures[2].getPosition().x + this.spritesProcedures[2].getTexture().getSize().x - this.spriteTabP2.getTexture().getSize().x*fScale , this.spritesProcedures[2].getPosition().y - this.spriteTabP2.getTexture().getSize().y*fScale + 10);
			this.spriteTabP2.setScale(fScale, fScale);
			Menu_principal.fenetre.draw(this.spriteTabP2);
			
		case 2:
			this.spriteTabP1.setPosition(this.spritesProcedures[1].getPosition().x + this.spritesProcedures[1].getTexture().getSize().x - this.spriteTabP1.getTexture().getSize().x*fScale , this.spritesProcedures[1].getPosition().y - this.spriteTabP1.getTexture().getSize().y*fScale + 10);
			this.spriteTabP1.setScale(fScale, fScale);
			Menu_principal.fenetre.draw(this.spriteTabP1);
			

		default:
			this.spriteTabMain.setPosition(this.spritesProcedures[0].getPosition().x + this.spritesProcedures[0].getTexture().getSize().x - this.spriteTabMain.getTexture().getSize().x*fScale , this.spritesProcedures[0].getPosition().y - this.spriteTabMain.getTexture().getSize().y*fScale + 10);
			this.spriteTabMain.setScale(fScale, fScale);
			Menu_principal.fenetre.draw(this.spriteTabMain);
			break;
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
		
		public StructStringSprite(Sprite sprite, String string){
			Sprite temp = new Sprite(sprite.getTexture());
			this.sprite = temp;
			this.nom = string;
		}
		
		public StructStringSprite(StructStringSprite struct){
			Sprite temp = new Sprite(struct.sprite.getTexture());
			this.sprite = temp;
			this.nom = struct.nom;
		}	
	}
	
	
	private Color couleur_case_vers_couleur_Graphique(Couleur coul){
		if(coul == Couleur.Blanc) return  Color.WHITE;
		else if(coul == Couleur.Rose) return couleurRose;
		else if(coul ==Couleur.Violet) return couleurViolet;
		System.out.println("Erreur de couleur");
		return null;
	}
	
	private Couleur couleur_graphique_vers_couleur_case(Color coul){
		if(coul == Color.WHITE) return Couleur.Blanc;
		else if(coul == couleurRose )return Couleur.Rose;
		else if(coul ==couleurViolet ) return Couleur.Violet;
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
			monNiveau.getProgrammes().get(this.progSelect).insererQueue(new Avancer(this.couleur_graphique_vers_couleur_case(struct.sprite.getColor())   ));
			break;
		case "gauche":
			monNiveau.getProgrammes().get(this.progSelect).insererQueue(new TournerGauche(this.couleur_graphique_vers_couleur_case(struct.sprite.getColor()) ));
			break;
		case "droite":
			monNiveau.getProgrammes().get(this.progSelect).insererQueue(new TournerDroite(this.couleur_graphique_vers_couleur_case(struct.sprite.getColor()) ));
			break;
		case "sauter":
			monNiveau.getProgrammes().get(this.progSelect).insererQueue(new Sauter(this.couleur_graphique_vers_couleur_case(struct.sprite.getColor()) ));
			break;
		case "allumer":
			monNiveau.getProgrammes().get(this.progSelect).insererQueue(new Allumer(this.couleur_graphique_vers_couleur_case(struct.sprite.getColor()) ));
			break;
		case "allumerBattle":
			monNiveau.getProgrammes().get(this.progSelect).insererQueue(new AllumerBattle(this.couleur_graphique_vers_couleur_case(struct.sprite.getColor()) ));
			break;
		case "poser":
			monNiveau.getProgrammes().get(this.progSelect).insererQueue(new PoserBloc(this.couleur_graphique_vers_couleur_case(struct.sprite.getColor()) ));
			break;
		case "suppr":
			monNiveau.getProgrammes().get(this.progSelect).insererQueue(new RetirerBloc(this.couleur_graphique_vers_couleur_case(struct.sprite.getColor()) ));
			break;
		case "wash":
			monNiveau.getProgrammes().get(this.progSelect).insererQueue(new Wash(this.couleur_graphique_vers_couleur_case(struct.sprite.getColor()) ));
			break;
		case "comp":
			monNiveau.getProgrammes().get(this.progSelect).insererQueue(new CompareFront(this.couleur_graphique_vers_couleur_case(struct.sprite.getColor()) ));
			break;
		case "swap":
			monNiveau.getProgrammes().get(this.progSelect).insererQueue(new Swap(this.couleur_graphique_vers_couleur_case(struct.sprite.getColor()) ));
			break;
		case "break":
			monNiveau.getProgrammes().get(this.progSelect).insererQueue(new Break(this.couleur_graphique_vers_couleur_case(struct.sprite.getColor()) ));
			break;
		case "P1":			
			monNiveau.getProgrammes().get(this.progSelect).insererQueue(monNiveau.getProgrammes().get(1));
			monNiveau.getProgrammes().get(1).setCouleur(this.couleur_graphique_vers_couleur_case(struct.sprite.getColor()));
			break;
			case "P2":
			monNiveau.getProgrammes().get(this.progSelect).insererQueue(monNiveau.getProgrammes().get(2));
			monNiveau.getProgrammes().get(2).setCouleur(this.couleur_graphique_vers_couleur_case(struct.sprite.getColor()));
			break;
		default:
			break;
		}
	}
	
	
	void reset_niveau(Mode_Jeu mj, int selection){
		this.afficherWin=false;
		StructStringSprite temp;
		monNiveau=mj.getNiveau(selection);
		
		reinitialiser_anim();
		initialiser_anim();
		
		for(int index=0;index< robots.size();index++)
			robots.get(index)[monNiveau.getPersonnages().get(index).getOrientationInt()][this.animInfos.get(index).getNum()]
				.setColor(Color.WHITE);
		

		this.set_textures_cases();
		set_pos_robot();
		
		for(int l = 0; l<monNiveau.getPersonnages().size();l++){
			this.animInfos.get(l).setX(monNiveau.getPersonnages().get(l).getPositionX());
			this.animInfos.get(l).setY(monNiveau.getPersonnages().get(l).getPositionY());
			this.animInfos.get(l).setNum(0);
		}
	}
	
	void setProgramme(List[] liste){
		int save_select = this.progSelect;
		for(int l=0;l<liste.length;l++){
			this.progSelect=l;
			if (!liste[this.progSelect].isEmpty()) {
				for (int k = 0; k < liste[this.progSelect].size(); k++) {
					inserer_actions((StructStringSprite) liste[this.progSelect].get(k));	
				}
			}
		}
		this.progSelect = save_select;
	}
	
	
	
	public void supprimer_programme(int ind){
		if(ind<this.nbRobotsInit)
			for(int i=0;i<this.tabProgramme.get(ind).length;i++)
				this.tabProgramme.get(ind)[i].clear();	
	}
	
	void setNextPeinture (){
		if(couleurUtilisee == Color.WHITE) couleurUtilisee = couleurRose;
		else if(couleurUtilisee == couleurRose ) couleurUtilisee = couleurViolet ;
		else if(couleurUtilisee == couleurViolet)	couleurUtilisee = Color.WHITE;
		spritePeinture.setColor(couleurUtilisee);
	}
	
	private void pause (int p){
		try {
			Thread.sleep(p);
		} catch (InterruptedException e){}
	}
	
	
	private void afficher_phrase_fin(){
		if(monNiveau.getTerrain().getWinner()==0 && action_deja_presente(new StructStringSprite(this.spriteSymboleAllumer, "allumerBattle"))  ){
			this.afficherWin=true;
			this.texteWin = new Text("Le joueur rose a gagne !", police, 40);
			this.texteWin.setPosition(650,150);
		}else if(monNiveau.getTerrain().getWinner()==1 && action_deja_presente(new StructStringSprite(this.spriteSymboleAllumer, "allumerBattle"))  ){
			this.afficherWin=true;
			this.texteWin = new Text("Le joueur violet a gagne !", police, 40);
			this.texteWin.setPosition(650,150);
		}else if(monNiveau.getTerrain().getWinner()==-1 && action_deja_presente(new StructStringSprite(this.spriteSymboleAllumer, "allumerBattle"))  ){
			this.afficherWin=true;
			this.texteWin = new Text("Match nul !", police, 40);
			this.texteWin.setPosition(780,150);
		}else{
			this.afficherWin=true;
			if(monNiveau.getTerrain().getMaxLampe() == monNiveau.getTerrain().getNbLampeAllumee()){
				int nbCoups=monNiveau.getTerrain().getNbActionsPossible()-monNiveau.getTerrain().getNbActionsRestantes();
				this.texteWin = new Text("             Felicitations !\nNombre de d'actions utilisées : "+nbCoups+"\nClic pour passer au niveau suivant", police, 40);
				this.texteWin.setPosition(400,150);
			}else{
				this.texteWin = new Text("Algorithme termine", police, 40);
				this.texteWin.setPosition(650,150);
			}	
		}
	}

	/**
	 * Méthode principale de la classe qui permet d'afficher tout un niveau avec les actions et procédures associée
	 * @param niveauCharger Le niveau que l'on veut afficher
	 */
	public boolean afficher_niveau(Niveau niveauCharger,Mode_Jeu mj, int selection){		
		int j=0;
		boolean unSeulPlay=true;
		boolean sortie=true;
		StructStringSprite temp;
		
		//Initialisation des textures
		monNiveau=niveauCharger;
		init_niveau(1.0f);
		initialiser_anim();
		set_position_cases();
		set_pos_robot();
		initActionsPossible();
		afficher_boutons();
		initProcedures();
		afficher_carte();
		this.nbRobotsInit=monNiveau.getPersonnages().size();

		while (Menu_principal.fenetre.isOpen() && sortie ){
			pause(10);
			for (Event event : Menu_principal.fenetre.pollEvents()) {					
				if (event.type == Event.Type.MOUSE_BUTTON_PRESSED) { 
					Vector2i pos = Mouse.getPosition(Menu_principal.fenetre); 
					
					//Si clique droit sur un élément du main, on le supprime
					if (!this.tabProgramme.get(indexRobot)[this.progSelect].isEmpty()) {
						for (int k = 0; k < this.tabProgramme.get(indexRobot)[this.progSelect].size(); k++) {
							temp = (StructStringSprite) this.tabProgramme.get(indexRobot)[this.progSelect].get(k);
							if(temp.sprite.getGlobalBounds().contains(pos.x,pos.y) && event.asMouseButtonEvent().button == Button.RIGHT)
								this.tabProgramme.get(indexRobot)[this.progSelect].remove(k);
						}
					}
					
					//Ajout des éléments qu'on a cliqué dans le main
					if (!this.list_action_possible.isEmpty()) {
						for (int k = 0; k < this.list_action_possible.size(); k++) {
							temp = (StructStringSprite) this.list_action_possible.get(k);
							if(temp.sprite.getGlobalBounds().contains(pos.x,pos.y) && !this.spritesProcedures[this.progSelect].getGlobalBounds().contains(pos.x,pos.y)){
								StructStringSprite struct = new StructStringSprite(temp);
								//monNiveau.getProgrammes().add(struct);
								if(this.tabProgramme.get(indexRobot)[this.progSelect].size() < monNiveau.getProgrammes().get(this.progSelect).getNbMaxAction()){
									this.tabProgramme.get(indexRobot)[this.progSelect].add(struct);
								}
							}
						}
					}
					if(Menu_principal.spriteRetour.getGlobalBounds().contains(pos.x,pos.y))	sortie=false;
					
					else if(this.spriteBoutonPlay.getGlobalBounds().contains(pos.x,pos.y) && unSeulPlay){	
						int saveIndex = this.indexRobot;
						for (j=0;j<this.tabProgramme.size();j++){
							this.indexRobot=j;
							this.setProgramme(this.tabProgramme.get(indexRobot));
							try {
								if(this.tabProgramme.size()>1){
									monNiveau.getPersonnages().get(this.indexRobot).setProgramme(((Niveau)monNiveau.clone()).getProgrammes().get(0));
									monNiveau.viderListProgrammes();
								}else{
									monNiveau.getPersonnages().get(this.indexRobot).setProgramme(monNiveau.getProgrammes().get(0));
								}
							} catch (CloneNotSupportedException e){}	
						}
						this.indexRobot=saveIndex;
						Ordonnanceur monOrdonnanceur = new Ordonnanceur (monNiveau,this);
						monOrdonnanceur.run();

						if (!this.tabProgramme.get(indexRobot)[0].isEmpty())
							unSeulPlay=false;
						
						
						afficher_phrase_fin();				
					}
					else if(this.spritesProcedures[this.progSelect].getGlobalBounds().contains(pos.x,pos.y) ){
						//Si clique droit sur un élément du main, on le supprime
						if (!this.tabProgramme.get(indexRobot)[this.progSelect].isEmpty()) {
							for (int k = 0; k < this.tabProgramme.get(indexRobot)[this.progSelect].size(); k++) {
								temp = (StructStringSprite) this.tabProgramme.get(indexRobot)[this.progSelect].get(k);
								if(temp.sprite.getGlobalBounds().contains(pos.x,pos.y)){
									temp = (StructStringSprite) this.tabProgramme.get(indexRobot)[this.progSelect].get(k);	
									temp.sprite.setColor(couleurUtilisee);
								}
							}
						}
					}else if(this.spritePeinture.getGlobalBounds().contains(pos.x,pos.y) && this.conditionExiste){
						this.setNextPeinture();
					}else if(this.spriteJoueurSuivant.getGlobalBounds().contains(pos.x,pos.y) && monNiveau.getPersonnages().size()>1){
						reset_sprite_selectionne();
						this.indexRobot=1-this.indexRobot;
					}else if(this.spriteBoutonReset.getGlobalBounds().contains(pos.x,pos.y)){			
						reset_niveau(mj,selection);
						unSeulPlay=true;
					}else if(monNiveau.getTerrain().getMaxLampe() == monNiveau.getTerrain().getNbLampeAllumee() ){
						sortie=false;
					}else if (Menu_principal.spriteSon[sonOn].getGlobalBounds().contains(pos.x,pos.y)){
						sonOn=1-sonOn;
						music.setVolume((1-sonOn)*100);
					}else{
						sprite_selectionne(pos);
					}
					if(!Menu_principal.spriteRetour.getGlobalBounds().contains(pos.x,pos.y))  afficher_carte();
				}

				if (event.type == Event.Type.CLOSED) {
					Menu_principal.fenetre.close();
				}
				 else if (event.type == Event.Type.KEY_PRESSED) {
					 if (Keyboard.isKeyPressed(Key.ESCAPE))sortie=false;
				}
			}
		}
		if(selection<mj.getNbNiveaux()-1 && monNiveau.getTerrain().getMaxLampe() == monNiveau.getTerrain().getNbLampeAllumee()){
			return true;
		}
		return false;
	}

}