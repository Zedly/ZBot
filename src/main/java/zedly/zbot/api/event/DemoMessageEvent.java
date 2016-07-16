package zedly.zbot.api.event;

public class DemoMessageEvent extends Event
{
	private float value;

	public DemoMessageEvent(float value)
	{
		this.value = value;
	}

	public float getValue()
	{
		return value;
	}
}
