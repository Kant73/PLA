package LightBot.personnage;

public enum  Pcardinaux{
		NORTH(0),
		EAST(1),
		SOUTH(2),
		WEST(3);
		
		private int orientationInt;
		
		Pcardinaux (int i){
			this.orientationInt = i;
		}
		
		int getCode(){
			return this.orientationInt;
		}
	};