package LightBot;

import java.io.File;
import java.util.ArrayList;

import LightBot.parser.Parser;



public class Mode_Jeu {	

	private ArrayList<String> noms; 
	
	public static void main(String[] pArgs){
		new Mode_Jeu(NomMode.Basic);
	}
	
	public Mode_Jeu(NomMode mode){
		this.noms=new ArrayList<String>();
		this.setNoms(mode);
	}	
	
	public int getNbNiveaux(){
		return this.noms.size();
	}
	public Niveau getNiveau(int index){
		if(index>=0 && index <this.noms.size()){
			Parser p=new Parser(this.noms.get(index));
			p.lire();
			return p.getNiveau();
		}else return null;
	}
	
	private void setNoms(NomMode mode){
		if(mode!=null && this.noms!=null){
			File folder = new File(mode.getPath().replace("%20", " "));					
		    for (File fichier:folder.listFiles()) {
		    	String nomFichier=fichier.toString();		    	
				if (fichier.isFile() && nomFichier.substring(nomFichier.length()-4).equals(".xml"))
					noms.add(nomFichier);
		    }
		    this.noms.sort(String.CASE_INSENSITIVE_ORDER);
		}
	}
		
}
