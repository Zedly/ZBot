package zedly.zbot.api.event;

public class ServerDifficultyEvent extends Event
{
	private int difficulty;

	public ServerDifficultyEvent(int difficulty)
	{
		this.difficulty = difficulty;
	}

	public int getDifficulty()
	{
		return difficulty;
	}
}