package zedly.zbot.network;

import java.io.ByteArrayInputStream;
import zedly.zbot.network.packet.serverbound.Packet01EncryptionResponse;
import zedly.zbot.network.packet.clientbound.Packet00Response;
import zedly.zbot.network.packet.clientbound.Packet01Ping;
import zedly.zbot.network.packet.clientbound.Packet02LoginSuccess;
import zedly.zbot.network.packet.clientbound.Packet01EncryptionRequest;
import zedly.zbot.network.packet.serverbound.Packet00LoginStart;
import zedly.zbot.network.packet.serverbound.Packet00Disconnect;
import zedly.zbot.network.packet.serverbound.Packet00Request;
import zedly.zbot.network.packet.clientbound.*;
import zedly.zbot.network.packet.serverbound.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import zedly.zbot.Util;

public final class PacketInputStream extends ExtendedDataInputStream {

    private static final HashMap<Integer, Class<? extends ServerBoundPacket>> STATUS_OUT;
    private static final HashMap<Integer, Class<? extends ClientBoundPacket>> STATUS_IN;

    private static final HashMap<Integer, Class<? extends ServerBoundPacket>> LOGIN_OUT;
    private static final HashMap<Integer, Class<? extends ClientBoundPacket>> LOGIN_IN;

    private static final HashMap<Integer, Class<? extends ServerBoundPacket>> PLAY_OUT;
    private static final HashMap<Integer, Class<? extends ClientBoundPacket>> PLAY_IN;

    private final Inflater inflater = new Inflater();
    private final int[] lastOps = new int[16];

    private boolean compressionEnabled = false;
    private int lastOpPtr = 0;
    private StreamState state;
    private HashMap<Integer, Class<? extends ClientBoundPacket>> mapIn;
    //private HashMap<Integer, Class<? extends ServerBoundPacket>> mapOut;

    private ClientBoundPacket newPacketForId(int op) throws InstantiationException, IllegalAccessException {
        Class<? extends ClientBoundPacket> clazz = mapIn.get(op);
        if (clazz == null) {
            throw new IllegalArgumentException("Unknown protocol id " + op);
        }
        return clazz.newInstance();
    }

    public void setState(StreamState state) {
        this.state = state;
        switch (state) {
            case STATUS:
                mapIn = STATUS_IN;
                //mapOut = STATUS_OUT;
                break;
            case LOGIN:
                mapIn = LOGIN_IN;
                //mapOut = LOGIN_OUT;
                break;
            case PLAY:
                mapIn = PLAY_IN;
                //mapOut = PLAY_OUT;
                break;
        }
    }

    public ClientBoundPacket getInstanceClientBound(int op, StreamState state) throws InstantiationException, IllegalAccessException {
        Class<? extends ClientBoundPacket> clazz = mapIn.get(op);
        if (clazz == null) {
            throw new IllegalArgumentException("Unknown protocol id " + op);
        }
        return clazz.newInstance();
    }

    public PacketInputStream(InputStream is) {
        super(is);
    }

    public PacketInputStream(InputStream is, StreamState state) {
        super(is);
        this.state = state;
        setState(state);
    }

    public ClientBoundPacket readPacket() throws IOException, InstantiationException, DataFormatException, IllegalAccessException {
        int op;
        ClientBoundPacket p;
        int packetLength = readVarInt();
        //System.out.println("Play Debug: Len " + len);
        if (compressionEnabled) {
            int dataLength = readVarInt();
            //System.out.println("Play Debug: CLen " + clen);
            if (dataLength != 0) {
                //System.out.println("Compressed Debug: Len " + len + " clen " + clen);
                byte[] compressedData = new byte[packetLength - Util.getVarIntLength(dataLength)];
                byte[] data = new byte[dataLength];
                readFully(compressedData);
                inflater.setInput(compressedData);
                inflater.inflate(data);
                long actualLength = inflater.getBytesWritten();
                if(actualLength != dataLength) {
                    System.err.println("Compressed packet claiming length " + dataLength + ", actual len " + actualLength);
                }
                
                inflater.reset();
                ByteArrayInputStream bbis = new ByteArrayInputStream(data);
                ExtendedDataInputStream bis = new ExtendedDataInputStream(bbis);

                op = bis.readVarInt();
                lastOps[(lastOpPtr++) % 16] = op;
                //System.out.println("Play Debug: Op " + Integer.toHexString(op));
                p = newPacketForId(op);
                p.readPacket(bis, (int) actualLength - 1);
                if (bbis.available() != 0) {
                    System.err.println("Dropped " + bbis.available() + " bytes in compressed packet " + Integer.toHexString(op) + " of size " + dataLength + "!");
                }
            } else {
                op = readVarInt();
                lastOps[(lastOpPtr++) % 16] = op;
                //System.out.println("Play Debug: Op " + Integer.toHexString(op));
                p = newPacketForId(op);
                p.readPacket(this, packetLength - 2);
            }
        } else {
            op = readVarInt();
            lastOps[(lastOpPtr++) % 16] = op;
            //System.out.println("Play Debug: Op " + Integer.toHexString(op));
            p = newPacketForId(op);
            p.readPacket(this, packetLength - 1);
        }

        checkStateChange(p);

        return p;
    }

