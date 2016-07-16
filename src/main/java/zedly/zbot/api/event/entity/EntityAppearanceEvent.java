package zedly.zbot.api.event.entity;

import zedly.zbot.api.event.Event;

public class EntityAppearanceEvent extends Event
{
	private float value;

	public EntityAppearanceEvent(float value)
	{
		this.value = value;
	}

	public float getValue()
	{
		return value;
	}
}
