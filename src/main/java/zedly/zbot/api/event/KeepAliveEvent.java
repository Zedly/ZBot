package zedly.zbot.api.event;

public class KeepAliveEvent extends Event
{
	private int keepAliveId;

	public KeepAliveEvent(int keepAliveId)
	{
		this.keepAliveId = keepAliveId;
	}

	public int getKeepAliveId()
	{
		return keepAliveId;
	}
}