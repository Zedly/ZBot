package zedly.zbot.api.event;

public class SkyFadeEvent extends Event
{
	public float value;

	public SkyFadeEvent(float value)
	{
		this.value = value;
	}

	public float getValue()
	{
		return value;
	}
}
