package zedly.zbot.api.event;

public class HealthChangeEvent extends Event
{
	private float health;
	private int food;
	private float saturation;

	public HealthChangeEvent(float health, int food, float saturation)
	{
		this.health = health;
		this.food = food;
		this.saturation = saturation;
	}

	public float getHealth()
	{
		return health;
	}

	public int getFood()
	{
		return food;
	}

	public float getSaturation()
	{
		return saturation;
	}
}
