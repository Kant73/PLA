package Graphique;

import java.io.IOException;
import java.nio.file.Paths;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Mouse;
import org.jsfml.window.Keyboard.Key;
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
	
	private int sprite_pos_souris(Vector2i pos)
	{	
		for (int i=0; i< this.nbBoutons; i++)
		{
			if(mesBoutons[i].getGlobalBounds().contains(pos.x,pos.y))
			{
				mesBoutons[i].setTexture(mesTextures[i+this.nbBoutons]);
				return i;
			}
		}
		
		for (int i=0; i< this.nbBoutons ; i++)
			mesBoutons[i].setTexture(mesTextures[i]);
		
		return -1;
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
		int selection=-1;
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
							selection = sprite_pos_souris(pos);
							
							switch (selection)
							{
							case jouer : 
								Menu_modes modes = new  Menu_modes();
								modes.afficher_menu();
								System.out.println("JOUER");
								break;
							case credits :
								System.out.println("CREDITS");
							}
							selection = sprite_pos_souris(pos);
							fenetre.draw(spriteFond);
							this.afficher_boutons();
							fenetre.display();
						}
					}
					else if (event.type == Event.Type.MOUSE_MOVED) 
						{
							Vector2i pos = Mouse.getPosition(fenetre); 
							selection = sprite_pos_souris(pos);
							
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
		
		fenetre.create(new VideoMode(1366, 768), "PXTB679X3");
		Menu_principal monMenu = new Menu_principal();
		monMenu.afficher_menu();
	}
}



