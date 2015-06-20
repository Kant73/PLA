package Graphique;

import java.io.IOException;
import java.nio.file.Paths;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Mouse;
import org.jsfml.window.Keyboard.Key;
import org.jsfml.window.Mouse.Button;
import org.jsfml.window.event.Event;

import LightBot.Mode_Jeu;
import LightBot.NomMode;

public class Menu_modes extends Menu_principal{

	private static final int nbBoutons = NomMode.values().length;
	private int selection;
	private Sprite [] mesBoutons;
	private Texture[] mesTextures ;
	
	public static Texture fondMode;
	public static Sprite spritefondMode;


	private void charger_fond_mode(int sel)
	{	
		fondMode=new Texture();
		spritefondMode = new Sprite();
		try {
			switch (sel)
			{
			case 0:
				fondMode.loadFromFile(Paths.get("src/Img/Basic.png"));
				break;
			case 1:
				fondMode.loadFromFile(Paths.get("src/Img/Procedure.png"));
				break;
			case 2:
				fondMode.loadFromFile(Paths.get("src/Img/Loop.png"));
				break;
			case 3:
				fondMode.loadFromFile(Paths.get("src/Img/Condition.png"));
				break;
			case 4:
				fondMode.loadFromFile(Paths.get("src/Img/Star.png"));
				break;
			case 5:
				fondMode.loadFromFile(Paths.get("src/Img/Memory.png"));
				break;
			case 6:
				fondMode.loadFromFile(Paths.get("src/Img/Clone.png"));
				break;
			case 7:
				fondMode.loadFromFile(Paths.get("src/Img/Battle.png"));
				break;
			case 8:
				fondMode.loadFromFile(Paths.get("src/Img/Tri.png"));
				break;
			default :
				System.out.println("pas d'image de fond disponible");
				break;
		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		spritefondMode.setTexture(fondMode);
		spritefondMode.setPosition(0, 0);
	}
		
	
	private void init_images () 
	{  
		int k=0;
		int ecartPix = 30;
		int nbMaxLigne=5;
		mesBoutons = new Sprite[nbBoutons];
		mesTextures = new Texture[nbBoutons*2];
		
		for (int i=0; i< this.nbBoutons*2 ; i++)
		{
			mesTextures[i]=new Texture();
			try {
					if(i<nbBoutons)
					{
						mesTextures[i].loadFromFile(Paths.get("src/Img/mode00"+i+".png"));
						mesBoutons[i]=new Sprite();
						mesBoutons[i].setTexture(mesTextures[i]);
						if(i%nbMaxLigne==0 && i!=0)
							k++;
						
						mesBoutons[i].setPosition(Menu_principal.fenetre.getSize().x/2-nbMaxLigne*(mesTextures[i].getSize().x/2+ecartPix/2) + (mesTextures[i].getSize().x+ecartPix)*(i%nbMaxLigne)
								, Menu_principal.fenetre.getSize().y/3+(mesTextures[i].getSize().y+ecartPix)*k);
					}
					else
					{
						mesTextures[i].loadFromFile(Paths.get("src/Img/mode00"+(i-this.nbBoutons)+"_select.png"));
					}
					
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void hover(Vector2f pos)
	{	
		int last_select = this.selection;
		this.selection=-1;
		for (int i=0; i< this.nbBoutons; i++)
		{
			if(mesBoutons[i].getGlobalBounds().contains(pos.x,pos.y))
			{
				mesBoutons[i].setTexture(mesTextures[i+this.nbBoutons]);
				this.selection=i;
			}
		}
		if(this.selection!=last_select && last_select!=-1)
			mesBoutons[last_select].setTexture(mesTextures[last_select]);
	}
	
	
	private void afficher_boutons()
	{
		for (int i=0; i< this.nbBoutons; i++)
		{
			Menu_principal.fenetre.draw(mesBoutons[i]);
		}
		Menu_principal.fenetre.draw(Menu_principal.spriteRetour);
		Menu_principal.fenetre.draw(Menu_principal.spriteSon[sonOn]);

	}
	
	private void reinit_textures()
	{
		for (int i=0; i< this.nbBoutons; i++)
		{
				mesBoutons[i].setTexture(mesTextures[i]);
		}
	}
	
	private void fondu()
	{
		for(int i=10;i<40;i++)
		{
			spriteFond.setColor(new Color(spriteFond.getColor(), 3*i));
			fenetre.draw(spriteFond);
			fenetre.display();
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}
		spriteFond.setColor(new Color(spriteFond.getColor(), 255));
	}

	
	public void afficher_menu()
	{
		Menu_principal.fenetre.clear();
		this.selection=-1;
		this.init_images () ;
		Menu_principal.fenetre.draw(spriteFond);
		this.afficher_boutons();
		Menu_principal.fenetre.display();
		boolean sortie=true;
		while (Menu_principal.fenetre.isOpen() && sortie  ) 
		{
				for (Event event : Menu_principal.fenetre.pollEvents()) 
				{					
					if (event.type == Event.Type.MOUSE_BUTTON_PRESSED) 
					{ 
						if(event.asMouseButtonEvent().button == Button.LEFT)
						{
							Vector2i pos = Mouse.getPosition(Menu_principal.fenetre); 
							Vector2f posRedim = fenetre.mapPixelToCoords(pos); 
							hover(posRedim);
							
							if(this.selection !=-1)
							{
								Menu_niveaux modes = new  Menu_niveaux();
								music.playMusic(this.selection);		//Joue la musique en fonction du mode.
								fondu();
								charger_fond_mode(this.selection);
								modes.afficher_menu(this.selection);
								fondu();
								fenetre.draw(Menu_principal.spriteFond);
								reinit_textures();
								this.afficher_boutons();
								fenetre.display();
							}
							
							if(Menu_principal.spriteRetour.getGlobalBounds().contains(pos.x,pos.y))
							{
								sortie=false;
							}
							else if (Menu_principal.spriteSon[sonOn].getGlobalBounds().contains(pos.x,pos.y))
							{
								sonOn=1-sonOn;
								music.setVolume((1-sonOn)*100);
								Menu_principal.fenetre.draw(spriteFond);
								this.afficher_boutons();
								Menu_principal.fenetre.display();
							}	
						}
					}
					else if (event.type == Event.Type.KEY_PRESSED) 
					{ 
						if (Keyboard.isKeyPressed(Key.ESCAPE) || Keyboard.isKeyPressed(Key.ESCAPE))
						{
							sortie=false;
						}	
					}
					else if (event.type == Event.Type.MOUSE_MOVED) 
						{
							Vector2i pos = Mouse.getPosition(Menu_principal.fenetre); 
							Vector2f posRedim = fenetre.mapPixelToCoords(pos); 
							hover(posRedim);
							
							Menu_principal.fenetre.draw(spriteFond);
							this.afficher_boutons();
							Menu_principal.fenetre.display();
						}
					
					else if (event.type == Event.Type.CLOSED) {
						Musique.music.stop();
						Menu_principal.fenetre.close();
					}
				}
			
				try {
					Thread.sleep(15);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
		}
	}
	
}
