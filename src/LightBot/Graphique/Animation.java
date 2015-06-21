package LightBot.Graphique;

import java.util.List;

public class Animation {

	private int ancX,ancY;
	private int numAnim;
	
	public Animation(int i,int j, int k)
	{
		this.ancX=i;
		this.ancY=j;
		this.numAnim=k;
	}
	
	public void setX(int i)
	{
		this.ancX=i;
	}
	
	public void setY(int i)
	{
		this.ancY=i;
	}
	
	public void setNum(int i)
	{
		this.numAnim=i;
	}
	
	public int getX()
	{
		return this.ancX;
	}
	
	public int getY()
	{
		return this.ancY;
	}
	
	public int getNum()
	{
		return this.numAnim;
	}
	
	
	
}
