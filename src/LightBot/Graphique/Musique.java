package LightBot.Graphique;

import java.io.IOException;
import java.nio.file.Paths;

import org.jsfml.audio.Music;

import LightBot.outils.Utils;

public class Musique {

	public static Music music;
	
	/**
	 * Lancer la musique en param�tre comme par exemple "musique.ogg".
	 * Convertir les fichiers audios en .ogg pour pouvoir les utiliser.
	 * Ajouter les fichiers audios dans le package Music.
	 * @param pNomMusique
	 */
	public void playMusic(int pSelectMode) {

		if(music!=null)
		{
			music.stop();
		}
		music = new Music();
		music.setVolume((1-Menu_principal.sonOn)*100);
		try {
			switch (pSelectMode)
			{
			case 0:
				music.openFromStream(Utils.getInputStream("LightBot/Music/Basic.ogg"));
				break;
			case 1:
				music.openFromStream(Utils.getInputStream("LightBot/Music/ModeProcedure.ogg"));
				break;
			case 2:
				music.openFromStream(Utils.getInputStream("LightBot/Music/StarWarsIntro.ogg"));
				break;
			case 3:
				music.openFromStream(Utils.getInputStream("LightBot/Music/Zarnakand.ogg"));
				break;
			case 4:
				music.openFromStream(Utils.getInputStream("LightBot/Music/Pointeur.ogg"));
				break;
			case 5:
				music.openFromStream(Utils.getInputStream("LightBot/Music/Memory.ogg"));
				break;
			case 6:
				music.openFromStream(Utils.getInputStream("LightBot/Music/Clone.ogg"));
				break;
			case 7:
				music.openFromStream(Utils.getInputStream("LightBot/Music/ModeVersus.ogg"));
				break;
			case 8:
				music.openFromStream(Utils.getInputStream("LightBot/Music/Tri.ogg"));
				break;
			case 9:			//Démarrage
				music.openFromStream(Utils.getInputStream("LightBot/Music/StarWarsIntro.ogg"));
				break;
			case 10:		//Crédits
				music.openFromStream(Utils.getInputStream("LightBot/Music/star_wars_cantina.ogg"));
				break;
			default :
				System.out.println("pas d'image de son disponible");
				break;
		}
		} catch (IOException e) {
			e.printStackTrace();
		}
		music.setLoop(true);
		music.play();
	}

	public void setVolume(int i) {
		music.setVolume(i);
		
	}
	
}
