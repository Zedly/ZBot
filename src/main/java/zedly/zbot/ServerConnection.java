package zedly.zbot;

public class ServerConnection
{
	private final String ip;
	private final int    port;
	private final String username;

	public ServerConnection(String ip, int port, String username)
	{
		this.ip = ip;
		this.port = port;
		this.username = username;
	}

	public String getIp()
	{
		return ip;
	}

	public int getPort()
	{
		return port;
	}

	public String getUsername()
	{
		return username;
	}
}