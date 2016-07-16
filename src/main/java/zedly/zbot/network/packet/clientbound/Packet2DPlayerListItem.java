/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.network.packet.clientbound;

import java.io.IOException;
import zedly.zbot.GameContext;
import zedly.zbot.environment.PlayerListItem;
import zedly.zbot.environment.PlayerListItemProperty;
import zedly.zbot.network.ExtendedDataInputStream;

/**
 *
 * @author Dennis
 */
public class Packet2DPlayerListItem implements ClientBoundPacket {

    private int action;
    private PlayerListItem[] listItems;

    @Override
    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
        action = dis.readVarInt();
        int len = dis.readVarInt();
        listItems = new PlayerListItem[len];
        for (int i = 0; i < len; i++) {
            listItems[i] = new PlayerListItem();
            listItems[i].uuid = dis.readUUID();
            switch (action) {
                case 0:
                    listItems[i].name = dis.readString();
                    int len1 = dis.readVarInt();
                    listItems[i].properties = new PlayerListItemProperty[len1];
                    for (int j = 0; j < len1; j++) {
                        String name = dis.readString();
                        String value = dis.readString();
                        if (dis.readBoolean()) {
                            listItems[i].properties[j] = new PlayerListItemProperty(name, value, dis.readString());
                        } else {
                            listItems[i].properties[j] = new PlayerListItemProperty(name, value);
                        }
                    }
                    listItems[i].gameMode = dis.readVarInt();
                    listItems[i].ping = dis.readVarInt();
                    listItems[i].hasDisplayName = dis.readBoolean();
                    if (listItems[i].hasDisplayName) {
                        listItems[i].displayName = dis.readString();
                    }
                    break;
                case 1:
                    listItems[i].gameMode = dis.readVarInt();
                    break;
                case 2:
                    listItems[i].ping = dis.readVarInt();
                    break;
                case 3:
                    listItems[i].hasDisplayName = dis.readBoolean();
                    if (listItems[i].hasDisplayName) {
                        listItems[i].displayName = dis.readString();
                    }
                    break;
                case 4:
                    break;
            }
        }
    }

    public void process(GameContext context) {
        for (PlayerListItem item : listItems) {
            if (item.name != null) {
                context.getSelf().getEnvironment().addPlayerNameAndUUID(item.uuid, item.name);
            }
        }
    }

}
