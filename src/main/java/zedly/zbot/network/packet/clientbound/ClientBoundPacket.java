package zedly.zbot.network.packet.clientbound;

import zedly.zbot.GameContext;
import zedly.zbot.network.ExtendedDataInputStream;

import java.io.IOException;

public interface ClientBoundPacket
{
	void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException;

	default void process(GameContext context)
	{
	}
}
