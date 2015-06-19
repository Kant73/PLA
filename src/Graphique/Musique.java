package Graphique;

import java.io.IOException;
import java.nio.file.Paths;

import org.jsfml.audio.Music;

public class Musique {

	public static Music music = new Music();
	
	/**
	 * Lancer la musique en param�tre comme par exemple "musique.ogg".
	 * Convertir les fichiers audios en .ogg pour pouvoir les utiliser.
	 * Ajouter les fichiers audios dans le package Music.
	 * @param pNomMusique
	 */
	public void playMusic(int pSelectMode) {
		try {
			switch (pSelectMode)
			{
			case 0:
				music.openFromFile(Paths.get("src/Music/Basic.ogg"));
				break;
			case 1:
				music.openFromFile(Paths.get("src/Music/ModeProcedure.ogg"));
				break;
			case 2:
				music.openFromFile(Paths.get("src/Music/Menu.ogg"));
				break;
			case 3:
				music.openFromFile(Paths.get("src/Music/Zarnakand.ogg"));
				break;
			case 4:
				music.openFromFile(Paths.get("src/Music/Pointeur.ogg"));
				break;
			case 5:
				music.openFromFile(Paths.get("src/Music/Memory.ogg"));
				break;
			case 6:
				music.openFromFile(Paths.get("src/Music/Clone.ogg"));
				break;
			case 7:
				music.openFromFile(Paths.get("src/Music/ModeVersus.ogg"));
				break;
			case 8:
				music.openFromFile(Paths.get("src/Music/Tri.ogg"));
				break;
			case 9:			//Démarrage
				music.openFromFile(Paths.get("src/Music/StarWarsIntro.ogg"));
				break;
			case 10:		//Crédits
				music.openFromFile(Paths.get("src/Music/star_wars_cantina.ogg"));
				break;
			default :
				System.out.println("pas d'image de fond disponible");
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
