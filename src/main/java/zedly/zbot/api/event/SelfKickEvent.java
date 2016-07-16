package zedly.zbot.api.event;

public class SelfKickEvent extends Event
{
	private String reason;
	private String formattedReason;

	public SelfKickEvent(String reason, String formattedReason)
	{
		this.reason = reason;
		this.formattedReason = formattedReason;
	}

	public String getReason()
	{
		return reason;
	}

	public String getFormattedReason()
	{
		return formattedReason;
	}
}
