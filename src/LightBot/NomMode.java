package LightBot;

public enum NomMode {
		Versus_IA(NomMode.class.getResource("levels/ia/").getPath()),
		Basic(NomMode.class.getResource("levels/basic/").getPath()),
		Procedure(NomMode.class.getResource("levels/procedure/").getPath()),
		Loop(NomMode.class.getResource("levels/loop/").getPath()),
		Memory(NomMode.class.getResource("levels/memory/").getPath()),
		Fork(NomMode.class.getResource("levels/fork/").getPath());
		
		private String path;
		
		NomMode(String path){
			this.path=path;
		}
		
		public String getPath(){
			return this.path;
		}
}
