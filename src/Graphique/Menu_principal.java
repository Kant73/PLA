package Graphique;

import java.io.IOException;
import java.nio.file.Paths;

import org.jsfml.audio.Music;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Image;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.internal.JSFMLError;
import org.jsfml.internal.SFMLNative;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Keyboard.Key;
import org.jsfml.window.Mouse;
import org.jsfml.window.Mouse.Button;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;


public class Menu_principal {
	
	public static final RenderWindow fenetre = new RenderWindow(); 
	public static Texture texRetour;
	public static Sprite spriteRetour;
	public static Sprite[] spriteSon;
	public static Texture[] texSon;
	public static int sonOn;
	
	public static Musique music = new Musique();
	
	private static final int nbBoutons = 2;
	private static final int jouer = 0;
	private static final int credits = 1;
	
	private Sprite [] mesBoutons;
	private Texture[] mesTextures ;
	
	public static Sprite spriteFond;
	private Texture monFond;
	private int selection;
	

	private void init_images () 
	{  
		monFond = new Texture();
		mesBoutons = new Sprite[nbBoutons];
		mesTextures = new Texture[nbBoutons*2];
		texRetour = new Texture();
		spriteRetour = new Sprite();
		
		spriteSon=new Sprite[2];
		spriteSon[0] = new Sprite();
		spriteSon[1] = new Sprite();
		
		texSon=new Texture[2];
		texSon[0] = new Texture();
		texSon[1] = new Texture();
		
		
		try {
			texSon[0].loadFromFile(Paths.get("src/Img/son.png"));
			texSon[1].loadFromFile(Paths.get("src/Img/son_coupe.png"));
			monFond.loadFromFile(Paths.get("src/Img/fond_menu.png"));
			texRetour.loadFromFile(Paths.get("src/Img/retour2.png"));
			spriteFond=new Sprite ();
			spriteFond.setTexture(monFond);
			spriteFond.setPosition(0,0);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		spriteRetour.setTexture(texRetour);
		spriteRetour.setPosition(0, 0);
		
		spriteSon[0].setTexture(texSon[0]);
		spriteSon[0].setPosition(0,0);
		spriteSon[1].setTexture(texSon[1]);
		spriteSon[1].setPosition(0,0);
		
		for (int i=0; i< this.nbBoutons*2 ; i++)
		{
			mesTextures[i]=new Texture();
			try {
				if(i<nbBoutons)
				{
					mesTextures[i].loadFromFile(Paths.get("src/Img/00"+i+".png"));
					mesBoutons[i]=new Sprite();
					mesBoutons[i].setTexture(mesTextures[i]);
					mesBoutons[i].setPosition(fenetre.getSize().x/2-mesTextures[i].getSize().x/2, fenetre.getSize().y/3+(mesTextures[i].getSize().y+30)*i);
				}
				else
				{
					mesTextures[i].loadFromFile(Paths.get("src/Img/00"+(i-this.nbBoutons)+"_select.png"));
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
			if(mesBoutons[i].getGlobalBounds().contains(pos))
			{
				mesBoutons[i].setTexture(mesTextures[i+this.nbBoutons]);
				this.selection=i;
			}
		}
		if(this.selection!=last_select && last_select!=-1)
			mesBoutons[last_select].setTexture(mesTextures[last_select]);
	}
	
	private void reinit_textures()
	{
		for (int i=0; i< this.nbBoutons; i++)
		{
			mesBoutons[i].setTexture(mesTextures[i]);
		}
	}
	
	private void afficher_boutons()
	{
		for (int i=0; i< this.nbBoutons; i++)
		{
			fenetre.draw(mesBoutons[i]);
		}
		fenetre.draw(spriteSon[sonOn]);
		
	}
	

	private void fondu()
	{
		for(int i=10;i<80;i+=2)
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
		
		this.selection=-1;
		this.init_images () ;
		music.playMusic(9);
		music.setVolume(100);
		
		fenetre.draw(spriteFond);
		this.afficher_boutons();
		fenetre.display();

		while (fenetre.isOpen()) 
		{
				for (Event event : fenetre.pollEvents()) 
				{					
					if (event.type == Event.Type.MOUSE_BUTTON_PRESSED) 
					{ 
						if(event.asMouseButtonEvent().button == Button.LEFT)
						{
							Vector2i pos = Mouse.getPosition(fenetre); 
							Vector2f posRedim = fenetre.mapPixelToCoords(pos);
							
							hover(posRedim);
							
							if(selection != -1)
							{
								spriteSon[0].setPosition(0+texRetour.getSize().x + 5,0);
								spriteSon[1].setPosition(spriteSon[0].getPosition());
								fondu();
								switch (selection)
								{
								case jouer : 
									Menu_modes modes = new  Menu_modes();
									modes.afficher_menu();
									break;
								case credits :
									music.playMusic(10);
									Credits credit = new  Credits();
									credit.afficher_credits();
								}
								fondu();
								spriteSon[0].setPosition(0,0);
								spriteSon[1].setPosition(0,0);
								
								fenetre.draw(spriteFond);
								reinit_textures();
								this.afficher_boutons();
								fenetre.display();
								}
							
							if (Menu_principal.spriteSon[sonOn].getGlobalBounds().contains(pos.x,pos.y))
							{
								sonOn=1-sonOn;
								music.setVolume((1-sonOn)*100);
								fenetre.draw(spriteFond);
								this.afficher_boutons();
								fenetre.display();
							}
						}
					}
					else if (event.type == Event.Type.MOUSE_MOVED) 
						{
							Vector2i pos = Mouse.getPosition(fenetre); 
							Vector2f posRedim = fenetre.mapPixelToCoords(pos); 
							hover(posRedim);
							
							fenetre.draw(spriteFond);
							this.afficher_boutons();
							fenetre.display();
						}
					else if (event.type == Event.Type.CLOSED ||  ((event.type == Event.Type.KEY_PRESSED)  && (Keyboard.isKeyPressed(Key.ESCAPE)))) {
						Musique.music.stop();
						fenetre.close();	
					}
				}
			
				try {
					Thread.sleep(15);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
		}
		Musique.music.stop();
	}
	

	public static void main(String[] args) {
	
		try {SFMLNative.loadNativeLibraries();} 
		catch (JSFMLError err) {/*things*/}
	
		sonOn = 0;
		fenetre.create(new VideoMode(1366, 768), "StarBot");
		Menu_principal monMenu = new Menu_principal();
		
		fenetre.setFramerateLimit(60);
		fenetre.setVerticalSyncEnabled(true);
		
		Image icon = new Image();
		try {
			icon.loadFromFile(Paths.get("src/Img/BB8_tete.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fenetre.setIcon(icon);
		
		Textures.initTextures();
		monMenu.afficher_menu();
	}
}



