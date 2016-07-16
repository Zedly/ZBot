package zedly.zbot.api.event;

public class TickEvent extends Event
{
	private final int tickId;

	public TickEvent(int tickId)
	{
		this.tickId = tickId;
	}

	public int getTickId()
	{
		return tickId;
	}
}