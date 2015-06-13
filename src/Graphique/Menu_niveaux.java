package Graphique;

import java.io.IOException;
import java.nio.file.Paths;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Text;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Keyboard.Key;
import org.jsfml.window.Mouse;
import org.jsfml.window.Mouse.Button;
import org.jsfml.window.event.Event;

import LightBot.Mode_Jeu;
import LightBot.NomMode;

public class Menu_niveaux extends Menu_modes{

	private Font police;
	private Text monTexte;
	private static int nbBoutons;
	private Sprite [] mesBoutons;
	private Texture maTexture ;
	private Texture maTextureSel ;
	
	private Sprite spriteFond;
	private Texture monFond;
	private int selection;
	
	
private void init_font()
{
	police = new Font();
	
	try {
		police.loadFromFile(Paths.get("src/Fonts/arial.ttf"));
	} catch(IOException ex) {
	    //Failed to load font
	    ex.printStackTrace();
	}
}
	
	
	private void init_images () 
	{  
		int k=0;
		int ecartPix = 30;
		int nbMaxLigne=4;
		monFond = new Texture();
		mesBoutons = new Sprite[nbBoutons];
		maTexture = new Texture();
		maTextureSel = new Texture();
		
		try {
			monFond.loadFromFile(Paths.get("src/Img/fond_menu.png"));
			maTexture.loadFromFile(Paths.get("src/Img/numero_level.png"));
			maTextureSel.loadFromFile(Paths.get("src/Img/numero_level_selec.png"));
			
			spriteFond=new Sprite ();
			spriteFond.setTexture(monFond);
			spriteFond.setPosition(0,0);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
		for (int i=0; i< this.nbBoutons ; i++)
		{
					
					mesBoutons[i]=new Sprite();
					mesBoutons[i].setTexture(maTexture);
					if(i%nbMaxLigne==0 && i!=0)
						k++;
					
					mesBoutons[i].setPosition(Menu_principal.fenetre.getSize().x/2-nbMaxLigne*(maTexture.getSize().x/2+ecartPix/2) + (maTexture.getSize().x+ecartPix)*(i%nbMaxLigne)
							, Menu_principal.fenetre.getSize().y/2-maTexture.getSize().y/2 +(maTexture.getSize().y+ecartPix)*k);
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
				mesBoutons[i].setTexture(maTextureSel);
				this.selection=i;
			}
		}
		if(this.selection!=last_select && last_select!=-1)
			mesBoutons[last_select].setTexture(maTexture);
	}
	
	
	private void afficher_boutons()
	{
		monTexte = new Text();
		Text monTexte = new Text("", police, 50);
		monTexte.setColor(Color.BLACK);
		
		for (int i=0; i< this.nbBoutons; i++)/*On affiche les boutons et les numéros à l'intérieur*/
		{
			Menu_principal.fenetre.draw(mesBoutons[i]);
			
			monTexte.setString("" + (i+1));
			
			monTexte.setPosition(mesBoutons[i].getPosition().x+mesBoutons[i].getTexture().getSize().x/2-monTexte.getLocalBounds().width/2
					,mesBoutons[i].getPosition().y+mesBoutons[i].getTexture().getSize().y/2-monTexte.getLocalBounds().height/2-10);
			Menu_principal.fenetre.draw(monTexte);
		}
		Menu_principal.fenetre.draw(Menu_principal.spriteRetour);
	}
	
	private void reinit_textures()
	{
		for (int i=0; i< this.nbBoutons; i++)
		{
				mesBoutons[i].setTexture(maTexture);
		}
	}
	
	public void afficher_menu(int modeSelectionne)
	{
		Menu_principal.fenetre.clear();
		Mode_Jeu mj=new Mode_Jeu(NomMode.values()[modeSelectionne]);
		nbBoutons=mj.getNiveaux().size();
		
		this.selection=-1;
		this.init_images () ;
		init_font();
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
							hover(pos);
							
							if(selection !=-1)
							{
								Afficher_niveau level = new  Afficher_niveau();
								//level.playMusic("StarWarsCantina8Bits.ogg");		//Musique lors de la r�solution du niveau.
								level.afficher_niveau(mj.getNiveaux().get(selection));
								
								fenetre.draw(spriteFond);
								reinit_textures();
								this.afficher_boutons();
								fenetre.display();
								
							}
							if(Menu_principal.spriteRetour.getGlobalBounds().contains(pos.x,pos.y))
							{
								sortie=false;
							}
								
						}
					}
					else if (event.type == Event.Type.KEY_PRESSED) 
					{ 
						if (Keyboard.isKeyPressed(Key.ESCAPE))
						{
							sortie=false;
						}
						
					}
					else if (event.type == Event.Type.MOUSE_MOVED) 
						{
							Vector2i pos = Mouse.getPosition(Menu_principal.fenetre); 
							hover(pos);
							
							Menu_principal.fenetre.draw(spriteFond);
							this.afficher_boutons();
							Menu_principal.fenetre.display();
						}
					
					else if (event.type == Event.Type.CLOSED) {
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
