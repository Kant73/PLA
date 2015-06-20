package Graphique;

import java.io.IOException;
import java.nio.file.Paths;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Mouse;
import org.jsfml.window.Keyboard.Key;
import org.jsfml.window.Mouse.Button;
import org.jsfml.window.event.Event;

import LightBot.Niveau;

public class Tuto extends Menu_niveaux{
	
	private Sprite [] spritesTutoTab;
	private Texture[] texTutoTab;
	
	private int selection;
	
	public Sprite[] getSpritesTutoTab(){
		return this.spritesTutoTab;
	}
	
	/**
	 * Initialisation du tuto en chargeant toutes les images nécéssaires
	 * @param nom_niveau
	 * @param nb_img
	 */
	public void init_tuto(String nom_niveau, int nb_img){
		this.spritesTutoTab = new Sprite[nb_img];
		this.texTutoTab = new Texture[nb_img];
		
		for (int i = 0; i < nb_img; i++) {
			try {
				this.texTutoTab[i] = new Texture();
				this.spritesTutoTab[i] = new Sprite();
				//System.out.println("src/Img/"+ nom_niveau + "_lvl1_" + (i+1) + ".png");
				texTutoTab[i].loadFromFile(Paths.get("src/Img/"+ nom_niveau + "_lvl1_" + (i+1) + ".png"));
				this.spritesTutoTab[i].setTexture(texTutoTab[i]);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}	
		
	}
	
	/**
	 * Défini le nombre d'image que le tuto contient pour chaque mode de jeu
	 * @param nom_mode
	 * @return  Le nombre d'image que le tuto contient pour le mode de jeu nom_mode
	 */
	public int nb_image_par_mode(String nom_mode){
		int nb_image = 0;
		switch (nom_mode) {
		case "Basic":
			nb_image = 7;
			break;
		case "Procedure":
			nb_image = 4;
			break;
		case "Loop":
			nb_image = 5;
			break;
		case "Condition":
			nb_image = 5;
			break;
		case "Pointeur":
			nb_image = 5;
			break;
		case "Memory":
			nb_image = 8;
			break;
		case "Fork":
			nb_image = 4;
			break;
		case "Versus_IA":
			nb_image = 6;
			break;
		case "Tri":
			nb_image = 7;
			break;
		default:
			break;
		}
		return nb_image;
	}
	
	/**
	 * Permet de créer un fondu entre les différentes images qui consituent un tuto 
	 * @param sprite_tuto
	 */
	private void fondu(int sprite_tuto)
	{
		for(int i=10;i<80;i+=2)
		{
			this.spritesTutoTab[sprite_tuto].setColor(new Color(Menu_modes.spritefondMode.getColor(), 3*i));
			fenetre.draw(this.spritesTutoTab[sprite_tuto]);
			fenetre.display();
			/*
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
			
		}
		this.spritesTutoTab[sprite_tuto].setColor(new Color(Menu_modes.spritefondMode.getColor(), 255));
	}
	

	/**
	 * Permet d'afficher le tuto d'un mode de jeu
	 * @param nom_niveau Le nom du mode de jeu dont on veut afficher le tuto
	 */
	public void affiche_tuto(String nom_niveau){
		int nb_img = this.nb_image_par_mode(nom_niveau);
		if (nb_img == 0) {
			return;
		}
		
		int tutoCourant = 0;
		
		boolean sortie=true;
		while (Menu_principal.fenetre.isOpen() && sortie) 
		{
			
			for (Event event : Menu_principal.fenetre.pollEvents()) 
			{	
				if (event.type == Event.Type.KEY_PRESSED) 
				{ 
					if (Keyboard.isKeyPressed(Key.ESCAPE))
					{
						sortie=false;
						Menu_principal.fenetre.clear();
					}
				}

				else if (event.type == Event.Type.MOUSE_BUTTON_PRESSED) 
				{ 
					if(event.asMouseButtonEvent().button == Button.LEFT)
					{
						tutoCourant++;
						if (tutoCourant < nb_img) {
							fondu(tutoCourant);
						}
						if (tutoCourant >= nb_img) {
							sortie=false;
						}		
					}
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
