package zedly.zbot.api.event;

import zedly.zbot.entity.CraftPlayer;
import zedly.zbot.api.entity.Player;

public class PlayerSpawnEvent extends Event
{
	private final CraftPlayer player;

	public PlayerSpawnEvent(CraftPlayer player)
	{
		this.player = player;
	}

	public Player getEntity()
	{
		return player;
	}
}