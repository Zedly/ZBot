package zedly.zbot.network;

import zedly.zbot.network.packet.serverbound.Packet16ResourcePackStatus;
import zedly.zbot.network.packet.serverbound.Packet0FPlayer;
import zedly.zbot.network.packet.serverbound.Packet0CPlayerPosition;
import zedly.zbot.network.packet.serverbound.Packet17HeldItemChange;
import zedly.zbot.network.packet.serverbound.Packet1BSpectate;
import zedly.zbot.network.packet.serverbound.Packet03ClientStatus;
import zedly.zbot.network.packet.serverbound.Packet0DPlayerPositionAndLook;
import zedly.zbot.network.packet.serverbound.Packet0AUseEntity;
import zedly.zbot.network.packet.serverbound.Packet14EntityAction;
import zedly.zbot.network.packet.serverbound.Packet1AAnimation;
import zedly.zbot.network.packet.serverbound.Packet06EnchantItem;
import zedly.zbot.network.packet.serverbound.Packet07ClickWindow;
import zedly.zbot.network.packet.serverbound.Packet12PlayerAbilities;
import zedly.zbot.network.packet.serverbound.Packet0BKeepAlive;
import zedly.zbot.network.packet.serverbound.Packet01TabComplete;
import zedly.zbot.network.packet.serverbound.Packet15SteerVehicle;
import zedly.zbot.network.packet.serverbound.Packet08CloseWindow;
import zedly.zbot.network.packet.serverbound.Packet13PlayerDigging;
import zedly.zbot.network.packet.serverbound.Packet11SteerBoat;
import zedly.zbot.network.packet.serverbound.Packet02ChatMessage;
import zedly.zbot.network.packet.serverbound.Packet1DUseItem;
import zedly.zbot.network.packet.serverbound.Packet1CPlayerBlockPlacement;
import zedly.zbot.network.packet.serverbound.Packet18CreativeInventoryAction;
import zedly.zbot.network.packet.serverbound.Packet19UpdateSign;
import zedly.zbot.network.packet.serverbound.Packet09PluginMessage;
import zedly.zbot.network.packet.serverbound.Packet0EPlayerLook;
import zedly.zbot.network.packet.serverbound.Packet05ConfirmTransaction;
import zedly.zbot.network.packet.serverbound.Packet10VehicleMove;
import zedly.zbot.network.packet.serverbound.Packet04ClientSettings;
import java.io.IOException;
import java.util.HashMap;
import zedly.zbot.network.packet.clientbound.*;
import zedly.zbot.network.packet.serverbound.ServerBoundPacket;

public class Packet {

    private static final HashMap<Integer, Class> serverBoundPackets;
    private static final HashMap<Integer, Class> clientBoundPackets;

    public static ServerBoundPacket getInstanceServerBound(int op) throws InstantiationException, IllegalAccessException {
        return (ServerBoundPacket) serverBoundPackets.get(op).newInstance();
    }

    public static ClientBoundPacket getInstanceClientBound(int op) throws InstantiationException, IllegalAccessException {
        return (ClientBoundPacket) clientBoundPackets.get(op).newInstance();
    }

    public static ServerBoundPacket getInstanceServerBound(int op, StreamState state) throws InstantiationException, IllegalAccessException {
        if (null != state) switch (state) {
            case PLAY:
                return (ServerBoundPacket) serverBoundPackets.get(op).newInstance();
            case LOGIN:
                switch (op) {
                    case 0:
                        return new Packet00LoginStart();
                    case 1:
                        return new Packet01EncryptionResponse();
                }
            case STATUS:
                switch (op) {
                    case 0:
                        return new Packet00Request();
                    case 1:
                        return new Packet01Ping();
                }
            default:
                break;
        }
        return null;
    }

    public static ClientBoundPacket getInstanceClientBound(int op, StreamState state) throws InstantiationException, IllegalAccessException {
        if (null != state) switch (state) {
            case PLAY:
                return (ClientBoundPacket) clientBoundPackets.get(op).newInstance();
            case LOGIN:
                switch (op) {
                    case 0:
                        return new Packet00Disconnect();
                    case 1:
                        return new Packet01EncryptionRequest();
                    case 2:
                        return new Packet02LoginSuccess();
                }
            case STATUS:
                switch (op) {
                    case 0:
                        return new Packet00Response();
                    case 1:
                        return new Packet01Ping();
                }
            default:
                break;
        }
        return null;
    }

    // Status, Serverbound
    
    public static class Packet00Request implements ServerBoundPacket {