    private void checkStateChange(ClientBoundPacket p) {
        if (p instanceof Packet03SetCompression) {
            compressionEnabled = true;
            System.out.println("Set Compression Threshold " + ((Packet03SetCompression) p).getThreshold());
        } else if (p instanceof Packet02LoginSuccess) {
            System.out.println("DIS joined");
            setState(StreamState.PLAY);
        }
    }

    public int getLastOp() {
        return lastOps[lastOpPtr % 16];
    }

    public int[] getLastOps() {
        return lastOps;
    }

    public int getNumPacketsReceived() {
        return lastOpPtr;
    }

    public void printCrashInfo() {
        System.out.println("Crash Info:");
        System.out.println("Died after " + lastOpPtr + " packets");
        System.out.print("Last received Packets: ");
        for (int i = lastOpPtr; i < lastOpPtr + 16; i++) {
            System.out.print("0x" + Integer.toHexString(lastOps[i % 16]) + " ");
        }
        System.out.print("\r\n");
    }

    static {
        STATUS_OUT = new HashMap<>();
        STATUS_IN = new HashMap<>();
        LOGIN_OUT = new HashMap<>();
        LOGIN_IN = new HashMap<>();
        PLAY_OUT = new HashMap<>();
        PLAY_IN = new HashMap<>();

        // Status, Clientbound
        STATUS_IN.put((0x00), Packet00Response.class);
        STATUS_IN.put((0x01), Packet01Ping.class);

        // Status, Serverbound
        STATUS_OUT.put((0x00), Packet00Request.class);
        STATUS_OUT.put((0x01), Packet01Ping.class);

        //Login, Client
        LOGIN_IN.put((0x00), Packet00Disconnect.class);
        LOGIN_IN.put((0x01), Packet01EncryptionRequest.class);
        LOGIN_IN.put((0x02), Packet02LoginSuccess.class);
        LOGIN_IN.put((0x03), Packet03SetCompression.class);
        LOGIN_IN.put((0x18), Packet18PluginMessage.class);

        //Login, Server
        LOGIN_OUT.put((0x00), Packet00LoginStart.class);
        LOGIN_OUT.put((0x01), Packet01EncryptionResponse.class);

        //Play, Client
        PLAY_IN.put(0x00, Packet00SpawnObject.class);
        PLAY_IN.put(0x01, Packet01SpawnExperienceOrb.class);
        PLAY_IN.put(0x02, Packet02SpawnGlobalEntity.class);
        PLAY_IN.put(0x03, Packet03SpawnMob.class);
        PLAY_IN.put(0x04, Packet04SpawnPainting.class);
        PLAY_IN.put(0x05, Packet05SpawnPlayer.class);
        PLAY_IN.put(0x06, Packet06Animation.class);
        PLAY_IN.put(0x07, Packet07Statistics.class);
        PLAY_IN.put(0x08, Packet08BlockBreakAnimation.class);
        PLAY_IN.put(0x09, Packet09UpdateBlockEntity.class);
        PLAY_IN.put(0x0A, Packet0ABlockAction.class);
        PLAY_IN.put(0x0B, Packet0BBlockChange.class);
        PLAY_IN.put(0x0C, Packet0CBossBar.class);
        PLAY_IN.put(0x0D, Packet0DServerDifficulty.class);
        PLAY_IN.put(0x0E, Packet0EChatMessage.class);
        PLAY_IN.put(0x0F, Packet0FMultiBlockChange.class);
        PLAY_IN.put(0x10, Packet10TabComplete.class);
        PLAY_IN.put(0x11, Packet11DeclareCommands.class);
        PLAY_IN.put(0x12, Packet12ConfirmTransaction.class);
        PLAY_IN.put(0x13, Packet13CloseWindow.class);
        PLAY_IN.put(0x14, Packet14WindowItems.class);
        PLAY_IN.put(0x15, Packet15WindowProperty.class);
        PLAY_IN.put(0x16, Packet16SetSlot.class);
        PLAY_IN.put(0x17, Packet17SetCooldown.class);
        PLAY_IN.put(0x18, Packet18PluginMessage.class);
        PLAY_IN.put(0x19, Packet19NamedSoundEffect.class);
        PLAY_IN.put(0x1A, Packet1ADisconnect.class);
        PLAY_IN.put(0x1B, Packet1BEntityStatus.class);
        PLAY_IN.put(0x1C, Packet1CExplosion.class);
        PLAY_IN.put(0x1D, Packet1DUnloadChunk.class);
        PLAY_IN.put(0x1E, Packet1EChangeGameState.class);
        PLAY_IN.put(0x1F, Packet1FOpenHorseWindow.class);
        PLAY_IN.put(0x20, Packet20KeepAlive.class);
        PLAY_IN.put(0x21, Packet21ChunkData.class);
        PLAY_IN.put(0x22, Packet22Effect.class);
        PLAY_IN.put(0x23, Packet23Particle.class);
        PLAY_IN.put(0x24, Packet24UpdateLight.class);
        PLAY_IN.put(0x25, Packet25JoinGame.class);
        PLAY_IN.put(0x26, Packet26MapData.class);
        PLAY_IN.put(0x27, Packet27TradeList.class);
        PLAY_IN.put(0x28, Packet28EntityRelativeMove.class);
        PLAY_IN.put(0x29, Packet29EntityLookAndRelativeMove.class);
        PLAY_IN.put(0x2A, Packet2AEntityLook.class);
        PLAY_IN.put(0x2B, Packet2BEntity.class);
        PLAY_IN.put(0x2C, Packet2CVehicleMove.class);
        PLAY_IN.put(0x2D, Packet2DOpenBook.class);
        PLAY_IN.put(0x2E, Packet2EOpenWindow.class);
        PLAY_IN.put(0x2F, Packet2FOpenSignEditor.class);
        PLAY_IN.put(0x30, Packet30CraftRecipeResponse.class);
        PLAY_IN.put(0x31, Packet31PlayerAbilities.class);
        PLAY_IN.put(0x32, Packet32CombatEvent.class);
        PLAY_IN.put(0x33, Packet33PlayerInfo.class);
        PLAY_IN.put(0x34, Packet34FacePlayer.class);
        PLAY_IN.put(0x35, Packet35PlayerPositionAndLook.class);
        PLAY_IN.put(0x36, Packet36UnlockRecipes.class);
        PLAY_IN.put(0x37, Packet37DestroyEntities.class);
        PLAY_IN.put(0x38, Packet38RemoveEntityEffect.class);
        PLAY_IN.put(0x39, Packet39ResourcePackSend.class);
        PLAY_IN.put(0x3A, Packet3ARespawn.class);
        PLAY_IN.put(0x3B, Packet3BEntityHeadLook.class);
        PLAY_IN.put(0x3C, Packet3CSelectAdvancementTab.class);
        PLAY_IN.put(0x3D, Packet3DWorldBorder.class);
        PLAY_IN.put(0x3E, Packet3ECamera.class);
        PLAY_IN.put(0x3F, Packet3FHeldItemChange.class);
        PLAY_IN.put(0x40, Packet40UpdateViewPosition.class);
        PLAY_IN.put(0x41, Packet41UpdateViewDistance.class);
        PLAY_IN.put(0x42, Packet42DisplayScoreboard.class);
        PLAY_IN.put(0x43, Packet43EntityMetadata.class);
        PLAY_IN.put(0x44, Packet44AttachEntity.class);
        PLAY_IN.put(0x45, Packet45EntityVelocity.class);
        PLAY_IN.put(0x46, Packet46EntityEquipment.class);
        PLAY_IN.put(0x47, Packet47SetExperience.class);
        PLAY_IN.put(0x48, Packet48UpdateHealth.class);
        PLAY_IN.put(0x49, Packet49ScoreboardObjective.class);
        PLAY_IN.put(0x4A, Packet4ASetPassengers.class);
        PLAY_IN.put(0x4B, Packet4BTeams.class);
        PLAY_IN.put(0x4C, Packet4CUpdateScore.class);
        PLAY_IN.put(0x4D, Packet4DSpawnPosition.class);
        PLAY_IN.put(0x4E, Packet4ETimeUpdate.class);
        PLAY_IN.put(0x4F, Packet4FTitle.class);
        PLAY_IN.put(0x50, Packet50EntitySoundEffect.class);
        PLAY_IN.put(0x51, Packet51SoundEffect.class);
        PLAY_IN.put(0x52, Packet52StopSound.class);
        PLAY_IN.put(0x53, Packet53PlayerListHeaderAndFooter.class);
        PLAY_IN.put(0x54, Packet54NBTQueryResponse.class);
        PLAY_IN.put(0x55, Packet55CollectItem.class);
        PLAY_IN.put(0x56, Packet56EntityTeleport.class);
        PLAY_IN.put(0x57, Packet57Advancements.class);
        PLAY_IN.put(0x58, Packet58EntityProperties.class);
        PLAY_IN.put(0x59, Packet59EntityEffect.class);
        PLAY_IN.put(0x5A, Packet5ADeclareRecipes.class);
        PLAY_IN.put(0x5B, Packet5BTags.class);
        PLAY_IN.put(0x5C, Packet5CAcknowledgePlayerDigging.class);

        PLAY_OUT.put(0x00, Packet00TeleportConfirm.class);
        PLAY_OUT.put(0x01, Packet01QueryBlockNBT.class);
        PLAY_OUT.put(0x02, Packet02SetDifficulty.class);
        PLAY_OUT.put(0x03, Packet03ChatMessage.class);
        PLAY_OUT.put(0x04, Packet04ClientStatus.class);
        PLAY_OUT.put(0x05, Packet05ClientSettings.class);
        PLAY_OUT.put(0x06, Packet06TabComplete.class);
        PLAY_OUT.put(0x07, Packet07ConfirmTransaction.class);
        PLAY_OUT.put(0x07, Packet07ClickWindowButton.class);
        PLAY_OUT.put(0x09, Packet09ClickWindow.class);
        PLAY_OUT.put(0x0A, Packet0ACloseWindow.class);
        PLAY_OUT.put(0x0C, Packet0CEditBook.class);
        PLAY_OUT.put(0x0D, Packet0DQueryEntityNBT.class);
        PLAY_OUT.put(0x0F, Packet0FKeepAlive.class);
        PLAY_OUT.put(0x10, Packet10LockDifficulty.class);
        PLAY_OUT.put(0x11, Packet11PlayerPosition.class);
        PLAY_OUT.put(0x12, Packet12PlayerPositionAndLook.class);
        PLAY_OUT.put(0x13, Packet13PlayerLook.class);
        PLAY_OUT.put(0x14, Packet14Player.class);
        PLAY_OUT.put(0x15, Packet15VehicleMove.class);
        PLAY_OUT.put(0x16, Packet16SteerBoat.class);
        PLAY_OUT.put(0x17, Packet17PickItem.class);
        PLAY_OUT.put(0x18, Packet18CraftRecipeRequest.class);
        PLAY_OUT.put(0x19, Packet19PlayerAbilities.class);
        PLAY_OUT.put(0x1A, Packet1APlayerDigging.class);
        PLAY_OUT.put(0x1B, Packet1BEntityAction.class);
        PLAY_OUT.put(0x1C, Packet1CSteerVehicle.class);
        PLAY_OUT.put(0x1E, Packet1ENameItem.class);
        PLAY_OUT.put(0x1F, Packet1FResourcePackStatus.class);
        PLAY_OUT.put(0x21, Packet21SelectTrade.class);
        PLAY_OUT.put(0x22, Packet22SetBeaconEffect.class);
        PLAY_OUT.put(0x23, Packet23HeldItemChange.class);
        PLAY_OUT.put(0x25, Packet25UpdateCommandBlockMinecart.class);
        PLAY_OUT.put(0x26, Packet26CreativeInventoryAction.class);
        PLAY_OUT.put(0x27, Packet27UpdateJigsawBlock.class);
        PLAY_OUT.put(0x29, Packet29UpdateSign.class);
        PLAY_OUT.put(0x2A, Packet2AAnimation.class);
        PLAY_OUT.put(0x2B, Packet2BSpectate.class);
        PLAY_OUT.put(0x2C, Packet2CPlayerBlockPlacement.class);
        PLAY_OUT.put(0x2D, Packet2DUseItem.class);
    }
}
