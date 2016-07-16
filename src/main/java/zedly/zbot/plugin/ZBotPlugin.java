package zedly.zbot.plugin;

import zedly.zbot.api.self.Self;

public abstract class ZBotPlugin
{
	public abstract void onEnable(Self self);

	public abstract void onDisable();

	public void onJoin() {}

	public void onQuit() {}

	public void onLoad() {}
}