        @Override
        public int opCode() {
            return 0x00;
        }

        public Packet00Request() {
        }

        @Override
        public void writePacket(ExtendedDataOutputStream dos) throws IOException {
            //This packet has no payload
        }
    }

    public static class Packet01Ping implements ServerBoundPacket, ClientBoundPacket {

        long time;

        public Packet01Ping() {
        }

        public Packet01Ping(long time) {
            this.time = time;
        }

        @Override
        public int opCode() {
            return 0x01;
        }

        @Override
        public void writePacket(ExtendedDataOutputStream dos) throws IOException {
            dos.writeLong(time);
        }

        @Override
        public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
            time = dis.readLong();
        }

        public long getTime() {
            return time;
        }
    }

    // Status, Clientbound
    public static class Packet00Response implements ClientBoundPacket {

        private String JSONResponse;

        @Override
        public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
            JSONResponse = dis.readString();
        }

        public String getJSONResponse() {
            return JSONResponse;
        }
    }

    //Login, Client
    public static class Packet00Disconnect implements ClientBoundPacket {

        private String JSONData;

        @Override
        public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
            JSONData = dis.readString();
        }

        public String getJSONData() {
            return JSONData;
        }
    }

    public static class Packet01EncryptionRequest implements ClientBoundPacket {

        private String serverID;
        private byte[] publicKey;
        private byte[] verifyToken;

        @Override
        public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
            serverID = dis.readString();
            int len = dis.readVarInt();
            publicKey = new byte[len];
            dis.readFully(publicKey);
            len = dis.readVarInt();
            verifyToken = new byte[len];
            dis.readFully(verifyToken);
        }

        public String getServerID() {
            return serverID;
        }

        public byte[] getPublicKey() {
            return publicKey;
        }

        public byte[] getVerifyToken() {
            return verifyToken;
        }
    }

    public static class Packet02LoginSuccess implements ClientBoundPacket {

        private String uuid;
        private String username;

        @Override
        public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
            uuid = dis.readString();
            username = dis.readString();
        }

        public String getUuid() {
            return uuid;
        }

        public String getUsername() {
            return username;
        }
    }

    public static class Packet03SetCompression implements ClientBoundPacket {

        private int threshold;

        public int opCode() {
            return 0x03;
        }

        public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {
            threshold = dis.readVarInt();
        }

        public int getThreshold() {
            return threshold;
        }
    }

    //Login, Server
    public static class Packet00LoginStart implements ServerBoundPacket {

        String name;

        public Packet00LoginStart() {
        }

        public Packet00LoginStart(String name) {
            this.name = name;
        }

        @Override
        public int opCode() {
            return 0x00;
        }

        @Override
        public void writePacket(ExtendedDataOutputStream dos) throws IOException {
            dos.writeString(name);
        }
    }

    public static class Packet01EncryptionResponse implements ServerBoundPacket {

        byte[] sharedSecret;
        byte[] verifyToken;

        public Packet01EncryptionResponse() {
        }

        public Packet01EncryptionResponse(byte[] sharedSecret, byte[] verifyToken) {
            this.sharedSecret = sharedSecret;
            this.verifyToken = verifyToken;
        }

        @Override
        public int opCode() {
            return 0x01;
        }

        @Override
        public void writePacket(ExtendedDataOutputStream dos) throws IOException {
            dos.writeVarInt(sharedSecret.length);
            for (int x = 0; x < sharedSecret.length; x++) {
                dos.write(sharedSecret[x]);
            }
            dos.writeVarInt(verifyToken.length);
            for (int x = 0; x < verifyToken.length; x++) {
                dos.write(verifyToken[x]);
            }
        }
    }

    //Handshaking
    public static class Packet00Handshake implements ServerBoundPacket {

        int protocolVersion;
        String serverIP;
        int serverPort;
        int state;

        public Packet00Handshake(int protocolVersion, String serverIP, int serverPort, int state) {
            this.protocolVersion = protocolVersion;
            this.serverIP = serverIP;
            this.serverPort = serverPort;
            this.state = state;
        }

        @Override
        public int opCode() {
            return 0x00;
        }

        @Override
        public void writePacket(ExtendedDataOutputStream dos) throws IOException {
            dos.writeVarInt(protocolVersion);
            dos.writeString(serverIP);
            dos.writeShort(serverPort);
            dos.writeVarInt(state);
        }
    }

    static {
        serverBoundPackets = new HashMap<>();
        clientBoundPackets = new HashMap<>();
        //Play, Client

        clientBoundPackets.put((0x00), Packet00SpawnObject.class);
        clientBoundPackets.put((0x01), Packet01SpawnExperienceOrb.class);
        clientBoundPackets.put((0x02), Packet02SpawnGlobalEntity.class);
        clientBoundPackets.put((0x03), Packet03SpawnMob.class);
        clientBoundPackets.put((0x04), Packet04SpawnPainting.class);
        clientBoundPackets.put((0x05), Packet05SpawnPlayer.class);
        clientBoundPackets.put((0x06), Packet06Animation.class);
        clientBoundPackets.put((0x07), Packet07Statistics.class);
        clientBoundPackets.put((0x08), Packet08BlockBreakAnimation.class);
        clientBoundPackets.put((0x09), Packet09UpdateBlockEntity.class);
        clientBoundPackets.put((0x0A), Packet0ABlockAction.class);
        clientBoundPackets.put((0x0B), Packet0BBlockChange.class);
        clientBoundPackets.put((0x0C), Packet0CBossBar.class);
        clientBoundPackets.put((0x0D), Packet0DServerDifficulty.class);
        clientBoundPackets.put((0x0E), Packet0ETabComplete.class);
        clientBoundPackets.put((0x0F), Packet0FChatMessage.class);
        clientBoundPackets.put((0x10), Packet10MultiBlockChange.class);
        clientBoundPackets.put((0x11), Packet11ConfirmTransaction.class);
        clientBoundPackets.put((0x12), Packet12CloseWindow.class);
        clientBoundPackets.put((0x13), Packet13OpenWindow.class);
        clientBoundPackets.put((0x14), Packet14WindowItems.class);
        clientBoundPackets.put((0x15), Packet15WindowProperty.class);
        clientBoundPackets.put((0x16), Packet16SetSlot.class);
        clientBoundPackets.put((0x17), Packet17SetCooldown.class);
        clientBoundPackets.put((0x18), Packet18PluginMessage.class);
        clientBoundPackets.put((0x19), Packet19NamedSoundEffect.class);
        clientBoundPackets.put((0x1A), Packet1ADisconnect.class);
        clientBoundPackets.put((0x1B), Packet1BEntityStatus.class);
        clientBoundPackets.put((0x1C), Packet1CExplosion.class);
        clientBoundPackets.put((0x1D), Packet1DUnloadChunk.class);
        clientBoundPackets.put((0x1E), Packet1EChangeGameState.class);
        clientBoundPackets.put((0x1F), Packet1FKeepAlive.class);
        clientBoundPackets.put((0x20), Packet20ChunkData.class);
        clientBoundPackets.put((0x21), Packet21Effect.class);
        clientBoundPackets.put((0x22), Packet22Particle.class);
        clientBoundPackets.put((0x23), Packet23JoinGame.class);
        clientBoundPackets.put((0x24), Packet24Map.class);
        clientBoundPackets.put((0x25), Packet25EntityRelativeMove.class);
        clientBoundPackets.put((0x26), Packet26EntityLookandRelativeMove.class);
        clientBoundPackets.put((0x27), Packet27EntityLook.class);
        clientBoundPackets.put((0x28), Packet28Entity.class);
        clientBoundPackets.put((0x29), Packet29VehicleMove.class);
        clientBoundPackets.put((0x2A), Packet2AOpenSignEditor.class);
        clientBoundPackets.put((0x2B), Packet2BPlayerAbilities.class);
        clientBoundPackets.put((0x2C), Packet2CCombatEvent.class);
        clientBoundPackets.put((0x2D), Packet2DPlayerListItem.class);
        clientBoundPackets.put((0x2E), Packet2EPlayerPositionAndLook.class);
        clientBoundPackets.put((0x2F), Packet2FUseBed.class);
        clientBoundPackets.put((0x30), Packet30DestroyEntities.class);
        clientBoundPackets.put((0x31), Packet31RemoveEntityEffect.class);
        clientBoundPackets.put((0x32), Packet32ResourcePackSend.class);
        clientBoundPackets.put((0x33), Packet33Respawn.class);
        clientBoundPackets.put((0x34), Packet34EntityHeadLook.class);
        clientBoundPackets.put((0x35), Packet35WorldBorder.class);
        clientBoundPackets.put((0x36), Packet36Camera.class);
        clientBoundPackets.put((0x37), Packet37HeldItemChange.class);
        clientBoundPackets.put((0x38), Packet38DisplayScoreboard.class);
        clientBoundPackets.put((0x39), Packet39EntityMetadata.class);
        clientBoundPackets.put((0x3A), Packet3AAttachEntity.class);
        clientBoundPackets.put((0x3B), Packet3BEntityVelocity.class);
        clientBoundPackets.put((0x3C), Packet3CEntityEquipment.class);
        clientBoundPackets.put((0x3D), Packet3DSetExperience.class);
        clientBoundPackets.put((0x3E), Packet3EUpdateHealth.class);
        clientBoundPackets.put((0x3F), Packet3FScoreboardObjective.class);
        clientBoundPackets.put((0x40), Packet40SetPassengers.class);
        clientBoundPackets.put((0x41), Packet41Teams.class);
        clientBoundPackets.put((0x42), Packet42UpdateScore.class);
        clientBoundPackets.put((0x43), Packet43SpawnPosition.class);
        clientBoundPackets.put((0x44), Packet44TimeUpdate.class);
        clientBoundPackets.put((0x45), Packet45Title.class);
        //clientBoundPackets.put((0x46), Packet46UpdateSign.class);
        clientBoundPackets.put((0x46), Packet46SoundEffect.class);
        clientBoundPackets.put((0x47), Packet47PlayerListHeaderFooter.class);
        clientBoundPackets.put((0x48), Packet48CollectItem.class);
        clientBoundPackets.put((0x49), Packet49EntityTeleport.class);
        clientBoundPackets.put((0x4A), Packet4AEntityProperties.class);
        clientBoundPackets.put((0x4B), Packet4BEntityEffect.class);

        //Play, Server
        
        serverBoundPackets.put((0x01), Packet01TabComplete.class);
        serverBoundPackets.put((0x02), Packet02ChatMessage.class);
        serverBoundPackets.put((0x03), Packet03ClientStatus.class);
        serverBoundPackets.put((0x04), Packet04ClientSettings.class);
        serverBoundPackets.put((0x05), Packet05ConfirmTransaction.class);
        serverBoundPackets.put((0x06), Packet06EnchantItem.class);
        serverBoundPackets.put((0x07), Packet07ClickWindow.class);
        serverBoundPackets.put((0x08), Packet08CloseWindow.class);
        serverBoundPackets.put((0x09), Packet09PluginMessage.class);
        serverBoundPackets.put((0x0A), Packet0AUseEntity.class);
        serverBoundPackets.put((0x0B), Packet0BKeepAlive.class);
        serverBoundPackets.put((0x0C), Packet0CPlayerPosition.class);
        serverBoundPackets.put((0x0D), Packet0DPlayerPositionAndLook.class);
        serverBoundPackets.put((0x0E), Packet0EPlayerLook.class);
        serverBoundPackets.put((0x0F), Packet0FPlayer.class);
        serverBoundPackets.put((0x10), Packet10VehicleMove.class);
        serverBoundPackets.put((0x11), Packet11SteerBoat.class);
        serverBoundPackets.put((0x12), Packet12PlayerAbilities.class);
        serverBoundPackets.put((0x13), Packet13PlayerDigging.class);
        serverBoundPackets.put((0x14), Packet14EntityAction.class);
        serverBoundPackets.put((0x15), Packet15SteerVehicle.class);
        serverBoundPackets.put((0x16), Packet16ResourcePackStatus.class);
        serverBoundPackets.put((0x17), Packet17HeldItemChange.class);
        serverBoundPackets.put((0x18), Packet18CreativeInventoryAction.class);
        serverBoundPackets.put((0x19), Packet19UpdateSign.class);
        serverBoundPackets.put((0x1A), Packet1AAnimation.class);
        serverBoundPackets.put((0x1B), Packet1BSpectate.class);
        serverBoundPackets.put((0x1C), Packet1CPlayerBlockPlacement.class);
        serverBoundPackets.put((0x1D), Packet1DUseItem.class);
        
        // Status, Clientbound
        /*clientBoundPackets.put((0x00), Packet00Response.class);

         clientBoundPackets.put((0x01), Packet01Ping.class);

         // Status, Serverbound
         serverBoundPackets.put((0x00), Packet00Request.class);

         serverBoundPackets.put((0x01), Packet01Ping.class);

         //Login, Client
         clientBoundPackets.put((0x00), Packet00Disconnect.class);

         clientBoundPackets.put((0x01), Packet01EncryptionRequest.class);

         clientBoundPackets.put((0x02), Packet02LoginSuccess.class);

         //Login, Server
         serverBoundPackets.put((0x00), Packet00LoginStart.class);

         serverBoundPackets.put((0x01), Packet01EncryptionResponse.class);*/
    }
}
