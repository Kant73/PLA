package Graphique;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Random;

import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Keyboard.Key;
import org.jsfml.window.Mouse;
import org.jsfml.window.Mouse.Button;
import org.jsfml.window.event.Event;

import LightBot.NomMode;

public class Credits extends Menu_principal{

	private static final int nbBoutons = 6;
	private static final int rotMax = 30;
	private static final int rotMin = -rotMax;

	
	private int selection;
	private Sprite [] mesBoutons;
	private Texture[] mesTextures ;
	
	private boolean [] sens;
	private Sprite spriteFond;
	private Texture monFond;

	
	private void init_images () 
	{  
		int k=0;
		int ecartPix = 150;
		int nbMaxLigne=3;
		sens = new boolean[nbBoutons];
		monFond = new Texture();
		mesBoutons = new Sprite[nbBoutons];
		mesTextures = new Texture[nbBoutons];
		
		try {
			monFond.loadFromFile(Paths.get("src/Img/fond_menu.png"));
			spriteFond=new Sprite ();
			spriteFond.setTexture(monFond);
			spriteFond.setPosition(0,0);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		for (int i=0; i< this.nbBoutons ; i++)
		{
			mesTextures[i]=new Texture();
			try {
					mesTextures[i].loadFromFile(Paths.get("src/Img/credit"+i+".png"));
					mesBoutons[i]=new Sprite();
					mesBoutons[i].setTexture(mesTextures[i]);
					if(i%nbMaxLigne==0 && i!=0)
						k++;
					
					mesBoutons[i].setPosition(150+Menu_principal.fenetre.getSize().x/2-nbMaxLigne*(mesTextures[i].getSize().x/2+ecartPix/2) + (mesTextures[i].getSize().x+ecartPix)*(i%nbMaxLigne)
							,300+(mesTextures[i].getSize().y+ecartPix-50)*k);
					
					mesBoutons[i].rotate((int)(Math.random() * (rotMax-rotMin)) + rotMin);
					this.sens[i]=new Random().nextBoolean() ;
					mesBoutons[i].setOrigin(mesBoutons[i].getTexture().getSize().x/2, mesBoutons[i].getTexture().getSize().y/2);

						
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void anim_credits()
	{
		for (int i=0; i< this.nbBoutons; i++)
		{
			if(this.sens[i])
			{
				mesBoutons[i].rotate(2.0f);
				
				if(mesBoutons[i].getRotation()>=rotMax)
					this.sens[i]=false;
			}	
		}
	
		for (int i=0; i< this.nbBoutons; i++)
		{
			if(!this.sens[i])
			{
				mesBoutons[i].rotate(-2.0f);
				
				if(mesBoutons[i].getRotation()<=rotMin)
					this.sens[i]=true;
			}
		}	
	}


	
	
	private void afficher_boutons()
	{
		for (int i=0; i< this.nbBoutons; i++)
		{
			Menu_principal.fenetre.draw(mesBoutons[i]);
		}
		Menu_principal.fenetre.draw(Menu_principal.spriteRetour);
		fenetre.draw(spriteSon[sonOn]);
	}
	

	
	public void afficher_credits()
	{
		Menu_principal.fenetre.clear();


		this.selection=-1;
		this.init_images () ;
		Menu_principal.fenetre.draw(spriteFond);
		
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
							if(Menu_principal.spriteRetour.getGlobalBounds().contains(pos.x,pos.y))
							{
								sortie=false;
								music.playMusic(9);
							}
							else if (Menu_principal.spriteSon[sonOn].getGlobalBounds().contains(pos.x,pos.y))
							{
								sonOn=1-sonOn;
								music.setVolume((1-sonOn)*100);
							}	
						}
					}
					else if (event.type == Event.Type.KEY_PRESSED) 
					{ 
						if (Keyboard.isKeyPressed(Key.ESCAPE))
						{
							sortie=false;
							music.playMusic(9);
						}	
					}
					else if (event.type == Event.Type.CLOSED) {
						Musique.music.stop();
						Menu_principal.fenetre.close();
					}
				}
				
				anim_credits();
				Menu_principal.fenetre.draw(spriteFond);
				this.afficher_boutons();
				Menu_principal.fenetre.display();
				
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
		}
	}
}
