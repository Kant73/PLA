package Graphique;

import java.io.IOException;
import java.nio.file.Paths;

import org.jsfml.graphics.Image;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.internal.JSFMLError;
import org.jsfml.internal.SFMLNative;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Keyboard.Key;
import org.jsfml.window.Mouse;
import org.jsfml.window.Mouse.Button;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;


public class Menu_principal {
	
	public static final RenderWindow fenetre = new RenderWindow(); 
	
	private static final int nbBoutons = 2;
	private static final int jouer = 0;
	private static final int credits = 1;
	private Sprite [] mesBoutons;
	private Texture[] mesTextures ;
	
	private Sprite spriteFond;
	private Texture monFond;
	private int selection;
	
//	public static Music music = new Music();
//	
//	/**
//	 * Lancer la musique en param�tre comme par exemple "musique.ogg".
//	 * Convertir les fichiers audios en .ogg pour pouvoir les utiliser.
//	 * Ajouter les fichiers audios dans le package Music.
//	 * @param pNomMusique
//	 */
//	protected void playMusic(String pNomMusique) {
//		
//		try {
//			music.openFromFile(Paths.get("src/Music/" + pNomMusique));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		music.setVolume(30);
//		music.setLoop(true);
//		music.play();
//	}

	private void init_images () 
	{  
		monFond = new Texture();
		mesBoutons = new Sprite[nbBoutons];
		mesTextures = new Texture[nbBoutons*2];
		
		try {
			monFond.loadFromFile(Paths.get("src/Img/fond_menu.png"));
			spriteFond=new Sprite ();
			spriteFond.setTexture(monFond);
			spriteFond.setPosition(0,0);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
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
	
	private void hover(Vector2i pos)
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
			fenetre.draw(mesBoutons[i]);
		}
	}
	

	public void afficher_menu()
	{
		
		this.selection=-1;
		this.init_images () ;
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
							hover(pos);
							
							switch (selection)
							{
							case jouer : 
								Menu_modes modes = new  Menu_modes();
								modes.afficher_menu();
								break;
							case credits :
								Credits credit = new  Credits();
								credit.afficher_credits();
							}
							hover(pos);
							fenetre.draw(spriteFond);
							this.afficher_boutons();
							fenetre.display();
						}
					}
					else if (event.type == Event.Type.MOUSE_MOVED) 
						{
							Vector2i pos = Mouse.getPosition(fenetre); 
							hover(pos);
							
								fenetre.draw(spriteFond);
							this.afficher_boutons();
							fenetre.display();
						}
					else if (event.type == Event.Type.CLOSED ||  ((event.type == Event.Type.KEY_PRESSED)  && (Keyboard.isKeyPressed(Key.ESCAPE)))) {
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
	}
	
	

	
public static void main(String[] args) {
		

	try {SFMLNative.loadNativeLibraries();} 
	catch (JSFMLError err) {/*things*/}


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
		
		//monMenu.playMusic("Zarnakand.ogg");
		monMenu.afficher_menu();
		
	}
}



