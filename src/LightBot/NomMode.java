package LightBot;

import LightBot.outils.Utils;

public enum NomMode {
		Basic(Utils.getStringPath("LightBot/levels/basic/")),
		Procedure(Utils.getStringPath("LightBot/levels/procedure/")),
		Loop(Utils.getStringPath("LightBot/levels/loop/")),
		Condition(Utils.getStringPath("LightBot/levels/condition/")),
		Pointeur(Utils.getStringPath("LightBot/levels/pointeur/")),
		Memory(Utils.getStringPath("LightBot/levels/memory/")),
		Fork(Utils.getStringPath("LightBot/levels/fork/")),
		Versus_IA(Utils.getStringPath("LightBot/levels/ia/")),
		Tri(Utils.getStringPath("LightBot/levels/tri/"));
		
		private String path;
		
		NomMode(String path){
			this.path=path;
		}
		
		public String getPath(){
			return this.path;
		}
}
