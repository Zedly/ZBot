package zedly.zbot.network;

import java.io.ByteArrayInputStream;
import zedly.zbot.network.packet.serverbound.Packet01EncryptionResponse;
import zedly.zbot.network.packet.clientbound.Packet02LoginSuccess;
import zedly.zbot.network.packet.clientbound.Packet01EncryptionRequest;
import zedly.zbot.network.packet.serverbound.Packet00TeleportConfirm;
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
    private final int[] lastBytes = new int[2048];

    private boolean compressionEnabled = false;
    private int lastOpPtr = 0;
    private int lastBytePtr = 0;
    private StreamState state;
    private HashMap<Integer, Class<? extends ClientBoundPacket>> mapIn;
    //private HashMap<Integer, Class<? extends ServerBoundPacket>> mapOut;

    @Override
    public int read() throws IOException {
        int nextByte = super.read();
        lastBytes[lastBytePtr] = nextByte;
        lastBytePtr = (lastBytePtr + 1) % 2048;
        return nextByte;
    }

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
                if (actualLength != dataLength) {
                    System.err.println("Compressed packet claiming length " + dataLength + ", actual len " + actualLength);
                }

                inflater.reset();
                ByteArrayInputStream bbis = new ByteArrayInputStream(data);
                ExtendedDataInputStream bis = new ExtendedDataInputStream(bbis);

                op = bis.readVarInt();
                lastOps[(lastOpPtr++) % 16] = op;
                //System.out.println("Play Debug: Op " + Integer.toHexString(op));
                p = newPacketForId(op);
                p.readPacket(bis, (int) actualLength - 2);
                if (bbis.available() != 0) {
                    System.err.println("Dropped " + bbis.available() + " bytes in compressed packet " + Integer.toHexString(op) + " of size " + dataLength + "!");
                }
            } else {
                op = readVarInt();
                lastOps[(lastOpPtr++) % 16] = op;
                //System.out.println("Play Debug: Op " + Integer.toHexString(op));
                p = newPacketForId(op);
                p.readPacket(this, packetLength - 2);
                op = op;
            }
        } else {
            op = readVarInt();
            lastOps[(lastOpPtr++) % 16] = op;
            //System.out.println("Play Debug: Op " + Integer.toHexString(op));
            p = newPacketForId(op);
            p.readPacket(this, packetLength - 2);
            op = op;
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
        System.out.print("\nLast received Packets: ");
        for (int i = lastOpPtr; i < lastOpPtr + 16; i++) {
            System.out.print(Integer.toHexString(lastOps[i % 16]).toUpperCase() + " ");
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
        STATUS_IN.put((0x01), Packet01Pong.class);

        // Status, Serverbound
        STATUS_OUT.put((0x00), Packet00Request.class);
        STATUS_OUT.put((0x01), Packet01Ping.class);

        //Login, Client
        LOGIN_IN.put((0x00), Packet00Disconnect.class);
        LOGIN_IN.put((0x01), Packet01EncryptionRequest.class);
        LOGIN_IN.put((0x02), Packet02LoginSuccess.class);
        LOGIN_IN.put((0x03), Packet03SetCompression.class);
        LOGIN_IN.put((0x04), Packet04LoginPluginRequest.class);

        //Login, Server
        LOGIN_OUT.put((0x00), Packet00TeleportConfirm.class);
        LOGIN_OUT.put((0x01), Packet01EncryptionResponse.class);
        LOGIN_OUT.put((0x01), Packet02LoginPluginResponse.class);

        PLAY_IN.put(0x00, Packet00SpawnEntity.class);
        PLAY_IN.put(0x01, Packet01SpawnExperienceOrb.class);
        PLAY_IN.put(0x02, Packet02SpawnLivingEntity.class);
        PLAY_IN.put(0x03, Packet03SpawnPainting.class);
        PLAY_IN.put(0x04, Packet04SpawnPlayer.class);
        PLAY_IN.put(0x05, Packet05SculkVibrationSignal.class);
        PLAY_IN.put(0x06, Packet06EntityAnimation.class);
        PLAY_IN.put(0x07, Packet07Statistics.class);
        PLAY_IN.put(0x08, Packet08AcknowledgePlayerDigging.class);
        PLAY_IN.put(0x09, Packet09BlockBreakAnimation.class);
        PLAY_IN.put(0x0A, Packet0ABlockEntityData.class);
        PLAY_IN.put(0x0B, Packet0BBlockAction.class);
        PLAY_IN.put(0x0C, Packet0CBlockChange.class);
        PLAY_IN.put(0x0D, Packet0DBossBar.class);
        PLAY_IN.put(0x0E, Packet0EServerDifficulty.class);
        PLAY_IN.put(0x0F, Packet0FChatMessage.class);
        PLAY_IN.put(0x10, Packet10ClearTitles.class);
        PLAY_IN.put(0x11, Packet11TabComplete.class);
        PLAY_IN.put(0x12, Packet12DeclareCommands.class);
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
        PLAY_IN.put(0x20, Packet20InitializeWorldBorder.class);
        PLAY_IN.put(0x21, Packet21KeepAlive.class);
        PLAY_IN.put(0x22, Packet22ChunkData.class);
        PLAY_IN.put(0x23, Packet23Effect.class);
        PLAY_IN.put(0x24, Packet24Particle.class);
        PLAY_IN.put(0x25, Packet25UpdateLight.class);
        PLAY_IN.put(0x26, Packet26JoinGame.class);
        PLAY_IN.put(0x27, Packet27MapData.class);
        PLAY_IN.put(0x28, Packet28TradeList.class);
        PLAY_IN.put(0x29, Packet29EntityPosition.class);
        PLAY_IN.put(0x2A, Packet2AEntityPositionandRotation.class);
        PLAY_IN.put(0x2B, Packet2BEntityRotation.class);
        PLAY_IN.put(0x2C, Packet2CVehicleMove.class);
        PLAY_IN.put(0x2D, Packet2DOpenBook.class);
        PLAY_IN.put(0x2E, Packet2EOpenWindow.class);
        PLAY_IN.put(0x2F, Packet2FOpenSignEditor.class);
        PLAY_IN.put(0x30, Packet30Ping.class);
        PLAY_IN.put(0x31, Packet31CraftRecipeResponse.class);
        PLAY_IN.put(0x32, Packet32PlayerAbilities.class);
        PLAY_IN.put(0x33, Packet33EndCombatEvent.class);
        PLAY_IN.put(0x34, Packet34EnterCombatEvent.class);
        PLAY_IN.put(0x35, Packet35DeathCombatEvent.class);
        PLAY_IN.put(0x36, Packet36PlayerInfo.class);
        PLAY_IN.put(0x37, Packet37Face.class);
        PLAY_IN.put(0x38, Packet38PlayerPositionAndLook.class);
        PLAY_IN.put(0x39, Packet39UnlockRecipes.class);
        PLAY_IN.put(0x3A, Packet3ADestroyEntities.class);
        PLAY_IN.put(0x3B, Packet3BRemoveEntityEffect.class);
        PLAY_IN.put(0x3C, Packet3CResourcePackSend.class);
        PLAY_IN.put(0x3D, Packet3DRespawn.class);
        PLAY_IN.put(0x3E, Packet3EEntityHeadLook.class);
        PLAY_IN.put(0x3F, Packet3FMultiBlockChange.class);
        PLAY_IN.put(0x40, Packet40SelectAdvancementTab.class);
        PLAY_IN.put(0x41, Packet41ActionBar.class);
        PLAY_IN.put(0x42, Packet42WorldBorderCenter.class);
        PLAY_IN.put(0x43, Packet43WorldBorderLerpSize.class);
        PLAY_IN.put(0x44, Packet44WorldBorderSize.class);
        PLAY_IN.put(0x45, Packet45WorldBorderWarningDelay.class);
        PLAY_IN.put(0x46, Packet46WorldBorderWarningReach.class);
        PLAY_IN.put(0x47, Packet47Camera.class);
        PLAY_IN.put(0x48, Packet48HeldItemChange.class);
        PLAY_IN.put(0x49, Packet49UpdateViewPosition.class);
        PLAY_IN.put(0x4A, Packet4AUpdateViewDistance.class);
        PLAY_IN.put(0x4B, Packet4BSpawnPosition.class);
        PLAY_IN.put(0x4C, Packet4CDisplayScoreboard.class);
        PLAY_IN.put(0x4D, Packet4DEntityMetadata.class);
        PLAY_IN.put(0x4E, Packet4EAttachEntity.class);
        PLAY_IN.put(0x4F, Packet4FEntityVelocity.class);
        PLAY_IN.put(0x50, Packet50EntityEquipment.class);
        PLAY_IN.put(0x51, Packet51SetExperience.class);
        PLAY_IN.put(0x52, Packet52UpdateHealth.class);
        PLAY_IN.put(0x53, Packet53ScoreboardObjective.class);
        PLAY_IN.put(0x54, Packet54SetPassengers.class);
        PLAY_IN.put(0x55, Packet55Teams.class);
        PLAY_IN.put(0x56, Packet56UpdateScore.class);
        PLAY_IN.put(0x57, Packet57UpdateSimulationDistance.class);
        PLAY_IN.put(0x58, Packet58SetTitleSubTitle.class);
        PLAY_IN.put(0x59, Packet59TimeUpdate.class);
        PLAY_IN.put(0x5A, Packet5ASetTitleText.class);
        PLAY_IN.put(0x5B, Packet5BSetTitleTimes.class);
        PLAY_IN.put(0x5C, Packet5CEntitySoundEffect.class);
        PLAY_IN.put(0x5D, Packet5DSoundEffect.class);
        PLAY_IN.put(0x5E, Packet5EStopSound.class);
        PLAY_IN.put(0x5F, Packet5FPlayerListHeaderAndFooter.class);
        PLAY_IN.put(0x60, Packet60NBTQueryResponse.class);
        PLAY_IN.put(0x61, Packet61CollectItem.class);
        PLAY_IN.put(0x62, Packet62EntityTeleport.class);
        PLAY_IN.put(0x63, Packet63Advancements.class);
        PLAY_IN.put(0x64, Packet64EntityProperties.class);
        PLAY_IN.put(0x65, Packet65EntityEffect.class);
        PLAY_IN.put(0x66, Packet66DeclareRecipes.class);
        PLAY_IN.put(0x67, Packet67Tags.class);
        PLAY_OUT.put(0x00, Packet00TeleportConfirm.class);
        PLAY_OUT.put(0x01, Packet01QueryBlockNBT.class);
        PLAY_OUT.put(0x02, Packet02SetDifficulty.class);
        PLAY_OUT.put(0x03, Packet03ChatMessage.class);
        PLAY_OUT.put(0x04, Packet04ClientStatus.class);
        PLAY_OUT.put(0x05, Packet05ClientSettings.class);
        PLAY_OUT.put(0x06, Packet06TabComplete.class);
        PLAY_OUT.put(0x07, Packet07ClickWindowButton.class);
        PLAY_OUT.put(0x09, Packet09CloseWindow.class);
        PLAY_OUT.put(0x0B, Packet0BEditBook.class);
        PLAY_OUT.put(0x0C, Packet0CQueryEntityNBT.class);
        PLAY_OUT.put(0x0E, Packet0EGenerateStructure.class);
        PLAY_OUT.put(0x0F, Packet0FKeepAlive.class);
        PLAY_OUT.put(0x10, Packet10LockDifficulty.class);
        PLAY_OUT.put(0x11, Packet11PlayerPosition.class);
        PLAY_OUT.put(0x12, Packet12PlayerPositionAndRotation.class);
        PLAY_OUT.put(0x13, Packet13PlayerRotation.class);
        PLAY_OUT.put(0x14, Packet14PlayerMovement.class);
        PLAY_OUT.put(0x15, Packet15VehicleMove.class);
        PLAY_OUT.put(0x16, Packet16SteerBoat.class);
        PLAY_OUT.put(0x17, Packet17PickItem.class);
        PLAY_OUT.put(0x18, Packet18CraftRecipeRequest.class);
        PLAY_OUT.put(0x19, Packet19PlayerAbilities.class);
        PLAY_OUT.put(0x1A, Packet1APlayerDigging.class);
        PLAY_OUT.put(0x1B, Packet1BEntityAction.class);
        PLAY_OUT.put(0x1C, Packet1CSteerVehicle.class);
        PLAY_OUT.put(0x1D, Packet1DPong.class);
        PLAY_OUT.put(0x1E, Packet1ESetRecipeBookState.class);
        PLAY_OUT.put(0x1F, Packet1FSetDisplayedRecipe.class);
        PLAY_OUT.put(0x20, Packet20NameItem.class);
        PLAY_OUT.put(0x21, Packet21ResourcePackStatus.class);
        PLAY_OUT.put(0x23, Packet23SelectTrade.class);
        PLAY_OUT.put(0x24, Packet24SetBeaconEffect.class);
        PLAY_OUT.put(0x25, Packet25HeldItemChange.class);
        PLAY_OUT.put(0x27, Packet27UpdateCommandBlockMinecart.class);
        PLAY_OUT.put(0x28, Packet28CreativeInventoryAction.class);
        PLAY_OUT.put(0x29, Packet29UpdateJigsawBlock.class);
        PLAY_OUT.put(0x2B, Packet2BUpdateSign.class);
        PLAY_OUT.put(0x2C, Packet2CAnimation.class);
        PLAY_OUT.put(0x2D, Packet2DSpectate.class);
        PLAY_OUT.put(0x2E, Packet2EPlayerBlockPlacement.class);
        PLAY_OUT.put(0x2F, Packet2FUseItem.class);
    }
}
