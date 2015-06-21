package LightBot;

import java.util.ArrayList;

import LightBot.outils.Utils;
import LightBot.parser.Parser;



public class Mode_Jeu {	

	private ArrayList<String> noms; 
	private String modeStr;
	
	public static void main(String[] pArgs){
		new Mode_Jeu(NomMode.Basic);
	}
	
	public Mode_Jeu(NomMode mode){
		this.noms=new ArrayList<String>();
		this.modeStr=mode.toString();
		this.noms=Utils.getListFiles(mode.getPath());
		//this.setNoms(mode);
	}	
	
	public int getNbNiveaux(){
		return this.noms.size();
	}
	public Niveau getNiveau(int index){
		if(index>=0 && index <this.noms.size()){
			Parser p=null;
			try{
				p=new Parser(Utils.getInputStream(this.noms.get(index)));
			}catch(Exception e){
				p=new Parser(this.noms.get(index));				
			}
			p.lire();
			return p.getNiveau();
		}else return null;
	}
	
	public String toString(){
		return this.modeStr;
	}
		
}
