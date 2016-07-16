package zedly.zbot.api.event;

public class UnloadChunkEvent extends Event
{
	private int x;
	private int z;

	public  UnloadChunkEvent(int x, int z)
	{
		this.x=x;
		this.z=z;
	}

	public int getX()
	{
		return x;
	}

	public int getZ()
	{
		return z;
	}
}
