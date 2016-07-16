package zedly.zbot.api.event;

public class RainChangeEvent extends Event
{
	boolean raining;
	float   value;

	public RainChangeEvent(boolean raining, float value)
	{
		this.raining = raining;
		this.value = value;
	}

	public boolean isRaining()
	{
		return raining;
	}

	public float getValue()
	{
		return value;
	}
}
