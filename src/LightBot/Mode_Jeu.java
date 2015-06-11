package LightBot;

import java.io.File;
import java.util.ArrayList;

import LightBot.parser.Parser;



public class Mode_Jeu {	

	private ArrayList<Niveau> niveaux; 
	
	public Mode_Jeu(NomMode mode){
		this.niveaux=new ArrayList<Niveau>();
		this.setNiveaux(mode);
	}	
	
	public ArrayList<Niveau> getNiveaux(){
		return this.niveaux;
	}
	
	private void setNiveaux(NomMode mode){
		if(mode!=null && this.niveaux!=null){
			File folder = new File(mode.getPath());
			Parser p;
		    for (File fichier:folder.listFiles()) {
		    	String nomFichier=fichier.toString();
				if (fichier.isFile() && nomFichier.substring(nomFichier.length()-4).equals(".xml")) {
					p=new Parser(fichier.toString());
					p.lire();
					this.niveaux.add(p.getNiveau());
				}
		    }
		}
	}
		
}
