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
	
	
	private void fondu(int sprite_tuto)
	{
		for(int i=0;i<100;i+=2)
		{
			this.spritesTutoTab[sprite_tuto].setColor(new Color(Menu_modes.spritefondMode.getColor(), 2*i));
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
	

	public void affiche_tuto(String nom_niveau){
		int nb_img = this.nb_image_par_mode(nom_niveau);
		if (nb_img == 0) {
			return;
		}
		
		int tutoCourant = 0;
		boolean temp = true;
		boolean first_fondu = true;
		Menu_principal.fenetre.clear();
		//this.init_tuto(nom_niveau, nb_img);
		
		Menu_principal.fenetre.display();
		
		boolean sortie=true;
		while (Menu_principal.fenetre.isOpen() && sortie) 
		{
			if (temp) {
				fondu(tutoCourant);
				temp = false;
			}
			
			
			for (Event event : Menu_principal.fenetre.pollEvents()) 
			{	
				if (event.type == Event.Type.KEY_PRESSED) 
				{ 
					if (Keyboard.isKeyPressed(Key.ESCAPE))
					{
						sortie=false;
					}
				}

				else if (event.type == Event.Type.MOUSE_BUTTON_PRESSED) 
				{ 
					if(event.asMouseButtonEvent().button == Button.LEFT)
					{
						Vector2i pos = Mouse.getPosition(Menu_principal.fenetre); 
						
						if (this.spritesTutoTab[tutoCourant].getGlobalBounds().contains(pos.x, pos.y)) {
							tutoCourant++;
							temp = true;
						}
						
						if (tutoCourant == nb_img) {
							sortie=false;
						}
							
					}
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
